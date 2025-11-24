import java.sql.Date;

/**
 * Represents an order with details such as customer information, order dates, 
 * shipping method, and financial calculations.
 * @author Ethan C and Aarav S
 * @version 1.0
 */
public class Order {
    private int orderID;
    private int customerID;
    private String customerName;
    private Date date;
    private Date shippingDate;
    private String status;
    private String shippingMethod;
    private double salesTax;
    private double orderSubtotal;
    private double calculatedTax;
    private double orderTotal;
    private double discount;

    /**
     * Default constructor for creating an Order object with no initial values.
     */
    public Order() {
    }

    /**
     * Gets the unique ID of the order.
     * 
     * @return the order ID as an integer.
     */
    public int getOrderID() {
        return orderID;
    }

    /**
     * Sets the unique ID of the order.
     * 
     * @param orderID the unique order ID to set.
     */
    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    /**
     * Gets the ID of the customer associated with the order.
     * 
     * @return the customer's ID as an int.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the ID of the customer associated with the order.
     * 
     * @param customerID the customer's ID to set.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets the name of the customer
     * 
     * @return the customer's name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the customer
     * 
     * @param customerName the customer name to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the date when the order was placed.
     * 
     * @return the order date as a {@link Date} object.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date when the order was placed.
     * 
     * @param date the order date to set.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the shipping date of the order.
     * 
     * @return the shipping date as a {@link Date} object.
     */
    public Date getShippingDate() {
        return shippingDate;
    }

    /**
     * Sets the shipping date of the order.
     * 
     * @param shippingDate the shipping date to set.
     */
    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    /**
     * Gets the status of the order.
     * 
     * @return the order status as a string.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status of the order.
     * 
     * @param status the order status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the shipping method of the order.
     * 
     * @return the shipping method as a string.
     */
    public String getShippingMethod() {
        return shippingMethod;
    }

    /**
     * Sets the shipping method of the order.
     * 
     * @param shippingMethod the shipping method to set.
     */
    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    /**
     * Gets the sales tax percentage for the order.
     * 
     * @return the sales tax as a double.
     */
    public double getSalesTax() {
        return salesTax;
    }

    /**
     * Sets the sales tax percentage for the order.
     * 
     * @param salesTax the sales tax to set.
     */
    public void setSalesTax(double salesTax) {
        this.salesTax = salesTax;
    }

    /**
     * Gets the subtotal amount for the order.
     * 
     * @return the order subtotal as a double.
     */
    public double getOrderSubtotal() {
        return orderSubtotal;
    }

    /**
     * Sets the subtotal amount for the order.
     * 
     * @param orderSubtotal the order subtotal to set.
     */
    public void setOrderSubtotal(double orderSubtotal) {
        this.orderSubtotal = orderSubtotal;
    }

    /**
     * Gets the calculated tax amount for the order.
     * 
     * @return the calculated tax as a double.
     */
    public double getCalculatedTax() {
        return calculatedTax;
    }

    /**
     * Sets the calculated tax amount for the order.
     * 
     * @param calculatedTax the calculated tax to set.
     */
    public void setCalculatedTax(double calculatedTax) {
        this.calculatedTax = calculatedTax;
    }

    /**
     * Gets the total amount for the order.
     * 
     * @return the order total as a double.
     */
    public double getOrderTotal() {
        return orderTotal;
    }

    /**
     * Sets the total amount for the order.
     * 
     * @param orderTotal the order total to set.
     */
    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    /**
     * Gets the discount applied to the order.
     * 
     * @return the discount as a double.
     */
    public double getDiscount() {
        return discount;
    }

    /**
     * Sets the discount applied to the order.
     * 
     * @param discount the discount to set.
     */
    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
