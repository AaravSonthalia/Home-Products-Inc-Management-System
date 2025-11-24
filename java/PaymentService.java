import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.util.ArrayList;  
import java.util.List;  
import java.sql.Date;

/**
 * Payment service class
 * @author Ethan C and Aarav S
 */
public class PaymentService 
{
	/**
	 * Method to get all the payments and some of their information
	 * @return A list of all payments
	 */
	public static List<Payment> getAllPayments(int customerId)
	{
		//Initialize variables
		List<Payment> payments = new ArrayList<>();  
        String query = "SELECT PaymentID, OrderID, Amount, Method FROM tblPayment WHERE CustomerID = ?";

        //Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  

            PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
  
            preparedStatement.setInt(1, customerId);  
            try (ResultSet resultSet = preparedStatement.executeQuery()) {  
                while (resultSet.next()) {  
                    //Getting some information for all payments for each customer
                    Payment payment = new Payment();
                    payment.setPaymentID(resultSet.getInt("PaymentID"));
                    payment.setOrderID(resultSet.getInt("OrderID"));
                    payment.setAmount(resultSet.getDouble("Amount"));
                    payment.setMethod(resultSet.getString("Method"));

                    payments.add(payment);
                }  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }   
  
        return payments;  
	}

	/**
	 * Method to get all the information on one payment
	 * @param id The payment's id
	 * @return The payment
	 */
	public static Payment getPaymentById(int id) {  
		//Initialize variables
        Payment payment = null;  
        String query = "SELECT * FROM tblCustomer WHERE ID = ?";  
  	
  		//Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  

             PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
  
            preparedStatement.setInt(1, id);  
            try (ResultSet resultSet = preparedStatement.executeQuery()) {  
                if (resultSet.next()) {  
                	//Grabbing all info from the customer
                    payment = new Payment();  
                    payment.setPaymentID(resultSet.getInt("PaymentID"));
                    payment.setCustomerID(resultSet.getInt("CustomerID"));
                    payment.setOrderID(resultSet.getInt("OrderID"));
                    payment.setDate(resultSet.getDate("Date"));
                    payment.setCardOwner(resultSet.getString("CardOwner"));
                    payment.setAmount(resultSet.getDouble("Amount"));
                    payment.setMethod(resultSet.getString("Method"));
                    payment.setCardNumber(resultSet.getString("CardNumber"));
                    payment.setCardExpirationDate(resultSet.getDate("CardExpirationDate"));
                    payment.setCreditCard(resultSet.getString("CreditCard"));
                }  
            }  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
  
        return payment;  
    }  

    /**
     * Method to add a payment
     * @param payment The Payment
     * @return True or false
     */
    public static boolean addPayment(Payment payment) {  
    	//Initialize variables
        String query = "INSERT INTO tblPayment (CustomerID, OrderID, Date, CardOwner, Amount, Method, CardNumber, CardExpirationDate, CreditCard) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";  
        boolean success = false;

        //Connecting to SQL DB
        try (Connection connection = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUsername(), DatabaseConfig.getDbPassword());  
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {  
  	
  			//Getting all the payment's info
            preparedStatement.setInt(1, payment.getCustomerID());  
            preparedStatement.setInt(2, payment.getOrderID());  
            preparedStatement.setDate(3, payment.getDate());
            preparedStatement.setString(4, payment.getCardOwner()); 
            preparedStatement.setDouble(5, payment.getAmount());  
            preparedStatement.setString(6, payment.getMethod());  
            preparedStatement.setString(7, payment.getCardNumber());
            preparedStatement.setDate(8, payment.getCardExpirationDate());
            preparedStatement.setString(9, payment.getCreditCard());     

            //Checking if payment has been added
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