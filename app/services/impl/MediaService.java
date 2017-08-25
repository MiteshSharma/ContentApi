package services.impl;

import com.google.inject.Inject;
import controllers.CoreController;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import exceptions.NoProjectExistException;
import model.Media;
import model.Project;
import pojo.MediaObject;
import repository.IMediaRepository;
import repository.IProjectRepository;
import services.IMediaService;

import java.util.List;
import java.util.Map;

/**
 * Created by mitesh on 20/12/16.
 */
public class MediaService implements IMediaService {
    @Inject
    IMediaRepository mediaRepository;
    @Inject
    IProjectRepository projectRepository;

    @Override
    public MediaObject create(String projectId, Media media) throws NoProjectExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }
        media = this.mediaRepository.create(media);

        this.projectRepository.incrementTotalMediaCount(project, 1);

        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("projectId", project.getId().toString());
        eventParam.put("projectName", project.getName());
        eventParam.put("mediaName", media.getName());
        EventDispatcher.dispatch(EventName.MEDIA_CREATE, eventParam);

        return MediaObject.getMediaObject(media);
    }

    @Override
    public List<MediaObject> getAll(String projectId) throws NoProjectExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }
        List<Media> mediaList = this.mediaRepository.getAll(projectId);
        return MediaObject.getMediaObjects(mediaList);
    }

    @Override
    public boolean delete(String projectId, String mediaId) throws NoProjectExistException {
        Project project = this.projectRepository.get(projectId);
        if (project == null) {
            throw new NoProjectExistException();
        }
        Media media = this.mediaRepository.get(mediaId);
        this.mediaRepository.delete(mediaId);
        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("projectId", project.getId().toString());
        eventParam.put("projectName", project.getName());
        eventParam.put("mediaName", media.getName());
        EventDispatcher.dispatch(EventName.MEDIA_DELETE, eventParam);
        return true;
    }
}
