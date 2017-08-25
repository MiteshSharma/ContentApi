package services;

import com.google.inject.ImplementedBy;
import exceptions.UserLoginFailedException;
import services.impl.JwtService;

/**
 * Created by mitesh on 18/11/16.
 */
@ImplementedBy(JwtService.class)
public interface IJwtService {
    public String getJwtWithMessage(String message) throws UserLoginFailedException;
    public String verifyJwt(String jwt);
}
