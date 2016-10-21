/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg07_mekiv3;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Manuel Sammer
 */
class Watch extends Thread {

    private long speedFactor = 1;
    private boolean isFinished = false;
    private long totalTime = 0;
    private long timeDistance;

    public Watch(boolean isSpeed) {
        if (isSpeed) {
            speedFactor = 10;
        }
    }

    @Override
    public void run() {
        try {
            while (!isFinished) {
                sleep(10000 / speedFactor);
                totalTime = totalTime + 10;
                System.out.println("......................................................................" + totalTime);
            }
            if (isFinished) {
                System.out.println("......................................................................" + "\033[0;1m" + "watch stopped");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Watch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setStop() {
        isFinished = true;
    }
}
