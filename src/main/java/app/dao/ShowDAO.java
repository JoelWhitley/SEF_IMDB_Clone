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

    public static Show getShowByString(String titleSearch) {
        // Fish out the results
        List<Show> show = new ArrayList<>();

        try {
            // Here you prepare your sql statement
        
        	
/*
            String sql = "SELECT `show`.showid, `show`.show_title, `show`.genre, `show`.length, `show`.movie, `show`.series, `show`.proco_id, `show`.`year`"
            		+ "FROM `show`"
            		+ "LEFT JOIN `credits_roll`"
            		+ "ON `show`.showid = credits_roll.show_id"
            		+ "LEFT JOIN `person`"
            		+ "on credits_roll.person_id = person.person_id"
            		+ "WHERE ( UPPER(show_title) LIKE UPPER('%" + titleSearch + "%')"
            		+ "OR UPPER(genre) LIKE UPPER('%" + titleSearch + "%')"
            		+ "OR UPPER(person.fullname) LIKE UPPER('%" + titleSearch + "%')"
            		+ "OR UPPER(credits_roll.character_name) LIKE UPPER('%" + titleSearch + "%'))"
            		+ "group by showid;";
*/
        	String sql = "SELECT * FROM `show`;";


            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            while(result.next()) {
                show.add(   
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
        if(!show.isEmpty()) return show.get(0);
        // If we are here, something bad happened
        return null;
    }
    /*
    public static Show getShowByProducer(String fullName) {
        // Fish out the results
        List<Show> shows = new ArrayList<>();

        try {
       

            //sql script. Show show title by searching first name of actor
        	String sql = "SELECT `show`.showid, `show`.show_title, `show`.genre, `show`.length, `show`.movie, `show`.series, `show`.proco_id, `show`.`year`"
            		+ "FROM `show`"
            		+ "LEFT JOIN `credits_roll`"
            		+ "ON `show`.showid = credits_roll.show_id"
            		+ "LEFT JOIN `person`"
            		+ "on credits_roll.person_id = person.person_id"
            		+ "WHERE ( UPPER(show_title) LIKE UPPER('%" + fullName + "%')"
            		+ "OR UPPER(genre) LIKE UPPER('%" + fullName + "%')"
            		+ "OR UPPER(person.fullname) LIKE UPPER('%" + fullName + "%')"
            		+ "OR UPPER(credits_roll.character_name) LIKE UPPER('%" + fullName + "%'))"
            		+ "group by showid;";
           

            //sql script. Show Writer/Based-On & Search-By


            // Execute the query
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
    
    
    public static Show getShowByName(String fullName, String role) {
        // Fish out the results
        List<Show> shows = new ArrayList<>();

        try {
            //sql script. Show show title by searching first name of actor
            String sql = "SELECT `person`.person_id, `person`.fullname, `person`.`role`, `person`.birthdate, `person`.bio " +
                    "FROM `person` " +
                    "LEFT JOIN `credits_roll` " +
                    "ON credits_roll.person_id = person.person_id " +
                    "LEFT JOIN `show` " +
                    "on `show`.showid = credits_roll.show_id " +
                    "WHERE ( UPPER(show_title) LIKE UPPER('%" + fullName + "%') " +
                    "OR UPPER(person.`role`) LIKE UPPER('%" + role + "%') " +
                    "OR UPPER(genre) LIKE UPPER('%" + fullName + "%') " +
                    "OR UPPER(person.fullname) LIKE UPPER('%" + fullName + "%') " +
                    "OR UPPER(credits_roll.character_name) LIKE UPPER('%" + fullName + "%')) " +
                    "group by person_id;";
          

            // Execute the query
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
    */
    
}
