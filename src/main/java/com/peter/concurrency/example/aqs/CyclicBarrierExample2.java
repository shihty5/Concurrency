package com.peter.concurrency.example.aqs;

import java.util.Random;
import java.util.concurrent.*;

public class CyclicBarrierExample2 {

    private static CyclicBarrier barrier = new CyclicBarrier(5, () -> {
        System.out.println(
                "凑齐了！一起干活！！"
        );
    });

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
        barrier.await();
        System.out.println(String.format("[%s] doing next", i));

    }
}
