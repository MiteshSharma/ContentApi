package pojo;

/**
 * Created by mitesh on 04/01/17.
 */
public enum Scope {
    GetSelf,
    GetAll,
    ContentWrite,
    ContentDelete,
    TeamWrite,
    TeamDelete,
    CollectionWrite,
    CollectionDelete,
    FieldWrite,
    FieldDelete,
    EnvironmentWrite,
    EnvironmentDelete,
    ProjectWrite,
    ProjectDelete,
    WebhookWrite,
    All;
}
