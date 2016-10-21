/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07_mekiv3;

import java.util.HashSet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadLocalRandom;
import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.concurrent.Task;

/**
 *
 * @author Manuel Sammer
 */
public class Customer extends Task<String> {

    private String nameOfDriver;
    private Semaphore semaOrderFree;
    private Semaphore semaCashDispenserFree;
    private long speedFactor = 1;
    private long totalWaitingTime;
    private boolean firstCar;
    private int timeCars;
    private CustomerType type;
    private LongProperty lp = new SimpleLongProperty();

    public Customer(CustomerType type, String name, Semaphore semaOrderFree, Semaphore semaCashDispenserFree, boolean isSpeed, int timeCars, boolean firstCar) {
        nameOfDriver = name;
        this.type = type;
        this.semaOrderFree = semaOrderFree;
        this.semaCashDispenserFree = semaCashDispenserFree;
        if (isSpeed) {
            speedFactor = 10;
        }
        this.timeCars = timeCars;
        this.firstCar = firstCar;
    }

    public String getNameOfDriver() {
        return nameOfDriver;
    }

    @Override
    public String call() {
        try {
            //Database.getInstance().log.add("TEST TEST TEST");
            String strTemp;
            long i = 0;
            if (!firstCar) {
                Thread.sleep((timeCars / speedFactor) * 1000);
            }
            //Customer gets hungry happens every 1-5 Min
            //sleep(ThreadLocalRandom.current().nextInt(1, 5 + 1) * 1000 * 60 / speedFactor);
            totalWaitingTime = Database.getInstance().watch.getTotalTime();
            System.out.println(toString() + "hungry ==> arriving to Meki's DrveIn");

            //Customer waits for free lane
            if(type != CustomerType.Walker)
            {
            System.out.println(toString() + "waiting for free lane");
            semaOrderFree.acquire();
            updateValue("--" + i + "--");
            strTemp = "==>"+ i + "<==";
            Platform.runLater(()->setLP(110));
            //110
            System.out.println(toString() + "driving on free lane");
            }
            else {
            System.out.println(toString() + "I am of Walker I no wait");
            System.out.println(toString() + "driving on free lane");
            }

            //Customer places order takes 2-3 Min
            System.out.println(toString() + "starts placing order");
            Thread.sleep(ThreadLocalRandom.current().nextInt(2, 3 + 1) * 1000 * 60 / speedFactor);
            System.out.println(toString() + "order placed");
            
            //Customer waits for free dispenser
            if(type != CustomerType.FireTruck) {
            System.out.println(toString() + "waiting for cash dispenser");
            semaCashDispenserFree.acquire();
            updateValue("--" + i + "--");
            strTemp = "==>"+ i + "<==";
            Platform.runLater(()->setLP(240));
            //240
            
            
            //Customer pays order takes 1-2 Min
            System.out.println(toString() + "starts paying");
            Thread.sleep(ThreadLocalRandom.current().nextInt(1, 2 + 1) * 1000 * 60 / speedFactor);
            System.out.println(toString() + "paying finished");
            semaCashDispenserFree.release();
            }
            else {
                System.out.println("I am of Fireman i no pay for foods");
            }
            //Customer waits for finished order takes 2-3 Min
            System.out.println(toString() + "waiting for finished order");
            updateValue("--" + i + "--");
            strTemp = "==>"+ i + "<==";
            Platform.runLater(()->setLP(370));
            Thread.sleep(ThreadLocalRandom.current().nextInt(2, 3 + 1) * 1000 * 60 / speedFactor);
            System.out.println(toString() + "food received");
            semaOrderFree.release();
            System.out.println("\033[0;1m" + toString() + "leaving Mekis Drive In; total waiting time==>" + (Database.getInstance().watch.getTotalTime() - totalWaitingTime));
            
            //TOTAL WAITING TIME ?

        } catch (InterruptedException ex) {
            System.out.println("\033[0;1m" + toString() + "Car Driver was gently put down!");
        }
        return "x-x-x-x-x-x";
    }

     public LongProperty getLp() {
        return lp;
    }

    private void setLP(long lp) {
        this.lp.setValue(lp);
    }

    public CustomerType getCustomerType() {
        return type;
    }
    
    @Override
    public String toString() {
        return nameOfDriver + ":";
    }

}
