package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.SearchIndexDAO;
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
    	model.put("shows", SearchIndexDAO.getShowsByTitle(getShowQuery(ctx)));
    	ctx.render(Template.INDEXSEARCH,model);
    };
    
    public static String getShowQuery(Context ctx) {
        return ctx.formParam("showTitleSearch");
    }
    
    


}
