package com.acme;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {
        System.out.println(fromStringConstructor(String.class, "Hello World"));
    }

    private static <T> T fromStringConstructor(Class<T> type, String str) {
        try {
            Constructor<T> declaredConstructor = type.getDeclaredConstructor(String.class);
            return declaredConstructor.newInstance(str);
        } catch (NoSuchMethodException
                 | InstantiationException
                 | InvocationTargetException
                 | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
