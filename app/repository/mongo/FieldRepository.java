package repository.mongo;

import com.google.inject.Inject;
import model.Field;
import org.bson.types.ObjectId;
import repository.IFieldRepository;
import repository.mongo.driver.IDbStore;

import java.util.List;

/**
 * Created by mitesh on 19/11/16.
 */
public class FieldRepository extends BaseMasterRepository implements IFieldRepository {
    @Inject
    public FieldRepository(IDbStore dbStore) {
        super(dbStore);
    }

    @Override
    public void create(Field field) {
        if (field.getId() == null) {
            field.setId(new ObjectId());
        }
        this.datastore.save(field);
    }

    @Override
    public void update(Field field) {
        this.datastore.merge(field);
    }

    @Override
    public Field get(String fieldId) {
        return getById(Field.class, fieldId);
    }

    @Override
    public List<Field> getAll(String collectionId) {
        return getObjects(Field.class, "collectionId", collectionId);
    }

    @Override
    public void delete(String fieldId) {
        this.datastore.delete(Field.class, new ObjectId(fieldId));
    }
}
