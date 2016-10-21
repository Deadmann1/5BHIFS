/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07_mekiv3;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author schueler
 */
public class MainGUIController implements Initializable {

    private final static int MAX_ORDER = 2;
    private final static int MAX_PAYMENT = 1;
    private long speedFactor = 1;
    private Semaphore semaOrderFree;
    private Semaphore semaCashDispenserFree;
    private boolean isRunning = false;
    private int nrCars;
    private int timeCars;
    private int fireNr;
    private int walkerNr;

    @FXML
    private Button btnStart;

    @FXML
    private Button btnViewer;

    @FXML
    private ListView<String> listViewLog;
    
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
    private TextField txtFireTruck;
    @FXML
    private TextField txtWalker;
    
    private ListProperty<String> lp = new SimpleListProperty<>();

    @FXML
    void btnStartClicked(ActionEvent event) {
        if (!isRunning) {
            if (this.txtNrCars != null && this.txtTimeCars != null && txtNrCars.getText().matches("[0-9]+") && txtTimeCars.getText().matches("[0-9]+")
                    &&txtFireTruck != null && this.txtWalker != null && txtFireTruck.getText().matches("[0-9]+") && txtWalker.getText().matches("[0-9]+")
                    && Integer.parseInt(txtTimeCars.getText()) > 0 && Integer.parseInt(txtNrCars.getText()) > 0 
                    && Integer.parseInt(txtFireTruck.getText()) > 0 && Integer.parseInt(txtFireTruck.getText()) < 100
                    && Integer.parseInt(txtWalker.getText()) > 0 && Integer.parseInt(txtWalker.getText()) < 100
                    && (Integer.parseInt(txtFireTruck.getText()) + Integer.parseInt(txtWalker.getText()))  < 100
                    ) {
                nrCars = Integer.parseInt(txtNrCars.getText());
                timeCars = Integer.parseInt(txtTimeCars.getText());
                fireNr = Integer.parseInt(txtFireTruck.getText());
                walkerNr = Integer.parseInt(txtWalker.getText());
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    private void generateSemaphores() {
        semaOrderFree = new Semaphore(MAX_ORDER);
        semaCashDispenserFree = new Semaphore(MAX_PAYMENT);
    }

    private void startSimulation() {
        /*Database.getInstance().log = FXCollections.observableList(new ArrayList<String>());
        lp.set(Database.getInstance().log);
        listViewLog.itemsProperty().bind(lp);
        */
        CustomerType type;
        lblMessage.setText("Simulation Started");
        boolean isSpeed = this.chkbxSpeedSimulation.isSelected();
        isRunning = true;
        boolean firstCar = true;
        Database.getInstance().watch = new Watch(isSpeed);
        Database.getInstance().watch.setDaemon(true);
        Database.getInstance().vecCustomer = new Vector<Task>();
        Customer driver;
        for (int x = 0; x < nrCars; x++) {
            int random = ThreadLocalRandom.current().nextInt(0, 100 + 1);
            if((100 - (fireNr+walkerNr)) >= random) {
                driver = new Customer(CustomerType.CarDriver, "driver " + (x + 1), this.semaOrderFree, this.semaCashDispenserFree, isSpeed, timeCars * x, firstCar);
            }
            else if ((100 - (fireNr + walkerNr)) < random ) {
                driver = new Customer(CustomerType.FireTruck, "fireTruck " + (x + 1), this.semaOrderFree, this.semaCashDispenserFree, isSpeed, timeCars * x, firstCar);
            }
            else {
                driver = new Customer(CustomerType.Walker, "walker " + (x + 1), this.semaOrderFree, this.semaCashDispenserFree, isSpeed, timeCars * x, firstCar);
            }
            Database.getInstance().vecCustomer.add(driver);
            firstCar=false;
        }
         Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("ViewerGUI.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
            stage.setTitle("My Meki Viewer");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainGUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void endSimulation() {
        Database.getInstance().vecCustomer.forEach(obj -> {
            obj.cancel();
        });
        Database.getInstance().vecCustomer.clear();
        Database.getInstance().watch.setStop();
        isRunning = false;
        semaOrderFree = null;
        semaCashDispenserFree = null;
        lblMessage.setText("Simulation Stopped");
    }

}
