package com.tterrag.advent2016;

import java.lang.reflect.Method;

public class Main {
    
    public static void main(String[] args) throws Exception {
        String day = args[0];
        
        Class<?> dayClass = Class.forName(Main.class.getCanonicalName().replaceAll("Main", "Day" + Integer.parseInt(day)));
        Method m = dayClass.getDeclaredMethod("main", String[].class);
        m.setAccessible(true);
        m.invoke(null, (Object) new String[0]);
    }
}
