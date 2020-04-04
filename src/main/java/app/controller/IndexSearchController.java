package app.controller;

import java.util.List;

import app.dao.PersonDAO;
import app.model.Person;

public class IndexSearchController {

    // Authenticate the user by hashing the inputted password using the stored salt,
    // then comparing the generated hashed password to the stored hashed password
    public static List<Person> search(String searchName) {
        if (searchName == null) {
            return null;
        }
        List<Person> results = PersonDAO.getPeopleByName(searchName);
        if (results == null) {
            return null;
        }

        /**
         * What is the "salt"? You can read more in here:
         * https://www.baeldung.com/java-password-hashing
         */
        
        return results;
    }

}
