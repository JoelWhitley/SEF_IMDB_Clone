package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.PersonDAO;
import app.model.Person;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.Map;




public class IndexController {


    public static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Person actorInfo = PersonDAO.getRandomActorInfo();
        model.put("actorBio", actorInfo.getBio());
        model.put("actorName", actorInfo.getFullName());
        
        ctx.render(Template.INDEX, model);
    };
    
    
    
    
    public static String getQueryNameSearch(Context ctx) {
        return ctx.formParam("showActorSearch");
    }
    
    public static String getShowSearch(Context ctx) {
        return ctx.formParam("showTitleSearch");
    }
}
