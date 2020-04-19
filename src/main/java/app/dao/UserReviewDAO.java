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

public class UserReviewDAO {
	
	//Used when constructing a Review to display.
	public static UserReview getReviewById(int id) {
		List<UserReview> reviews =  new ArrayList<>();
		String sql = "SELECT * FROM `user_review` WHERE reviewId ='" + id + "';";
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            // If you have multiple results, you do a while
	        while(result.next()) {
	            reviews.add(  
	              new UserReview(result.getInt("reviewId"), result.getString("user_id"), result.getInt("show_id"),
	            		  result.getInt("rating"), result.getString("review"), result.getDate("date"))
	              );
	        }
	
	        // Close it
	        DatabaseUtils.closeConnection(connection);
		    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	
	
	    // If there is a result
	    if(!reviews.isEmpty()) return reviews.get(0);
	    // If we are here, something bad happened
	    return null;
	}
	
	//Used when grabbing all of a show's reviews.
	public static List<UserReview> searchReviewByShowID(int showID) {
		List<UserReview> reviews =  new ArrayList<>();
		String sql = "SELECT * FROM `user_review` WHERE show_id ='" + showID + "';";
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            // If you have multiple results, you do a while
	        while(result.next()) {
	            reviews.add(  
	              new UserReview(result.getInt("reviewId"), result.getString("user_id"), result.getInt("show_id"),
	            		  result.getInt("rating"), result.getString("review"), result.getDate("date"))
	              );
	        }
	
	        // Close it
	        DatabaseUtils.closeConnection(connection);
		    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	
	
	    // If there is a result
	    if(!reviews.isEmpty()) return reviews;
	    // If we are here, something bad happened
	    return null;
	}
	
	//Used when displaying a user's review-list.
	public static List<UserReview> searchReviewByUsername(int username) {
		List<UserReview> reviews =  new ArrayList<>();
		String sql = "SELECT * FROM `user_review` WHERE user_id ='" + username + "';";
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            // If you have multiple results, you do a while
	        while(result.next()) {
	            reviews.add(  
	              new UserReview(result.getInt("reviewId"), result.getString("user_id"), result.getInt("show_id"),
	            		  result.getInt("rating"), result.getString("review"), result.getDate("date"))
	              );
	        }
	
	        // Close it
	        DatabaseUtils.closeConnection(connection);
		    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	
	
	    // If there is a result
	    if(!reviews.isEmpty()) return reviews;
	    // If we are here, something bad happened
	    return null;
	}
	
	public static void insertReviewIntoDataBase(UserReview review) {
		
		String countReviewsQuery = "Select COUNT(reviewId) from `user_review`;";
		
		try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(countReviewsQuery);
            result.next();
            
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

