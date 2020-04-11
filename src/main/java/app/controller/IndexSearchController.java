package app.controller;



import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.ShowDAO;
import io.javalin.http.Handler;
import static app.controller.utils.RequestUtil.*;

public class IndexSearchController {
	
	 		public static Handler fetchShow = ctx -> {
	        Map<String, Object> model = ViewUtil.baseModel(ctx);
	        model.put("show", ShowDAO.getShowById(getParamShowId(ctx)));
	        ctx.render(Template.SHOW, model);
	    };
}
