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

	public static final String SALT = "$2a$10$h.dl5J86rGH7I8bD9bZeZe";


	public static Show getShowById(int id) {
		List<Show> shows =  new ArrayList<>();
		String sql = "SELECT * FROM `show` WHERE showid ='" + id + "'";
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            // If you have multiple results, you do a while
	        while(result.next()) {
	            shows.add(   
	              new Show(result.getInt("showid"),result.getString("show_title")
	            		  ,result.getDouble("length")
	            		  ,result.getBoolean("movie"),result.getBoolean("series")
	            		  ,result.getString("genre"),result.getInt("year"),ShowStatus.VISABLE
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
	
	public static int getStarRating(int id, int star) {
		int totalReviews = 0;
		//String sql = "SELECT * FROM `user_review` WHERE show_id = " + id + " AND rating = '" + star + "';";
		
		String sql = "SELECT COUNT(*) AS total FROM `user_review` WHERE show_id = " + id + " AND rating = '" + star + "';";
		
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            //System.out.println(sql);
            result.next();
            totalReviews = result.getInt(1);
            
            //System.out.println(totalReviews);

	        // Close it
	        DatabaseUtils.closeConnection(connection);
		    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	    return totalReviews;
	}
	
	public static double getStarPercent(int id, int star) {
		double starReviews = 0;
		double allReviews = 0;
		//String sql = "SELECT * FROM `user_review` WHERE show_id = " + id + " AND rating = '" + star + "';";
		String sql = "SELECT COUNT(*) FROM `user_review` WHERE show_id = " + id + ";";
		
		 try {
	        	Connection connection = DatabaseUtils.connectToDatabase();
	            Statement statement = connection.createStatement();
	            ResultSet result = statement.executeQuery(sql);
	            
	            //System.out.println(sql);
	            result.next();
	            allReviews = result.getInt(1);
	            
	            //System.out.println(allReviews);
		
		        // Close it
		        DatabaseUtils.closeConnection(connection);
			    }
		    catch (Exception e) {
		        e.printStackTrace();
		    }
		
		sql = "SELECT COUNT(*) AS total FROM `user_review` WHERE show_id = " + id + " AND rating = '" + star + "';";
		
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            //System.out.println(sql);
            result.next();
            starReviews = result.getInt(1);
            
            //System.out.println(starReviews);
	
	        // Close it
	        DatabaseUtils.closeConnection(connection);
		    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
        double percent = (starReviews/allReviews)*1000;
        percent = Math.round(percent)/10;
        
        //System.out.println(percent);
	
	    return percent;
	}

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
	
	public static List<Show> getShowsByPending() {
		List<Show> shows =  new ArrayList<>();
		String sql = "SELECT * FROM `show` WHERE status ='" + ShowStatus.USERSUBMISSION.getString() + "'";
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            // If you have multiple results, you do a while
	        while(result.next()) {
	            shows.add(   
	              new Show(result.getInt("showid"),result.getString("show_title"), result.getDouble("length"), 
	            		  result.getBoolean("movie"),result.getBoolean("series"),result.getString("genre"),result.getInt("year"),ShowStatus.USERSUBMISSION,
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

	public static int getHighestShowID() {
		List<Show> shows =  new ArrayList<>();
		String sql = "SELECT * FROM `show`";
		int last = 0;
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
	        while(result.next()) {
	            last++;
	        }
	
	        // Close it
	        DatabaseUtils.closeConnection(connection);
		    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	
	
	    // If there is a result
	    if(!shows.isEmpty()) return last;
	    // If we are here, something bad happened
	    return 1;
	}

	public static void updateStatus() {
		Connection connection;
		try {
			connection = DatabaseUtils.connectToDatabase();
			String updateQuery = "UPDATE `show` SET status = 'VISABLE' WHERE status = 'PROCOSUBMISSION' AND DATEDIFF(datetime_submitted, CURRENT_TIMESTAMP ) <= -1;";
			PreparedStatement insertStatement = connection.prepareStatement(updateQuery);
	    	insertStatement.execute();
		} catch (Exception e) {
			System.out.println("Error connecting to database");
		}
		
	
	}


	public static void changeShowStatus(int index, ShowStatus status) {
		Connection connection;
		try {
			connection = DatabaseUtils.connectToDatabase();
			String updateQuery = "UPDATE `show` SET status = '" + status.getString() + "' WHERE showid = " + index + ";";
			PreparedStatement insertStatement = connection.prepareStatement(updateQuery);
	    	insertStatement.execute();
		} catch (Exception e) {
			System.out.println("Error connecting to database");
		}
		
	}

	public static void deleteShow(int index) {
		Connection connection;
		try {
			connection = DatabaseUtils.connectToDatabase();
			//delete refs to show
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
		} catch (Exception e) {
			System.out.println("Error connecting to database");
		}
		
	}
	
	public static int getLowestUnusedID() {
		String sql = "SELECT * FROM `show`";
		int currentRow = 1;
		
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
	
        return currentRow;
	}


	public static boolean checkDuplicateName(String showTitle) {
		String sql = "SELECT * FROM `show` WHERE show_title = '" + showTitle + "'";
		
        try {
        	Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
	        while(result.next()) {
	            return true;
	        }

		}
	    catch (Exception e) {
	        e.printStackTrace();
	    }
	
        return false;
	}
}

	
       

