package app.controller;

import static app.controller.utils.RequestUtil.getParamShowId;
import static app.controller.utils.RequestUtil.getSessionCurrentUser;

import java.util.List;
import java.util.Map;

import app.controller.paths.Template;
import app.controller.paths.Web;
import app.controller.utils.ViewUtil;
import app.dao.ShowDAO;
import app.dao.UserReviewDAO;
import app.model.Show;
import app.model.UserReview;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ShowController {

	
	public static Handler serveShowPage = ctx -> {
		ShowDAO.updateProCoShowStatus();
		Map<String, Object> model = ViewUtil.baseModel(ctx);
		Show pageShow = ShowDAO.getShowById(getParamShowId(ctx));
		if (pageShow.getStatus().toString().equals("VISABLE")) {
			
	        model.put("show", ShowDAO.getShowById(getParamShowId(ctx)));
	        model.put("cast", ShowDAO.getCast(getParamShowId(ctx)));
	        model.put("proco", ShowDAO.getProco(getParamShowId(ctx)));
	        model.put("reviews", UserReviewDAO.searchReviewByShowID(getParamShowId(ctx)));
	        
	        model.put("cast", ShowDAO.getCast(getParamShowId(ctx),"NOT "));
	        model.put("crew",ShowDAO.getCast(getParamShowId(ctx),""));
	        
	        model.put("alreadyReviewed", checkAlreadyReviewed(ctx));
	        
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
		}
		else {
			model.put("show.showTitle", "Error. This show has not been "
					+ "approved for our database yet. Please wait for an admin to review it.");
		}
		
		
        ctx.render(Template.SHOW, model);
    };
    public static Handler handleShowPage = ctx -> {

    	
    	//if proco press delete button, deletes show and returns to index
    	if(getProcoDeleteShow(ctx)!=null) {
	    	deleteShow(getParamShowId(ctx));

	    	ctx.redirect(Web.INDEX);
	    }
    	
    	//if proco press edit show button, redirects to edit show page
    	if(getProcoEditShow(ctx)!=null) {

	    	ctx.redirect(Web.INDEX);
	    }
    	
    	//if proco press edit cast, redirects to edit show page
    	if(getProcoEditCast(ctx)!=null) {

	    	ctx.redirect(Web.INDEX);
	    }
    	
    	UserReview review = null;
    	if(getReviewPost(ctx) != null || getRatingPost(ctx) != -1) {
    		//Check User has not
    		
    		if(checkAlreadyReviewed(ctx)== false) {
	    	 if(getReviewPost(ctx) != null) {
	    		 review = new UserReview(-1, getSessionCurrentUser(ctx), getParamShowId(ctx), getRatingPost(ctx), getReviewPost(ctx));
	    		 UserReviewDAO.insertReviewIntoDataBase(review);
	    	 }
	    	 else if (getRatingPost(ctx) != -1) {
	    		 review = new UserReview(-1, getSessionCurrentUser(ctx), getParamShowId(ctx), getRatingPost(ctx));
	    		 UserReviewDAO.insertReviewIntoDataBase(review);
	    	 }
    		}
    	}
    	 //if delete button = pressed then execute sql delete query for current logged user with current showid
    	if(getReviewDeleteUsername(ctx)!=null) {
    		deleteUserReview(getReviewDeleteUsername(ctx), getParamShowId(ctx));
    	}
    	 
    	 Map<String, Object> model = ViewUtil.baseModel(ctx);
         model.put("show", ShowDAO.getShowById(getParamShowId(ctx)));
         model.put("cast", ShowDAO.getCast(getParamShowId(ctx)));
         model.put("proco", ShowDAO.getProco(getParamShowId(ctx)));
         model.put("reviews", UserReviewDAO.searchReviewByShowID(getParamShowId(ctx)));
         
         model.put("alreadyReviewed", checkAlreadyReviewed(ctx));
         
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
    
    public static String getReviewDeleteUsername(Context ctx) {
    	return ctx.formParam("Check");
    }
    
    public static boolean checkAlreadyReviewed(Context ctx) {
		List<UserReview> currentUsersReviews =  UserReviewDAO.searchReviewByUsername(getSessionCurrentUser(ctx));
		boolean contains = false;
		if (currentUsersReviews != null) {
		for(UserReview review : currentUsersReviews) {
			if(review.getShowID() == getParamShowId(ctx)) {
				contains = true;
			}
		}
		}
		return contains;
	}
    
    public static void deleteUserReview(String username, int showID) {
    	UserReviewDAO.deleteReviewInDataBase(username, showID);
    }
    
    public static String getProcoDeleteShow(Context ctx) {
    	return ctx.formParam("deleteShow");
    }
    
    public static void deleteShow(int showID) {
    	ShowDAO.deleteShow(showID);
    }
    
    public static String getProcoEditShow(Context ctx) {
    	return ctx.formParam("editShow");
    }
    
    public static String getProcoEditCast(Context ctx) {
    	return ctx.formParam("editCast");
    }
}


	
