package app.controller.utils;


import io.javalin.http.Context;

public class RequestUtil {


	//login redirect
    public static String getQueryLoginRedirect(Context ctx) {
        return ctx.queryParam("loginRedirect");
    }

    //current user
    public static String getSessionCurrentUser(Context ctx) {
        return (String) ctx.sessionAttribute("currentUser");
    }
    
    //string to search for (shows)
    public static String getSessionShowSearch(Context ctx) {
        return (String) ctx.sessionAttribute("showSearch");
    }
    
    //string to search for (people)
    public static String getSessionPersonSearch(Context ctx) {
        return (String) ctx.sessionAttribute("personSearch");
    }
    
    
    public static int getParamShowId(Context ctx) {
        return Integer.parseInt(ctx.pathParam("showid"));
    }


    public static int getParamPersonId(Context ctx) {
        return Integer.parseInt(ctx.pathParam("personId"));
    }
    
    public static String getParamUsername(Context ctx) {
    	return ctx.pathParam("username");
    }


    //new suggestion set


}
