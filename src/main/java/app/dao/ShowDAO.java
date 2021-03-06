package app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

	}

	
       

