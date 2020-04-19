package app.controller;

import static app.controller.utils.RequestUtil.getParamShowId;
import static app.controller.utils.RequestUtil.getSessionCurrentUser;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.paths.Web;
import app.controller.utils.ViewUtil;
import app.dao.ShowDAO;
import app.dao.UserReviewDAO;
import app.model.UserReview;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ShowController {

	
	public static Handler serveShowPage = ctx -> {
		Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("show", ShowDAO.getShowById(getParamShowId(ctx)));
        model.put("reviews", UserReviewDAO.searchReviewByShowID(getParamShowId(ctx)));
        ctx.render(Template.SHOW, model);
        
        
    };
    
    public static Handler handleUserReview = ctx -> {
    	UserReview review = null;
    	 if(getReviewPost(ctx) != null) {
    	review = new UserReview(-1, getSessionCurrentUser(ctx), getParamShowId(ctx), getRatingPost(ctx), getReviewPost(ctx));
    	UserReviewDAO.insertReviewIntoDataBase(review);
    	 }
    	 else if (getRatingPost(ctx) != -1) {
    		 review = new UserReview(-1, getSessionCurrentUser(ctx), getParamShowId(ctx), getRatingPost(ctx));
    		 UserReviewDAO.insertReviewIntoDataBase(review);
    	 }
    	 Map<String, Object> model = ViewUtil.baseModel(ctx);
         model.put("show", ShowDAO.getShowById(getParamShowId(ctx)));
         model.put("reviews", UserReviewDAO.searchReviewByShowID(getParamShowId(ctx)));
         ctx.render(Template.SHOW, model);
    };

    public static String getReviewPost(Context ctx) {
        return ctx.formParam("review");
    }
    
    public static int getRatingPost(Context ctx) {
    	if (ctx.formParam("Rating") != null) {
        return Integer.parseInt(ctx.formParam("Rating"));
    	}
    	else {
    		return -1;
    	}
    }

}
