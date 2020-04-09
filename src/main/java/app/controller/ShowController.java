package app.controller;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import io.javalin.http.Handler;

public class ShowController {

	
	public static Handler serveShowPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("showTitle", "hello?");
        
    };
}
