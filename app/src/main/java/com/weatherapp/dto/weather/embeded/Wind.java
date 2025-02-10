package com.weatherapp.dto.weather.embeded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wind {
    public double speed;
    public int deg;
    public double gust;
}
