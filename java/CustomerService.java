import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.util.ArrayList;  
import java.util.List;  

/**
 * Customer service class
 * @author Ethan C and Aarav S
 */
public class CustomerService 
{
	/**
	 * Method to get all the customers and some of their information
	 * @return A list of all customers
	 */
	public static List<Customer> getAllCustomers()
	{
		//Initialize variables
		List<Customer> customers = new ArrayList<>();  
        String query = "SELECT CustomerID, FirstName, LastName, Company, Email, Status FROM tblCustomer";  
  		
  		//Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query);  
             ResultSet resultSet = preparedStatement.executeQuery()) {  
  			
  			//Looping through all customers
            while (resultSet.next()) {  
            	//Getting all information from each customer
                Customer customer = new Customer();  
                customer.setCustomerID(resultSet.getInt("CustomerID"));
                customer.setFirstName(resultSet.getString("FirstName"));
                customer.setLastName(resultSet.getString("LastName"));
                customer.setCompany(resultSet.getString("Company"));
                customer.setEmail(resultSet.getString("Email"));
                customer.setStatus(resultSet.getString("Status"));

                customers.add(customer);  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
  
        return customers;  
	}

	/**
	 * Method to get all the information on one customer
	 * @param id The customer's id
	 * @return The customer
	 */
	public static Customer getCustomerByID(int id) {  
		//Initialize variables
        Customer customer = null;  
        String query = "SELECT C.*, ROUND(SUM(PO.QuantityOrdered * PO.QuotedPrice), 2) AS LifetimeOrderTotal," +
            "Credit - ROUND(SUM(PO.QuantityOrdered * PO.QuotedPrice), 2) AS RemainingCredit " +
            "FROM tblOrder AS O " +
            "LEFT JOIN tblProductsOrder AS PO " +
            "ON O.OrderID = PO.OrderID " +
            "LEFT JOIN tblCustomer AS C " + 
            "ON C.CustomerID = O.CustomerID " +
            "WHERE C.CustomerID = ?";  
  	
  		//Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  

            PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
  
            preparedStatement.setInt(1, id);  
            try (ResultSet resultSet = preparedStatement.executeQuery()) {  
                if (resultSet.next()) {  
                	//Grabbing all info from the customer
                    customer = new Customer();  
                    customer.setCustomerID(resultSet.getInt("CustomerID"));  
                    customer.setFirstName(resultSet.getString("FirstName"));  
                    customer.setLastName(resultSet.getString("LastName"));  
                    customer.setStreet(resultSet.getString("Street"));  
                    customer.setCity(resultSet.getString("City"));  
                    customer.setState(resultSet.getString("State"));  
                    customer.setZipCode(resultSet.getString("ZipCode"));  
                    customer.setCredit(resultSet.getDouble("Credit"));  
                    customer.setSalesRepID(resultSet.getInt("SalesRepID"));  
                    customer.setCompany(resultSet.getString("Company")); 
                    customer.setWebsite(resultSet.getString("Website"));
                    customer.setEmail(resultSet.getString("Email")); 
                    customer.setBusinessNumber(resultSet.getString("BusinessNumber"));
                    customer.setCellNumber(resultSet.getString("CellNumber"));
                    customer.setTitle(resultSet.getString("Title"));
                    customer.setStatus(resultSet.getString("Status"));
                    customer.setNotes(resultSet.getString("Notes"));
                    customer.setLifetimeOrderTotal(resultSet.getDouble("LifetimeOrderTotal"));
                    customer.setRemainingCredit(resultSet.getDouble("RemainingCredit"));
                }  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
  
        return customer;  
    }  

    /**
     * Method to add a customer
     * @param customer The customer
     * @return True or false
     */
    public static boolean addCustomer(Customer customer) {  
    	//Initialize variables
        String query = "INSERT INTO tblCustomer (FirstName, LastName, Street, City, State, ZipCode, Credit, SalesRepID, Company, Website, Email, BusinessNumber, CellNumber, Title, Status, Notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";  
        boolean success = false;

        //Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
  	
  			//Getting all the customer's info
            preparedStatement.setString(1, customer.getFirstName());  
            preparedStatement.setString(2, customer.getLastName());  
            preparedStatement.setString(3, customer.getStreet());  
            preparedStatement.setString(4, customer.getCity()); 
            preparedStatement.setString(5, customer.getState());  
            preparedStatement.setString(6, customer.getZipCode());  
            preparedStatement.setDouble(7, customer.getCredit());
            preparedStatement.setInt(8, customer.getSalesRepID());
            preparedStatement.setString(9, customer.getCompany());   
            preparedStatement.setString(10, customer.getWebsite());   
            preparedStatement.setString(11, customer.getEmail());  
            preparedStatement.setString(12, customer.getBusinessNumber());
            preparedStatement.setString(13, customer.getCellNumber()); 
            preparedStatement.setString(14, customer.getTitle());
            preparedStatement.setString(15, customer.getStatus());
            preparedStatement.setString(16, customer.getNotes());   

            //Checking if customer has been added
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
     * Method to get the count of customers in the database
     * @return The total number of customers
     */
    public static int getCustomerCount() {
        // Initialize variables
        int count = 0;
        String query = "SELECT COUNT(*) AS CustomerCount FROM tblCustomer";

        // Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            // Retrieve the count from the result set
            if (resultSet.next()) {
                count = resultSet.getInt("CustomerCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Method to edit a customer
     * @param customer The customer to edit
     * @return True or false
     */
    public static boolean editCustomer(Customer customer)
    {
        //Initialize variables
        String query = "UPDATE tblCustomer " +
        "SET FirstName = ?, LastName = ?, Street = ?, City = ?, State = ?, ZipCode = ?, Credit = ?, SalesRepID = ?, Company = ?, Website = ?, " + 
        "Email = ?, BusinessNumber = ?, CellNumber = ?, Title = ?, Status = ?, Notes = ? WHERE CustomerID = ?";  
        boolean success = false;

        //Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
    
            //Getting all the customer's info
            preparedStatement.setString(1, customer.getFirstName());  
            preparedStatement.setString(2, customer.getLastName());  
            preparedStatement.setString(3, customer.getStreet());  
            preparedStatement.setString(4, customer.getCity()); 
            preparedStatement.setString(5, customer.getState());  
            preparedStatement.setString(6, customer.getZipCode());  
            preparedStatement.setDouble(7, customer.getCredit());
            preparedStatement.setInt(8, customer.getSalesRepID());
            preparedStatement.setString(9, customer.getCompany());   
            preparedStatement.setString(10, customer.getWebsite());   
            preparedStatement.setString(11, customer.getEmail());  
            preparedStatement.setString(12, customer.getBusinessNumber());
            preparedStatement.setString(13, customer.getCellNumber()); 
            preparedStatement.setString(14, customer.getTitle());
            preparedStatement.setString(15, customer.getStatus());
            preparedStatement.setString(16, customer.getNotes());  
            preparedStatement.setInt(17, customer.getCustomerID()); 

            //Checking if customer has been edited
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
     * Method to get all payments from a customer
     * @param id The customer's id
     * @return A list of all the payments
     */
    public static List<Payment> getAllCustomersPayments(int id)
    {
        //Initialize variables
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT PaymentID, OrderID, Date, Amount, Method FROM tblPayment WHERE CustomerID = ?";

        //Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) { 
    
            //Getting all the payment's info
            preparedStatement.setInt(1, id);
        
            Payment payment = new Payment();
            payment.setPaymentID(resultSet.getInt("PaymentID"));
            payment.setOrderID(resultSet.getInt("OrderID"));
            payment.setDate(resultSet.getDate("Date"));
            payment.setAmount(resultSet.getDouble("Amount"));
            payment.setMethod(resultSet.getString("Method"));

            payments.add(payment);

        } catch (SQLException e) {  
            e.printStackTrace();  
        }   

        return payments;
    }

    /**
     * Method to get all payments from a customer with additional details
     * @param id The customer's id
     * @return A List where each inner List contains [PaymentID, OrderID, Date, Amount, Method, CardOwner]
     */
    public static ArrayList<ArrayList<Object>> getAllCustomerPaymentsDetailed(int id) {
        // Initialize variables
        ArrayList<ArrayList<Object>> payments = new ArrayList<>();
        String query = "SELECT PaymentID, OrderID, Date, Amount, Method, CardOwner " +
                      "FROM tblPayment WHERE CustomerID = ?";

        // Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.getDbUrl(), 
                DatabaseConfig.getDbUsername(), 
                DatabaseConfig.getDbPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the customer ID parameter
            preparedStatement.setInt(1, id);

            // Execute query and process results
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ArrayList<Object> payment = new ArrayList<>();
                    payment.add(resultSet.getInt("PaymentID"));
                    payment.add(resultSet.getInt("OrderID"));
                    payment.add(resultSet.getDate("Date"));
                    payment.add(resultSet.getDouble("Amount"));
                    payment.add(resultSet.getString("Method"));
                    payment.add(resultSet.getString("CardOwner"));
                    
                    payments.add(payment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }

    /**
     * Method to get all products ordered by a customer
     * @param id The customer's id
     * @return A List where each inner List contains [ProductID, Description, Quantity, QuotedPrice, OrderID, OrderDate]
     */
    public static ArrayList<ArrayList<Object>> getAllCustomerProducts(int id) {
        // Initialize variables
        ArrayList<ArrayList<Object>> products = new ArrayList<>();
        String query = "SELECT p.ProductID, p.Description, po.QuantityOrdered, " +
                      "po.QuotedPrice, o.OrderID, o.Date " +
                      "FROM tblOrder o " +
                      "JOIN tblProductsOrder po ON o.OrderID = po.OrderID " +
                      "JOIN tblProduct p ON po.ProductID = p.ProductID " +
                      "WHERE o.CustomerID = ? " +
                      "ORDER BY o.Date DESC";

        // Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(
                DatabaseConfig.getDbUrl(), 
                DatabaseConfig.getDbUsername(), 
                DatabaseConfig.getDbPassword());
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the customer ID parameter
            preparedStatement.setInt(1, id);

            // Execute query and process results
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ArrayList<Object> product = new ArrayList<>();
                    product.add(resultSet.getString("ProductID"));
                    product.add(resultSet.getString("Description"));
                    product.add(resultSet.getInt("QuantityOrdered"));
                    product.add(resultSet.getDouble("QuotedPrice"));
                    product.add(resultSet.getInt("OrderID"));
                    product.add(resultSet.getDate("Date"));
                    
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}