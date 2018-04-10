package com.peter.concurrency.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyExample {

    public static void main(String[] args) {

        UserMgr userMgr = new UserMgrImpl();
        InvocationHandler h = new MyInvocationHandler(userMgr);

        UserMgr proxy = (UserMgr) Proxy.newProxyInstance(userMgr.getClass().getClassLoader(), userMgr.getClass().getInterfaces(), h);

        proxy.addUser();
        proxy.delUser();
    }
}
