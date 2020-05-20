package app.controller;

import static app.controller.utils.RequestUtil.getParamShowId;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.ShowDAO;
import app.dao.UserReviewDAO;
import app.model.Show;
import io.javalin.http.Handler;

public class ShowProcoController {
	
	public static Handler serveEditShowPage = ctx -> {
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
		
		ctx.render(Template.EDITSHOW, model);
	};
}
