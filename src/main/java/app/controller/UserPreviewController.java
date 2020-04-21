package app.controller;

import static app.controller.utils.RequestUtil.getParamUsername;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.ShowDAO;
import app.dao.UserReviewDAO;
import app.model.Account;
import app.model.Show;
import app.model.UserReview;
import io.javalin.http.Handler;

public class UserPreviewController {
	public static Handler serveUserPage = ctx -> {
		Map<String, Object> model = ViewUtil.baseModel(ctx);
		Account user = UserController.getUserInfo(getParamUsername(ctx));
        model.put("username", user.getUsername());
        model.put("country", user.getCountry());
        model.put("gender", user.getGender());
		
		List<UserReview> reviews = UserReviewDAO.searchReviewByUsername(getParamUsername(ctx));
        model.put("reviews", reviews);
        List<Show> shows = new ArrayList<Show>();
        for(UserReview review : reviews) {
        	shows.add(ShowDAO.getShowById(review.getShowID()));
        }
        model.put("shows", shows);
        ctx.render(Template.USER, model);
    };
}
