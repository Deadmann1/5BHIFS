/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg04_mekiv2;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

/**
 *
 * @author org
 */
public class Worker extends Task<String>{
    public int MAX = 1000000;
    private StringProperty sp = new SimpleStringProperty();
    private IntegerProperty ip = new SimpleIntegerProperty();
    
    @Override public String call() {
        for (int i=1; i<=MAX && !isCancelled(); i+= 1) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException ex) {
               
            }
            updateValue("--" + i + "--");
            String strTemp = "==>"+ i + "<==";
            int intTemp = 15+i;
            Platform.runLater(()->setSP(strTemp));
            Platform.runLater(()->setIP(intTemp));
            /**
             * or alternative coding without lambda
             * Platform.runLater(new Runnable() {
             *  @Override
             *  public void run() {
             *      setSp(stringTmp)
             *  }
             * };
             * )};
             * 
             */
        }
        return "x-x-x-x-x-x";
    }

    public StringProperty getSp() {
        return sp;
    }

    private void setSP(String msg) {
        sp.set(msg);
    }

    public IntegerProperty getIp() {
        return ip;
    }

    private void setIP(int ip) {
        this.ip.setValue(ip);
    }
    
}
