package repository;

import com.google.inject.ImplementedBy;
import play.mvc.Http;
import repository.aws.AwsS3Repository;

import java.io.File;

/**
 * Created by mitesh on 30/12/16.
 */
@ImplementedBy(AwsS3Repository.class)
public interface IAwsS3Repository {
    public String uploadFile(Http.MultipartFormData.FilePart file);
}
