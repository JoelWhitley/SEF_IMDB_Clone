package app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.dao.utils.DatabaseUtils;
import app.model.Person;
import app.model.Show;

public class PersonDAO {
	public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";

	
	

    public static Person getPersonByString(String personSearch) {
        // Fish out the results
        List<Person> person = new ArrayList<>();

        try {
       

            //sql script. Show show title by searching first name of actor
        	/*
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
                    
                    */
        	
        	String sql = "SELECT * FROM person;";
            //sql script. Show Writer/Based-On & Search-By


            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            
            /*
        	 * private int personId;
            private String fullName;
            private String role;
            private String bio;
            private Date birthdate;
            */
            
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
        if(!person.isEmpty()) return person.get(0);
        // If we are here, something bad happened
        return null;
    }
    
    

}

