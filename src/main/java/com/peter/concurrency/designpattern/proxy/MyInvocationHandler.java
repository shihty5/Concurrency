package com.peter.concurrency.designpattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {
    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * @param proxy  被代理对象
     * @param method 被代理的方法
     * @param args   方法的参数
     * @return Object 方法的返回值
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态代理工作 Before");
        method.invoke(this.target, args);
        System.out.println("动态代理工作 After");

        return null;
    }
}
