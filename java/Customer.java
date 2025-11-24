/**
 * Represents a customer with various details such as name, contact information,
 * address, and other business-related data.
 * @author Ethan C and Aarav S
 * @version 1.0
 */
public class Customer {
    private int customerID;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private double credit;
    private int salesRepID;
    private String company;
    private String website;
    private String email;
    private String businessNumber;
    private String cellNumber;
    private String title;
    private String status;
    private String notes;
    private double lifetimeOrderTotal;
    private double remainingCredit;

    /**
     * Default constructor for creating a Customer object with no initial values.
     */
    public Customer() {
    }

    /**
     * Gets the customer's unique ID.
     * @return the customer's ID as an integer.
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the customer's unique ID.
     * @param customerID the unique ID to set for the customer.
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets the customer's first name.
     * @return the first name as a string.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the customer's first name.
     * @param firstName the first name to set for the customer.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the customer's last name.
     * @return the last name as a string.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the customer's last name.
     * @param lastName the last name to set for the customer.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the customer's street address.
     * @return the street address as a string.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the customer's street address.
     * @param street the street address to set for the customer.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the customer's city.
     * @return the city as a string.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the customer's city.
     * @param city the city to set for the customer.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the customer's state.
     * @return the state as a string.
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the customer's state.
     * @param state the state to set for the customer.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the customer's ZIP code.
     * 
     * @return the ZIP code as a string.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the customer's ZIP code.
     * @param zipCode the ZIP code to set for the customer.
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Gets the customer's credit amount.
     * @return the credit amount as a double.
     */
    public double getCredit() {
        return credit;
    }

    /**
     * Sets the customer's credit amount.
     * @param credit the credit amount to set for the customer.
     */
    public void setCredit(double credit) {
        this.credit = credit;
    }

    /**
     * Gets the sales representative ID associated with the customer.
     * @return the sales representative ID as an integer.
     */
    public int getSalesRepID() {
        return salesRepID;
    }

    /**
     * Sets the sales representative ID associated with the customer.
     * @param salesRepID the sales representative ID to set for the customer.
     */
    public void setSalesRepID(int salesRepID) {
        this.salesRepID = salesRepID;
    }

    /**
     * Gets the customer's company name.
     * @return the company name as a string.
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the customer's company name.
     * @param company the company name to set for the customer.
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Gets the customer's website URL.
     * @return the website URL as a string.
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Sets the customer's website URL.
     * @param website the website URL to set for the customer.
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Gets the customer's email address.
     * @return the email address as a string.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the customer's email address.
     * @param email the email address to set for the customer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the customer's business phone number.
     * @return the business phone number as a string.
     */
    public String getBusinessNumber() {
        return businessNumber;
    }

    /**
     * Sets the customer's business phone number.
     * @param businessNumber the business phone number to set for the customer.
     */
    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    /**
     * Gets the customer's cell phone number.
     * @return the cell phone number as a string.
     */
    public String getCellNumber() {
        return cellNumber;
    }

    /**
     * Sets the customer's cell phone number.
     * @param cellNumber the cell phone number to set for the customer.
     */
    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    /**
     * Gets the customer's title or position.
     * @return the title as a string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the customer's title or position.
     * @param title the title to set for the customer.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the customer's status.
     * @return the status as a string.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the customer's status.
     * @param status the status to set for the customer.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets any additional notes related to the customer.
     * @return the notes as a string.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets any additional notes related to the customer.
     * @param notes the notes to set for the customer.
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Gets the lifetime order total for the customer.
     * @return the liftime order total to set for the customer.
     */
    public double getLifetimeOrderTotal() {
        return lifetimeOrderTotal;
    }

    /**
     * Sets the lifetime order total for the customer.
     * @param lifetimeOrderTotal the lifetime order total to set for the customer.
     */
    public void setLifetimeOrderTotal(double lifetimeOrderTotal) {
        this.lifetimeOrderTotal = lifetimeOrderTotal;
    }

    /**
     * Gets the remaining credit for the customer.
     * @return the remaining credit to get for the customer.
     */
    public double getRemainingCredit() {
        return remainingCredit;
    }

    /**
     * Sets the remaining credit for the customer.
     * @return the remaining credit to set for the customer.
     */
    public void setRemainingCredit(double remainingCredit) {
        this.remainingCredit = remainingCredit;
    }
}