package Project.Controller;

import Project.Model.InHouse;
import Project.Model.Inventory;
import Project.Model.Outsourced;
import Project.Model.Part;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * ModifyPartFormController is the controller class responsible for handling the Modify Part Form.
 * @author Diar Shabani
 */
public class ModifyPartFormController {

    private Inventory inventory;
    private Part part;


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
    private ToggleGroup partTypeToggleGroup;
    @FXML
    private Label machineIdOrCompanyNameLabel;
    @FXML
    private TextField machineIdOrCompanyNameField;
    private MainFormController mainFormController;

    /**
     * Constructor for the ModifyPartFormController class.
     *
     * @param inventory  The inventory instance to manage parts.
     * @param part  The part to modify.
     * @param mainFormController  The main form controller instance to update tables.
     */
    public ModifyPartFormController(Inventory inventory, Part part, MainFormController mainFormController) {
        this.inventory = inventory;
        this.part = part;
        this.mainFormController = mainFormController;
    }

    /**
     * Initializes the Modify Part Form with the current part data.
     * Sets up the radio buttons for part type, populates the form fields with
     * the part's ID, name, price, stock, minimum, and maximum values, and
     * sets the appropriate field for InHouse or Outsourced.
     */
    @FXML
    private void initialize() {
        partIdField.setDisable(true);
        partIdField.setText(String.valueOf(this.inventory.returnNextPartID()));
        partTypeToggleGroup = new ToggleGroup();
        inHouseRadio.setToggleGroup(partTypeToggleGroup);
        outsourcedRadio.setToggleGroup(partTypeToggleGroup);

        partTypeToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == inHouseRadio) {
                machineIdOrCompanyNameLabel.setText("Machine ID");
            } else {
                machineIdOrCompanyNameLabel.setText("Company Name");
            }
        });

        partIdField.setText(Integer.toString(part.getId()));
        partNameField.setText(part.getName());
        partPriceField.setText(Double.toString(part.getPrice()));
        partStockField.setText(Integer.toString(part.getStock()));
        partMinField.setText(Integer.toString(part.getMin()));
        partMaxField.setText(Integer.toString(part.getMax()));

        if (part instanceof InHouse) {
            inHouseRadio.setSelected(true);
            machineIdOrCompanyNameField.setText(Integer.toString(((InHouse) part).getMachineId()));
        } else {
            outsourcedRadio.setSelected(true);
            machineIdOrCompanyNameField.setText(((Outsourced) part).getCompanyName());
        }
    }

    /**
     * Handles the save action on the Modify Part Form.
     * Retrieves the updated part information from the form fields,
     * creates a new Part instance (InHouse or Outsourced) with the updated data,
     * updates the part in the inventory, refreshes the main form's tables,
     * and closes the Modify Part Form.
     */
    @FXML
    public void handleSave() {
        int id = Integer.parseInt(partIdField.getText());
        String name = partNameField.getText();
        Double price;
        Integer stock,min, max;

        try {
            price = Double.parseDouble(partPriceField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Price must be a numeric value");
            alert.setContentText("Please correct the price input value.");
            alert.showAndWait();
            return;
        }

        try {
            stock = Integer.parseInt(partStockField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Stock must be a numeric value");
            alert.setContentText("Please correct the stock input value.");
            alert.showAndWait();
            return;
        }

        try {
            min = Integer.parseInt(partMinField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Min must be a numeric value");
            alert.setContentText("Please correct the min input value.");
            alert.showAndWait();
            return;
        }

        try {
            max = Integer.parseInt(partMaxField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Max must be a numeric value");
            alert.setContentText("Please correct the max input value.");
            alert.showAndWait();
            return;
        }

        Part updatedPart;
        if (inHouseRadio.isSelected()) {
            Integer machineId;
            try {
                machineId = Integer.parseInt(machineIdOrCompanyNameField.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("machineId must be a numeric value");
                alert.setContentText("Please correct the Machine Id input value.");
                alert.showAndWait();
                return;
            }
            updatedPart = new InHouse(this.part.getId(), name, price, stock, min, max, machineId);
            updatedPart.setId(id);
        } else {
            if (machineIdOrCompanyNameField==null||machineIdOrCompanyNameField.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Company Name cannot be null");
                alert.setContentText("Please correct the Company Name value.");
                alert.showAndWait();
                return;
            }
            String companyName = machineIdOrCompanyNameField.getText();
            updatedPart = new Outsourced(this.part.getId(),name, price, stock, min, max, companyName);
            updatedPart.setId(id);
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

        if (stock < min) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Stock cannot be less than minimum storage for the product");
            alert.setContentText("Please correct the stock value.");
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

        int index = inventory.getAllParts().indexOf(part);
        inventory.updatePart(index, updatedPart);
        mainFormController.updateTables();
        closeWindow();


    }

    /**
     * Handles the cancel action on the Modify Part Form.
     * Closes the form without saving changes.
     */
    @FXML
    public void handleCancel() {
        closeWindow();
    }

    /**
     * Closes the Modify Part Form window.
     */
    private void closeWindow() {
        Stage stage = (Stage) partIdField.getScene().getWindow();
        stage.close();
    }
}