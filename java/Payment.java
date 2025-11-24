import java.sql.Date;

/**
 * Represents a payment with details about the transaction, customer, 
 * order, and payment method.
 * @author Ethan C and Aarav S
 * @version 1.0
 */
public class Payment {
    private int paymentID;
    private int customerID;
    private int orderID;
    private Date date;
    private String cardOwner;
    private double amount;
    private String method;
    private String cardNumber;
    private Date cardExpirationDate;
    private String creditCard;

    /**
     * Default constructor for creating a Payment object with no initial values.
     */
    public Payment() {
    }

    /**
     * Gets the unique ID of the payment.
     * 
     * @return the payment ID as an integer.
     */
    public int getPaymentID() {
        return paymentID;
    }

    /**
     * Sets the unique ID of the payment.
     * 
     * @param paymentID the payment ID to set.
     */
    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    /**
     * Gets the unique ID of the customer associated with the payment.
     * 
     * @return the customer ID as an integer.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the unique ID of the customer associated with the payment.
     * 
     * @param customerID the customer ID to set.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets the unique ID of the order associated with the payment.
     * 
     * @return the order ID as an integer.
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * Sets the unique ID of the order associated with the payment.
     * 
     * @param orderID the order ID to set.
     */
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    /**
     * Gets the date of the payment.
     * 
     * @return the payment date as a Date object.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the payment.
     * 
     * @param date the payment date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the name of the card owner.
     * 
     * @return the card owner's name as a string.
     */
    public String getCardOwner() {
        return cardOwner;
    }

    /**
     * Sets the name of the card owner.
     * 
     * @param cardOwner the card owner's name to set.
     */
    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
    }

    /**
     * Gets the payment amount.
     * 
     * @return the payment amount as a double.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the payment amount.
     * 
     * @param amount the payment amount to set.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Gets the payment method.
     * 
     * @return the payment method as a string.
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the payment method.
     * 
     * @param method the payment method to set.
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Gets the credit card number used for the payment.
     * 
     * @return the card number as a string.
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the credit card number used for the payment.
     * 
     * @param cardNumber the card number to set.
     */
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Gets the expiration date of the credit card.
     * 
     * @return the card expiration date as a Date object.
     */
    public Date getCardExpirationDate() {
        return cardExpirationDate;
    }

    /**
     * Sets the expiration date of the credit card.
     * 
     * @param cardExpirationDate the card expiration date to set.
     */
    public void setCardExpirationDate(Date cardExpirationDate) {
        this.cardExpirationDate = cardExpirationDate;
    }

    /**
     * Gets the type of credit card used (e.g., Visa, MasterCard).
     * 
     * @return the credit card type as a string.
     */
    public String getCreditCard() {
        return creditCard;
    }

    /**
     * Sets the type of credit card used (e.g., Visa, MasterCard).
     * 
     * @param creditCard the credit card type to set.
     */
    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }
}
