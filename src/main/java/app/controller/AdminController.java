package app.controller;

import java.util.Map;
import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.ShowDAO;
import app.model.enumeration.showStatus;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AdminController {
	
	//serve page
	public static Handler serveAdminPage = ctx -> {
		Map<String, Object> model = ViewUtil.baseModel(ctx);
		//list each show by type
		serveAllShowLists(model);
        ctx.render(Template.ADMIN, model);
    };
    
    //handle admin action
    public static Handler handleAdminAction = ctx -> {
    	int index;
		if (getFormShowIndex(ctx) != null) {
			try {
				index = Integer.parseInt(getFormShowIndex(ctx));
				handleStatusUpdate(index, getFormShowStatus(ctx), getFormDecision(ctx));
			}
			catch (NumberFormatException e) {

			}
		}
		
		Map<String, Object> model = ViewUtil.baseModel(ctx);
		serveAllShowLists(model);
        ctx.render(Template.ADMIN, model);
    };
    
    //handle action depending on status
    public static void handleStatusUpdate(int showIndex, String currentStatus, boolean decision) {
    	
    	if (currentStatus.equals(showStatus.USERSUBMISSION.getString())) {
    		if (decision) {
    			ShowDAO.changeShowStatus(showIndex,showStatus.VISABLE);
    		}
    		else {
    			ShowDAO.deleteShow(showIndex);
    		}
    	} else if (currentStatus.equals(showStatus.PROCOSUBMISSION.getString())) {
    		if (decision) {
    			ShowDAO.changeShowStatus(showIndex,showStatus.VISABLE);
    		}
    		else {
    			ShowDAO.changeShowStatus(showIndex,showStatus.PENDING);
    		}
    	} else if (currentStatus.equals(showStatus.PENDING.getString())) {
    		if (decision) {
    			ShowDAO.changeShowStatus(showIndex,showStatus.VISABLE);
    		}
    		else {
    			ShowDAO.deleteShow(showIndex);
    		}
    	} else if (currentStatus.equals(showStatus.SUSPENDED.getString())) {
    		if (decision) {
    			ShowDAO.changeShowStatus(showIndex,showStatus.VISABLE);
    		}
    		else {
    			ShowDAO.deleteShow(showIndex);
    		}
    	} else if (currentStatus.equals(showStatus.VISABLE.getString())) {
    		if (decision) {
    			ShowDAO.changeShowStatus(showIndex,showStatus.SUSPENDED);
    		}
    	}
    }
    
    //return form values
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
    public static String getFormShowStatus(Context ctx) {
		return ctx.formParam("status");
    }
    
    //serve all lists
    public static void serveAllShowLists(Map<String, Object> model) {
    	model.put("userShows", ShowDAO.getShowsByType(showStatus.USERSUBMISSION));
        model.put("procoShows", ShowDAO.getShowsByType(showStatus.PROCOSUBMISSION));
        model.put("pendingShows", ShowDAO.getShowsByType(showStatus.PENDING));
        model.put("suspendedShows", ShowDAO.getShowsByType(showStatus.SUSPENDED));
        model.put("visableShows", ShowDAO.getShowsByType(showStatus.VISABLE));
    }
    

}
