package com.peter.designpattern.proxy.cglib;

public class CglibExample {
    public static void main(String[] args) {
        CGLibProxy proxy = new CGLibProxy();

        Train train = (Train) proxy.getProxy(Train.class);

        train.move();
    }
}
