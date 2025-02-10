package com.weatherapp.dto.weather.embeded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Main {
    public double temp;
    public double feels_like;
    public double temp_min;
    public double temp_max;
    public int pressure;
    public int humidity;
    public int sea_level;
    public int grnd_level;
}
