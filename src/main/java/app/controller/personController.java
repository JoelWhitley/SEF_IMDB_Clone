package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import io.javalin.http.Handler;
import java.util.Map;

public class personController {


    public static Handler personPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        ctx.render(Template.PERSON, model);
    };


}
