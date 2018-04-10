package com.peter.concurrency.deadlock;

public class DeadLockExample implements Runnable {

    private static Object o1 = new Object();
    private static Object o2 = new Object();
    public int flag = 1;

    public DeadLockExample(int flag) {
        this.flag = flag;
    }

    public static void main(String[] args) {
        new Thread(new DeadLockExample(1)).start();
        new Thread(new DeadLockExample(2)).start();


    }

    @Override
    public void run() {
        System.out.println("Flag = " + flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    System.out.println("flag =1 and get o1");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("HHH");
                }
                synchronized (o2) {
                    System.out.println("get o2");
                }
            }
        }
        if (flag == 2) {
            synchronized (o2) {
                try {
                    System.out.println("flag =2 and get o2");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println("HHH");
                }
                synchronized (o1) {
                    System.out.println("get o1");
                }
            }
        }
    }
}
