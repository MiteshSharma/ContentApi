package controllers;

import com.google.inject.Inject;
import helpers.ApiAuth;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import pojo.Scope;
import pojo.WebhookObject;
import services.IWebhookService;

import java.util.List;

/**
 * Created by mitesh on 16/01/17.
 */
public class WebhookController extends CoreController {

    @Inject
    IWebhookService webhookService;

    @ApiAuth(scope = Scope.WebhookWrite)
    public Result create(String projectId) {
        String userId = (String) Http.Context.current().args.get("userId");

        WebhookObject webhookObject = Json.fromJson(request().body().asJson(), WebhookObject.class);
        webhookObject = webhookService.create(webhookObject, projectId, userId);
        return ok(Json.toJson(webhookObject));
    }

    @ApiAuth
    public Result getAll(String projectId) {
        String userId = (String) Http.Context.current().args.get("userId");
        List<WebhookObject> webhookObjects = webhookService.getAll(projectId);
        return ok(Json.toJson(webhookObjects));
    }
}
