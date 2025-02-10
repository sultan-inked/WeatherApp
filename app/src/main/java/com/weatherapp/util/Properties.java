package com.weatherapp.util;

public class Properties {
    public static final String WEATHER_API_KEY = "ce5250f440f47fdbd0936be22ee677d4";
    public static final int LIMIT = 1;
    public static final String UNITS = "metric";
    public static final String LANG = "ru";
    public static final String CITY_COORD_URI_TEMPLATE = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=%s&appid=%s";
    public static final String WEATHER_BI_COORDINATED_URI_TEMPLATE = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=%s&lang=%s";
}
