package controllers;

import com.google.inject.Inject;
import event_handler.EventName;
import helpers.ApiAuth;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import services.IWebhookService;

import java.util.List;

/**
 * Created by mitesh on 22/01/17.
 */
public class WebhookEventController extends CoreController {

    @Inject
    IWebhookService webhookService;

    @ApiAuth
    public Result getAll(String projectId) {
        String userId = (String) Http.Context.current().args.get("userId");
        List<EventName> webhookEvents = webhookService.getAllEvents();
        return ok(Json.toJson(webhookEvents));
    }
}
