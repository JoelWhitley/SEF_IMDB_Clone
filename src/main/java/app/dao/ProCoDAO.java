package app.dao;

import app.Main;
import app.dao.utils.DatabaseUtils;
import app.model.ProductionCompany;

import org.eclipse.collections.impl.list.mutable.FastList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class ProCoDAO {


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
    

}
