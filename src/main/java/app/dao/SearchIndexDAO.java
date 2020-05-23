package app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.dao.utils.DatabaseUtils;
import app.model.Show;
import app.model.enumeration.ShowStatus;

public class SearchIndexDAO {	
	
	//TODO cleanup? Rico
	/**
     * Method to get shows using their title, genre, any character's names 
     * or actor's names, given that the show is visable
     *
     * @param search term to search for
     * @return list of shows that match in any way
     * @throws SQLexception Tried to execute a statement that was not valid
     */
    public static List<Show> searchAllShows(String search) {
    	
    	ShowDAO.updateProCoShowStatus();
    	
        // Fish out the results
        List<Show> shows = new ArrayList<>();
        	//%" + search + "%
        try {
            // Here you prepare your sql statement
            String sql = "SELECT `show`.showid, `show`.show_title, `show`.genre, `show`.length, `show`.movie, `show`.series, `show`.proco_id, `show`.`year` " + 
            		"FROM `show` " + 
            		"LEFT JOIN `credits_roll` " + 
            		
            		"ON `show`.showid = credits_roll.show_id " + 
            		"LEFT JOIN `person` " + 
            		"on credits_roll.person_id = person.person_id " + 
            		"WHERE ( UPPER(show_title) LIKE UPPER('%" + search + "%') " + 
            		"OR UPPER(genre) LIKE UPPER('%" + search + "%') " + 
            		"OR UPPER(person.fullname) LIKE UPPER('%" + search + "%') " + 
            		"OR UPPER(credits_roll.character_name) LIKE UPPER('%" + search + "%')) " +
            		"AND `show`.status LIKE 'VISABLE'" +
            		"group by showid;";


            //sql script. Show show title by searching first name of actor
            //String sql = "SELECT show_title 
            //FROM credits_roll,`show`, person 
            //WHERE credits_roll.show_id = `show`.showid AND person.person_id = credits_roll.person_id AND UPPER(fullname) LIKE '%" + firstname + "%';";

            //sql script. search actor by first name

            //String sql = "SELECT *
            //FROM person;
            //WHERE fullname LIKE '%" + name + "%';";

            //sql script. Show Writer/Based-On & Search-By


            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            while(result.next()) {
                shows.add(   
                  new Show(result.getInt("showid"),
                		  result.getString("show_title"), 
                		  result.getDouble("length"),
                		  result.getBoolean("movie"),
                		  result.getBoolean("series"),
                		  result.getString("genre"),
                		  result.getInt("year"),
                		  ShowStatus.VISABLE,
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


}
