/**
 * Represents a sales representative with contact details, address, 
 * and managerial information.
 * @author Ethan C and Aarav S
 * @version 1.0
 */
public class SalesRep {
    private int salesRepID;
    private String firstName;
    private String lastName;
    private String businessNumber;
    private String cellNumber;
    private String homeNumber;
    private String faxNumber;
    private String title;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private double commission;
    private int managerID;

    /**
     * Default constructor for creating a SalesRep object with no initial values.
     */
    public SalesRep() {
    }

    /**
     * Gets the sales representative's unique ID.
     * 
     * @return the sales representative's ID as an integer.
     */
    public int getSalesRepID() {
        return salesRepID;
    }

    /**
     * Sets the sales representative's unique ID.
     * 
     * @param salesRepID the sales representative's ID to set.
     */
    public void setSalesRepID(int salesRepID) {
        this.salesRepID = salesRepID;
    }

    /**
     * Gets the sales representative's first name.
     * 
     * @return the first name as a string.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the sales representative's first name.
     * 
     * @param firstName the first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the sales representative's last name.
     * 
     * @return the last name as a string.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the sales representative's last name.
     * 
     * @param lastName the last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the sales representative's business phone number.
     * 
     * @return the business phone number as a string.
     */
    public String getBusinessNumber() {
        return businessNumber;
    }

    /**
     * Sets the sales representative's business phone number.
     * 
     * @param businessNumber the business phone number to set.
     */
    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber;
    }

    /**
     * Gets the sales representative's cell phone number.
     * 
     * @return the cell phone number as a string.
     */
    public String getCellNumber() {
        return cellNumber;
    }

    /**
     * Sets the sales representative's cell phone number.
     * 
     * @param cellNumber the cell phone number to set.
     */
    public void setCellNumber(String cellNumber) {
        this.cellNumber = cellNumber;
    }

    /**
     * Gets the sales representative's home phone number.
     * 
     * @return the home phone number as a string.
     */
    public String getHomeNumber() {
        return homeNumber;
    }

    /**
     * Sets the sales representative's home phone number.
     * 
     * @param homeNumber the home phone number to set.
     */
    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    /**
     * Gets the sales representative's fax number.
     * 
     * @return the fax number as a string.
     */
    public String getFaxNumber() {
        return faxNumber;
    }

    /**
     * Sets the sales representative's fax number.
     * 
     * @param faxNumber the fax number to set.
     */
    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    /**
     * Gets the sales representative's title or position.
     * 
     * @return the title as a string.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the sales representative's title or position.
     * 
     * @param title the title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the sales representative's street address.
     * 
     * @return the street address as a string.
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the sales representative's street address.
     * 
     * @param street the street address to set.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets the sales representative's city.
     * 
     * @return the city as a string.
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the sales representative's city.
     * 
     * @param city the city to set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the sales representative's state.
     * 
     * @return the state as a string.
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the sales representative's state.
     * 
     * @param state the state to set.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the sales representative's ZIP code.
     * 
     * @return the ZIP code as a string.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the sales representative's ZIP code.
     * 
     * @param zipCode the ZIP code to set.
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Gets the sales representative's commission rate.
     * 
     * @return the commission as a double.
     */
    public double getCommission() {
        return commission;
    }

    /**
     * Sets the sales representative's commission rate.
     * 
     * @param commission the commission to set.
     */
    public void setCommission(double commission) {
        this.commission = commission;
    }

    /**
     * Gets the ID of the manager associated with the sales representative.
     * 
     * @return the manager's ID as an integer.
     */
    public int getManagerID() {
        return managerID;
    }

    /**
     * Sets the ID of the manager associated with the sales representative.
     * 
     * @param managerID the manager's ID to set.
     */
    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }
}
