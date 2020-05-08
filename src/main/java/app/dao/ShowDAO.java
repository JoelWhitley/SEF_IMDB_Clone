package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.dao.utils.DatabaseUtils;
import app.model.CreditsRoll;
import app.model.Person;
import app.model.Show;
import app.model.enumeration.showStatus;

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
	              new Show(result.getInt("showid"),result.getString("show_title")
	            		  ,result.getDouble("length")
	            		  ,result.getBoolean("movie"),result.getBoolean("series")
	            		  ,result.getString("genre"),result.getInt("year"),showStatus.VISABLE
	            		  ,result.getString("proco_id")) 
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
	public static List<CreditsRoll> getCast(int id) {
		List<CreditsRoll> cast = new ArrayList<>();
		String sql = "SELECT * FROM credits_roll WHERE show_id ='" + id + "'";
		try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            // If you have multiple results, you do a while
	        while(result.next()) {
	        	Person p = PersonDAO.getPersonById(result.getInt("person_id"));
	            cast.add(  	
	              new CreditsRoll(p.getPersonId(),result.getString("role"), p.getFullName(),result.getString("character_name")) 
	              );
	        }
	
	        // Close it
	        DatabaseUtils.closeConnection(connection);
		    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
     // If there is a result
	    if(!cast.isEmpty()) return cast;
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

	
	public static List<Show> getShowsByPending() {
		List<Show> shows =  new ArrayList<>();
		String sql = "SELECT * FROM `show` WHERE status ='" + showStatus.USERSUBMISSION.getString() + "'";
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            // If you have multiple results, you do a while
	        while(result.next()) {
	            shows.add(   
	              new Show(result.getInt("showid"),result.getString("show_title"), result.getDouble("length"), 
	            		  result.getBoolean("movie"),result.getBoolean("series"),result.getString("genre"),result.getInt("year"),showStatus.USERSUBMISSION,
	              		result.getString("proco_id")) 
	              );
	        }
	
	        // Close it
	        DatabaseUtils.closeConnection(connection);
		    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
     // If there is a result
	    if(!shows.isEmpty()) return shows;
	    // If we are here, something bad happened
	    return null;
	}

	public static int getHighestShowID() {
		List<Show> shows =  new ArrayList<>();
		String sql = "SELECT * FROM `show`";
		int last = 0;
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
	        while(result.next()) {
	            last++;
	        }
	
	        // Close it
	        DatabaseUtils.closeConnection(connection);
		    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	
	
	    // If there is a result
	    if(!shows.isEmpty()) return last;
	    // If we are here, something bad happened
	    return 1;
	}

	public static void updateStatus() {
		Connection connection;
		try {
			connection = DatabaseUtils.connectToDatabase();
			String updateQuery = "UPDATE `show` SET status = 'VISABLE' WHERE status = 'PROCOSUBMISSION' AND DATEDIFF(datetime_submitted, CURRENT_TIMESTAMP ) <= -1;";
			PreparedStatement insertStatement = connection.prepareStatement(updateQuery);
	    	insertStatement.execute();
		} catch (Exception e) {
			System.out.println("Error connecting to database");
		}
		
	
	}


	public static void changeShowStatus(int index, showStatus status) {
		Connection connection;
		try {
			connection = DatabaseUtils.connectToDatabase();
			String updateQuery = "UPDATE `show` SET status = '" + status.getString() + "' WHERE showid = " + index + ";";
			PreparedStatement insertStatement = connection.prepareStatement(updateQuery);
	    	insertStatement.execute();
		} catch (Exception e) {
			System.out.println("Error connecting to database");
		}
		
	}


	public static void deleteShow(int index) {
		Connection connection;
		try {
			connection = DatabaseUtils.connectToDatabase();
			String updateQuery = "DELETE FROM `show` WHERE showid = " + index + ";";
			PreparedStatement insertStatement = connection.prepareStatement(updateQuery);
	    	insertStatement.execute();
		} catch (Exception e) {
			System.out.println("Error connecting to database");
		}
		
	}
	
	public static int getLowestUnusedID() {
		String sql = "SELECT * FROM `show`";
		int currentRow = 1;
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
	        while(result.next()) {
	            if (result.getInt("showid") != currentRow) {
	            	DatabaseUtils.closeConnection(connection);
	            	return currentRow;
	            }
	            currentRow++;
	        }

		}
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	
        return currentRow;
	}
}

	
       

