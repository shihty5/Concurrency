package com.peter.concurrency.designpattern.proxy;

public class UserMgrImpl implements UserMgr {
    @Override
    public void addUser() {
        System.out.println("add user");
    }

    @Override
    public void delUser() {
        System.out.println("delete user");
    }
}
