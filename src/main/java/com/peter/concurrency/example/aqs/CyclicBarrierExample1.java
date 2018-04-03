package com.peter.concurrency.example.aqs;

import java.util.Random;
import java.util.concurrent.*;

public class CyclicBarrierExample1 {

    private static CyclicBarrier barrier = new CyclicBarrier(5);

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {

            final int threadNum = i;
            exec.submit(() -> {
                try {
                    race(threadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            });
        }

        exec.shutdown();

    }


    private static void race(int i) throws InterruptedException, BrokenBarrierException, TimeoutException {
        Thread.sleep(new Random().nextInt(100));
        System.out.println(String.format("[%s] is ready", i));
        try {
            barrier.await(10, TimeUnit.MILLISECONDS);
        } catch (BrokenBarrierException b) {
            System.out.println("Got Broken!!!");
        } catch (TimeoutException t) {
            System.out.println("Time out!!!");
        }
        System.out.println(String.format("[%s] doing next", i));

    }
}
