package app.controller;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.ShowDAO;
import app.model.enumeration.showStatus;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AdminController {
	public static Handler serveAdminPage = ctx -> {
		Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("shows", ShowDAO.getShowsByPending());
        ctx.render(Template.ADMIN, model);
    };
    
    public static Handler handleAdminAction = ctx -> {
    	
    	int index;
		if (getFormShowIndex(ctx) != null) {
			try {
				index = Integer.parseInt(getFormShowIndex(ctx));
				
				if (getFormDecision(ctx)) {
					ShowDAO.changeShowStatus(index,showStatus.VISABLE);
				}
				else {
					ShowDAO.deleteShow(index);
				}
				
				
			}
			catch (NumberFormatException e) {
				//someone been messing with our form data
			}
			
			
		}
		
		

    	
		Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("shows", ShowDAO.getShowsByPending());
        ctx.render(Template.ADMIN, model);
    };
    
    
    public static String getFormShowIndex(Context ctx) {
        return ctx.formParam("show");
    }
    
    public static Boolean getFormDecision(Context ctx) {
    	Boolean result = false;
    	if (ctx.formParam("accept") != null) {
    		result = true;
    	}
    	return result;
    }
}
