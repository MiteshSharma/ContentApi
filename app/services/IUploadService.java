package services;

import com.google.inject.ImplementedBy;
import play.mvc.Http;
import services.impl.UploadService;

import java.io.File;

/**
 * Created by mitesh on 30/12/16.
 */
@ImplementedBy(UploadService.class)
public interface IUploadService {
    public String uploadFile(Http.MultipartFormData.FilePart file);
}
