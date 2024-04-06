package Project.Controller;

import Project.Model.Inventory;
import Project.Model.Part;
import Project.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * AddProductFormController is responsible for handling the Add Product Form UI interactions,
 * managing the creation of new products, and adding associated parts to the product.
 * This class also provides methods for handling form input validation and searching parts.
 * @author Diar Shabani
 */
public class AddProductFormController {
    private Inventory inventory;

    @FXML
    private TextField productIdField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productPriceField;
    @FXML
    private TextField productStockField;
    @FXML
    private TextField productMinField;
    @FXML
    private TextField productMaxField;
    @FXML
    private TextField searchField;
    private MainFormController mainFormController;
    @FXML
    private TableView<Part> availablePartsTableView;
    @FXML
    private TableColumn<Part, Integer> availablePartIdColumn;
    @FXML
    private TableColumn<Part, String> availablePartNameColumn;
    @FXML
    private TableColumn<Part, Integer> availablePartInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> availablePartPriceColumn;
    @FXML
    private TableView<Part> appliedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> appliedPartIdColumn;
    @FXML
    private TableColumn<Part, String> appliedPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> appliedPartInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> appliedPartPriceColumn;

    /**
     * Constructs an AddProductFormController with the given Inventory and MainFormController instances.
     * This constructor initializes the AddProductFormController with the inventory to manage parts and products,
     * and the MainFormController to update the main form's tables when a new product is added.
     *
     * @param inventory  The Inventory instance to manage parts and products.
     * @param mainFormController  The MainFormController instance to update the main form's tables.
     */
    public AddProductFormController(Inventory inventory, MainFormController mainFormController) {
        this.inventory = inventory;
        this.mainFormController = mainFormController;
    }

    /**
     * Initializes the AddProductFormController by setting up table columns and populating the available parts table.
     * This method sets up the cell value factories for both the available parts table and the applied parts table,
     * and populates the available parts table with all the parts from the inventory.
     */
    @FXML
    public void initialize() {
        productIdField.setDisable(true);
        productIdField.setText(String.valueOf(Product.getNextId()));
        availablePartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        availablePartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        availablePartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availablePartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        appliedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        appliedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        appliedPartInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        appliedPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        availablePartsTableView.setItems(inventory.getAllParts());
    }

    /**
     * Handles the Add Part button click event and adds the selected part to the applied parts table.
     * This method retrieves the currently selected part from the available parts table, and if a part is selected,
     * it adds the selected part to the applied parts table, which represents the parts associated with the product being created
     */
    @FXML
    public void handleAddPart() {
        Part selectedPart = availablePartsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            appliedPartsTableView.getItems().add(selectedPart);
        }
    }

    /**
     * Handles the Search button click event and filters the available parts table based on the search text.
     * This method retrieves the search text from the search field and performs a search based on the following logic:
     * 1. If the search text is empty, it populates the available parts table with all the parts from the inventory.
     * 2. If the search text can be parsed as an integer (part ID), it searches for a part with the given ID and
     *    updates the available parts table with the found part or with an empty table if no part is found.
     * 3. If the search text is a non-integer string (part name), it searches for parts with names containing the search text
     *    and updates the available parts table with the found parts or with an empty table if no parts are found.
     */
    @FXML
    public void handleSearch() {
        String searchText = searchField.getText();

        if (searchText.isEmpty()) {
            availablePartsTableView.setItems(inventory.getAllParts());
        } else {
            try {
                int partId = Integer.parseInt(searchText);
                Part searchedPart = inventory.lookupPart(partId);

                if (searchedPart != null) {
                    ObservableList<Part> foundPart = FXCollections.observableArrayList();
                    foundPart.add(searchedPart);
                    availablePartsTableView.setItems(foundPart);
                } else {
                    availablePartsTableView.setItems(null);
                }
            } catch (NumberFormatException e) {
                availablePartsTableView.setItems(inventory.lookupPart(searchText));
            }
        }
    }

    /**
     * Handles the Remove Part button click event and removes the selected part from the applied parts table
     */
    @FXML
    public void handleRemovePart() {
        Part selectedPart = appliedPartsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            appliedPartsTableView.getItems().remove(selectedPart);
        }
    }

    /**
     * Handles the Save button click event and saves the new product with its associated parts
     * @param event The ActionEvent for this button click
     */
    @FXML
    public void handleSave(ActionEvent event) {
        int id = Integer.parseInt(productIdField.getText());
        String name = productNameField.getText();
        double price = Double.parseDouble(productPriceField.getText());
        int stock = Integer.parseInt(productStockField.getText());
        int min = Integer.parseInt(productMinField.getText());
        int max = Integer.parseInt(productMaxField.getText());

        if (stock > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Stock cannot be more than maximum storage for the part");
            alert.setContentText("Please correct the stock value.");
            alert.showAndWait();
            return;
        }

        if (stock < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Stock cannot be less than minimum storage for the product");
            alert.setContentText("Please correct the stock value.");
            alert.showAndWait();
            return;
        }

        if (price < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Price cannot be negative");
            alert.setContentText("Please correct the Price value.");
            alert.showAndWait();
            return;
        }

        if (stock < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Stock cannot be negative");
            alert.setContentText("Please correct the Stock value.");
            alert.showAndWait();
            return;
        }

        if (min < 0 || max < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Min and Max values cannot be negative");
            alert.setContentText("Please correct the input values.");
            alert.showAndWait();
            return;
        }

        if (min > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Min value cannot be greater than Max value");
            alert.setContentText("Please correct the input values.");
            alert.showAndWait();
            return;
        }

        if (name==null||name.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Name cannot be empty");
            alert.setContentText("Please add a name.");
            alert.showAndWait();
            return;
        }
        Product product = new Product(id, name, price, stock, min, max);
        inventory.addProduct(product);
        mainFormController.updateTables();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Handles the Cancel button click event and closes the Add Product form
     * @param event The ActionEvent for this button click
     */
    @FXML
    public void handleCancel(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}