package app.controller;

import static app.controller.utils.RequestUtil.getParamShowId;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.paths.Web;
import app.controller.utils.RequestUtil;
import app.controller.utils.ViewUtil;
import app.dao.ShowDAO;
import app.model.Show;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ShowProcoController {
	
	public static Handler serveEditShowPage = ctx -> {
		
		ShowDAO.updateProCoShowStatus();
		Map<String, Object> model = ViewUtil.baseModel(ctx);
		Show pageShow = ShowDAO.getShowById(getParamShowId(ctx));
		if (pageShow.getStatus().toString().equals("VISABLE")) {
			
	        model.put("show", ShowDAO.getShowById(getParamShowId(ctx)));

		}
		else {
			model.put("show.showTitle", "Error. This show has not been "
					+ "approved for our database yet. Please wait for an admin to review it.");
		}
		
		ctx.render(Template.EDITSHOW, model);
		
	};
	
	public static Handler handleEditShowPage = ctx ->{
		
		ShowDAO.editShow(RequestUtil.getParamShowId(ctx), getShowTitle(ctx), getShowLength(ctx), getShowGenre(ctx));
		String varShow = Web.SHOW.replace(":showid", ctx.pathParam("showid", Integer.class).get().toString());

		ctx.redirect(varShow);
	};
	
	public static String getShowTitle(Context ctx) {
        return ctx.formParam("showTitle");
    }
	
	public static String getShowGenre(Context ctx) {
        return ctx.formParam("showGenre");
    }
	
	public static double getShowLength(Context ctx) {
        return Double.parseDouble(ctx.formParam("showLength"));
    }
}
