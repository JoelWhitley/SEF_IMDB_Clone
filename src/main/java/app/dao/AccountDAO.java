package app.dao;

import app.dao.utils.DatabaseUtils;
import app.model.Account;
import app.model.enumeration.AccountRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AccountDAO {
    public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";


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
    public static Account getUserByUsername(String username) {
        // Fish out the results
        List<Account> accounts = new ArrayList<>();

        try {
            // Here you prepare your sql statement
            String sql = "SELECT * FROM account WHERE username ='" + username + "';";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            while(result.next()) {
                // 2) Add it to the list we have prepared
                accounts.add(
                		// 1) Create a new account object
	        		new Account(result.getString("username"),
	                        result.getString("password"), result.getString("first_name"),
	                        result.getString("last_name"), result.getString("address"), 
	                        result.getString("country"), result.getString("gender"), 
	                        result.getString("email"),
	                        AccountRole.valueOf(result.getString("type")),
	                        result.getString("ban_timeout"))
                );
            }

            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        // If there is a result
        if(!accounts.isEmpty()) return accounts.get(0);
        // If we are here, something bad happened
        return null;
    }
    
    /**
     * Method to get a user's details by their username
     *
     * @param username name of the user to get details of
     * @return Account object with all of the user's details
     * @throws SQLexception Tried to execute a statement that was not valid
     */
    public static Account getUserDetails(String username) {
    	Account user = null;
    		try {
                // Here you prepare your sql statement
                String sql = "SELECT * FROM account WHERE username ='" + username + "'";

                // Execute the query
                Connection connection = DatabaseUtils.connectToDatabase();
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);

                // If you have multiple results, you do a while
                while(result.next()) {
                    // 2) Add it to the list we have prepared
                	
                	//if(result.getString("type").contentEquals(accountRole.ADMIN.getString()))
                	//we put in user type = 
                    user = new Account(result.getString("username"),
                              result.getString("password"), result.getString("first_name"),
                              result.getString("last_name"), result.getString("address"), 
                              result.getString("country"), result.getString("gender"), 
                              result.getString("email"),
                              AccountRole.valueOf(result.getString("type")),
                              result.getString("ban_timeout"));
                    
                }

                // Close it
                DatabaseUtils.closeConnection(connection);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
    	return user;
    	
    }
    
    /**
     * Method to get a user's type
     *
     * @param username name of the user to get the type of
     * @return string of the user's access level
     * @throws SQLexception Tried to execute a statement that was not valid
     */
    public static String getUserType(String username) {
	    String user = null;
		try {
	        // Here you prepare your sql statement
	        String sql = "SELECT * FROM `account` WHERE username LIKE '" + username + "'";
	
	        // Execute the query
	        Connection connection = DatabaseUtils.connectToDatabase();
	        Statement statement = connection.createStatement();
	        ResultSet result = statement.executeQuery(sql);
	        while(result.next()) {
                user = result.getString("type");
            }
	        // Close it
	        DatabaseUtils.closeConnection(connection);
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		return user;
    }
    
    /**
     * Method to update a user's type
     * <p>
     * This method also checks if they are banned and are trying to request a
     * higher level account, in order to ensure that people cannot repeatedly
     * spam account upgrade requests if they have recently been rejected.
     *
     * @param username name of the user to get the type of
     * @param type user level to change to
     * @throws SQLexception Tried to execute a statement that was not valid
     */
    public static void updateUserType(String username, AccountRole type) {
    	boolean isNotBanned = true;
    	//check if they are banned and trying to get approved for a better account
    	//otherwise this will block admins from updating their status manually
    	if (type.getString().equals("PENDING_PROCO") || type.getString().equals("PENDING_CRITIC")) {
    		if (AccountDAO.getUserByUsername(username).getBanned() == true) {
    			
    			isNotBanned = false;
    		}
    	}
    	
    	//otherwise, update the status field of that user to be the given type.
    	if (isNotBanned) {
    		Connection connection;
    		try {
    			connection = DatabaseUtils.connectToDatabase();
    			String updateQuery = "UPDATE `account` SET type = '" + type.getString() + "' WHERE username = '" + username + "';";
    			PreparedStatement insertStatement = connection.prepareStatement(updateQuery);
    	    	insertStatement.execute();
    	    	// Close it
    	        DatabaseUtils.closeConnection(connection);
    		} catch (Exception e) {
    			System.out.println("Error connecting to database");
    		}
    	}   
    }

    
    /**
     * Method to get all accounts of a certain type
     *
     * @param role role that all returned user accounts should match
     * @return list of accounts that have this user level
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static List<Account> getAccountsByType(AccountRole role) {
		
		//look for any account that has a type field matching the given role
		List<Account> accounts =  new ArrayList<>();
		String sql = "SELECT * FROM `account` WHERE type ='" + role.getString() + "'";
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            // If you have multiple results, you do a while
	        while(result.next()) {
	            accounts.add(   
	              new Account(result.getString("username"), result.getString("password"),
	            		  result.getString("first_name"), result.getString("last_name"), 
	            		  result.getString("address"), result.getString("country"), 
	            		  result.getString("gender"), result.getString("email"), role,
	            		  result.getString("ban_timeout"))
	              );
	        }
	
	        // Close it
	        DatabaseUtils.closeConnection(connection);
		    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
        // If there is a result
	    if(!accounts.isEmpty()) return accounts;
	    // If we are here, something bad happened
	    return null;
	}

	/**
     * Method to ban a user for an amount of time
     *
     * @param username the username of the user we are banning
     * @param timeAmount the amount of time measures to ban the account for
     * @param timeMeasure a measure of time - must be a string matching one of: 
	 * 	<p><ul>
	 * 		<li>hours</li>
	 * 		<li>days</li>
	 * 		<li>months</li>
	 * 		<li>years</li>
	 * 	</ul>
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static void banUserForTime(String username, int timeAmount, String timeMeasure) {
		
		//create a date string and make it X measures from today
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Calendar cal = Calendar.getInstance();
        
		if (timeMeasure.equals("hours")) {
			cal.add(Calendar.HOUR_OF_DAY, timeAmount);
		}
		else if (timeMeasure.equals("days")) {
			cal.add(Calendar.DATE, timeAmount);
		}
		else if (timeMeasure.equals("months")) {
			cal.add(Calendar.MONTH, timeAmount);
		}
		else if (timeMeasure.equals("years")) {
			cal.add(Calendar.YEAR, timeAmount);
		}
		
		Date forwardDate = cal.getTime();    
        String stringDate = dateFormat.format(forwardDate);
        //use it to update the account's ban timeout
        Connection connection;
		try {
			connection = DatabaseUtils.connectToDatabase();
			String updateQuery = "UPDATE `account` SET ban_timeout = '" + stringDate + "' WHERE username = '" + username + "';";
			PreparedStatement insertStatement = connection.prepareStatement(updateQuery);
	    	insertStatement.execute();
	    	// Close it
	        DatabaseUtils.closeConnection(connection);
		} catch (Exception e) {
			System.out.println("Error connecting to database");
		}
		
		//this is then checked when the user performs various actions,
		//stopping someone who has not been accepted as a proco or critic from
		//applying again too quickly or making too many shows of the same
		//type
	}
}
