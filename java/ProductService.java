import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.util.ArrayList;  
import java.util.List;  

/**
 * Product service class
 * @author Ethan C and Aarav S
 */
public class ProductService
{
    /**
     * Method to get all the products
     * @return A list of all products
     */
    public static List<Product> getAllProducts()
    {
        //Initialize variables
        List<Product> products = new ArrayList<>();  
        String query = "SELECT * FROM tblProduct";  
        
        //Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query);  
             ResultSet resultSet = preparedStatement.executeQuery()) {  
            
            //Looping through all products
            while (resultSet.next()) {  
                //Getting all information from each product
                Product product = new Product();  
                product.setProductID(resultSet.getString("ProductID"));
                product.setDescription(resultSet.getString("Description"));
                product.setUnitPrice(resultSet.getDouble("UnitPrice"));
                product.setUnitsOnHand(resultSet.getInt("UnitsOnHand"));
                product.setProductClass(resultSet.getString("Class"));
                product.setWarehouseID(resultSet.getInt("WarehouseID"));

                products.add(product);
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
  
        return products;  
    }

    /**
     * Method to get all info on one product
     * @param id The product's id
     * @return The product
     */
    public static Product getProductByID(String id)
    {
        //Initialize variables
        Product product = null;
        String query = "SELECT * FROM tblProduct WHERE ProductID = ?";

        //Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  

            PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
  
            preparedStatement.setString(1, id);  
            try (ResultSet resultSet = preparedStatement.executeQuery()) {  
                if (resultSet.next()) {  
                    //Grabbing all info from product
                    product = new Product();
                    product.setProductID(resultSet.getString("ProductID"));
                    product.setDescription(resultSet.getString("Description"));
                    product.setUnitPrice(resultSet.getDouble("UnitPrice"));
                    product.setUnitsOnHand(resultSet.getInt("UnitsOnHand"));
                    product.setProductClass(resultSet.getString("Class"));
                    product.setWarehouseID(resultSet.getInt("WarehouseID"));
                }  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
  
        return product; 
    }

    /**
     * Method to add a product
     * @param product The product
     * @return True or false
     */
    public static boolean addProduct(Product product) {  
        //Initialize variables
        String query = "INSERT INTO tblProduct (ProductID, Description, UnitPrice, UnitsOnHand, Class, WarehouseID) VALUES (?, ?, ?, ?, ?, ?)";  
        boolean success = false;

        //Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
    
            //Getting all the product's info 
            preparedStatement.setString(1, product.getProductID());
            preparedStatement.setString(2, product.getDescription());  
            preparedStatement.setDouble(3, product.getUnitPrice());  
            preparedStatement.setInt(4, product.getUnitsOnHand()); 
            preparedStatement.setString(5, product.getProductClass());  
            preparedStatement.setInt(6, product.getWarehouseID());  

            //Checking if product has been added
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
     * Method to get the count of all products
     * @return The count of products
     */
    public static int getProductCount() {
        int productCount = 0;
        String query = "SELECT COUNT(*) AS ProductCount FROM tblProduct";

        // Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                productCount = resultSet.getInt("ProductCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productCount;
    }

    /**
     * Method to get the total units on hand for all products
     * @return The total units on hand
     */
    public static int getTotalUnitsOnHand() {
        int totalUnitsOnHand = 0;
        String query = "SELECT SUM(UnitsOnHand) AS TotalUnitsOnHand FROM tblProduct";

        // Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                totalUnitsOnHand = resultSet.getInt("TotalUnitsOnHand");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalUnitsOnHand;
    }

    /**
     * Method to edit a product
     * @param product The product
     * @return True or false
     */
    public static boolean editProduct(Product product)
    {
        //Initialize variables
        String query = "UPDATE tblProduct " +
        "SET Description = ?, UnitPrice = ?, UnitsOnHand = ?, Class = ?, WarehouseID = ? WHERE ProductID = ?";  
        boolean success = false;

        //Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
    
            //Getting all the products's info
            preparedStatement.setString(1, product.getDescription());  
            preparedStatement.setDouble(2, product.getUnitPrice());  
            preparedStatement.setInt(3, product.getUnitsOnHand());  
            preparedStatement.setString(4, product.getProductClass()); 
            preparedStatement.setInt(5, product.getWarehouseID());  
            preparedStatement.setString(6, product.getProductID());  

            //Checking if product has been edited
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
     * Method to get the count of all warehouses
     * @return The count of warehouses
     */
    public static int getWarehouseCount() {
        int warehouseCount = 0;
        String query = "SELECT COUNT(*) AS WarehouseCount FROM tblWarehouse";

        // Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // setting the warehouse count
            if (resultSet.next()) {
                warehouseCount = resultSet.getInt("WarehouseCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warehouseCount;
    }
}