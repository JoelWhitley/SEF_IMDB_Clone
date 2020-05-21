package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import app.dao.utils.DatabaseUtils;
import app.model.Person;
import app.model.ProductionCompany;
import app.model.CreditsRoll;
import app.model.Show;
import app.model.enumeration.ShowStatus;

public class ShowDAO {

	/**
     * Method to get a show depending on it's ID
     *
     * @param id the ID of a show in the database
     * @return the show with that ID or null if none match
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static Show getShowById(int id) {
		//select the show that has the id given
		List<Show> shows =  new ArrayList<>();
		String sql = "SELECT * FROM `show` WHERE showid ='" + id + "'";
		
        try {
        	//return as a show object
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            // If you have multiple results, you do a while
	        while(result.next()) {
	        	ShowStatus status = ShowStatus.valueOf(result.getString("status"));
	        	
	            shows.add(   
	              new Show(result.getInt("showid"),result.getString("show_title")
	            		  ,result.getDouble("length")
	            		  ,result.getBoolean("movie"),result.getBoolean("series")
	            		  ,result.getString("genre"),result.getInt("year"),status
	            		  ,result.getString("proco_id")) 
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
	
	/**
     * Method to get a single member of a show's cast using the show ID and the character name
     *
     * @param id the ID of a show in the database
     * @param check the name of the character in that show
     * @return a person with all their details, who played that role
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static List<CreditsRoll> getCast(int id, String check) {
		List<CreditsRoll> cast = new ArrayList<>();
		String sql = "SELECT * FROM credits_roll WHERE show_id ='" + id + "' AND character_name IS " + check + "NULL;";
		try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            // If you have multiple results, you do a while
	        while(result.next()) {
	        	Person p = PersonDAO.getPersonById(result.getInt("person_id"));
	            cast.add(  	
	              new CreditsRoll(p.getPersonId(),result.getString("role"), p.getFullName(),result.getString("character_name")) 
	              );
	        }
	
	        // Close it
	        DatabaseUtils.closeConnection(connection);
		    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
     // If there is a result
	    if(!cast.isEmpty()) return cast;
	    // If we are here, something bad happened
	    return null;
		
	}
	
	
	/**
     * Method to get a count of reviews with a certain star rating of a show
     *
     * @param id the ID of a show in the database
     * @param star the star rating given
     * @return a count of the reviews with that star rating
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static int getStarRating(int id, int star) {
		int totalReviews = 0;
		String sql = "SELECT COUNT(*) AS total FROM `user_review` WHERE show_id = " + id + " AND rating = '" + star + "';";
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            result.next();
            totalReviews = result.getInt(1);

	        // Close it
	        DatabaseUtils.closeConnection(connection);
		}
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    return totalReviews;
	}
	
	/**
     * Method to get a percentage of people who rated a show a certain star rating
     *
     * @param id the ID of a show in the database
     * @param star the star rating given
     * @return a count of the reviews with that star rating
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static double getStarPercent(int id, int star) {
		double starReviews = 0;
		double allReviews = 0;
		//count how many reviews we have
		String sql = "SELECT COUNT(*) FROM `user_review` WHERE show_id = " + id + ";";
		
		 try {
	        	Connection connection = DatabaseUtils.connectToDatabase();
	            Statement statement = connection.createStatement();
	            ResultSet result = statement.executeQuery(sql);
	            result.next();
	            allReviews = result.getInt(1);
		
		        // Close it
		        DatabaseUtils.closeConnection(connection);
			}
		    catch (Exception e) {
		        e.printStackTrace();
		    }
		
		sql = "SELECT COUNT(*) AS total FROM `user_review` WHERE show_id = " + id + " AND rating = '" + star + "';";
		
		//count the stars of each review
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            result.next();
            starReviews = result.getInt(1);
	        // Close it
	        DatabaseUtils.closeConnection(connection);
		}
	    catch (Exception e) {
	        e.printStackTrace();
	    }
        
        //total stars / reviews counted = % of people giving it that rating
        double percent = (starReviews/allReviews)*1000;
        percent = Math.round(percent)/10;
        //return 
	    return percent;
	}

	
	/**
     * Method to get a show's proco
     *
     * @param showId the ID of a show in the database
     * @return the proco who worked on that show
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static List<ProductionCompany> getProco(int showId) {
		List<ProductionCompany> proco = new ArrayList<>();
		
		try {
            // Here you prepare your sql statement
            String sql = "SELECT proco_name "
            		+ "FROM `show` "
            		+ "JOIN production_company "
            		+ "ON production_company.proco_id =`show`.proco_id "
            		+ "WHERE `show`.showid="
            		+ showId +"";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            while(result.next()) {
                // 2) Add it to the list we have prepared
            	proco.add(
                  // 1) Create a new account object
            			new ProductionCompany(result.getString("proco_name"))
                );
            }
            // Close it
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        // If there is a result
        if(!proco.isEmpty()) return proco;
        // If we are here, something bad happened
        return null;
	}
	
	
	/**
     * Method to get a show's cast
     *
     * @param showId the ID of a show in the database
     * @return a list of people who worked on that show
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static List<Person> getCast(int showId) {
		// Fish out the results
        List<Person> cast = new ArrayList<>();

        try {
            // Here you prepare your sql statement
            String sql = "SELECT `person`.*"
            		+ " FROM `show`"
            		+ " JOIN credits_roll ON credits_roll.show_id=`show`.showid"
            		+ " JOIN person ON credits_roll.person_id=person.person_id"
            		+ " WHERE credits_roll.show_id=" + showId
            		+ " ORDER BY person.person_id ASC";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            while(result.next()) {
                // 2) Add it to the list we have prepared
            	cast.add(
                  // 1) Create a new account object
            			//new Person(personId, name, role, birthdate, bio)
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
        if(!cast.isEmpty()) return cast;
        // If we are here, something bad happened
        return null;
	}
	
	/**
     * Method to get shows using their status
     *
     * @param status the show staus we are looking for
     * @return list of shows that have that status
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static List<Show> getShowsByType(ShowStatus status) {
		List<Show> shows =  new ArrayList<>();
		String sql = "SELECT * FROM `show` WHERE status ='" + status.getString() + "'";
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            // If you have multiple results, you do a while
	        while(result.next()) {
	            shows.add(   
	              new Show(result.getInt("showid"),result.getString("show_title"), result.getDouble("length"), 
	            		  result.getBoolean("movie"),result.getBoolean("series"),result.getString("genre"),result.getInt("year"),status,
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

	
	/**
     * Method to update any proco shows that have been waiting for > 24 hours
     * to visable
     *
     * @throws SQLexception Tried to execute a statement that was not valid
     */

