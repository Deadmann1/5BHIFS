package pkg04_mekiv2;

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
        for (int x = 0; x < 5; x++) {
            ImageView image = new ImageView();
            image.setImage(new Image(getClass().getResourceAsStream("carSmall.jpg")));
            boxCars.getChildren().add(image);
            Worker w = new Worker();
            w.MAX = (int) recPlaceOrder.xProperty().get() / 2;
            image.xProperty().bind(w.getIp());
            new Thread(w).start();
        }
    }
}
