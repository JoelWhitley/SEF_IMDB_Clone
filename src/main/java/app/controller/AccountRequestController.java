package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.AccountDAO;
import app.dao.ProCoDAO;
import app.dao.ShowDAO;
import app.dao.SuggestionDAO;
import app.model.Show;
import app.model.enumeration.AccountRole;
import app.model.enumeration.ShowStatus;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.Calendar;
import java.util.Map;

public class AccountRequestController {


	
	public static Handler serveRequestPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Template.REQUEST, model);
        
    };
    
    public static Handler handleRequestForm = ctx -> {
    	String currentUser = getFormUser(ctx);
    	if(getFormAccountType(ctx).equals("pco")) {
    		AccountDAO.updateUserType(currentUser, AccountRole.PENDING_PROCO);
    	} 
    	else if (getFormAccountType(ctx).equals("critic")) {
    		AccountDAO.updateUserType(currentUser, AccountRole.PENDING_CRITIC);
    	}
    	Map<String, Object> model = ViewUtil.baseModel(ctx);
    	model.put("message", "Request has been sent, and an admin will review it.");
        ctx.render(Template.REQUEST, model);
    };
    
    
    public static String getFormUser(Context ctx) {
        return ctx.formParam("user");
    }
    
    public static String getFormAccountType(Context ctx) {
        return ctx.formParam("accountType");
    }
    
   

}
