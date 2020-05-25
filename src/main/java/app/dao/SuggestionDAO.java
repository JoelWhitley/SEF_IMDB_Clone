package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.dao.utils.DatabaseUtils;
import app.model.Account;
import app.model.Show;

public class SuggestionDAO {
	
	/**
     * Method to insert a new show suggestion into the database, with the show's
     * status depending on the user's level who is inserting it
     *
     * @param suggestion the show to be inserted
     * @param currentUser the user who is responsible for that show suggestion
     * @throws SQLexception Tried to execute a statement that was not valid
     */
	public static void insertSuggestionShow(Show suggestion, Account currentUser) {

		try {
			//create SQL connection
        	Connection connection = DatabaseUtils.connectToDatabase();
            
        	//gather info to build show
            String company = suggestion.getProCo();
            String status = "";
            
            //check user level to what status it is inserted at
            //admin is instantly visable
            if (currentUser.getType().getString().equals("ADMIN")) status = "VISABLE";
            //proco has 24 hours before visable
            else if (currentUser.getType().getString().equals("PROCO")) status = "PROCOSUBMISSION";
            //otherwise, needs admin approval
            else status = "USERSUBMISSION";
            
            //get the date inserted (used for proco, but still good data
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
            Date date = new Date();  
            String dateTime = formatter.format(date);
            
            //Insert query.
            String insertQuery = String.format("INSERT INTO `show` VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", 
            		ShowDAO.getLowestUnusedID(), suggestion.getShowTitle(), suggestion.getGenre(), 
            		suggestion.getLength(), suggestion.getIsMovie(), suggestion.getIsSeries(),
            		ProCoDAO.findProCoID(company), suggestion.getYear(), status, dateTime);
            		
            try {
            	//try and insert
            	PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
            	insertStatement.execute();
            }
            catch (Exception e) {
            	//catch errors
    	        e.printStackTrace();
    	    }
            //close
            DatabaseUtils.closeConnection(connection);
		}
		catch (Exception e) {
	        e.printStackTrace();
	    }
		
	}
	
	
}

