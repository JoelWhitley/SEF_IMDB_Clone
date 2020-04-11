package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.PersonDAO;
import io.javalin.http.Handler;
import java.util.Map;
import static app.controller.utils.RequestUtil.*;

public class personController {


	
    public static Handler personPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("person", PersonDAO.getPersonById(getParamPersonId(ctx)));
        ctx.render(Template.PERSON, model);
        
    };
    
   

    
}
