package controllers;

import play.mvc.Result;

/**
 * Created by mitesh on 20/01/17.
 */
public class MainController extends CoreController {

    public Result index() {
        // Landing Page UI
        //return ok(views.html.main.render());
        return ok("Welcome to Content API");
    }
}
