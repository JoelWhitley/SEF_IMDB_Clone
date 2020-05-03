package app.controller;

import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.ShowDAO;
import io.javalin.http.Handler;

public class AdminController {
	public static Handler serveAdminPage = ctx -> {
		Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("shows", ShowDAO.getShowsByPending());
        ctx.render(Template.ADMIN, model);
    };
    
    public static Handler handleAdminAction = ctx -> {
		Map<String, Object> model = ViewUtil.baseModel(ctx);
        model.put("shows", ShowDAO.getShowsByPending());
        ctx.render(Template.ADMIN, model);
    };
}
