package app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.math.*;

import app.dao.utils.DatabaseUtils;
import app.model.Person;
import app.model.Show;

public class ShowDAO {

	public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";


	public static Show getShowById(int id) {
		List<Show> shows =  new ArrayList<>();
		String sql = "SELECT * FROM `show` WHERE showid ='" + id + "'";
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            // If you have multiple results, you do a while
	        while(result.next()) {
	            shows.add(   
	              new Show(result.getInt("showid"),result.getString("show_title"), result.getDouble("length"),
	            		  result.getBoolean("movie"),result.getBoolean("series"),result.getString("genre"),result.getInt("year"))
	              );
	        }
	
	        // Close it
	        DatabaseUtils.closeConnection(connection);
		    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	
	
	    // If there is a result
	    if(!shows.isEmpty()) return shows.get(0);
	    // If we are here, something bad happened
	    return null;
	}
	
	
	public static int getStarRating(int id, int star) {
		int totalReviews = 0;
		//String sql = "SELECT * FROM `user_review` WHERE show_id = " + id + " AND rating = '" + star + "';";
		
		String sql = "SELECT COUNT(*) AS total FROM `user_review` WHERE show_id = " + id + " AND rating = '" + star + "';";
		
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            //System.out.println(sql);
            result.next();
            totalReviews = result.getInt(1);
            
            //System.out.println(totalReviews);

	        // Close it
	        DatabaseUtils.closeConnection(connection);
		    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    return totalReviews;
	}


	public static double getStarPercent(int id, int star) {
		double starReviews = 0;
		double allReviews = 0;
		//String sql = "SELECT * FROM `user_review` WHERE show_id = " + id + " AND rating = '" + star + "';";
		String sql = "SELECT COUNT(*) FROM `user_review` WHERE show_id = " + id + ";";
		
		 try {
	        	Connection connection = DatabaseUtils.connectToDatabase();
	            Statement statement = connection.createStatement();
	            ResultSet result = statement.executeQuery(sql);
	            
	            //System.out.println(sql);
	            result.next();
	            allReviews = result.getInt(1);
	            
	            //System.out.println(allReviews);
		
		        // Close it
		        DatabaseUtils.closeConnection(connection);
			    }
		    catch (Exception e) {
		        e.printStackTrace();
		    }
		
		sql = "SELECT COUNT(*) AS total FROM `user_review` WHERE show_id = " + id + " AND rating = '" + star + "';";
		
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            //System.out.println(sql);
            result.next();
            starReviews = result.getInt(1);
            
            //System.out.println(starReviews);
	
	        // Close it
	        DatabaseUtils.closeConnection(connection);
		    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
        double percent = (starReviews/allReviews)*1000;
        percent = Math.round(percent)/10;
        
        //System.out.println(percent);
	
	    return percent;
	}

}

	
       

