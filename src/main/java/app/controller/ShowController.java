package app.controller;

import static app.controller.utils.RequestUtil.getParamShowId;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.ShowDAO;
import app.dao.UserReviewDAO;
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
    	 Map<String, Object> model = ViewUtil.baseModel(ctx);
    	 System.out.println(getReviewPost(ctx));
    	 
    };

    public static String getReviewPost(Context ctx) {
        return ctx.formParam("review");
    }

}
