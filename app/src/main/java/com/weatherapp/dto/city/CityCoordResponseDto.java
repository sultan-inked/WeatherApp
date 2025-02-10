package com.weatherapp.dto.city;

import com.weatherapp.dto.city.embeded.LocalNames;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityCoordResponseDto {
    public String name;
    public LocalNames local_names;
    public double lat;
    public double lon;
    public String country;
    public String state;
}
