package repository.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.Md5Utils;
import com.google.inject.Inject;
import play.Configuration;
import play.mvc.Http;
import repository.IAwsS3Repository;
import utils.FileUtil;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by mitesh on 30/12/16.
 */
public class AwsS3Repository implements IAwsS3Repository {

    AmazonS3Client s3Client;
    String bucketName;
    String baseS3Url;

    @Inject
    public AwsS3Repository(Configuration configuration) {
        this.s3Client = new AmazonS3Client(getAwsCredentials(configuration));
        this.bucketName = configuration.getString("aws.s3.bucketName");
        this.baseS3Url = configuration.getString("aws.s3.baseUrl");
    }

    private AWSCredentials getAwsCredentials(Configuration configuration) {
        return new BasicAWSCredentials(
                configuration.getString("aws.accessKey"),
                configuration.getString("aws.secretKey"));
    }

    public boolean uploadFile(String bucketName, String filePath, File file) {
        PutObjectRequest objectRequest = new PutObjectRequest(bucketName, filePath, file);
        objectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
        try {
            this.s3Client.putObject(objectRequest);
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public String uploadFile(Http.MultipartFormData.FilePart file) {
        String fileExtension = FileUtil.getFileExtension(file.getFilename());
        String fileName = FileUtil.generateUniqueFileName();
        if (fileExtension != null) {
            fileName += "." + fileExtension;
        }
        this.uploadFile(this.bucketName, fileName, (File) file.getFile());
        return this.baseS3Url+"/"+fileName;
    }
}