package app;

import app.controller.AccountController;
import app.controller.AccountRequestController;
import app.controller.AdminController;
import app.controller.IndexController;
import app.controller.ShowSearchController;
import app.controller.AccountRequestReviewController;
import app.controller.UserPreviewController;
import app.controller.LoginController;
import app.controller.PersonSearchController;
import app.controller.ShowController;
import app.controller.ShowProcoController;
import app.controller.PersonController;
import app.controller.SuggestionController;
import app.controller.paths.Web;
import app.controller.utils.ViewUtil;

import io.javalin.Javalin;
import io.javalin.core.util.RouteOverviewPlugin;
import static io.javalin.apibuilder.ApiBuilder.*;

public class Main {

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/public");
            config.registerPlugin(new RouteOverviewPlugin("/routes"));
        }).start(getHerokuAssignedPort());

        app.routes(() -> {

            // You will have to update this, to limit who can see the reviews
            // before(LoginController.ensureLoginBeforeViewing);

            get(Web.INDEX, IndexController.serveIndexPage);
            post(Web.INDEX, IndexController.handleIndexPost);
            
            get(Web.LOGIN, LoginController.serveLoginPage);
            post(Web.LOGIN, LoginController.handleLoginPost);
            post(Web.LOGOUT, LoginController.handleLogoutPost);

            get(Web.ACCOUNT, AccountController.serveAccountPage);
            
            get(Web.USER, UserPreviewController.serveUserPage);
            
            get(Web.RESULT, PersonSearchController.servePersonResults);
           
            get(Web.SEARCHINDEX, ShowSearchController.serveShowResults);
            
            get(Web.REQUEST_REVIEW, AccountRequestReviewController.serveRequestReviewPage);
            post(Web.REQUEST_REVIEW, AccountRequestReviewController.handleAdminAction);
            
            get(Web.EDITSHOW,ShowProcoController.serveEditShowPage);
            post(Web.EDITSHOW,ShowProcoController.handleEditShowPage);
            
            get(Web.SHOW, ShowController.serveShowPage);
            post(Web.SHOW, ShowController.handleShowPage);
            
            
            
            get(Web.ADMIN, AdminController.serveAdminPage);
            post(Web.ADMIN, AdminController.handleAdminAction);

            get(Web.PERSON, PersonController.personPage);

			get(Web.SUGGESTION, SuggestionController.serveSuggestionPage);
			post(Web.SUGGESTION, SuggestionController.handleNewSuggestion);
			
			get(Web.REQUEST, AccountRequestController.serveRequestPage);
            post(Web.REQUEST, AccountRequestController.handleRequestForm);
		

            // Add new actions here
            // Seeing pages (get) and sending information in forms (post)
        });

        app.error(404, ViewUtil.notFound);
    }

    public static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000;
    }



}
