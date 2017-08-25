package controllers;

import com.google.inject.Inject;
import model.NewsletterSubscriber;
import play.libs.Json;
import play.mvc.Result;
import services.INewsletterService;

/**
 * Created by mitesh on 30/01/17.
 */
public class NewsletterController extends CoreController {

    @Inject
    INewsletterService newsletterService;

    public Result create() {
        NewsletterSubscriber newsletterSubscriber = Json.fromJson(request().body().asJson(), NewsletterSubscriber.class);
        newsletterService.create(newsletterSubscriber);
        return ok();
    }
}