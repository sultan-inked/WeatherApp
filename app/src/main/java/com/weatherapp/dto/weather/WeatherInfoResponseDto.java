package com.weatherapp.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.weatherapp.dto.weather.embeded.*;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInfoResponseDto {
    public Coord coord;
    public ArrayList<Weather> weather;
    public String base;
    public Main main;
    public int visibility;
    public Wind wind;
    public Rain rain;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;
}
