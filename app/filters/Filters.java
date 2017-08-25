package filters;

import javax.inject.*;

import filters.LoggingFilter;
import play.*;
import play.filters.cors.CORSFilter;
import play.filters.gzip.GzipFilter;
import play.mvc.EssentialFilter;
import play.http.HttpFilters;

/**
 * This class configures filters that run on every request. This
 * class is queried by Play to get a list of filters.
 *
 * Play will automatically use filters from any class called
 * <code>filters.Filters</code> that is placed the root package. You can load filters
 * from a different class by adding a `play.http.filters` setting to
 * the <code>application.conf</code> configuration file.
 */
@Singleton
public class Filters implements HttpFilters {

    private final Environment env;
    private final LoggingFilter loggingFilter;
    private final CORSFilter corsFilter;
    private final GzipFilter gzipFilter;

    @Inject
    public Filters(Environment env, CORSFilter corsFilter, LoggingFilter loggingFilter, GzipFilter gzipFilter) {
        this.env = env;
        this.corsFilter = corsFilter;
        this.loggingFilter = loggingFilter;
        this.gzipFilter = gzipFilter;
    }

    @Override
    public EssentialFilter[] filters() {
      // Use the example filter if we're running development mode. If
      // we're running in production or test mode then don't use any
      // filters at all.
      if (env.mode().equals(Mode.DEV)) {
          return new EssentialFilter[] { corsFilter.asJava(), this.gzipFilter.asJava(), loggingFilter };
      } else {
          return new EssentialFilter[] { corsFilter.asJava(), this.gzipFilter.asJava(), loggingFilter };
      }
    }

}
