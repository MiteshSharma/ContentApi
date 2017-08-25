package controllers.external;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import controllers.CoreController;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import exceptions.*;
import helpers.CollectionApiAuth;
import helpers.ExternalApiAuth;
import play.mvc.Http;
import play.mvc.Result;
import services.ICollectionDataService;

/**
 * Created by mitesh on 23/12/16.
 */
public class ExternalCollectionDataController extends CoreController {
    @Inject
    ICollectionDataService collectionDataService;

    @ExternalApiAuth
    public Result create(String collectionId) {
        String userId = (String) Http.Context.current().args.get("userId");
        String projectId = (String) Http.Context.current().args.get("projectId");
        String environmentId = (String) Http.Context.current().args.get("environmentId");
        JsonNode content = request().body().asJson();
        if (content.size() == 0) {
            return badRequest();
        }
        String responseJson;
        try {
            responseJson = collectionDataService.create(userId, projectId, environmentId, collectionId, content);
        } catch (NoCollectionExistException e) {
            return badRequest();
        } catch (ContentValidationFailedException e) {
            return badRequest();
        } catch (NoProjectExistException e) {
            return badRequest();
        } catch (NoEnvironmentExistException e) {
            return badRequest();
        }
        return ok(responseJson);
    }

    @ExternalApiAuth
    public Result update(String collectionId, String contentId) {
        String userId = (String) Http.Context.current().args.get("userId");
        String projectId = (String) Http.Context.current().args.get("projectId");
        String environmentId = (String) Http.Context.current().args.get("environmentId");
        JsonNode content = request().body().asJson();
        String responseJson;
        try {
            responseJson = collectionDataService.update(userId, projectId, environmentId, collectionId, contentId, content);
        } catch (NoCollectionExistException e) {
            return badRequest();
        } catch (ContentValidationFailedException e) {
            return badRequest();
        } catch (NoProjectExistException e) {
            return badRequest();
        } catch (CollectionHistorySaveException e) {
            return badRequest();
        } catch (NoContentExistException e) {
            return badRequest();
        } catch (NoEnvironmentExistException e) {
            return badRequest();
        }
        return ok(responseJson);
    }

    @ExternalApiAuth
    @CollectionApiAuth
    public Result getAll(String collectionId) {
        String projectId = (String) Http.Context.current().args.get("projectId");
        String environmentId = (String) Http.Context.current().args.get("environmentId");
        int offset = getQueryParamInt("offset", -1);
        int limit = getQueryParamInt("limit", -1);
        String orderBy = getQueryParam("orderBy");
        String sortBy = getQueryParam("sortBy");
        if (!isOrderByValid(orderBy)) {
            return badRequest("Order by parameter has wrong value.");
        }
        int isCountNeeded = getQueryParamInt("count", -1);
        String filters = getQueryParam("filters");
        String response;
        try {
            response = collectionDataService.getByEnv(projectId, environmentId, collectionId, null, offset, limit,
                    sortBy, orderBy, isCountNeeded, filters);
        } catch (NoProjectExistException e) {
            return badRequest();
        } catch (NoCollectionExistException e) {
            return badRequest();
        } catch (NoEnvironmentExistException e) {
            return badRequest();
        }
        return ok(response);
    }

    @ExternalApiAuth
    @CollectionApiAuth
    public Result get(String collectionId, String contentId) {
        String projectId = (String) Http.Context.current().args.get("projectId");
        String response;
        try {
            response = collectionDataService.getById(projectId, collectionId, contentId);
        } catch (NoProjectExistException e) {
            return badRequest();
        } catch (NoCollectionExistException e) {
            return badRequest();
        }
        return ok(response);
    }

    private boolean isOrderByValid(String orderBy) {
        if ((orderBy != null) && !(orderBy.equals("DESC") || orderBy.equals("ASC"))) {
            return false;
        }
        return true;
    }
}
