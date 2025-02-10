package com.weatherapp.service;

import static com.weatherapp.util.Properties.LANG;
import static com.weatherapp.util.Properties.UNITS;
import static com.weatherapp.util.Properties.WEATHER_API_KEY;
import static com.weatherapp.util.Properties.WEATHER_BI_COORDINATED_URI_TEMPLATE;
import static com.weatherapp.util.UriBuilder.buildUri;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapp.R;
import com.weatherapp.dto.city.CityCoordResponseDto;
import com.weatherapp.dto.weather.WeatherInfoResponseDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@SuppressLint("StaticFieldLeak")
public class WeatherDataRequester extends AsyncTask<String, String, String> {
    private final EditText enterCityField;
    private final Button actionButton;
    private final TextView weatherInfoHead;
    private final TextView weatherInfoBody;
    private final ObjectMapper objectMapper;

    public WeatherDataRequester(EditText enterCityField, Button actionButton, TextView weatherInfoHead, TextView weatherInfoBody, ObjectMapper objectMapper) {
        this.enterCityField = enterCityField;
        this.actionButton = actionButton;
        this.weatherInfoHead = weatherInfoHead;
        this.weatherInfoBody = weatherInfoBody;
        this.objectMapper = objectMapper;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        weatherInfoBody.setText(R.string.waiting_for_load_weather_info);
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL cityCoordUrl = new URL(strings[0]);
            connection = (HttpURLConnection) cityCoordUrl.openConnection();
            connection.connect();

            InputStream cityCoordStream = connection.getInputStream();

            List<CityCoordResponseDto> cityCoordDtoList = objectMapper.readValue(cityCoordStream,
                    new TypeReference<>() {});

            if (cityCoordDtoList.isEmpty()) {
                return "City with name: \n\"" + enterCityField.getText().toString() + "\"\n not found";
            }

            CityCoordResponseDto cityCoordDto = cityCoordDtoList.get(0);

            Double lat = cityCoordDto.lat;
            Double lon = cityCoordDto.lon;

            String weatherUri = buildUri(WEATHER_BI_COORDINATED_URI_TEMPLATE, lat, lon,
                    WEATHER_API_KEY, UNITS, LANG);

            URL url = new URL(weatherUri);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();

            WeatherInfoResponseDto responseDto = objectMapper.readValue(stream, WeatherInfoResponseDto.class);

            return weatherInfoBuilder(responseDto);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "";
    }

    private String weatherInfoBuilder(WeatherInfoResponseDto response) {
        String description = response.weather.get(0).description;
        Double temperature = response.main.temp;
        Double fellsLike = response.main.feels_like;
        Double tempMin = response.main.temp_min;
        Double tempMax = response.main.temp_max;
        return "description: " + description +
                "\n temperature: " + temperature +
                "\n fells like: " + fellsLike +
                "\n temp min: " + tempMin +
                "\n temp max: " + tempMax;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        weatherInfoBody.setText(result);
    }
}
