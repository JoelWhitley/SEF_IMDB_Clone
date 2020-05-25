package app.dao;

import app.dao.utils.DatabaseUtils;
import app.model.ProductionCompany;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class ProCoDAO {


	/**
     * Method to fetch procos from the database.
     *
     * @return all proCos
     * @throws SQLexception Tried to execute a statement that was not valid
     */
    public static List<ProductionCompany> getAllProCo() {
        // Fish out the results
        List<ProductionCompany> procos = new ArrayList<>();

        try {
            // Here you prepare your sql statement
            String sql = "SELECT proco_name FROM production_company;";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            // If you have multiple results, you do a while
            while(result.next()) {
                // 2) Add it to the list we have prepared
            	procos.add(
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
        if(!procos.isEmpty()) return procos;
        // If we are here, something bad happened
        return null;
    }
    
    /**
     * Method to fetch procos from the database by id.
     *
     * @return the proco with that ID
     * @param name Name that matches a proco in the database
     * @throws SQLexception Tried to execute a statement that was not valid
     */
    public static String findProCoID(String name) {
    	
    	String proCoID = "";
    	
        try {
            // Here you prepare your sql statement
            String sql = "SELECT * FROM production_company WHERE proco_name LIKE '" + name + "'";

            // Execute the query
            Connection connection = DatabaseUtils.connectToDatabase();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);

            
            //grab the id
            //contract: there are not duplicate names for procos
            while(result.next()) proCoID = result.getString("proco_id");

                  
            DatabaseUtils.closeConnection(connection);
        }
        catch (Exception e) {
        	proCoID = "null";
            e.printStackTrace();
        }


        // If there is a result
        if (proCoID != "") return proCoID;
        
        else {
        	return null;
        }
    }
    

}
