package app.controller;

import app.Main;
import app.dao.AccountDAO;
import app.model.Account;
import app.model.enumeration.accountRole;

import org.mindrot.jbcrypt.BCrypt;





public class UserController {



    // Authenticate the user by hashing the inputted password using the stored salt,
    // then comparing the generated hashed password to the stored hashed password
    public static boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        Account user = AccountDAO.getUserByUsername(username);
        if (user == null) {
            return false;
        }
        /**
         * What is the "salt"? You can read more in here:
         * https://www.baeldung.com/java-password-hashing
         */
        String hashedPassword = BCrypt.hashpw(password, AccountDAO.SALT);
        return hashedPassword.equals(user.getPassword());
    }
    
    public static Account getUserInfo(String username) {
        if (username == null) {
            return null;
        }
        try {
        	return AccountDAO.getUserDetails(username);
        }
        catch(Exception e) {
        	return null;
        }
        
    }




    public static void setPassword(String username, String oldPassword, String newPassword) {
        if (authenticate(username, oldPassword)) {
            String newSalt = BCrypt.gensalt();
            String newHashedPassword = BCrypt.hashpw(newSalt, newPassword);

            // Update the user salt and password
        }
    }
    
    public static boolean isAdmin(String username) {
    	Account user = AccountDAO.getUserDetails(username);
        if (user == null) {
            return false;
        }
        return user.getType() == accountRole.ADMIN;
    }

}
