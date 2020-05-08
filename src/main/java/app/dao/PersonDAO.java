package app.dao;


import app.dao.utils.DatabaseUtils;
import app.model.Person;
import app.model.Show;
import app.model.enumeration.showStatus;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class PersonDAO {
	public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";

	
	//returns a list of persons despite the name
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
     * Method to fetch users from the database.
     * You should use this as an example for future queries, though the sql statement
     * will change -and you are supposed to write them.
     *
     * Current user: caramel 6, password (the password is "password" without quotes)
     * @param username what the user typed in the log in form.
     * @return Some of the user data to check on the password. Null if there
     *         no matching user.
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
//            			public Show(int showid,String showTitle,double length,boolean isMovie,
//            		    		boolean isSeries,String genre,int year,showStatus status,
//            		    		String proCo)
            			 new Show(result.getInt("showid"),
            					 result.getString("show_title"),
            					 result.getDouble("length"),
            					 result.getBoolean("movie"),
            					 result.getBoolean("series"),
            					 result.getString("genre"),
            					 result.getInt("year"),
            					 showStatus.valueOf(result.getString("status")),
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

