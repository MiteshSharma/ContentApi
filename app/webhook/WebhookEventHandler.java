package webhook;

import akka.actor.ActorRef;
import akka.pattern.Patterns;
import com.google.inject.Inject;
import dispatcher.IEventDispatcher;
import dispatcher.IEventHandler;
import event_handler.EventName;
import play.Play;
import play.libs.ws.WSResponse;
import pojo.WebhookObject;
import scala.compat.java8.FutureConverters;
import services.IWebhookService;

import javax.inject.Named;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

/**
 * Created by mitesh on 22/01/17.
 */
public class WebhookEventHandler implements IEventHandler {

    @Inject
    @Named("webhook-handler-actor")
    ActorRef webhookEventHandlerActor;
    @Inject
    IWebhookService webhookService;
    private static final long TIMEOUT_IN_MILLIS = 120;

    public WebhookEventHandler() {
        this.webhookService = Play.application().injector().instanceOf(IWebhookService.class);
    }

    @Override
    public void handle(EventName eventName, Map<String, String> params) {
        String projectId = params.get("projectId");
        if (projectId != null) {
            List<WebhookObject> webhookObjects = this.webhookService.getAll(projectId);
            for (WebhookObject webhookObject : webhookObjects) {
                if (webhookObject.getEventName() == eventName) {
                    // Fire event and wait for response in sync
                    if (webhookObject.getIsSync()) {
                        CompletableFuture<WSResponse> futureResponse = FutureConverters.toJava(Patterns.ask(webhookEventHandlerActor, webhookObject, TIMEOUT_IN_MILLIS))
                                .toCompletableFuture()
                                .thenApply(wsResponse -> (WSResponse) wsResponse);
                        try {
                            futureResponse.get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    } else {
                        webhookEventHandlerActor.tell(webhookObject, ActorRef.noSender());
                    }
                }
            }
        }
    }
}
