package Project.Controller;

import Project.Model.Inventory;
import Project.Model.Outsourced;
import Project.Model.Part;
import Project.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * ModifyProductFormController is the controller class responsible for handling the Modify Product Form
 * @author Diar Shabani
 */
public class ModifyProductFormController {

    private Inventory inventory;
    private Product product;

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
    private TextField partSearchField;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableView<Part> associatedPartsTable;
    private MainFormController mainFormController;

    /**
     * Constructor for the ModifyProductFormController class.
     * @param inventory  The inventory instance to manage products.
     * @param product  The product to modify.
     * @param mainFormController  The main form controller instance to update tables.
     */
    public ModifyProductFormController(Inventory inventory, Product product,MainFormController mainFormController) {
        this.inventory = inventory;
        this.product = product;
        this.mainFormController = mainFormController;
    }

    /**
     * Initializes the Modify Product Form with the current product data
     * Populates the form fields with the product's ID, name, price, stock, minimum, and maximum values
     */
    @FXML
    public void initialize() {

        ObservableList<Part> copiedParts = FXCollections.observableArrayList();
        copiedParts.addAll(mainFormController.getPartsTable().getItems());

        partsTable.setItems(copiedParts);

        productIdField.setDisable(true);
        productIdField.setText(Integer.toString(product.getId()));
        productNameField.setText(product.getName());
        productPriceField.setText(Double.toString(product.getPrice()));
        productStockField.setText(Integer.toString(product.getStock()));
        productMinField.setText(Integer.toString(product.getMin()));
        productMaxField.setText(Integer.toString(product.getMax()));

        if (partsTable.getColumns().isEmpty()) {
            TableColumn<Part, Integer> partIdColumn = new TableColumn<>("Part ID");
            partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            partsTable.getColumns().add(partIdColumn);

            TableColumn<Part, String> partNameColumn = new TableColumn<>("Part Name");
            partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            partsTable.getColumns().add(partNameColumn);
        }

        if (associatedPartsTable.getColumns().isEmpty()) {
            TableColumn<Part, Integer> associatedPartIdColumn = new TableColumn<>("Associated Part ID");
            associatedPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            associatedPartsTable.getColumns().add(associatedPartIdColumn);

            TableColumn<Part, String> associatedPartNameColumn = new TableColumn<>("Associated Part Name");
            associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            associatedPartsTable.getColumns().add(associatedPartNameColumn);
        }

        associatedPartsTable.setItems(product.getAllAssociatedParts());
    }
    /**
     * Handles the search action on the Modify Product Form
     */
    @FXML
    public void handleSearch() {
        String searchTerm = partSearchField.getText();
        ObservableList<Part> searchResults = inventory.lookupPart(searchTerm);
        partsTable.setItems(searchResults);
    }

    /**
     * Handles the add part action on the Modify Product Form
     */
    @FXML
    public void handleAddPart() {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            product.addAssociatedPart(selectedPart);
            partsTable.getItems().remove(selectedPart);
            associatedPartsTable.setItems(product.getAllAssociatedParts());
        }
    }

    /**
     * Handles the remove part action on the Modify Product Form
     */
    @FXML
    public void handleRemovePart() {
        Part selectedPart = associatedPartsTable.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            product.deleteAssociatedPart(selectedPart);
            partsTable.getItems().add(selectedPart);
            associatedPartsTable.setItems(product.getAllAssociatedParts());
        }
    }

    /**
     * Handles the save action on the Modify Product Form.
     * Retrieves the updated product information from the form fields,
     * creates a new Product instance with the updated data, updates the product
     * in the inventory, refreshes the main form's tables, and closes the Modify Product Form
     * Future improvement, attempt to modify the product object instead of creating a new instance every time i save.
     */
    @FXML
    public void handleSave() {
        int id = Integer.parseInt(productIdField.getText());
        for(Product knownProducts : inventory.getAllProducts())
        {
            if (!knownProducts.equals(product))
            {
                if(knownProducts.getId()==id){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input on modify id field");
                    alert.setHeaderText("Id cannot be an another products existing id");
                    alert.setContentText("Please correct the id of the Product.");
                    alert.showAndWait();
                    return;
                }
            }
        }


        String name = productNameField.getText();
        double price = Double.parseDouble(productPriceField.getText());
        int stock = Integer.parseInt(productStockField.getText());
        int min = Integer.parseInt(productMinField.getText());
        int max = Integer.parseInt(productMaxField.getText());
        if (stock > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Stock cannot be more than maximum storage for the product");
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
        System.out.println(product.getAllAssociatedParts().toString());
        Product updatedProduct = new Product(id, name, price, stock, min, max);
        updatedProduct.setId(id);
        updatedProduct.addAssociatedParts(product.getAllAssociatedParts());
        int productIndex = inventory.getAllProducts().indexOf(product);
        inventory.updateProduct(productIndex, updatedProduct);
        mainFormController.updateTables();
        closeWindow();
    }

    /**
     * Handles the cancel action on the Modify Product Form
     * Closes the form without saving changes
     */
    @FXML
    public void handleCancel() {
        closeWindow();
    }

    /**
     * Closes the Modify Product Form window
     */
    private void closeWindow() {
        Stage stage = (Stage) productIdField.getScene().getWindow();
        stage.close();
    }
}