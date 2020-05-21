package app.controller.paths;


/**
 * his class contains the UTL fragments of the site. If you are going to login, then the
 * URL is going to be https://someurl.com/login
 *
 * If you want to add pages, you need to add that information here.
 */
public class Web {

    public static final String INDEX = "/";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
   
    //Viewing own account
    public static final String ACCOUNT = "/account";
    
    //Viewing another user profile
    public static final String USER = "/user/:username";
    public static final String SHOW = "/shows/:showid";
    public static final String REVIEW = "/review";
    public static final String EDITSHOW = "/shows/:showid/showEdit";
    //public static final String EDITCAST = "/edit/persons/:personId";
    
    public static final String SUGGESTION = "/suggestion";
    public static final String REQUEST = "/request";
    public static final String REQUEST_REVIEW = "/accountReview";
    
    public static final String SEARCHINDEX = "/search";

    public static final String PERSON = "/persons/:personId";

    public static final String RESULT = "/result";
    
    public static final String ADMIN = "/admin";

}