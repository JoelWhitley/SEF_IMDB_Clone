package app.controller;

import static app.controller.utils.RequestUtil.getParamShowId;
import static app.controller.utils.RequestUtil.getSessionCurrentUser;

import java.util.Map;

import app.controller.paths.Template;
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
        //simple impl
        model.put("fiveRating", ShowDAO.getStarRating(getParamShowId(ctx), 5));
        model.put("fourRating", ShowDAO.getStarRating(getParamShowId(ctx), 4));
        model.put("threeRating", ShowDAO.getStarRating(getParamShowId(ctx), 3));
        model.put("twoRating", ShowDAO.getStarRating(getParamShowId(ctx), 2));
        model.put("oneRating", ShowDAO.getStarRating(getParamShowId(ctx), 1));
        
        model.put("fivePercent", ShowDAO.getStarPercent(getParamShowId(ctx), 5));
        model.put("fourPercent", ShowDAO.getStarPercent(getParamShowId(ctx), 4));
        model.put("threePercent", ShowDAO.getStarPercent(getParamShowId(ctx), 3));
        model.put("twoPercent", ShowDAO.getStarPercent(getParamShowId(ctx), 2));
        model.put("onePercent", ShowDAO.getStarPercent(getParamShowId(ctx), 1));
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
    	 //if delete button = pressed then execute sql delete query for current logged user with current showid
    	 else if(getDelete(ctx)) {

    		//UserReviewDAO.deleteReviewInDataBase((String) ctx.sessionAttribute("currentUser"), Integer.parseInt(ctx.pathParam("showid")));
    		UserReviewDAO.deleteReviewInDataBase(getSessionCurrentUser(ctx), getParamShowId(ctx));

    	 }
    	 
    	 Map<String, Object> model = ViewUtil.baseModel(ctx);
         model.put("show", ShowDAO.getShowById(getParamShowId(ctx)));
         model.put("reviews", UserReviewDAO.searchReviewByShowID(getParamShowId(ctx)));
         
         model.put("fiveRating", ShowDAO.getStarRating(getParamShowId(ctx), 5));
         model.put("fourRating", ShowDAO.getStarRating(getParamShowId(ctx), 4));
         model.put("threeRating", ShowDAO.getStarRating(getParamShowId(ctx), 3));
         model.put("twoRating", ShowDAO.getStarRating(getParamShowId(ctx), 2));
         model.put("oneRating", ShowDAO.getStarRating(getParamShowId(ctx), 1));
         
         model.put("fivePercent", ShowDAO.getStarPercent(getParamShowId(ctx), 5));
         model.put("fourPercent", ShowDAO.getStarPercent(getParamShowId(ctx), 4));
         model.put("threePercent", ShowDAO.getStarPercent(getParamShowId(ctx), 3));
         model.put("twoPercent", ShowDAO.getStarPercent(getParamShowId(ctx), 2));
         model.put("onePercent", ShowDAO.getStarPercent(getParamShowId(ctx), 1));
         
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
    
    public static boolean getDelete(Context ctx) {
    	return ctx.formParam("Check") != null;
    }
}
