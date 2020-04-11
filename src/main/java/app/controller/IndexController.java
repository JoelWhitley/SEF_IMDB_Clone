package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.PersonDAO;
import app.dao.SearchIndexDAO;
import app.model.Image;
import app.model.Person;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.Map;




public class IndexController {


    public static Handler serveIndexPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        Person actorInfo = PersonDAO.getRandomActorInfo();
        Image actorImage = new Image(actorInfo.getPersonId(), true);
        model.put("actorBio", actorInfo.getBio());
        model.put("actorName", actorInfo.getFullName());
        model.put("actorImagePath", actorImage.getImagePath());
        ctx.render(Template.INDEX, model);
    };
    

    public static Handler handleIndexPost = ctx -> {
    	Map<String, Object> model = ViewUtil.baseModel(ctx);
    	
    	
    	if(getQueryNameSearch(ctx) != null) {
        	model.put("persons", PersonDAO.getPersonByString(getQueryNameSearch(ctx)));
        	ctx.render(Template.RESULT,model);
        	//ctx.render(Template.PERSON,model);
    	}
    	else if(getShowQuery(ctx) != null) {
        	model.put("shows", SearchIndexDAO.getShowsByTitle(getShowQuery(ctx)));
        	ctx.render(Template.INDEXSEARCH,model);
    	}

    };
    
    public static String getShowQuery(Context ctx) {
        return ctx.formParam("showTitleSearch");
    }
    
    public static String getQueryNameSearch(Context ctx) {
        return ctx.formParam("showActorSearch");
    }


}
