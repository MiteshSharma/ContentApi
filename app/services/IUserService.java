package services;

import com.google.inject.ImplementedBy;
import exceptions.UserAuthException;
import exceptions.UserLoginFailedException;
import model.User;
import services.impl.UserService;

/**
 * Created by mitesh on 16/11/16.
 */
@ImplementedBy(UserService.class)
public interface IUserService {
    public User create(User user);
    public User update(User user);
    public User get(String userId);
    public User getByEmail(String email);
    public User resetPassword(User user);
    public boolean isUserExist(User user);
    public User isValidLogin(User currentUser);
    public String login(User user) throws UserLoginFailedException;
    public void logout();
}
