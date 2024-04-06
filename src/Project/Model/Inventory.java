package Project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class provides a means to manage and store part and product objects
 * @author Diar Shabani
 */
public class Inventory {

    private ObservableList<Part> allParts;
    private ObservableList<Product> allProducts;

    /**
     * Constructs a new instance of the Inventory class with initialized lists
     */
    public Inventory() {
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
    }

    /**
     * Adds a new part into the inventory
     * @param newPart The part object to be added
     */
    public void addPart(Part newPart) {
        //System.out.println("Part added: " + newPart + ", Total parts: " + allParts.size());
        allParts.add(newPart);
    }

    /**
     * Adds a new product into the inventory
     * @param newProduct The product to be added
     */
    public void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches for a part using its ID
     * @param partId The ID tied to the desired part
     * @return The matched part or null if not found
     */
    public Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Searches for a product using its ID
     * @param productId The ID tied to the desired product
     * @returnThe matched product or null if not found
     */
    public Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Finds parts based on a name
     * @param partName The name to search for
     * @return A list of parts with matching names
     */
    public ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        for (Part part : allParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase())||(part.getId()+"").contains(partName)) {
                matchingParts.add(part);
            }
        }
        return matchingParts;
    }

    /**
     * Finds products based on a name
     * @param productName The name to search for
     * @return  A list of products with matching names
     */
    public ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        for (Product product : allProducts) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())||(product.getId()+"").contains(productName)) {
                matchingProducts.add(product);
            }
        }
        return matchingProducts;
    }

    /**
     * Modifies an existing part at a given index
     * @paramindex The index of the part to be changed
     * @param selectedPart The updated part data
     */
    public void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Modifies an existing product at a given index
     * @param index The index of the product to be changed
     * @param selectedProduct The updated product data
     */
    public void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Erases a specific part from the inventory
     * @param selectedPart The part targeted for deletion
     * @return True if successful, false otherwise
     */
    public boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * D Erases a specific product from the inventory
     * @param selectedProduct The product targeted for deletion
     * @return True if successful, false otherwise
     */
    public boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Provides a complete list of parts in the inventory
     * @return A list of all parts stored.
     */
    public ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Provides a complete list of products in the inventory
     * @return A list of all products stored
     */
    public ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Handles retrieving the next number for a apart id
     * logically earror is if we initialize with an empty parts list and I start from int i.
     */
    public int returnNextPartID() {
        for (int i = 1; i <= allParts.size() + 1; i++) {
            boolean idExists = false;
            for (Part part : allParts) {
                if (part.getId() == i) {
                    idExists = true;
                    break;
                }
            }
            if (!idExists) {
                return i;
            }
        }
        return allParts.size() + 1;
    }

    /**
     * Handles retrieving the next number for a product id
     */
    public int returnNextProductID() {
        for (int i = 1; i <= allProducts.size() + 1; i++) {
            boolean idExists = false;
            for (Product product : allProducts) {
                if (product.getId() == i) {
                    idExists = true;
                    break;
                }
            }
            if (!idExists) {
                return i;
            }
        }
        return allProducts.size() + 1;
    }
}