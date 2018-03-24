package com.peter.concurrency.badexample;

import com.peter.concurrency.annotations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@NotThreadSafe
public class ConcurrencyTest {

    //请求总数
    public static int clientTotal = 5000;

    //同时并发数
    public static int threadTotal = 200;

    public volatile static int count = 0;

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

    private synchronized static void add() {
        count++;
    }
}
