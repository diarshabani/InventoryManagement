
package Project.Controller;

import Project.Model.InHouse;
import Project.Model.Inventory;
import Project.Model.Outsourced;
import Project.Model.Part;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * AddPartFormController is responsible for handling the Add Part Form UI interactions,
 * managing the creation of new parts (InHouse and Outsourced), and handling form input validation.
 * @author Diar Shabani
 */
public class AddPartFormController {
    @FXML
    private TextField partIdField;
    @FXML
    private TextField partNameField;
    @FXML
    private TextField partPriceField;
    @FXML
    private TextField partStockField;
    @FXML
    private TextField partMinField;
    @FXML
    private TextField partMaxField;
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outsourcedRadio;
    @FXML
    private Label machineIdOrCompanyNameLabel;
    @FXML
    private TextField machineIdOrCompanyNameField;
    private Inventory inventory;
    private ToggleGroup toggleGroup;
    private MainFormController mainFormController;

    /**
     * Constructs an AddPartFormController instance with the provided inventory and mainFormController.
     * The constructor initializes the controller by setting the inventory and mainFormController
     * references. This allows the controller to interact with the inventory to add parts and
     * update the tables in the MainFormController after adding a new part.
     *
     * @param inventory         the Inventory object that holds parts and products
     * @param mainFormController the MainFormController instance to update tables after adding a new part
     */
    public AddPartFormController(Inventory inventory, MainFormController mainFormController) {
        this.inventory = inventory;
        this.mainFormController = mainFormController;
    }

    /**
     * Initializes the AddPartFormController by setting up the form fields, toggle group, and
     * default selection for the part type.
     */
    @FXML
    public void initialize() {
        partIdField.setDisable(true);
        partIdField.setText(String.valueOf(this.inventory.returnNextPartID()));
        toggleGroup = new ToggleGroup();
        inHouseRadio.setToggleGroup(toggleGroup);
        outsourcedRadio.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == inHouseRadio) {
                machineIdOrCompanyNameLabel.setText("Machine ID");
            } else {
                machineIdOrCompanyNameLabel.setText("Company Name");
            }
        });

        toggleGroup.selectToggle(inHouseRadio);
    }

    /**
     * Handles the save button click event for the Add Part form.
     * This method creates a new InHouse or Outsourced part based on what the user selected and adds it
     * to the inventory. It also performs input validation, such as checking if the minimum value is less than the maximum value,
     * if the provided input is in the correct format, if the min/max stock is negative, and if the price is negative.
     * After successfully adding the new part, it updates the tables in the MainFormController.
     *
     * Logical Error:  for when user adds a new part with negative stock, the runtime error as of right now would be our validation for stock being less than min,
     * but in order to make it a runtime validation, you would have to have a negative minimum validation as well and improve that too.
     * Future Improvements: Add a boolean for weather or not the user inputted negative stock and negative price.
     * Perform check at instance save is pressed and throw error if it occurs.
     *
     * Runtime error: user inputs no stock they retrieve a java.lang.numberformatexception as stock is stored as an int but we passed an empty string.
     * Improvement: add a validation to ensure that int stock = Integer.parseInt(partStockField.getText()); is parsing an int and not any characters apart of the string object.
     * Use a
     * try {
     * int stock = Integer.parseInt(input);
     * } catch(NumberFormatException e){
     *                  Alert alert = new Alert(Alert.AlertType.ERROR);
     *                 alert.setTitle("Invalid Input");
     *                 alert.setHeaderText("stock cannot be string");
     *                 alert.setContentText("Please correct the stock value.");
     *                 alert.showAndWait();
     *                 return;
     * }
     *
     * @param saveButton the ActionEvent object representing the save button
     *
     */
    @FXML
    public void handleSave(ActionEvent saveButton) {
        try {
            int id = Integer.parseInt(partIdField.getText());
            String name = partNameField.getText();
            double price = Double.parseDouble(partPriceField.getText());
            int stock = Integer.parseInt(partStockField.getText());
            int min = Integer.parseInt(partMinField.getText());
            int max = Integer.parseInt(partMaxField.getText());

            if (price < 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Price cannot be negative");
                alert.setContentText("Please correct the Price value.");
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

            if (stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Stock cannot be more than maximum storage for the part");
                alert.setContentText("Please correct the stock value.");
                alert.showAndWait();
                return;
            }
            if (name==null||name.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Name cannot be null");
                alert.setContentText("Please correct the name value.");
                alert.showAndWait();
                return;
            }


            Part newPart;

            if (inHouseRadio.isSelected()) {
                int machineId = Integer.parseInt(machineIdOrCompanyNameField.getText());

                if (machineId < 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText("Machine ID cannot be negative");
                    alert.setContentText("Please correct the Machine ID value.");
                    alert.showAndWait();
                    return;
                }

                newPart = new InHouse(id, name, price, stock, min, max, machineId);
                machineIdOrCompanyNameLabel.setText("Machine ID");
            } else {
                String companyName = machineIdOrCompanyNameField.getText();
                newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                machineIdOrCompanyNameLabel.setText("Company Name");
            }

            inventory.addPart(newPart);
            mainFormController.updateTables();
            handleClose(saveButton);

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid input for one or more fields");
            alert.setContentText("Please ensure all fields have valid input.");
            alert.showAndWait();
        }

    }

    /**
     * Handles the cancel button action by closing the AddPartForm window
     * @param cancelButton the ActionEvent object for the cancel button
     */
    @FXML
    public void handleCancel(ActionEvent cancelButton) {
        handleClose(cancelButton);
    }

    /**
     * Closes the Add Part window when the close button is pressed
     * @param closeButton the ActionEvent object for the close button
     */
    @FXML
    public void handleClose(ActionEvent closeButton) {
        Stage stage = (Stage) partIdField.getScene().getWindow();
        stage.close();
    }


}