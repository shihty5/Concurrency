package com.peter.concurrency.example;

import com.peter.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ThreadSafe
public class AtomicExample4 {

    //请求总数
    public static int clientTotal = 5000;
    //同时并发数
    public static int threadTotal = 200;
    public static AtomicInteger count = new AtomicInteger(0);
    private static AtomicBoolean isHappened = new AtomicBoolean(false);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            pool.execute(() -> {
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                } catch (Exception e) {
                    System.out.println(e);
                }
                countDownLatch.countDown();

            });
        }
        countDownLatch.await();
        pool.shutdown();
        System.out.println("count: " + count);
        System.out.println("isHappened :" + isHappened.get());
    }

    private static void add() {
        count.incrementAndGet();
    }

    private static void test() {
        if (isHappened.compareAndSet(false, true)) {
            System.out.println("SWAPPED!");
        }
    }
}
