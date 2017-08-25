package helpers;

import play.mvc.With;
import pojo.Scope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mitesh on 28/07/16.
 */
@With(ApiAuthManager.class)
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiAuth {
    Scope scope() default Scope.All;
}
