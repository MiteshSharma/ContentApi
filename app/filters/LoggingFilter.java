package filters;

import akka.stream.Materializer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.function.Function;
import javax.inject.*;

import eu.bitwalker.useragentutils.UserAgent;
import event_handler.EventName;
import play.Logger;
import play.Routes;
import play.mvc.*;
import play.mvc.Http.RequestHeader;


@Singleton
public class LoggingFilter extends Filter {

    private final Executor exec;

    /**
     * @param mat This object is needed to handle streaming of requests
     * and responses.
     * @param exec This class is needed to execute code asynchronously.
     * It is used below by the <code>thenAsyncApply</code> method.
     */
    @Inject
    public LoggingFilter(Materializer mat, Executor exec) {
        super(mat);
        this.exec = exec;
    }

    @Override
    public CompletionStage<Result> apply(
            Function<RequestHeader, CompletionStage<Result>> next,
            RequestHeader requestHeader) {

        long startTime = System.currentTimeMillis();
        return next.apply(requestHeader).thenApplyAsync(
                result -> {
                    long endTime = System.currentTimeMillis();
                    long requestTime = endTime - startTime;

                    if (!requestHeader.uri().contains("/assets/")) {
                        Logger.info("{} {} took {}ms and returned {}",
                                requestHeader.method(), requestHeader.uri(), requestTime, result.status());
                        sendEvent(requestHeader, requestTime, result.status());
                    }

                    return result.withHeader("Request-Time", "" + requestTime);
                });
    }

    private void sendEvent(RequestHeader requestHeader, long requestTime, int resultStatus) {
        String uri = requestHeader.uri().toString();
        String method = requestHeader.method().toString();
        String controller = "";
        if (requestHeader.tags().containsKey(Routes.ROUTE_CONTROLLER)) {
            controller = requestHeader.tags().get(Routes.ROUTE_CONTROLLER).toString();
        }
        String action = "";
        if (requestHeader.tags().containsKey(Routes.ROUTE_ACTION_METHOD)) {
            action = requestHeader.tags().get(Routes.ROUTE_ACTION_METHOD).toString();
        }

        Map<String, String> eventParam = new HashMap<>();
        eventParam.put("event_time", (System.currentTimeMillis()/1000)+"");
        UserAgent userAgent = new UserAgent(requestHeader.headers().get("User-Agent").toString());
        if (userAgent != null) {
            eventParam.put("os", userAgent.getOperatingSystem().getName());
            eventParam.put("browser", userAgent.getBrowser().getName());
            eventParam.put("device_type", userAgent.getOperatingSystem().getDeviceType().getName());
            eventParam.put("manufacturer", userAgent.getOperatingSystem().getManufacturer().getName());
        }

        eventParam.put("requestTime", requestTime+"");
        eventParam.put("uri", uri);
        eventParam.put("method", method);
        eventParam.put("controller", controller);
        eventParam.put("action", action);
        eventParam.put("resultStatus", resultStatus+"");
        dispatcher.EventDispatcher.dispatch(EventName.REQUEST_METRICS, eventParam);
    }

}