package com.peter.concurrency.example.aqs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreExample2 {
    private static int threadCount = 20;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(() -> {
                try {
                    if (semaphore.tryAcquire()) {
                        semaphore.acquire();
                        test(threadNum);
                        semaphore.release();
                    } else {
                        System.out.println("没抢到" + threadNum);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        System.out.println("finish");
        exec.shutdown();
    }

    private static void test(int threadNum) throws InterruptedException {
        System.out.println(String.format("[%s]", threadNum));
        Thread.sleep(2000);

    }
}
