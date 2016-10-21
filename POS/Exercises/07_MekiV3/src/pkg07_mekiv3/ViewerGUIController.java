package pkg07_mekiv3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ViewerGUIController {

    @FXML
    private Rectangle recPlaceOrder;

    @FXML
    private Button btnStart;

    @FXML
    private Rectangle recReceiveFood;

    @FXML
    private VBox boxCars;

    @FXML
    private Rectangle recPayOrder;

    @FXML
    void btnStartClicked(ActionEvent event) {
        startSimulation();
    }

    private void startSimulation() {
        Database.getInstance().watch.start();
        for(int x = 0; x < Database.getInstance().vecCustomer.size(); x++) {
            ImageView image = new ImageView();
            Customer c = (Customer)Database.getInstance().vecCustomer.elementAt(x);
            CustomerType type = c.getCustomerType();
            switch(type) {
                case CarDriver:
                    image.setImage(new Image(getClass().getResourceAsStream("carSmall.jpg")));
                    break;
                case Walker:
                    image.setImage(new Image(getClass().getResourceAsStream("man.png")));
                    break;
                case FireTruck:
                    image.setImage(new Image(getClass().getResourceAsStream("firebrigade_1.png")));
                    break;
            }
            image.setFitHeight(25);
            image.setFitWidth(50);
            
            boxCars.getChildren().add(image);
            image.xProperty().bind(c.getLp());
            Thread t = new Thread(((Customer)Database.getInstance().vecCustomer.elementAt(x)));
            t.setDaemon(true);
            t.start();
        }
    }
}
