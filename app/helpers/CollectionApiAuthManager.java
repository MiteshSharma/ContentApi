package helpers;

import com.google.common.net.HttpHeaders;
import com.google.inject.Inject;
import exceptions.NoCollectionExistException;
import exceptions.NoProjectExistException;
import model.User;
import play.Configuration;
import play.Logger;
import play.mvc.Action;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.ICollectionDataService;
import services.IJwtService;
import services.IUserService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by mitesh on 19/12/16.
 */
public class CollectionApiAuthManager extends Action<ApiAuth> {

    @Inject
    IJwtService jwtService;
    @Inject
    ICollectionDataService collectionDataService;
    @Inject
    IUserService userService;
    @Inject
    Configuration configuration;

    @Override
    public CompletionStage<Result> call(Http.Context context) {
        String authToken = Controller.request().getHeader(HttpHeaders.AUTHORIZATION);
        if (authToken == null || "".equals(authToken)) {
            authToken = Controller.session().get(HttpHeaders.AUTHORIZATION);
        }

        String projectId = this.getProjectId(context);
        String collectionId = this.getCollectionId(context);
        if (projectId != null && collectionId != null) {
            boolean isAuthNeeded;
            try {
                isAuthNeeded = collectionDataService.isAuthNeeded(projectId, collectionId);
            } catch (NoProjectExistException e) {
                return CompletableFuture.supplyAsync(() -> unauthorized());
            } catch (NoCollectionExistException e) {
                return CompletableFuture.supplyAsync(() -> unauthorized());
            }
            if (isAuthNeeded) {
                if (authToken == null) {
                    return CompletableFuture.supplyAsync(() -> unauthorized());
                }

                String payload = jwtService.verifyJwt(authToken);
                if (payload == null) {
                    return CompletableFuture.supplyAsync(() -> unauthorized());
                }

                User user = userService.get(payload);
                if (configuration.getString("projectId", "1").equals(user.getProjectId()) || this.getProjectId(context).equals(user.getProjectId())) {
                    context.args.put("userId", payload);
                } else {
                    return CompletableFuture.supplyAsync(() -> unauthorized());
                }
            }
        } else {
            return CompletableFuture.supplyAsync(() -> unauthorized());
        }

        return delegate.call(context);
    }

    private String getProjectId(Http.Context context) {
        String projectId = null;
        String path = context.request().path();
        if (path.contains("/api/v1")) {
            String companyUrlPath = path.replace("/api/v1/", "");
            String[] urlParams = companyUrlPath.split("/");
            if (urlParams.length > 0) {
                projectId = urlParams[0];
            }
        } else {
            projectId = (String) context.args.get("projectId");
        }
        return projectId;
    }

    private String getCollectionId(Http.Context context) {
        String collectionId = null;
        String path = context.request().path();
        if (path.contains("/api/v1")) {
            String companyUrlPath = path.replace("/api/v1/project/", "");
            String[] urlParams = companyUrlPath.split("/");
            if (urlParams.length > 2) {
                collectionId = urlParams[2];
            }
        } else {
            String url = path.replace("/collection/", "");
            String[] urlParams = url.split("/");
            if (urlParams.length > 0) {
                collectionId = urlParams[0];
            }
        }
        return collectionId;
    }
}