package repository;

import com.google.inject.AbstractModule;
import repository.mongo.driver.DbStore;
import repository.mongo.driver.IDbStore;

/**
 * Created by mitesh on 12/11/16.
 */
public class RepositoryModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(IDbStore.class).to(DbStore.class).asEagerSingleton();
    }
}
