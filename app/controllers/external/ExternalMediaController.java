package controllers.external;

import com.google.inject.Inject;
import controllers.CoreController;
import exceptions.NoProjectExistException;
import helpers.ExternalApiAuth;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import pojo.MediaObject;
import services.IMediaService;

import java.util.List;

/**
 * Created by mitesh on 22/12/16.
 */
public class ExternalMediaController extends CoreController {

    @Inject
    IMediaService mediaService;

    @ExternalApiAuth
    public Result create() {
        String projectId = (String) Http.Context.current().args.get("projectId");
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

    @ExternalApiAuth
    public Result getAll() {
        String projectId = (String) Http.Context.current().args.get("projectId");
        List<MediaObject> mediaObjects;
        try {
            mediaObjects = this.mediaService.getAll(projectId);
        } catch (NoProjectExistException e) {
            return badRequest("No project found.");
        }
        return ok(Json.toJson(mediaObjects));
    }

    @ExternalApiAuth
    public Result delete(String mediaId) {
        String projectId = (String) Http.Context.current().args.get("projectId");
        try {
            this.mediaService.delete(projectId, mediaId);
        } catch (NoProjectExistException e) {
            return badRequest("No project found.");
        }
        return ok();
    }
}