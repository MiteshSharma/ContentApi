package controllers;

import com.google.inject.Inject;
import helpers.ApiAuth;
import play.libs.Json;
import play.mvc.Result;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import play.mvc.*;
import play.mvc.Http.*;
import pojo.Scope;
import services.IUploadService;

/**
 * Created by mitesh on 20/12/16.
 */
public class UploadController extends CoreController {

    @Inject
    IUploadService uploadService;

    public Result create(String projectId) {
        MultipartFormData multipartFormData = request().body().asMultipartFormData();
        List<MultipartFormData.FilePart> fileParts = multipartFormData.getFiles();

        List<String> fileNames = new ArrayList<>();
        File file;
        for (Http.MultipartFormData.FilePart filePart : fileParts) {
            String fileName = this.uploadService.uploadFile(filePart);
            fileNames.add(fileName);
        }
        return ok(Json.toJson(fileNames));
    }
}