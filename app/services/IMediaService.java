package services;

import com.google.inject.ImplementedBy;
import exceptions.NoProjectExistException;
import model.Media;
import pojo.MediaObject;
import services.impl.MediaService;

import java.util.List;

/**
 * Created by mitesh on 20/12/16.
 */
@ImplementedBy(MediaService.class)
public interface IMediaService {
    public MediaObject create(String projectId, Media media) throws NoProjectExistException;
    public List<MediaObject> getAll(String projectId) throws NoProjectExistException;
    public boolean delete(String projectId, String mediaId) throws NoProjectExistException;
}