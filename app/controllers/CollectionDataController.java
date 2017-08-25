package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import exceptions.*;
import helpers.ApiAuth;
import helpers.CollectionApiAuth;
import model.User;
import play.mvc.Http;
import play.mvc.Result;
import pojo.Scope;
import services.ICollectionDataService;

/**
 * Created by mitesh on 12/11/16.
 */
public class CollectionDataController extends CoreController {
    @Inject
    ICollectionDataService collectionDataService;

    @ApiAuth(scope = Scope.ContentWrite)
    public Result create(String projectId, String collectionId, String envId) {
        String userId = (String) Http.Context.current().args.get("userId");
        JsonNode content = request().body().asJson();
        if (content.size() == 0) {
            return badRequest();
        }
        String responseJson;
        try {
            responseJson = collectionDataService.create(userId, projectId, envId, collectionId, content);
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

    @ApiAuth(scope = Scope.ContentWrite)
    public Result update(String projectId, String collectionId, String envId, String contentId) {
        String userId = (String) Http.Context.current().args.get("userId");
        JsonNode content = request().body().asJson();
        String responseJson;
        try {
            responseJson = collectionDataService.update(userId, projectId, envId, collectionId, contentId, content);
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

    @ApiAuth
    public Result getAll(String projectId, String collectionId, String envId) {
        Object isSelfObject = Http.Context.current().args.get("isSelf");
        boolean isSelfContent = false;
        if (isSelfObject != null) {
            isSelfContent = (boolean) Http.Context.current().args.get("isSelf");
        }
        String userId = (String) Http.Context.current().args.get("userId");
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
            response = collectionDataService.getByEnv(projectId, envId, collectionId, isSelfContent?userId:null, offset, limit,
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

    @ApiAuth
    public Result get(String projectId, String collectionId, String envId, String contentId) {
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