package com.weatherapp.dto.weather;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherInfoBody {
    private String description;
    private Double temperature;
    private Double fellsLike;
    private Double tempMin;
    private Double tempMax;

    @Override
    public String toString() {
        return "description: " + description +
                "\n temperature: " + temperature +
                "\n fells like: " + fellsLike +
                "\n temp min: " + tempMin +
                "\n temp max: " + tempMax;
    }
}
