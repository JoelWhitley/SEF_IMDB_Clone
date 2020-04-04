package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.Map;




public class IndexController {


    public static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        System.out.println(getQueryNameSearch(ctx));
        model.put("actorList", IndexSearchController.search(getQueryNameSearch(ctx)));
        ctx.render(Template.INDEX, model);
    };
    
    public static String getQueryNameSearch(Context ctx) {
        return ctx.formParam("showActorSearch");
    }


}
