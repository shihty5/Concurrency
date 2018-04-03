package com.peter.concurrency.example.future;

import java.util.concurrent.*;

public class FutureExample {

    public static void main(String[] args) throws InterruptedException {

        ExecutorService exec = Executors.newCachedThreadPool();
        Future<String> future = exec.submit(new MyCallable());
        System.out.println("Main is doing sth..");
        Thread.sleep(1000);
        try {
            System.out.println(future.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            System.out.println("doing something..");
            Thread.sleep(3000);
            return "Done";
        }
    }
}
