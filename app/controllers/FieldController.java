package controllers;

import com.google.inject.Inject;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import exceptions.NoCollectionExistException;
import exceptions.NoProjectExistException;
import helpers.ApiAuth;
import model.Field;
import play.libs.Json;
import play.mvc.Result;
import pojo.CollectionDetail;
import pojo.FieldObject;
import pojo.FieldObjectList;
import pojo.Scope;
import services.ICollectionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitesh on 19/11/16.
 */
public class FieldController extends CoreController {
    @Inject
    ICollectionService collectionService;

    @ApiAuth(scope = Scope.FieldWrite)
    public Result create(String projectId, String collectionId) {
        FieldObject fieldObject = Json.fromJson(request().body().asJson(), FieldObject.class);

        List<FieldObject> fieldObjects = new ArrayList<>();
        fieldObjects.add(fieldObject);
        List<Field> fields = FieldObject.getFields(collectionId, fieldObjects);
        CollectionDetail collectionDetail;
        try {
            collectionDetail = collectionService.createFields(projectId, collectionId, fields);
        } catch (NoCollectionExistException e) {
            return badRequest();
        } catch (NoProjectExistException e) {
            return badRequest();
        }
        return ok(Json.toJson(collectionDetail));
    }

    @ApiAuth(scope = Scope.FieldWrite)
    public Result update(String projectId, String collectionId, String fieldId) {
        FieldObject fieldObject = Json.fromJson(request().body().asJson(), FieldObject.class);
        CollectionDetail collectionDetail;
        try {
            collectionDetail = collectionService.updateField(projectId, collectionId, fieldObject.getField(collectionId));
        } catch (NoCollectionExistException e) {
            return badRequest();
        } catch (NoProjectExistException e) {
            return badRequest();
        }
        return ok(Json.toJson(collectionDetail));
    }

    @ApiAuth(scope = Scope.FieldDelete)
    public Result delete(String projectId, String collectionId, String fieldId) {
        CollectionDetail collectionDetail;
        try {
            collectionDetail = collectionService.deleteField(projectId, collectionId, fieldId);
        } catch (NoCollectionExistException e) {
            return badRequest();
        } catch (NoProjectExistException e) {
            return badRequest();
        }
        return ok(Json.toJson(collectionDetail));
    }
}
