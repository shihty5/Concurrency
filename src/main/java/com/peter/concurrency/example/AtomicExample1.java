package com.peter.concurrency.example;

import com.peter.concurrency.annotations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ThreadSafe
public class AtomicExample1 {

    //请求总数
    public static int clientTotal = 5000;

    //同时并发数
    public static int threadTotal = 200;

    public static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            pool.execute(() -> {
                try {
                    semaphore.acquire();
                } catch (Exception e) {
                    System.out.println(e);
                }
                countDownLatch.countDown();
                add();
                semaphore.release();
            });
        }
        countDownLatch.await();
        pool.shutdown();
        System.out.println("count: " + count);
    }

    private static void add() {
        count.incrementAndGet();
    }
}
