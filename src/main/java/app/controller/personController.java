package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.PersonDAO;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.Map;

public class personController {


	
    public static Handler personPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("person", PersonDAO.getPersonByString(getPersonQuery(ctx)));
        ctx.render(Template.PERSON, model);
        
    };
    
    public static String getPersonQuery(Context ctx) {
    	return ctx.formParam("personSearch");
    }

    
}
