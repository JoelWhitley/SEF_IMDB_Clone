package app.controller;

import app.controller.paths.Template;
import app.controller.utils.ViewUtil;
import app.dao.AccountDAO;
import app.dao.ProCoDAO;
import app.dao.ShowDAO;
import app.dao.SuggestionDAO;
import app.model.Account;
import app.model.Show;
import app.model.enumeration.ShowStatus;
import io.javalin.http.Context;
import io.javalin.http.Handler;

import java.util.Calendar;
import java.util.Map;

public class SuggestionController {


	
	public static Handler serveSuggestionPage = ctx -> {
        Map<String, Object> model = ViewUtil.baseModel(ctx);
        //added 10 years of ahead time for movies anticipated to come out.
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int[] years = new int[currentYear + 11 - 1800];
        for (int i = currentYear + 10; i >= 1800; i--) {
        	years[currentYear + 10 - i] = i;
        }
        model.put("years", years);
        model.put("procos", ProCoDAO.getAllProCo());
        ctx.render(Template.SUGGESTION, model);
        
    };
    
    public static Handler handleNewSuggestion = ctx -> {
    	Show suggestion = null;
    	Account currentUser = AccountDAO.getUserByUsername(getFormUser(ctx));
    	//form a suggestion show
    	int showID = ShowDAO.getLowestUnusedID();
		if (getFormTitle(ctx) != null) {
			//visable is a placeholder, gets replaced in the DAO
			suggestion = new Show(showID, getFormTitle(ctx), 
									getFormLength(ctx), getFormIsMovie(ctx),
									getFormIsSeries(ctx), getFormGenre(ctx),
									getFormYear(ctx), ShowStatus.VISABLE, getFormProCo(ctx));
		}
		//if there is another show of this name, bring up an error, else just redirect
		//to a preview of the page.
		if (ShowDAO.checkDuplicateName(suggestion.getShowTitle()) == false) {
			SuggestionDAO.insertSuggestionShow(suggestion,currentUser);
	    	Map<String, Object> model = ViewUtil.baseModel(ctx);
	        model.put("show", ShowDAO.getShowById(showID));
	        ctx.render(Template.SHOW, model);
		}
		else {
	    	Map<String, Object> model = ViewUtil.baseModel(ctx);
	        model.put("error", "This entry is a duplicate. If you believe this is incorrect"
	        		+ ", please contact an admin to edit the conflicting page.");
	        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	        int[] years = new int[currentYear + 11 - 1800];
	        for (int i = currentYear + 10; i >= 1800; i--) {
	        	years[currentYear + 10 - i] = i;
	        }
	        model.put("years", years);
	        model.put("procos", ProCoDAO.getAllProCo());
	        ctx.render(Template.SUGGESTION, model);
		}
		
    };
    
    
    public static String getFormTitle(Context ctx) {
        return ctx.formParam("show_title");
    }
    
    public static String getFormGenre(Context ctx) {
        return ctx.formParam("genre");
    }
    
    public static float getFormLength(Context ctx) {
    	try {
    		return Float.parseFloat(ctx.formParam("length"));
    	}
    	catch (NumberFormatException e) {
    		return new Float(0.0);
    	}
        
    }
    
    public static boolean getFormIsMovie(Context ctx) {
        return ctx.formParam("movie_or_series").equals("movie");
    }
    
    public static boolean getFormIsSeries(Context ctx) {
        return ctx.formParam("movie_or_series").equals("series");
    }
    
    public static String getFormProCo(Context ctx) {
        return ctx.formParam("proco");
    }
    
    public static int getFormYear(Context ctx) {
    	String year = ctx.formParam("year");
    	if (year != null) {
    		try {
    			int yearInt = Integer.parseInt(year);
        		return yearInt;
        	}
        	catch (NumberFormatException e) {
        		return 2020;
        	}
    	}
    	return 2020;
        
    }
    
    public static String getFormUser(Context ctx) {
    	return ctx.formParam("user");
    }
    
}
