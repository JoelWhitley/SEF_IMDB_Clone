package app.controller.paths;


/**
 * This one contains the location of the velocity template to be used to show a particular form.
 */
public class Template {
    public static final String INDEX = "/velocity/index/index.vm";
    public static final String NOT_FOUND = "/velocity/notFound.vm";
    public static final String LOGIN = "/velocity/login/login.vm";
    
    public static final String ACCOUNT = "/velocity/account/account.vm";
    public static final String USER = "/velocity/user/user.vm";
    public static final String ADMIN = "/velocity/user/admin.vm";

    public static final String SHOW = "/velocity/show/show.vm";
    public static final String INDEXSEARCH = "/velocity/index/indexSearch.vm";

    public static final String SUGGESTION = "/velocity/suggestion/suggestion.vm";
    
    public static final String REQUEST = "/velocity/accountRequest/accountRequest.vm";
    public static final String REQUEST_REVIEW = "/velocity/user/accountRequests.vm";
    
    public static final String PERSON = "/velocity/person/person.vm";
    public static final String RESULT = "/velocity/result/result.vm";
}