package services.impl;

import com.google.inject.Inject;
import play.mvc.Http;
import repository.IAwsS3Repository;
import services.IUploadService;

import java.io.File;

/**
 * Created by mitesh on 30/12/16.
 */
public class UploadService implements IUploadService {

    @Inject
    IAwsS3Repository awsS3Repository;

    @Override
    public String uploadFile(Http.MultipartFormData.FilePart file) {
        return awsS3Repository.uploadFile(file);
    }
}
