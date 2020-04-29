package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.PersonDAO;
import app.dao.ProCoDAO;
import app.dao.ShowDAO;
import app.dao.UserReviewDAO;
import app.model.UserReview;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.Calendar;
import java.util.Map;
import static app.controller.utils.RequestUtil.*;

public class suggestionController {


	
	public static Handler suggestionPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        //added 10 years of ahead time for movies anticipated to come out.
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int[] years = new int[currentYear + 11 - 1800];
        for (int i = currentYear + 10; i >= 1800; i--) {
        	years[currentYear + 10 - i] = i;
        }
        model.put("years", years);
        model.put("procos", ProCoDAO.getAllProCo());
        ctx.render(Template.SUGGESTION, model);
        
    };
    
    public static Handler handleNewSuggestion = ctx -> {
    	UserReview review = null;
		if (getSuggestionPost(ctx) != null) {
			review = new UserReview(-1, getSessionCurrentUser(ctx), getParamShowId(ctx), getRatingPost(ctx), getReviewPost(ctx));
			suggestionDAO.insertReviewIntoDataBase(review);
		}

    	 Map<String, Object> model = ViewUtil.baseModel(ctx);
         model.put("show", ShowDAO.getShowById(getParamShowId(ctx)));
         model.put("reviews", UserReviewDAO.searchReviewByShowID(getParamShowId(ctx)));
         ctx.render(Template.SHOW, model);
    };
    
    
    public static String getSuggestionPost(Context ctx) {
        return ctx.formParam("review");
    }
    
}
