package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.dao.utils.DatabaseUtils;
import app.model.UserReview;

public class suggestionDAO {
	
	//Need to fix review-id check. Because deleted reviews will subsequently cause duplicate id numbers.
	public static void insertReviewIntoDataBase(UserReview review) {
		
		//Getting number of reviews for the new reviewID
		String countReviewsQuery = "Select COUNT(reviewId) from `user_review`;";
		
		try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(countReviewsQuery);
            result.next();
            
            //Insert query.
            String insertQuery = String.format("INSERT INTO `user_review` VALUES ('%s', '%s', '%s', '%s', '%s', '%s')", 
            		result.getInt(1) +1, review.getShowID(), review.getUsername(), 
            		review.getRating(), review.getReview(), review.getDate());
            		
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

