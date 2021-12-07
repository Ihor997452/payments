package com.my.util;

public class StackTrace {
    private StackTrace() {
    }

    public static String getStackTrace(Exception e) {
        StringBuilder stringBuilder = new StringBuilder();

        for (StackTraceElement element : e.getStackTrace()) {
            stringBuilder.append(element.toString()).append(System.lineSeparator());
        }

        return stringBuilder.toString();
    }
}

