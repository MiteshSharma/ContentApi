package services;

import com.google.inject.ImplementedBy;
import exceptions.NoProjectExistException;
import model.Environment;
import pojo.EnvironmentObject;
import services.impl.EnvironmentService;

import java.util.List;

/**
 * Created by mitesh on 18/11/16.
 */
@ImplementedBy(EnvironmentService.class)
public interface IEnvironmentService {
    public EnvironmentObject create(String projectId, String userId) throws NoProjectExistException;
    public EnvironmentObject create(Environment environment) throws NoProjectExistException;
    public List<EnvironmentObject> getByProject(String projectId);
}
