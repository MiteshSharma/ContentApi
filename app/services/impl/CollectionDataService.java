package services.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import controllers.CoreController;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import exceptions.*;
import model.Collection;
import model.Environment;
import model.Field;
import model.Project;
import org.bson.Document;
import repository.*;
import services.ICollectionDataService;

import java.util.List;
import java.util.Map;

/**
 * Created by mitesh on 12/11/16.
 */
public class CollectionDataService implements ICollectionDataService {
    @Inject
    IProjectRepository projectRepository;
    @Inject
    ICollectionRepository collectionRepository;
    @Inject
    IEnvironmentRepository environmentRepository;
    @Inject
    IFieldRepository fieldRepository;
    @Inject
    ICollectionContentRepository collectionContentRepository;
    @Inject
    ICollectionContentHistoryRepository collectionContentHistoryRepository;
    @Inject
    IProjectDailyActivityRepository dailyActivityRepository;

    public String create(String userId, String projectId, String envId, String collectionId, JsonNode jsonNode) throws NoCollectionExistException, ContentValidationFailedException, NoProjectExistException, NoEnvironmentExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }
        Collection collection = this.collectionRepository.get(collectionId);
        if (collection == null) {
            throw new NoCollectionExistException();
        }
        Environment environment = this.environmentRepository.get(envId);
        if (environment == null) {
            throw new NoEnvironmentExistException();
        }
        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("projectId", project.getId().toString());
        eventParam.put("projectName", project.getName());
        eventParam.put("environmentName", environment.getName());
        eventParam.put("collectionName", collection.getName());

        List<Field> fields = this.fieldRepository.getAll(collectionId);
        for (Field field : fields) {
            JsonNode content = jsonNode.get(field.getName());
            if (field.getValidations() != null && !field.getValidations().validate(field, content)) {
                throw new ContentValidationFailedException();
            }
            if (field.getIsAnalytic() && content != null) {
                eventParam.put(field.getName(), content.asText().toString());
            }
        }
        ((ObjectNode)jsonNode).put("envId", envId);
        ((ObjectNode)jsonNode).put("userId", userId);
        if (jsonNode.get("contentState") == null) {
            ((ObjectNode) jsonNode).put("contentState", "PRODUCTION");
        }
        ((ObjectNode) jsonNode).put("contentVersion", 1);
        String jsonResponse = this.collectionContentRepository.create("CMS"+collection.getId().toString(), jsonNode);

        this.projectRepository.incrementTotalContentCount(project, 1);
        this.environmentRepository.incrementTotalContentCount(environment, 1);
        this.collectionRepository.incrementTotalContentCount(collection, 1);
        this.dailyActivityRepository.createUpdateDailyActivity(projectId, 0, 1, 0);

        EventDispatcher.dispatch(EventName.CONTENT_CREATE, eventParam);
        return jsonResponse;
    }

    @Override
    public String update(String userId, String projectId, String envId, String collectionId, String contentId, JsonNode jsonNode) throws NoCollectionExistException, ContentValidationFailedException, NoProjectExistException, NoEnvironmentExistException, CollectionHistorySaveException, NoContentExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }
        Collection collection = this.collectionRepository.get(collectionId);
        if (collection == null) {
            throw new NoCollectionExistException();
        }
        Environment environment = this.environmentRepository.get(envId);
        if (environment == null) {
            throw new NoEnvironmentExistException();
        }

        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("projectId", project.getId().toString());
        eventParam.put("projectName", project.getName());
        eventParam.put("environmentName", environment.getName());
        eventParam.put("collectionName", collection.getName());
        List<Field> fields = this.fieldRepository.getAll(collectionId);
        for (Field field : fields) {
            JsonNode content = jsonNode.get(field.getName());
            if (field.getValidations().validate(field, content)) {
                throw new ContentValidationFailedException();
            }
            if (field.getIsAnalytic() && content != null) {
                eventParam.put(field.getName(), content.asText().toString());
            }
        }
        if (jsonNode.get("envId") == null) {
            ((ObjectNode) jsonNode).put("envId", envId);
        }
        if (jsonNode.get("contentState") == null) {
            ((ObjectNode) jsonNode).put("contentState", "PRODUCTION");
        }
        ((ObjectNode)jsonNode).put("userId", userId);

        Document document = this.collectionContentRepository.getByIdWithDocument("CMS"+collection.getId().toString(), contentId);

        if (document != null) {
            throw new NoContentExistException();
        }
        int currentVersion = document.getInteger("contentVersion", 0);
        currentVersion += 1;
        ((ObjectNode) jsonNode).put("contentVersion", currentVersion);
        boolean isUpdateNeeded = false;
        for (Map.Entry<String, Object> entry : document.entrySet()) {
            if (entry.getKey().equals("envId") || entry.getKey().equals("userId") ||
                    entry.getKey().equals("collectionName") || entry.getKey().equals("contentState") ||
                    entry.getKey().equals("contentVersion")) {
                continue;
            } else {
                if (!entry.getValue().equals(jsonNode.get(entry.getKey()))) {
                    isUpdateNeeded = true;
                    break;
                }
            }
        }

        String jsonResponse = document.toJson();

        if (isUpdateNeeded) {
            if (!this.collectionContentHistoryRepository.create("CMS" + collection.getId().toString(), document)) {
                throw new CollectionHistorySaveException();
            }
            jsonResponse = this.collectionContentRepository.update("CMS" + collection.getId().toString(), contentId, jsonNode);
            EventDispatcher.dispatch(EventName.CONTENT_UPDATE, eventParam);
        }
        return jsonResponse;
    }

    @Override
    public String getByEnv(String projectId, String envId, String collectionId, String userId,
                           int offset, int limit, String sortBy, String orderBy, int isCountNeeded, String filters)
                            throws NoProjectExistException, NoCollectionExistException, NoEnvironmentExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }
        Collection collection = this.collectionRepository.get(collectionId);
        if (collection == null) {
            throw new NoCollectionExistException();
        }
        Environment environment = this.environmentRepository.get(envId);
        if (environment == null) {
            throw new NoEnvironmentExistException();
        }
        String responseJsonArr = this.collectionContentRepository.getByEnv("CMS"+collection.getId().toString(),
                envId, userId, offset, limit, sortBy, orderBy, isCountNeeded, filters);
        return responseJsonArr;
    }

    @Override
    public String getById(String projectId, String collectionId, String contentId) throws NoProjectExistException, NoCollectionExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }
        Collection collection = this.collectionRepository.get(collectionId);
        if (collection == null) {
            throw new NoCollectionExistException();
        }
        return this.collectionContentRepository.getById("CMS"+collection.getId().toString(), contentId);
    }

    @Override
    public boolean isAuthNeeded(String projectId, String collectionId) throws NoProjectExistException, NoCollectionExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }
        Collection collection = this.collectionRepository.get(collectionId);
        if (collection == null) {
            throw new NoCollectionExistException();
        }
        return collection.isAuthNeeded();
    }
}
