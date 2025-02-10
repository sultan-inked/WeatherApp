package com.weatherapp.dto.weather.embeded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sys {
    public int type;
    public int id;
    public String country;
    public int sunrise;
    public int sunset;
}
