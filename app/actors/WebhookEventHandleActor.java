package actors;

import akka.actor.UntypedActor;
import com.google.inject.Inject;
import play.libs.ws.WSClient;
import pojo.WebhookObject;

/**
 * Created by mitesh on 22/01/17.
 */
public class WebhookEventHandleActor extends UntypedActor {

    @Inject
    WSClient wsClient;

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof WebhookObject) {
            WebhookObject webhookObject = (WebhookObject) message;
            if (webhookObject.getWebhookData() != null) {
                // Make GET request and then send response back
                wsClient.url(webhookObject.getWebhookData())
                        .get().thenAccept(wsResponse -> getSender().tell(wsResponse, self()));
            }
        }
    }
}
