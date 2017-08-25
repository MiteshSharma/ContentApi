package controllers;

import play.mvc.Result;

/**
 * Created by mitesh on 29/11/16.
 */
public class AppViewController extends CoreController {

    public Result index() {
        // Dashboard page UI
        //return ok(views.html.index.render());
        return ok();
    }
}
