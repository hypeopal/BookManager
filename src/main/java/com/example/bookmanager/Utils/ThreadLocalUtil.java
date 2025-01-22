package com.example.bookmanager.Utils;

public class ThreadLocalUtil {
    private static final ThreadLocal<UserClaims> THREAD_LOCAL = new ThreadLocal<>();

    public static UserClaims get() {
        return THREAD_LOCAL.get();
    }

    public static void set(UserClaims claims) {
        THREAD_LOCAL.set(claims);
    }

    public static void remove() {
        THREAD_LOCAL.remove();
    }
}
