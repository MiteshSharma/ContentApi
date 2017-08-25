package controllers;

import com.google.inject.Inject;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import exceptions.NoProjectExistException;
import helpers.ApiAuth;
import model.Collection;
import play.libs.Json;
import play.mvc.Result;
import pojo.CollectionDetail;
import pojo.Scope;
import services.ICollectionService;

import java.util.List;

/**
 * Created by mitesh on 12/11/16.
 */
public class CollectionController extends CoreController {
    @Inject
    ICollectionService collectionService;

    @ApiAuth(scope = Scope.CollectionWrite)
    public Result create(String projectId) {
        CollectionDetail collectionDetail = Json.fromJson(request().body().asJson(), CollectionDetail.class);

        try {
            collectionDetail = this.collectionService.create(projectId, collectionDetail.getCollectionMetadata(projectId));
        } catch (NoProjectExistException e) {
            return badRequest("No project found.");
        }
        return ok(Json.toJson(collectionDetail));
    }

    @ApiAuth
    public Result get(String projectId, String collectionId) {
        CollectionDetail collectionDetail = this.collectionService.get(projectId, collectionId);
        return ok(Json.toJson(collectionDetail));
    }

    @ApiAuth
    public Result getAll(String projectId) {
        List<CollectionDetail> collectionDetails = this.collectionService.getAll(projectId);

        return ok(Json.toJson(collectionDetails));
    }
}