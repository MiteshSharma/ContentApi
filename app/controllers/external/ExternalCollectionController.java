package controllers.external;

import com.google.inject.Inject;
import controllers.CoreController;
import helpers.ExternalApiAuth;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import pojo.CollectionDetail;
import services.ICollectionService;

import java.util.List;

/**
 * Created by mitesh on 29/01/17.
 */
public class ExternalCollectionController extends CoreController {

    @Inject
    ICollectionService collectionService;

    @ExternalApiAuth
    public Result get(String collectionId) {
        String projectId = (String) Http.Context.current().args.get("projectId");
        CollectionDetail collectionDetail = this.collectionService.get(projectId, collectionId);
        return ok(Json.toJson(collectionDetail));
    }

    @ExternalApiAuth
    public Result getAll() {
        String projectId = (String) Http.Context.current().args.get("projectId");
        List<CollectionDetail> collectionDetails = this.collectionService.getAll(projectId);

        return ok(Json.toJson(collectionDetails));
    }
}
