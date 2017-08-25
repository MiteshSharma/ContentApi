package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.ImplementedBy;
import exceptions.*;
import services.impl.CollectionDataService;

import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
@ImplementedBy(CollectionDataService.class)
public interface ICollectionDataService {
    public String create(String userId, String projectId, String envId, String collectionId,
                         JsonNode jsonNode)
            throws NoCollectionExistException, ContentValidationFailedException, NoProjectExistException, NoEnvironmentExistException;
    public String update(String userId, String projectId, String envId, String collectionId, String contentId,
                         JsonNode jsonNode)
            throws NoCollectionExistException, ContentValidationFailedException, NoProjectExistException, NoEnvironmentExistException, CollectionHistorySaveException, NoContentExistException;
    public String getByEnv(String projectId, String envId, String collectionId, String userId,
                           int offset, int limit, String sortBy, String orderBy, int isCountNeeded, String filters)
                            throws NoProjectExistException, NoCollectionExistException, NoEnvironmentExistException;
    public String getById(String projectId, String collectionId, String contentId)
                          throws NoProjectExistException, NoCollectionExistException;

    public boolean isAuthNeeded(String projectId, String collectionId) throws NoProjectExistException, NoCollectionExistException ;
}