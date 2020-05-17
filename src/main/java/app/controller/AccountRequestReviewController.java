package app.controller;

import java.util.Map;
import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.AccountDAO;
import app.model.enumeration.AccountRole;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AccountRequestReviewController {
	
	//serve page
	public static Handler serveRequestReviewPage = ctx -> {
		Map<String, Object> model = ViewUtil.baseModel(ctx);
		//list each show by type
		serveAllUserLists(model);
        ctx.render(Template.REQUEST_REVIEW, model);
    };
    
    //handle admin action
    public static Handler handleAdminAction = ctx -> {
    	String username;
		if (getFormUsername(ctx) != null) {
			try {
				username = getFormUsername(ctx);
				handleStatusUpdate(username, getFormUserType(ctx), getFormDecision(ctx));
			}
			catch (NumberFormatException e) {

			}
		}
		
		Map<String, Object> model = ViewUtil.baseModel(ctx);
		serveAllUserLists(model);
        ctx.render(Template.REQUEST_REVIEW, model);
    };
    
    //handle action depending on status
    public static void handleStatusUpdate(String username, String currentStatus, boolean decision) {
    	if (currentStatus.equals(AccountRole.PENDING_PROCO.getString())) {
    		if (decision) {
    			AccountDAO.updateUserType(username, AccountRole.PROCO);
    		}
    		else {
    			AccountDAO.updateUserType(username, AccountRole.USER);
    			AccountDAO.banUserForTime(username, 1, "days");
    		}
    	} 
    	else if (currentStatus.equals(AccountRole.PENDING_CRITIC.getString())) {
    		if (decision) {
    			AccountDAO.updateUserType(username, AccountRole.CRITIC);
    		}
    		else {
    			AccountDAO.updateUserType(username, AccountRole.USER);
    			AccountDAO.banUserForTime(username, 1, "days");
    		}
    	}     		
    }
    
    //return form values
    public static String getFormUsername(Context ctx) {
        return ctx.formParam("username");
    }
    public static Boolean getFormDecision(Context ctx) {
    	Boolean result = false;
    	if (ctx.formParam("accept") != null) {
    		result = true;
    	}
    	return result;
    }
    public static String getFormUserType(Context ctx) {
		return ctx.formParam("role");
    }
    
    //serve all lists
    public static void serveAllUserLists(Map<String, Object> model) {
    	model.put("pendingProCo", AccountDAO.getAccountsByType(AccountRole.PENDING_PROCO));
        model.put("pendingCritic", AccountDAO.getAccountsByType(AccountRole.PENDING_CRITIC));
        model.put("proCo", AccountDAO.getAccountsByType(AccountRole.PROCO));
        model.put("critic", AccountDAO.getAccountsByType(AccountRole.CRITIC));
        model.put("users", AccountDAO.getAccountsByType(AccountRole.USER));
        model.put("admins", AccountDAO.getAccountsByType(AccountRole.ADMIN));
    }
    

}
