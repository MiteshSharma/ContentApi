package repository;

import com.google.inject.ImplementedBy;
import model.User;
import repository.mongo.UserRepository;

/**
 * Created by mitesh on 16/11/16.
 */
@ImplementedBy(UserRepository.class)
public interface IUserRepository {
    public User create(User user);
    public User save(User user);
    public User get(String email, String projectId);
    public User get(String email);
    public User getById(String id);
    public boolean isExist(String email, String projectId);
}
