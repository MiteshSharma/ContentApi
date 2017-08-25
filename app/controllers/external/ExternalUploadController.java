package controllers.external;

import com.google.inject.Inject;
import controllers.CoreController;
import helpers.ExternalApiAuth;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import services.IUploadService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitesh on 23/12/16.
 */
public class ExternalUploadController extends CoreController {
    @Inject
    IUploadService uploadService;

    @ExternalApiAuth
    public Result create() {
        Http.MultipartFormData multipartFormData = request().body().asMultipartFormData();
        List<Http.MultipartFormData.FilePart> fileParts = multipartFormData.getFiles();

        List<String> fileNames = new ArrayList<>();
        for (Http.MultipartFormData.FilePart filePart : fileParts) {
            String fileName = this.uploadService.uploadFile(filePart);
            fileNames.add(fileName);
        }
        return ok(Json.toJson(fileNames));
    }
}
