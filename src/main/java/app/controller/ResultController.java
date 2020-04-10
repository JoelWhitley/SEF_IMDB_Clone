package app.controller;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.PersonDAO;
import app.dao.ShowDAO;
import io.javalin.http.Handler;

public class ResultController {

	public static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Template.RESULT, model);
    };
    
    public static Handler handleIndexPost = ctx -> {
    	Map<String, Object> model = ViewUtil.baseModel(ctx);
    	ctx.render(Template.PERSON,model);
    	
    	
    };
}
