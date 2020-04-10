package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
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
    	
    	model.put("show", ShowDAO.getShowByTitle(getShowQuery(ctx)));
    	ctx.render(Template.SHOW,model);
    };
    
    public static String getShowQuery(Context ctx) {
        return ctx.formParam("showTitleSearch");
    }
    
    //wip
    public static String getActorQuery(Context ctx) {
        return ctx.formParam("showTitleSearch");
    }
    
    //wip
    public static String getProducerQuery(Context ctx) {
        return ctx.formParam("showProducerSearch");
    }


}
