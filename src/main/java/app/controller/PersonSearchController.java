package app.controller;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import static app.controller.utils.RequestUtil.getSessionPersonSearch;
import app.dao.PersonDAO;
import io.javalin.http.Handler;

public class PersonSearchController {
	public static Handler servePersonResults = ctx -> {
		Map<String, Object> model = ViewUtil.baseModel(ctx);
		model.put("persons", PersonDAO.getPersonByString(getSessionPersonSearch(ctx)));
    	ctx.render(Template.RESULT,model);
	};
	
}
