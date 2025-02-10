package com.weatherapp.dto.weather.embeded;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rain {
    @JsonProperty("1h")
    public double _1h;
}
