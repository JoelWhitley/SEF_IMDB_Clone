package app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import app.dao.utils.DatabaseUtils;
import app.model.Person;

public class PersonDAO {
	public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";

	
	 
    public static Person getActorByName(String fullName) {
        // Fish out the results
        List<Person> people = new ArrayList<>();

        try {
       

            //sql script. Show show title by searching first name of actor
            String sql2 = "SELECT fullname,person.role,birthdate,bio,character_name,show_title,start_year,genre,length\n" + 
            		"FROM credits_roll,`show`,person\n" + 
            		"WHERE credits_roll.show_id = `show`.showid "
            		+ "AND person.person_id = credits_roll.person_id "
            		+ "AND UPPER(fullname) LIKE '%" + fullName + "%';";
           
            //sql script. search actor by first name

            //String sql3 = "SELECT * FROM person WHERE fullname LIKE '%" + name + "%';";

            //sql script. Show Writer/Based-On & Search-By


            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql2);

            
            /*
        	 * private int personId;
            private String fullName;
            private String role;
            private String bio;
            private Date birthdate;
            */
            
            // If you have multiple results, you do a while
            while(result.next()) {
                people.add(   
                  new Person(result.getInt("personId"),result.getString("fullName"), result.getString("role"),result.getDate("birthdate"),
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
    
    

}
