import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.util.ArrayList;  
import java.util.List;  
import java.sql.Date;

/**
 * Order service class
 * @author Ethan C and Aarav S
 */
public class OrderService 
{
	/**
	 * Method to get all the orders and some of their information
	 * @return A list of all orders
	 */
	public static List<Order> getAllOrders()
	{
		//Initialize variables
		List<Order> orders = new ArrayList<>();  
        String query = "SELECT OrderID, CustomerID, Date FROM tblOrder";  
  		
  		//Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query);  
             ResultSet resultSet = preparedStatement.executeQuery()) {  
  			
  			//Looping through all customers
            while (resultSet.next()) {  
            	//Getting all information from each customer
                Order order = new Order();  
                order.setOrderID(resultSet.getInt("OrderID"));
                order.setCustomerID(resultSet.getInt("CustomerID"));
                order.setDate(resultSet.getDate("Date"));

                orders.add(order);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
  
        return orders;  
	}

	/**
	 * Method to get all the information on one order
	 * @param id The order's id
	 * @return The order
	 */
	public static Order getOrderByID(int id) {  
		//Initialize variables
        Order order = null;  
        String query = "SELECT O.OrderID, CONCAT(C.FirstName, ' ', C.LastName) AS CustomerName, O.Date, O.ShippingDate, " +
            "O.Status, O.ShippingMethod, O.SalesTax, SUM(PO.QuantityOrdered * PO.QuotedPrice) AS OrderSubtotal, O.SalesTax, " + 
            "ROUND(SUM(PO.QuantityOrdered * PO.QuotedPrice) * (1 + SalesTax), 2) AS OrderTotal, " + 
            "SUM((P.UnitPrice - PO.QuotedPrice) * PO.QuantityOrdered) AS Discount " +
            "FROM tblOrder AS O " + 
            "LEFT JOIN tblProductsOrder AS PO ON PO.OrderID = O.OrderID " + 
            "LEFT JOIN tblCustomer AS C ON C.CustomerID = O.CustomerID " + 
            "LEFT JOIN tblProduct AS P ON P.ProductID = PO.ProductID " +
            "GROUP BY O.OrderID HAVING O.OrderID = ?";
  	
  		//Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  

             PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
  
            preparedStatement.setInt(1, id);  
            try (ResultSet resultSet = preparedStatement.executeQuery()) {  
                if (resultSet.next()) {  
                	//Grabbing all info from the order
                    order = new Order();  
                    order.setOrderID(resultSet.getInt("OrderID"));  
                    order.setCustomerName(resultSet.getString("CustomerName"));
                    order.setDate(resultSet.getDate("Date"));
                    order.setShippingDate(resultSet.getDate("ShippingDate"));
                    order.setStatus(resultSet.getString("Status"));
                    order.setShippingMethod(resultSet.getString("ShippingMethod"));
                    order.setSalesTax(resultSet.getDouble("SalesTax"));
                    order.setOrderSubtotal(resultSet.getDouble("OrderSubtotal"));
                    order.setOrderTotal(resultSet.getDouble("OrderTotal"));
                    order.setDiscount(resultSet.getDouble("Discount"));
                }  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
  
        return order;  
    }  

    /**
     * Method to add an order
     * @param order The order
     * @return True or false
     */
    public static boolean addOrder(Order order) {  
    	//Initialize variables
        String query = "INSERT INTO tblOrder (CustomerID, Date, ShippingDate, Status, ShippingMethod, SalesTax) VALUES (?, ?, ?, ?, ?, ?)";  
        boolean success = false;

        //Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
  	
  			//Getting all the order's info  
            preparedStatement.setInt(1, order.getCustomerID());
            preparedStatement.setDate(2, order.getDate());   
            preparedStatement.setDate(3, order.getShippingDate());  
            preparedStatement.setString(4, order.getStatus()); 
            preparedStatement.setString(5, order.getShippingMethod());  
            preparedStatement.setDouble(6, order.getSalesTax());   

            //Checking if order has been added
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
     * Method to get the count of all orders
     * @return The count of orders
     */
    public static int getOrderCount() {
        int orderCount = 0;
        String query = "SELECT COUNT(*) AS OrderCount FROM tblOrder";

        // Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                orderCount = resultSet.getInt("OrderCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderCount;
    }

    /**
     * Method to get all products for a specific order
     * @param orderId The order's ID
     * @return ArrayList of ArrayLists containing product details
     */
    public static ArrayList<ArrayList<Object>> getOrderProducts(int orderId) {
        //Initialize variables
        ArrayList<ArrayList<Object>> products = new ArrayList<>();
        String query = "SELECT p.ProductID, p.Description, po.QuantityOrdered, po.QuotedPrice, " +
                      "(po.QuantityOrdered * po.QuotedPrice) as Total " +
                      "FROM tblProductsOrder po " +
                      "JOIN tblProduct p ON p.ProductID = po.ProductID " +
                      "WHERE po.OrderID = ?";

        //Connecting to the DB 
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), 
                DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);
            
            //Getting results from query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    //Adding all product info
                    ArrayList<Object> product = new ArrayList<>();
                    product.add(resultSet.getString("ProductID"));
                    product.add(resultSet.getString("Description"));
                    product.add(resultSet.getInt("QuantityOrdered"));
                    product.add(resultSet.getDouble("QuotedPrice"));
                    product.add(resultSet.getDouble("Total"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    /**
     * Method to get all payments for a specific order
     * @param orderId The order's ID
     * @return ArrayList of ArrayLists containing payment details
     */
    public static ArrayList<ArrayList<Object>> getOrderPayments(int orderId) {
        //Initialize variables
        ArrayList<ArrayList<Object>> payments = new ArrayList<>();
        String query = "SELECT PaymentID, Date, Amount, Method " +
                      "FROM tblPayment " +
                      "WHERE OrderID = ?";

        //Connecting to the DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), 
                DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, orderId);
            
            //Getting results from query
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    //Adding all payment info
                    ArrayList<Object> payment = new ArrayList<>();
                    payment.add(resultSet.getInt("PaymentID"));
                    payment.add(resultSet.getDate("Date"));
                    payment.add(resultSet.getDouble("Amount"));
                    payment.add(resultSet.getString("Method"));
                    payments.add(payment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }
}