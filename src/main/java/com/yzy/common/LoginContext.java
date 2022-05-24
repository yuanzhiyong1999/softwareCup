package com.yzy.common;


import com.yzy.entity.User;

public class LoginContext {
    private final static ThreadLocal<User> requestManagerInfo = new ThreadLocal<>();

    public static void add(User manager) {
        requestManagerInfo.set(manager);
    }

    public static User getUser() {
        return requestManagerInfo.get();
    }

    public static void remove() {
        requestManagerInfo.remove();
    }
}
