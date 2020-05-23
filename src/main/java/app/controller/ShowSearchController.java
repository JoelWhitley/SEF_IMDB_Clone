package app.controller;



import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.SearchIndexDAO;
import io.javalin.http.Handler;
import static app.controller.utils.RequestUtil.*;

public class ShowSearchController {	    	
	    	
	    	public static Handler serveShowResults = ctx -> {
	    		Map<String, Object> model = ViewUtil.baseModel(ctx);
	    		model.put("shows", SearchIndexDAO.searchAllShows(getSessionShowSearch(ctx)));
	        	ctx.render(Template.INDEXSEARCH,model);
	    	};
	  
}
