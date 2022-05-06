package com.company;

import java.util.concurrent.TimeUnit;

public class Generator implements Runnable {
    private int floor;

    @Override
    public void run() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(10);
                this.floor = (int) (Math.random() * 9);
                Manager.floorqueue.offer(floor);
                System.out.println("Call on " + floor + " floor");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
