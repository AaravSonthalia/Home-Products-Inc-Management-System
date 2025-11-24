/**
 * Represents a product with details such as ID, description, price, 
 * inventory, classification, and warehouse location.
 * @author Ethan C and Aarav S
 * @version 1.0
 */
public class Product {
    private String productID;
    private String description;
    private double unitPrice;
    private int unitsOnHand;
    private String productClass;
    private int warehouseID;

    /**
     * Default constructor for creating a Product object with no initial values.
     */
    public Product() {
    }

    /**
     * Gets the unique ID of the product.
     * 
     * @return the product ID as a string.
     */
    public String getProductID() {
        return productID;
    }

    /**
     * Sets the unique ID of the product.
     * 
     * @param productID the product ID to set.
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     * Gets the description of the product.
     * 
     * @return the product description as a string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the product.
     * 
     * @param description the product description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the unit price of the product.
     * 
     * @return the unit price as a double.
     */
    public double getUnitPrice() {
        return unitPrice;
    }

    /**
     * Sets the unit price of the product.
     * 
     * @param unitPrice the unit price to set.
     */
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * Gets the number of units on hand for the product.
     * 
     * @return the units on hand as an integer.
     */
    public int getUnitsOnHand() {
        return unitsOnHand;
    }

    /**
     * Sets the number of units on hand for the product.
     * 
     * @param unitsOnHand the units on hand to set.
     */
    public void setUnitsOnHand(int unitsOnHand) {
        this.unitsOnHand = unitsOnHand;
    }

    /**
     * Gets the classification of the product.
     * 
     * @return the product class as a string.
     */
    public String getProductClass() {
        return productClass;
    }

    /**
     * Sets the classification of the product.
     * 
     * @param productClass the product class to set.
     */
    public void setProductClass(String productClass) {
        this.productClass = productClass;
    }

    /**
     * Gets the ID of the warehouse where the product is stored.
     * 
     * @return the warehouse ID as an integer.
     */
    public int getWarehouseID() {
        return warehouseID;
    }

    /**
     * Sets the ID of the warehouse where the product is stored.
     * 
     * @param warehouseID the warehouse ID to set.
     */
    public void setWarehouseID(int warehouseID) {
        this.warehouseID = warehouseID;
    }
}
