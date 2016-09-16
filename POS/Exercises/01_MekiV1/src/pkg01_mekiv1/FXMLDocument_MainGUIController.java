/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg01_mekiv1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author schueler
 */
public class FXMLDocument_MainGUIController implements Initializable {
    
    @FXML
    private Button btnStart;

    @FXML
    private CheckBox chkbxSpeedSimulation;

    @FXML
    private Label lblMessage;

    @FXML
    private TextField txtTimeCars;

    @FXML
    private TextField txtNrCars;

    @FXML
    void btnStartClicked(ActionEvent event) {

    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
