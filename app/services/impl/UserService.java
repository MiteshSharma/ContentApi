package services.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.MACSigner;
import controllers.CoreController;
import dispatcher.EventDispatcher;
import event_handler.EventName;
import exceptions.UserAuthException;
import exceptions.UserLoginFailedException;
import model.TeamMember;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import play.libs.Json;
import pojo.TeamObject;
import repository.IUserRepository;
import services.IJwtService;
import services.ITeamService;
import services.IUserService;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by mitesh on 16/11/16.
 */
public class UserService implements IUserService {
    @Inject
    IUserRepository userRepository;
    @Inject
    IJwtService jwtService;
    @Inject
    ITeamService teamService;

    public User create(User user) {
        user = userRepository.save(user);
        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("userId", user.getId().toString());
        eventParam.put("user_name", user.getName());
        eventParam.put("email", user.getEmail());
        eventParam.put("creation_time", (System.currentTimeMillis()/1000)+"");
        EventDispatcher.dispatch(EventName.USER_CREATE, eventParam);
        return  user;
    }

    public User update(User user) {
        user = userRepository.save(user);
        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("userId", user.getId().toString());
        eventParam.put("user_name", user.getName());
        eventParam.put("email", user.getEmail());
        EventDispatcher.dispatch(EventName.USER_UPDATE, eventParam);
        return  user;
    }

    public User get(String userId) {
        return userRepository.getById(userId);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.get(email);
    }

    public User resetPassword(User user) {
        user.setSupersedePassword(UUID.randomUUID().toString());
        user = userRepository.save(user);
        Map<String, String> eventParam = CoreController.getBasicAnalyticsParam();
        eventParam.put("userId", user.getId().toString());
        eventParam.put("email", user.getEmail());
        EventDispatcher.dispatch(EventName.USER_PASSWORD_RESET, eventParam);
        return user;
    }

    public boolean isUserExist(User user) {
        return userRepository.get(user.getEmail(), user.getProjectId()) != null;
    }

    public User isValidLogin(User currentUser) {
        User user = userRepository.get(currentUser.getEmail());
        if (user == null) {
            return null;
        } else {
            if ("Normal".equals(user.getType()) || user.getType() == null) {
                if (user.getPasswordHash().equals(BCrypt.hashpw(currentUser.getPassword(), user.getSaltApplied()))) {
                    return user;
                } else if (user.getSupersedePassword() != null && user.getSupersedePassword().equals(currentUser.getSupersedePassword())) {
                    return user;
                }
            } else {
                if (user.getExternalId() != null && user.getExternalId().equals(currentUser.getExternalId())) {
                    return user;
                }
            }
        }
        return null;
    }

    @Override
    public String login(User user) throws UserLoginFailedException {
        ObjectNode jsonNode = Json.newObject();
        jsonNode.put("userId", user.getId().toString());
        List<TeamObject> teamObjects = this.teamService.getByUser(user.getId().toString());
        ObjectNode projectNode = Json.newObject();
        for (TeamObject teamObject : teamObjects) {
            TeamMember teamMember = teamObject.getTeamMembers().stream().filter(tMember -> tMember.getUserId().equals(user.getId().toString())).findFirst().get();
            projectNode.put(teamObject.getProjectId(), teamMember.getRole().toString());
        }
        jsonNode.put("project", projectNode.toString());
        String jwt = jwtService.getJwtWithMessage(jsonNode.toString());
        EventDispatcher.dispatch(EventName.USER_LOGIN, CoreController.getBasicAnalyticsParam());
        return jwt;
    }

    @Override
    public void logout() {
        EventDispatcher.dispatch(EventName.USER_LOGOUT, CoreController.getBasicAnalyticsParam());
    }

}
