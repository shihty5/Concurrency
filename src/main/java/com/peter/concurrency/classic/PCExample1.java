package com.peter.concurrency.classic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class PCExample1 {

    public static void main(String[] args) throws InterruptedException {
        Queue<Integer> buffer = new LinkedList<>();
        int maxSize = 10;

        WaitConsumer waitConsumer = new WaitConsumer(buffer);
        WaitProducer waitProducer = new WaitProducer(buffer, maxSize);

        waitProducer.start();
        waitConsumer.start();

    }
}

class WaitConsumer extends Thread {

    private Queue<Integer> buffer;

    public WaitConsumer(Queue<Integer> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (buffer) {
                if (buffer.isEmpty()) {

                    System.out.println("东西不够啦，WaitConsumer is waiting..");
                    try {
                        buffer.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("WaitConsumer gets [%s]. ", buffer.poll()));
                buffer.notifyAll();
            }
        }
    }
}

class WaitProducer extends Thread {

    private Queue<Integer> buffer;
    private int maxSize;

    public WaitProducer(Queue<Integer> buffer, int maxSize) {
        this.buffer = buffer;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (buffer) {
                if (buffer.size() == maxSize) {
                    try {
                        System.out.println("装满了，WaitProducer 先wait");
                        buffer.wait();
                    } catch (InterruptedException e) {
                        System.out.println("WaitProducer RuntimeException: " + e);
                    }
                }

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int product = new Random().nextInt(10);
                buffer.add(product);
                System.out.println(String.format("WaitProducer sets [%s]. ", product));
                buffer.notifyAll();
            }
        }
    }
}
