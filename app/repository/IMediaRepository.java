package repository;

import com.google.inject.ImplementedBy;
import model.Media;
import repository.mongo.MediaRepository;

import java.util.List;

/**
 * Created by mitesh on 20/12/16.
 */
@ImplementedBy(MediaRepository.class)
public interface IMediaRepository {
    public Media create(Media media);
    public Media get(String mediaId);
    public List<Media> getAll(String projectId);
    public void delete(String mediaId);
}
