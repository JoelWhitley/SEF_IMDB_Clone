package app.dao;


import app.dao.utils.DatabaseUtils;
import app.model.Person;
import app.model.Show;
import app.model.enumeration.ShowStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PersonDAO {
	public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";

	
	/**
     * Method to fetch people from the database.
     *
     * @param personSearch Name or term typed by the user to search for
     * @throws SQLexception Tried to execute a statement that was not valid
     * @return people that match the search term
     */
    public static List<Person> getPersonByString(String personSearch) {
        // Fish out the results
        List<Person> person = new ArrayList<>();

        try {
       

            //sql script. Show show title by searching first name of actor
        	
        	String sql = "SELECT `person`.person_id, `person`.fullname, `person`.`role`, `person`.birthdate, `person`.bio " +
                    "FROM `person` " +
                    "LEFT JOIN `credits_roll` " +
                    "ON credits_roll.person_id = person.person_id " +
                    "LEFT JOIN `show` " +
                    "on `show`.showid = credits_roll.show_id " +
                    "WHERE ( UPPER(show_title) LIKE UPPER('%" + personSearch + "%') " +
                    "OR UPPER(person.`role`) LIKE UPPER('%" + personSearch + "%') " +
                    "OR UPPER(genre) LIKE UPPER('%" + personSearch + "%') " +
                    "OR UPPER(person.fullname) LIKE UPPER('%" + personSearch + "%') " +
                    "OR UPPER(credits_roll.character_name) LIKE UPPER('%" + personSearch + "%')) " +
                    "group by person_id;";
                    
            //sql script. Show Writer/Based-On & Search-By


            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            
            
            
            // If you have multiple results, you do a while
            while(result.next()) {
                person.add(   
                  new Person(result.getInt("person_id"),result.getString("fullname"), result.getString("role"),result.getDate("birthdate"),
                		  result.getString("bio"))
                  );
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // If there is a result
        if(!person.isEmpty()) return person;
        // If we are here, something bad happened
        return null;
    }

    /**
     * Method to fetch people by their ID
     *
     * @param id the person's database ID
     * @return the person's full details
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static Person getPersonById(int id) {

		List<Person> people = new ArrayList<>();

		try {
			// Here you prepare your sql statement
			String sql = "SELECT * FROM person WHERE person_id = '" + id + "'";

			// Execute the query
			Connection connection = DatabaseUtils.connectToDatabase();
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);

			// If you have multiple results, you do a while
        while(result.next()) {
            // 2) Add it to the list we have prepared
            people.add(
              // 1) Create a new account object
              new Person(result.getInt("person_id"), 
            		  result.getString("fullname"), 
            		  result.getString("role"), 
            		  result.getDate("birthdate"), 
            		  result.getString("bio"))
            );
        }

			// Close it
			DatabaseUtils.closeConnection(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!people.isEmpty()) return people.get(0);
		
		return null;
	}

	
	/**
     * Method to get a random actor's details
     * Used mainly on the index page to get a "featured person"
     *
     * @return the person's full details
     * @throws SQLexception Tried to execute a statement that was not valid
     */
    public static Person getRandomActorInfo() {
        // Fish out the results
        List<Person> people = new ArrayList<>();

        try {
            // Here you prepare your sql statement
            String sql = "select * from person order by rand() limit 1";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            while(result.next()) {
                // 2) Add it to the list we have prepared
            	people.add(
                  // 1) Create a new account object
                  new Person(result.getInt("person_id"),
                          result.getString("fullname"),
                          result.getString("role"),
                          result.getDate("birthdate"),
                          result.getString("bio"))
                );
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        // If there is a result
        if(!people.isEmpty()) return people.get(0);
        // If we are here, something bad happened
        return null;
    }

    
    /**
     * Method to fetch people's filmography given their ID
     *
     * @param personId the person's database ID
     * @return a list (show) that the person has stared in
     */
	public static List<Show> getFilmography(int personId) {
		// Fish out the results
        List<Show> filmography = new ArrayList<>();

        try {
            // Here you prepare your sql statement
            String sql = "SELECT `show`.*, production_company.proco_name"
            		+ " FROM `show`"
            		+ " JOIN credits_roll ON credits_roll.show_id=`show`.showid"
            		+ " JOIN person ON credits_roll.person_id=person.person_id"
            		+ " JOIN production_company ON production_company.proco_id=`show`.proco_id"
            		+ " WHERE credits_roll.person_id=" + personId
            		+ " AND `status`='VISABLE'"
            		+ " ORDER BY `year` DESC";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            while(result.next()) {
                // 2) Add it to the list we have prepared
            	           	
            	filmography.add(
            			 new Show(result.getInt("showid"),
            					 result.getString("show_title"),
            					 result.getDouble("length"),
            					 result.getBoolean("movie"),
            					 result.getBoolean("series"),
            					 result.getString("genre"),
            					 result.getInt("year"),
            					 ShowStatus.valueOf(result.getString("status")),
            					 result.getString("proco_name")
            					 )
                );
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        // If there is a result
        if(!filmography.isEmpty()) return filmography;
        // If we are here, something bad happened
        return null;
	}


}

