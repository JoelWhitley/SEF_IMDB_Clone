package app.controller;

import static app.controller.utils.RequestUtil.getParamShowId;
import static app.controller.utils.RequestUtil.getParamPersonId;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.paths.Web;
import app.controller.utils.RequestUtil;
import app.controller.utils.ViewUtil;
import app.dao.ShowDAO;
import app.model.Show;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ShowProcoCharacterController {
	
	public static Handler serveEditCharacterPage = ctx -> {
		
		ShowDAO.updateProCoShowStatus();
		Map<String, Object> model = ViewUtil.baseModel(ctx);
		Show pageShow = ShowDAO.getShowById(getParamShowId(ctx));
		if (pageShow.getStatus().toString().equals("VISABLE")) {
			model.put("charCast", ShowDAO.getCastName(getParamShowId(ctx),getParamPersonId(ctx)));
			
			model.put("cast", ShowDAO.getCast(getParamShowId(ctx)));
	        model.put("show", ShowDAO.getShowById(getParamShowId(ctx)));
	        model.put("cast", ShowDAO.getCast(getParamShowId(ctx),"NOT "));
	        model.put("crew",ShowDAO.getCast(getParamShowId(ctx),""));

		}
		else {
			model.put("show.showTitle", "Error. This show has not been "
					+ "approved for our database yet. Please wait for an admin to review it.");
		}
		
		ctx.render(Template.EDITCHARACTER, model);
		
	};
	
	public static Handler handleEditCharacterPage = ctx ->{
		ShowDAO.editCharName(getParamShowId(ctx), getParamPersonId(ctx), getCharacterName(ctx));
		
		String varShow = Web.SHOW.replace(":showid", ctx.pathParam("showid", Integer.class).get().toString());

		ctx.redirect(varShow);
	};
	
	public static String getCharacterName(Context ctx) {
        return ctx.formParam("charName");
    }
	
    
	
}
