/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg04_mekiv2;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Manuel Sammer
 */
public class CarDriver extends Thread {

    private String nameOfDriver;
    private Semaphore semaOrderFree;
    private Semaphore semaCashDispenserFree;
    private long speedFactor = 1;
    private long totalWaitingTime;
    private boolean firstCar;
    private int timeCars;

    public CarDriver(String name, Semaphore semaOrderFree, Semaphore semaCashDispenserFree, boolean isSpeed, int timeCars, boolean firstCar) {
        nameOfDriver = name;
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
    public void run() {
        try {
            if (!firstCar) {
                sleep((timeCars / speedFactor) * 1000);
            }
            //Customer gets hungry happens every 1-5 Min
            //sleep(ThreadLocalRandom.current().nextInt(1, 5 + 1) * 1000 * 60 / speedFactor);
            // ASK CLASSMATES !!!!!
            //
            //
            //
            //
            //

            System.out.println(toString() + "hungry ==> arriving to Meki's DrveIn");

            //Customer waits for free lane
            System.out.println(toString() + "waiting for free lane");
            semaOrderFree.acquire();
            System.out.println(toString() + "driving on free lane");

            //Customer places order takes 2-3 Min
            System.out.println(toString() + "starts placing order");
            sleep(ThreadLocalRandom.current().nextInt(2, 3 + 1) * 1000 * 60 / speedFactor);
            System.out.println(toString() + "order placed");

            //Customer waits for free lane
            System.out.println(toString() + "waiting for cash dispenser");
            semaCashDispenserFree.acquire();

            //Customer pays order takes 1-2 Min
            System.out.println(toString() + "starts paying");
            sleep(ThreadLocalRandom.current().nextInt(1, 2 + 1) * 1000 * 60 / speedFactor);
            System.out.println(toString() + "paying finished");
            semaCashDispenserFree.release();

            //Customer waits for finished order takes 2-3 Min
            System.out.println(toString() + "waiting for finished order");
            sleep(ThreadLocalRandom.current().nextInt(2, 3 + 1) * 1000 * 60 / speedFactor);
            System.out.println(toString() + "food received");
            semaOrderFree.release();
            System.out.println("\033[0;1m" + toString() + "leaving Mekis Drive In; total waiting time==>" + totalWaitingTime);
            
            //TOTAL WAITING TIME ?

        } catch (InterruptedException ex) {
            System.out.println("\033[0;1m" + toString() + "Car Driver was gently put down!");
        }
    }

    @Override
    public String toString() {
        return nameOfDriver + ":";
    }

}
