package com.peter.concurrency.classic;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class PCExample2 {


    public static void main(String[] args) {
        final AtomicInteger increTask = new AtomicInteger(0);
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(10);

        BlockingConsumer consumer = new BlockingConsumer(blockingQueue);
        BlockingProducer producer = new BlockingProducer(blockingQueue, increTask);

        for (int i = 0; i < 2; i++) {
            new Thread(consumer).start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(producer).start();
        }
    }

    static class BlockingConsumer extends Thread {
        private BlockingQueue<Integer> blockingQueue;

        public BlockingConsumer(BlockingQueue<Integer> blockingQueue) {
            this.blockingQueue = blockingQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep((long) (500 + Math.random() * 100));
                    Integer get = blockingQueue.take();
                    System.out.println("服务器处理 :" + get);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class BlockingProducer extends Thread {
        private BlockingQueue<Integer> blockingQueue;
        private AtomicInteger increaTask;

        public BlockingProducer(BlockingQueue<Integer> blockingQueue, AtomicInteger increaTask) {
            this.blockingQueue = blockingQueue;
            this.increaTask = increaTask;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep((long) (Math.random() * 1000));
                    Integer nextTask = increaTask.getAndIncrement();
                    blockingQueue.put(nextTask);
                    System.out.println("用户请求 : " + nextTask);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

