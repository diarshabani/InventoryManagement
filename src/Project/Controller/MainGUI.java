package Project.Controller;

import Project.Model.InHouse;
import Project.Model.Inventory;
import Project.Model.Outsourced;
import Project.Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * MainGUI launches the Inventory Management application.
 * @author Diar Shabani
 */
public class MainGUI extends Application {

    /**
     * Starts the Inventory Management application for Software 1 C482
     * Initializes the FXMLLoader, sets the controller factory, and sets the scene
     * @param primaryStage The primary stage
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Project/View/Landing.fxml"));
        loader.setControllerFactory(c -> new MainFormController(getHomeGoodsInventory()));
        Parent root = loader.load();

        MainFormController controller = loader.getController();

        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * The main method launches the application.
     * @param args, launches application with arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Creates and returns a sample home goods inventory with parts and products
     * @return An Inventory instance with testing data
     */
    public static Inventory getHomeGoodsInventory() {
        Inventory inventory = new Inventory();

        inventory.addPart(new InHouse(1, "Sofa Cushion", 19.99, 10, 1, 100, 101));
        inventory.addPart(new InHouse(2, "Bath Towel", 9.99, 25, 1, 100, 102));
        inventory.addPart(new Outsourced(3, "Kitchen Mat", 14.99, 15, 1, 100, "HomeGoods Inc."));
        inventory.addPart(new Outsourced(4, "Pillow Cover", 7.99, 20, 1, 100, "HomeGoods Inc."));

        Product product1 = new Product(1, "Living Room Set", 499.99, 5, 1, 10);
        product1.addAssociatedPart(inventory.lookupPart(1));
        product1.addAssociatedPart(inventory.lookupPart(4));
        inventory.addProduct(product1);

        Product product2 = new Product(2, "Bathroom Set", 99.99, 10, 1, 20);
        product2.addAssociatedPart(inventory.lookupPart(2));
        inventory.addProduct(product2);

        Product product3 = new Product(3, "Kitchen Set", 199.99, 8, 1, 15);
        product3.addAssociatedPart(inventory.lookupPart(3));
        product3.addAssociatedPart(inventory.lookupPart(4));
        inventory.addProduct(product3);

        return inventory;
    }
}