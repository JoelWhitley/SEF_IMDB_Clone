package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.PersonDAO;
import app.dao.ShowDAO;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.Map;




public class IndexController {


    public static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Template.INDEX, model);
    };
    
    public static Handler handleIndexPost = ctx -> {
    	Map<String, Object> model = ViewUtil.baseModel(ctx);
    	
    	if(getPersonQuery(ctx) != null) {
        	model.put("person", PersonDAO.getPersonByString(getPersonQuery(ctx)));

        	ctx.render(Template.PERSON,model);
    	}
    	else if(getShowQuery(ctx) != null) {
        	model.put("show", ShowDAO.getShowByString(getShowQuery(ctx)));

        	ctx.render(Template.SHOW,model);
    	}
    	
    };
    
    public static String getShowQuery(Context ctx) {
        return ctx.formParam("showTitleSearch");
    }
    
    
    public static String getPersonQuery(Context ctx) {
        return ctx.formParam("showPersonSearch");
    }



}
