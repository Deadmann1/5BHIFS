/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg03_concurrency;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
/**
 * FXML Controller class
 *
 * @author org
 */
public class MainGUIController implements Initializable {
    @FXML
    private Button btnStart;

    @FXML
    private Button btnShow;

   @FXML
    private Button btnStop;

    @FXML
    private TextField txtMessage;

    @FXML
    private TextField txtMessage2;
    
    @FXML
    private ImageView imgCar;
    
    Worker worker = null;

    @FXML
    void onButtonClick(ActionEvent event) {
        try{
            if (event.getSource() == btnStart) {
                worker = new Worker();
                txtMessage2.textProperty().bind(worker.getSp());
                imgCar.xProperty().bind(worker.getIp());
                new Thread(worker).start();
                txtMessage.setText("worker started");
            } else
            if (event.getSource() == btnStop) {
                worker.cancel();
                txtMessage.setText("worker cancelled");
            } else
            if (event.getSource() == btnShow) {
                txtMessage.setText("   " + worker.getValue());
            }
        } catch(Exception ex) {
            txtMessage.setText("error:" + ex.getMessage());
        }

    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    /**
     * other non GUI attributes
     */
    
}
