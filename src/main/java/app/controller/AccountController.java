package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import io.javalin.http.Handler;

import java.util.Map;



public class AccountController {

    public static Handler serveAccountPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        // You'll have to update the model... maybe here

        ctx.render(Template.ACCOUNT, model);
    };




}
