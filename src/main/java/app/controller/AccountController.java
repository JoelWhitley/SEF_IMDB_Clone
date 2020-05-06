package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.model.Account;
import io.javalin.http.Handler;

import java.util.Map;



public class AccountController {

    public static Handler serveAccountPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        
        // You'll have to update the model... maybe here
        if(ctx.sessionAttribute("currentUser") != null) {
			Account user = UserController.getUserInfo(ctx.sessionAttribute("currentUser"));
        	model.put("nouser", false);
            model.put("username", user.getUsername());
            model.put("firstname", user.getFirstName());
            model.put("lastname", user.getLastName());
            model.put("address", user.getAddress());
            model.put("country", user.getCountry());
            model.put("gender", user.getGender());
            model.put("email", user.getEmail());
            model.put("type", user.getType());
            
        }
        else {
        	model.put("nouser", true);
        }
        
        ctx.render(Template.ACCOUNT, model);
    };


}