	public static void updateProCoShowStatus() {
		
		//updates a proco show's status if it has been over 24 hours
		Connection connection;
		try {
			//if it has the PROCOSUBMISSION tag and has a submission date
			//that is more than a day ago, make it visable
			connection = DatabaseUtils.connectToDatabase();
			String updateQuery = "UPDATE `show` SET status = 'VISABLE' WHERE status = 'PROCOSUBMISSION' AND DATEDIFF(datetime_submitted, CURRENT_TIMESTAMP ) <= -1;";
			PreparedStatement insertStatement = connection.prepareStatement(updateQuery);
	    	insertStatement.execute();
		} 
		catch (Exception e) {
			System.out.println("Error connecting to database");
		}
		
	
	}

	/**
     * Method to change a shows status
     *
     * @param index the show index we are changing
     * @param status the show staus we are changing to
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static void changeShowStatus(int index, ShowStatus status) {
		
		//given a show ID, change the status
		//used for manal admin page managment of shows
		Connection connection;
		try {
			connection = DatabaseUtils.connectToDatabase();
			String updateQuery = "UPDATE `show` SET status = '" + status.getString() + "' WHERE showid = " + index + ";";
			PreparedStatement insertStatement = connection.prepareStatement(updateQuery);
	    	insertStatement.execute();
		} 
		catch (Exception e) {
			System.out.println("Error connecting to database");
		}
		
	}

	/**
     * Method to createn't a show
     *
     * @param index the show index we are deleting
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static void deleteShow(int index) {
		
		//deletes a show given the index
		//used for manual admin page managment
		Connection connection;
		try {
			connection = DatabaseUtils.connectToDatabase();
			//delete refs to show, otherwise the db will have a hissy fit
			String updateQuery = "DELETE FROM `credits_roll` WHERE show_id = " + index + ";";
			PreparedStatement insertStatement = connection.prepareStatement(updateQuery);
	    	insertStatement.execute();
	    	updateQuery = "DELETE FROM `user_review` WHERE show_id = " + index + ";";
			insertStatement = connection.prepareStatement(updateQuery);
	    	insertStatement.execute();
	    	
			//delete show
			updateQuery = "DELETE FROM `show` WHERE showid = " + index + ";";
			insertStatement = connection.prepareStatement(updateQuery);
	    	insertStatement.execute();
		} 
		catch (Exception e) {
			System.out.println("Error connecting to database");
		}
		
	}
	
	/**
     * Method to get the lowest unused show ID
     *
     * @return lowest index that is unused
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static int getLowestUnusedID() {
		//get all the shows
		String sql = "SELECT * FROM `show`";
		//start at the first record
		int currentRow = 1;
		
		//as soon as we hit a result where the ID does not match the 
		//row number, we know that skipped row number is free for use
		   try {
		    	Connection connection = DatabaseUtils.connectToDatabase();
		        Statement statement = connection.createStatement();
		        ResultSet result = statement.executeQuery(sql);
		        while(result.next()) {
		            if (result.getInt("showid") != currentRow) {
		        	DatabaseUtils.closeConnection(connection);
		        	return currentRow;
		        }
		        currentRow++;
		    }
		
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
		
		//return the missed row
	    return currentRow;
	}

	/**
     * Method to check if a show of a certain name is already in the database
     *
     * @param showTitle show title we are checking against
     * @return true if the show has a duplicate in the database, false otherwise
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static boolean checkDuplicateName(String showTitle) {
		
		//look for any result at all that has the same name
		String sql = "SELECT * FROM `show` WHERE show_title = '" + showTitle + "'";
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            //if there are any results, we know we have a duplicate title
	        while(result.next()) {
	            return true;
	        }
		}
	    catch (Exception e) {
	        e.printStackTrace();
	    }
        
        //if there are none, there is no duplicate
        return false;
	}
	
	public static void editShow(int index, String showTitle, double length, String genre) {
		
		String sql = "UPDATE `show` SET show_title = '" + showTitle +
				"', genre = '" + genre + "', length = " + length + 
				"WHERE showid = " + index + ";";
		
		try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            DatabaseUtils.closeConnection(connection);
            //if there are any results, we know we have a duplicate title
	       
		}
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}

	
       

