/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg02_nürnbergring;

import java.awt.Rectangle;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;
import javafx.util.Duration;

/**
 *
 * @author schueler
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnStop;

    @FXML
    private Label txtMessage;

    @FXML
    private Rectangle imgCar;

    private FadeTransition ft;
    private PathTransition pathTransition;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        try{
            if(event.getSource() == btnStart) {
                doAnimationFading();
                doAnimationMoving();
                txtMessage.setText("Start of animation");
            }
            else if (event.getSource() == btnStop) {
                doAnimationStop();
                txtMessage.setText("Stop of animation");
            }
        }
        catch(Exception ex) {
            txtMessage.setText("error: " + ex.getMessage());
        }
    }
    
    private void doAnimationFading() throws Exception {
        ft = new FadeTransition(Duration.millis(3000), imgCar);
        ft.setFromValue(1.0);
        ft.setToValue(0.1);
        ft.setCycleCount(Timeline.INDEFINITE);
        ft.setAutoReverse(true);
        ft.play();
    }
    
    private void doAnimationMoving() throws Exception {
        Path path = new Path();
        path.getElements().add(new MoveTo(20d,20d));
        path.getElements().add(new HLineTo(20d+200d));
        path.getElements().add(new VLineTo(20d+200d));
        pathTransition = new PathTransition();
        pathTransition.setDuration((Duration.millis((3000))));
        pathTransition.setPath(path);
        pathTransition.setNode(imgCar);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(true);
        pathTransition.play();
    }
    
    private void doAnimationStop() {
        ft.stop();
        pathTransition.stop();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
