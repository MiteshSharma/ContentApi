package repository;

import com.google.inject.ImplementedBy;
import model.Field;
import repository.mongo.FieldRepository;

import java.util.List;

/**
 * Created by mitesh on 19/11/16.
 */
@ImplementedBy(FieldRepository.class)
public interface IFieldRepository {
    public void create(Field field);
    public void update(Field field);
    public Field get(String fieldId);
    public List<Field> getAll(String collectionId);
    public void delete(String fieldId);
}
