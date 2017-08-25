package helpers;

import play.mvc.Http;

/**
 * Created by mitesh on 09/01/17.
 */
public class HttpValueParseHelper {
    public static String getProjectId(Http.Context context) {
        String projectId = null;
        String path = context.request().path();
        if (path.contains("/api/v1")) {
            String companyUrlPath = path.replace("/api/v1/", "");
            String[] urlParams = companyUrlPath.split("/");
            if (urlParams.length > 1) {
                projectId = urlParams[1];
            }
        } else {
            projectId = (String) context.args.get("projectId");
        }
        return projectId;
    }
}
