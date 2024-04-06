package Project.Controller;

import Project.Model.Inventory;
import Project.Model.Part;
import Project.Model.Product;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

/**
 * MainFormController is responsible for handling the main form UI interactions
 * and managing parts and products within the inventory.
 * @author Diar Shabani
 */
public class MainFormController {

    private Inventory inventory;
    @FXML
    private TextField searchPartField;
    @FXML
    private TableView<Part> partsTableView;
    @FXML
    private Button addPartBtn;
    @FXML
    private Button modifyPartBtn;
    @FXML
    private Button deletePartBtn;
    @FXML
    private TextField searchProductField;
    @FXML
    private TableView<Product> productsTableView;
    @FXML
    private Button addProductBtn;
    @FXML
    private Button modifyProductBtn;
    @FXML
    private Button deleteProductBtn;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private Button exitBtn;

    /**
     * Constructs a MainFormController with a given inventory.
     * @param inventory The Inventory instance.
     */
    public MainFormController(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Sets the current inventory for this controller.
     * @param inventory The Inventory instance.
     */
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Initializes the main form UI components.
     */
    @FXML
    public void initialize() {
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTableView.setItems(inventory.getAllParts());
        productsTableView.setItems(inventory.getAllProducts());
    }

    /**
     * Handles the Add Part button click event and opens the Add Part form.
     * @param event The ActionEvent for this button click.
     */
    @FXML
    private void handleAddPart(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Project/View/AddPartForm.fxml"));
            loader.setControllerFactory(c -> new AddPartFormController(inventory,this));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Add Part");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the Modify Part button click event and opens the Modify Part form with the selected part.
     * @param event The ActionEvent for this button click.
     */
    @FXML
    private void handleModifyPart(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Project/View/ModifyPartForm.fxml"));
                loader.setControllerFactory(c -> new ModifyPartFormController(inventory, selectedPart, this));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Modify Part");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the Delete Part button click event and deletes the selected part from the inventory.
     * @param event The ActionEvent for this button click.
     */
    @FXML
    private void handleDeletePart(ActionEvent event) {
        Part selectedPart = partsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part");
            alert.setHeaderText("Are you sure you want to delete this part?");
            alert.setContentText("This action cannot be undone.");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                inventory.deletePart(selectedPart);
            }
        }
    }

    /**
     * Handles the search functionality for parts and filters the parts table view.
     */
    @FXML
    private void handleSearchPart() {
        String searchText = searchPartField.getText();
        ObservableList<Part> filteredList = inventory.lookupPart(searchText);
        partsTableView.setItems(filteredList);
    }

    /**
     * Handles the Add Product button click event and opens the Add Product form.
     * @param event The ActionEvent for this button click.
     */
    @FXML
    private void handleAddProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Project/View/AddProductForm.fxml"));
            loader.setControllerFactory(c -> new AddProductFormController(inventory, this));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Add Product");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the Modify Product button click event and opens the Modify Product form with the selected product.
     * @param event The ActionEvent for this button click.
     */
    @FXML
    private void handleModifyProduct(ActionEvent event) {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Project/View/ModifyProductForm.fxml"));
                loader.setControllerFactory(c -> new ModifyProductFormController(inventory, selectedProduct,this));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setTitle("Modify Product");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the Delete Product button click event and deletes the selected product from the inventory.
     * @param event The ActionEvent for this button click.
     */
    @FXML
    private void handleDeleteProduct(ActionEvent event) {
        Product selectedProduct = productsTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            inventory.deleteProduct(selectedProduct);
            productsTableView.setItems(inventory.getAllProducts());
        }
    }

    /**
     * Handles the search functionality for products and filters the products table view.
     */
    @FXML
    private void handleSearchProduct() {
        String searchText = searchProductField.getText();
        ObservableList<Product> filteredList = inventory.lookupProduct(searchText);
        productsTableView.setItems(filteredList);
    }

    /**
     * Handles the Exit button click event and closes the application.
     * @param event The ActionEvent for this button click.
     */
    @FXML
    private void handleExit(ActionEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

    public TableView<Part> getPartsTable(){
        return partsTableView;
    }

    /**
     * Updates the parts and products table views with the current inventory data.
     */
    public void updateTables() {
        partsTableView.setItems(inventory.getAllParts());
        productsTableView.setItems(inventory.getAllProducts());
    }
}