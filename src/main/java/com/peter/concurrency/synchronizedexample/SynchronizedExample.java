package com.peter.concurrency.synchronizedexample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizedExample {

    public static void main(String[] args) {
        SynchronizedExample example1 = new SynchronizedExample();
        SynchronizedExample example2 = new SynchronizedExample();
        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(() -> {
            example1.print3("A");
        });

        executorService.execute(() -> {
            example2.print3("B");
        });

    }

    public static void print1() {
        synchronized (SynchronizedExample.class) {
            for (int i = 0; i < 10; i++) {
                System.out.println(String.format("Test 1 - {%s}", i));
            }
        }
    }

    public synchronized static void print2() {
        for (int i = 0; i < 10; i++) {
            System.out.println(String.format("Test 2 - {%s}", i));
        }
    }

    public void print3(String j) {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                System.out.println(String.format("Test 3 - [%s] - {%s}", j, i));
            }
        }
    }

    public synchronized void print4() {
        for (int i = 0; i < 10; i++) {
            System.out.println(String.format("Test 4 - {%s}", i));
        }
    }
}
