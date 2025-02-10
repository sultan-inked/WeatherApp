package com.weatherapp.dto.weather.embeded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
    public int id;
    public String main;
    public String description;
    public String icon;
}
