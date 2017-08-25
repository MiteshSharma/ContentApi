package helpers.auth;

import org.pac4j.core.context.WebContext;
import org.pac4j.play.PlayWebContext;
import org.pac4j.play.http.DefaultHttpActionAdapter;
import play.mvc.Result;

/**
 * Created by mitesh on 17/11/16.
 */
public class CustomHttpActionAdapter extends DefaultHttpActionAdapter {
    @Override
    public Result adapt(int code, PlayWebContext context) {
//        if (code == HttpConstants.UNAUTHORIZED) {
//            return unauthorized(views.html.error401.render().toString()).as((HttpConstants.HTML_CONTENT_TYPE));
//        } else if (code == HttpConstants.FORBIDDEN) {
//            return forbidden(views.html.error403.render().toString()).as((HttpConstants.HTML_CONTENT_TYPE));
//        } else {
            return super.adapt(code, context);
        //}
    }
}
