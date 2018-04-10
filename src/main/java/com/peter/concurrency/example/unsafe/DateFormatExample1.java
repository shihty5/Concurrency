package com.peter.concurrency.example.unsafe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class DateFormatExample1 {
    //请求总数
    public static int clientTotal = 500;
    //同时并发数
    public static int threadTotal = 200;
    public volatile static int count = 0;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);

        for (int i = 0; i < clientTotal; i++) {
            pool.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
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
    }

    private static void add() {
        try {
            sdf.parse("20180405");
        } catch (ParseException e) {
            System.out.println("Error occurs +" + e.getLocalizedMessage());
        }
    }

}
