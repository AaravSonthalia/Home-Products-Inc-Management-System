import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.util.ArrayList;  
import java.util.List;  

/**
 * SalesRep service class
 * @author Ethan C and Aarav S
 */
public class SalesRepService
{
    /**
     * Method to get all the sales reps and some of their information
     * @return A list of all sales reps
     */
    public static List<SalesRep> getAllSalesReps()
    {
        //Initialize variables
        List<SalesRep> salesreps = new ArrayList<>();  
        String query = "SELECT SalesRepID, FirstName, LastName, BusinessNumber, Title FROM tblSalesRep";  
        
        //Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query);  
             ResultSet resultSet = preparedStatement.executeQuery()) {  
            
            //Looping through all salesReps
            while (resultSet.next()) {  
                //Getting all information from each salesRep
                SalesRep salesrep = new SalesRep();  
                salesrep.setSalesRepID(resultSet.getInt("SalesRepID"));
                salesrep.setFirstName(resultSet.getString("FirstName"));
                salesrep.setLastName(resultSet.getString("LastName"));
                salesrep.setBusinessNumber(resultSet.getString("BusinessNumber"));
                salesrep.setTitle(resultSet.getString("Title"));

                salesreps.add(salesrep);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
  
        return salesreps;  
    }

    /**
     * Method to get all the information on one sales rep
     * @param id The sales rep's id
     * @return The sales rep
     */
    public static SalesRep getSalesRepByID(int id) {  
        //Initialize variables
        SalesRep salesRep = null;  
        String query = "SELECT * FROM tblSalesRep WHERE SalesRepID = ?";  
    
        //Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  

            PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
  
            preparedStatement.setInt(1, id);  
            try (ResultSet resultSet = preparedStatement.executeQuery()) {  
                if (resultSet.next()) {  
                    //Grabbing all info from the sales rep
                    salesRep = new SalesRep();  
                    salesRep.setSalesRepID(resultSet.getInt("SalesRepID"));
                    salesRep.setLastName(resultSet.getString("LastName"));
                    salesRep.setFirstName(resultSet.getString("FirstName"));  
                    salesRep.setBusinessNumber(resultSet.getString("BusinessNumber"));
                    salesRep.setCellNumber(resultSet.getString("CellNumber"));    
                    salesRep.setHomeNumber(resultSet.getString("HomeNumber"));
                    salesRep.setFaxNumber(resultSet.getString("FaxNumber"));  
                    salesRep.setTitle(resultSet.getString("Title"));  
                    salesRep.setStreet(resultSet.getString("Street"));  
                    salesRep.setCity(resultSet.getString("City"));  
                    salesRep.setState(resultSet.getString("State"));  
                    salesRep.setZipCode(resultSet.getString("ZipCode"));  
                    salesRep.setCommission(resultSet.getDouble("Commission"));  
                    salesRep.setManagerID(resultSet.getInt("ManagerID")); 
                }  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
  
        return salesRep;  
    }  
    
    /**
     * Method to add a sales rep
     * @param salesrep The sales rep
     * @return True or false
     */
    public static boolean addSalesRep(SalesRep salesrep) {  
    	//Initialize variables
        String query = "INSERT INTO tblSalesRep (LastName, FirstName, BusinessNumber, CellNumber, HomeNumber, FaxNumber, Title, Street, City, State, ZipCode, Commission, ManagerID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";  
        boolean success = false;

        //Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
  	
  			//Getting all the sales rep's info
            preparedStatement.setString(1, salesrep.getLastName());  
            preparedStatement.setString(2, salesrep.getFirstName());  
            preparedStatement.setString(3, salesrep.getBusinessNumber());  
            preparedStatement.setString(4, salesrep.getCellNumber()); 
            preparedStatement.setString(5, salesrep.getHomeNumber());  
            preparedStatement.setString(6, salesrep.getFaxNumber());  
            preparedStatement.setString(7, salesrep.getTitle());
            preparedStatement.setString(8, salesrep.getStreet());
            preparedStatement.setString(9, salesrep.getCity());   
            preparedStatement.setString(10, salesrep.getState());   
            preparedStatement.setString(11, salesrep.getZipCode());  
            preparedStatement.setDouble(12, salesrep.getCommission());
            preparedStatement.setInt(13, salesrep.getManagerID());  

            //Checking if sales rep has been added
            int rowsAffected = preparedStatement.executeUpdate();  
            if (rowsAffected > 0) {  
                success = true;
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  

        return success;
    }

    /**
     * Method to get the count of sales reps in the database
     * @return The total number of sales reps
     */
    public static int getSalesRepCount() {
        // Initialize variables
        int count = 0;
        String query = "SELECT COUNT(*) AS SalesRepCount FROM tblSalesRep";

        // Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Retrieve the count from the result set
            if (resultSet.next()) {
                count = resultSet.getInt("SalesRepCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Method to edit a sales rep
     * @param salesRep The sales rep to edit
     * @return True or false
     */
    public static boolean editSalesRep(SalesRep salesRep)
    {
        //Initialize variables
        String query = "UPDATE tblSalesRep " +
            "SET LastName = ?, FirstName = ?, BusinessNumber = ?, CellNumber = ?, HomeNumber = ?, FaxNumber = ?, Title = ?, Street = ?, " +
            "City = ?, State = ?, ZipCode = ?, Commission = ?, ManagerID = ? WHERE SalesRepID = ?";  
        boolean success = false;

        //Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
    
            //Getting all the sales rep's info
            preparedStatement.setString(1, salesRep.getLastName());  
            preparedStatement.setString(2, salesRep.getFirstName());  
            preparedStatement.setString(3, salesRep.getBusinessNumber());  
            preparedStatement.setString(4, salesRep.getCellNumber()); 
            preparedStatement.setString(5, salesRep.getHomeNumber());  
            preparedStatement.setString(6, salesRep.getFaxNumber());  
            preparedStatement.setString(7, salesRep.getTitle());
            preparedStatement.setString(8, salesRep.getStreet());
            preparedStatement.setString(9, salesRep.getCity());   
            preparedStatement.setString(10, salesRep.getState());   
            preparedStatement.setString(11, salesRep.getZipCode());  
            preparedStatement.setDouble(12, salesRep.getCommission());
            preparedStatement.setInt(13, salesRep.getManagerID());  
            preparedStatement.setInt(14, salesRep.getSalesRepID()); 

            //Checking if sales rep has been edited
            int rowsAffected = preparedStatement.executeUpdate();  
            if (rowsAffected > 0) {  
                success = true;
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  

        return success;
    }
}