package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.dao.utils.DatabaseUtils;
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
			String updateQuery = "UPDATE `show` SET status = 'VISABLE' WHERE status = 'PROCOSUBMISSION' AND DATEDIFF(submitted, CURRENT_TIMESTAMP ) <= -1;";
			PreparedStatement insertStatement = connection.prepareStatement(updateQuery);
	    	insertStatement.execute();
		} catch (Exception e) {
			System.out.println("Error connecting to database");
		}
		
	
	}
}

	
       

