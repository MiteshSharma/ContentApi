package controllers;

import com.google.inject.Inject;
import exceptions.NoProjectExistException;
import helpers.ApiAuth;
import helpers.ProjectAuth;
import play.libs.Json;
import play.mvc.Result;
import pojo.MediaObject;
import pojo.Scope;
import services.IMediaService;

import java.util.List;

/**
 * Created by mitesh on 20/12/16.
 */
public class MediaController extends CoreController {

    @Inject
    IMediaService mediaService;

    @ApiAuth(scope = Scope.ContentWrite)
    public Result create(String projectId) {
        MediaObject mediaObject = Json.fromJson(request().body().asJson(), MediaObject.class);
        if (mediaObject.getUrl() == null) {
            return badRequest();
        }
        try {
            mediaObject = this.mediaService.create(projectId, mediaObject.getMedia(projectId));
        } catch (NoProjectExistException e) {
            return badRequest("No project found.");
        }
        return ok(Json.toJson(mediaObject));
    }

    @ApiAuth
    public Result getAll(String projectId) {
        List<MediaObject> mediaObjects;
        try {
            mediaObjects = this.mediaService.getAll(projectId);
        } catch (NoProjectExistException e) {
            return badRequest("No project found.");
        }
        return ok(Json.toJson(mediaObjects));
    }

    @ApiAuth(scope = Scope.ContentDelete)
    public Result delete(String projectId, String mediaId) {
        try {
            this.mediaService.delete(projectId, mediaId);
        } catch (NoProjectExistException e) {
            return badRequest("No project found.");
        }
        return ok();
    }
}
