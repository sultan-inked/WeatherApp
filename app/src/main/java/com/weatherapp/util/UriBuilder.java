package com.weatherapp.util;

public class UriBuilder {
    public static String buildUri(String template, Object... args) {
        return String.format(template, args);
    }
}
