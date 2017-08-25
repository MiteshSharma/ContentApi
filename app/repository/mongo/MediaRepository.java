package repository.mongo;

import com.google.inject.Inject;
import model.Media;
import org.bson.types.ObjectId;
import repository.IMediaRepository;
import repository.mongo.driver.IDbStore;

import java.util.List;

/**
 * Created by mitesh on 20/12/16.
 */
public class MediaRepository extends BaseMasterRepository implements IMediaRepository {

    @Inject
    public MediaRepository(IDbStore dbStore) {
        super(dbStore);
    }

    @Override
    public Media create(Media media) {
        if (media.getId() == null) {
            media.setId(new ObjectId());
        }
        datastore.save(media);
        return media;
    }

    @Override
    public Media get(String mediaId) {
        return getObject(Media.class, "_id", new ObjectId(mediaId));
    }

    @Override
    public List<Media> getAll(String projectId) {
        return getObjects(Media.class, "projectId", projectId);
    }

    @Override
    public void delete(String mediaId) {
        this.datastore.delete(Media.class, mediaId);
    }
}
