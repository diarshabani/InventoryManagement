package Project.Model;

import Project.Model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product is a distinct item that is composed of Part objects
 * @author Diar Shabani
 */
public class Product {
    private ObservableList<Part> associatedParts;
    private static int nextId = 0;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for the Product class.
     * @param id  The unique identifier of the product
     * @param name  The name of the product
     * @param price  Cost associated with the product
     * @param stock  The Amount of Stock for the product
     * @param min  The minimum amount of stock for the product
     * @param max  The maximum amount stock for the product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = ++nextId;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = FXCollections.observableArrayList();
    }

    /**
     * Returns the next available ID for a new Product
     * @return The next available ID
     */
    public static int getNextId() {
        return nextId + 1;
    }

    /**
     *  Returns the ID of the product object
     * @return Returns the ID of the product object
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the product object
     * @param id The ID to set for the product object
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the name of the product object
     * @return The name of the product object
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product object
     * @param name The name to set for the product object
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the price of the product object
     * @return The price of the product object
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product object
     * @param price The price to set for the product object
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Returns the amount of stock for the product obj
     * @return the amount of stock for the product obj
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets the stock amount of the product object
     * @param stock The stock amount to set for the product object
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Returns the minimum stock amount of the product object
     * @return The minimum stock amount of the product object
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets the minimum amount of stock for the product
     * @param min the minimum amount of stock for the product
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Returns the maximum amount of stock of the product
     * @return the maximum amount of stock of the product
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets the maximum amount of stock for the product
     * @param max the maximum amount of stock for the product
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds an associated part to the product
     * @param part the associated part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Adds an associated part list to the product
     * @param parts the associated parts list to add
     */
    public void addAssociatedParts(ObservableList parts) {
        associatedParts.setAll(parts);
    }

    /**
     * Deletes an associated part from the product
     * @param part The part to deleted from the associated parts list
     * @return true if the part was successfully deleted, false otherwise
     */
    public boolean deleteAssociatedPart(Part part) {
        return associatedParts.remove(part);
    }

    /**
     * Returns a list of all associated parts for the product
     * @return A ObservableList of all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


}