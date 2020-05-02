package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.dao.utils.DatabaseUtils;
import app.model.Account;
import app.model.ProductionCompany;
import app.model.Show;
import app.model.UserReview;

public class suggestionDAO {
	
	public static void insertSuggestionShow(Show suggestion, String user) {
		
		//Getting number of reviews for the new reviewID
		String countReviewsQuery = "Select COUNT(showid) from `show`;";
		
		try {
			
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(countReviewsQuery);
            result.next();
            
            
            String company = suggestion.getProCo();
            
            String status = "";
            
            if (user.equals("admin")) status = "visable";
            else if (user.equals("proco")) status = "procosubmission";
            else status = "usersubmission";
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            Date date = new Date();  
            String dateTime = formatter.format(date);
            
            //Insert query.
            String insertQuery = String.format("INSERT INTO `show` VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", 
            		result.getInt(1) +1, suggestion.getShowTitle(), suggestion.getGenre(), 
            		suggestion.getLength(), suggestion.getIsMovie(), suggestion.getIsSeries(),
            		ProCoDAO.findProCoID(company), suggestion.getYear(), status, dateTime);
            		
            try {
            	PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            	insertStatement.execute();
            }
            catch (Exception e) {
    	        e.printStackTrace();
    	    }
            
            DatabaseUtils.closeConnection(connection);
		}
		catch (Exception e) {
	        e.printStackTrace();
	    }

	}
	
	
}

