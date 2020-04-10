package app.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.dao.utils.DatabaseUtils;
import app.model.Person;

public class PersonDAO {

	public static Person getPersonByName(String fullname) {

		List<Person> people = new ArrayList<>();

		try {
			// Here you prepare your sql statement
			String sql = "SELECT * FROM person WHERE person_id = '" + person_id + "'";

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
}
