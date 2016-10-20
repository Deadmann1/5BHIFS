/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg04_mekiv2;

import java.io.IOException;
import static java.lang.System.console;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MainGUIController implements Initializable {

    private final static int MAX_ORDER = 2;
    private final static int MAX_PAYMENT = 1;
    private Vector<Thread> vecCarDriver;
    private long speedFactor = 1;
    private Semaphore semaOrderFree;
    private Semaphore semaCashDispenserFree;
    private Watch watch;
    private boolean isRunning = false;
    private int nrCars;
    private int timeCars;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnViewer;

    @FXML
    private Button btnStop;

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
        if (!isRunning) {
            if (this.txtNrCars != null && this.txtTimeCars != null && txtNrCars.getText().matches("[0-9]+") && txtTimeCars.getText().matches("[0-9]+")
                    && Integer.parseInt(txtTimeCars.getText()) > 0 && Integer.parseInt(txtNrCars.getText()) > 0) {
                nrCars = Integer.parseInt(txtNrCars.getText());
                timeCars = Integer.parseInt(txtTimeCars.getText());
                generateSemaphores();
                startSimulation();
            } else {
                lblMessage.setText("Error: Please enter valid Numbers!");
            }
        } else {
            lblMessage.setText("Error: Simulation is already running!");
        }
    }

    @FXML
    void btnStopClicked(ActionEvent event) {
        if (isRunning) {
            endSimulation();
        } else {
            lblMessage.setText("Error: There is no Simulation running!");
        }
    }

    @FXML
    void btnViewerClicked(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void generateSemaphores() {
        semaOrderFree = new Semaphore(MAX_ORDER);
        semaCashDispenserFree = new Semaphore(MAX_PAYMENT);
    }

    private void startSimulation() {
        lblMessage.setText("Simulation Started");
        boolean isSpeed = this.chkbxSpeedSimulation.isSelected();
        isRunning = true;
        boolean firstCar = true;
        watch = new Watch(isSpeed);
        watch.setDaemon(true);
        vecCarDriver = new Vector<Thread>();

        watch.start();
        for (int x = 0; x < nrCars; x++) {
            CarDriver driver = new CarDriver("driver " + (x + 1), this.semaOrderFree, this.semaCashDispenserFree, isSpeed, timeCars * x, firstCar);
            driver.setDaemon(true);
            vecCarDriver.add(driver);
            driver.start();
            firstCar = false;
        }
    }

    private void endSimulation() {
        vecCarDriver.forEach(obj -> {
            obj.interrupt();
        });
        vecCarDriver.clear();
        watch.setStop();
        isRunning = false;
        semaOrderFree = null;
        semaCashDispenserFree = null;
        lblMessage.setText("Simulation Stopped");
    }

}
