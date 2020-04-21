package app.controller.utils;


import io.javalin.http.Context;

public class RequestUtil {


    public static String getQueryLoginRedirect(Context ctx) {
        return ctx.queryParam("loginRedirect");
    }

    public static String getSessionCurrentUser(Context ctx) {
        return (String) ctx.sessionAttribute("currentUser");
    }
    
    public static String getSessionShowSearch(Context ctx) {
        return (String) ctx.sessionAttribute("showSearch");
    }
    
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





}
