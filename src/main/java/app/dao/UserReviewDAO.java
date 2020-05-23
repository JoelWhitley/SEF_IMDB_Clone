package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.dao.utils.DatabaseUtils;
import app.model.UserReview;

public class UserReviewDAO {
	
	/**
     * Method to grab a review from the database
     *
     * @return the review with that ID
     * @param id ID that matches a review in the database
     * @throws SQLexception Tried to execute a statement that was not valid
     */
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
	
	/**
     * Method to grab all reviews for one show from the database
     *
     * @return the reviews with that show ID
     * @param showID ID that matches a show in the database
     * @throws SQLexception Tried to execute a statement that was not valid
     */
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
	
	/**
     * Method to grab all reviews for one user from the database
     *
     * @return the reviews with that username attached
     * @param username username that matches a user in the database
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static List<UserReview> searchReviewByUsername(String username) {
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
	
	/**
     * Method to insert a new review into the DB
     *
     * @param review a constructed review object to be inserted
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	
	//TODO
	//Need to fix review-id check. Because deleted reviews will subsequently cause duplicate id numbers.
	public static void insertReviewIntoDataBase(UserReview review) {
		
		//String countReviewsQuery = "Select COUNT(reviewId) from `user_review`;";
		String maxQuery = "SELECT MAX(reviewId) FROM user_review";
		
		try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(maxQuery);
            result.next();
            
            //Insert query.
            String insertQuery = String.format("INSERT INTO `user_review` VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
            		result.getInt(1) +1, review.getShowID(), review.getUsername(),
            		review.getRating(), review.getReview().replace("'", "''").replace("\\", "\\\\"),review.getDate());
            
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
	
	
	/**
     * Method to grab all reviews for one user from the database
     *
     * @param username username that matches a user in the database
     * @param showId a showID for a show in the database that the user has commented on
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static void deleteReviewInDataBase(String username, int showId) {
		
		//check for the review that the user has made on this show (can only be one)
		//delete it
		String deleteQuery = "DELETE FROM user_review WHERE user_id = '" + username 
		+ "' AND show_id = " + showId + ";";
		
		try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            PreparedStatement insertStatement = connection.prepareStatement(deleteQuery);
         	insertStatement.execute();
                       
            DatabaseUtils.closeConnection(connection);
		}
		catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}

