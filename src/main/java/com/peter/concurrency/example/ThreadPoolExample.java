package com.peter.concurrency.example;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExample {
    public static void main(String[] args) {

        ScheduledExecutorService exec = Executors.newScheduledThreadPool(5);

        exec.schedule(new Runnable() {
            @Override
            public void run() {
                //System.out.println("Scheduled task...");
            }
        }, 3, TimeUnit.SECONDS);

        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //System.out.println("Hello!!");
            }
        }, 2, 1, TimeUnit.SECONDS);

        //exec.shutdown();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Hello TimerTask!");
            }
        }, new Date(), 5 * 1000);
    }
}
