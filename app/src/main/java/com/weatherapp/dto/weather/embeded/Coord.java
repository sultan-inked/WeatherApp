package com.weatherapp.dto.weather.embeded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coord {
    public double lon;
    public double lat;
}
