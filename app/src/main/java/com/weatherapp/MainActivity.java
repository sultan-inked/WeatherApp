package com.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapp.dto.city.CityCoordResponseDto;
import com.weatherapp.dto.weather.WeatherInfoResponseDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lombok.Getter;

@Getter
public class MainActivity extends AppCompatActivity {
    private static final String WEATHER_API_KEY = "ce5250f440f47fdbd0936be22ee677d4";
    private static final int LIMIT = 1;
    private static final String UNITS = "metric";
    private static final String LANG = "ru";
    private static final String CITY_COORD_URI_TEMPLATE = "https://api.openweathermap.org/geo/1.0/direct?q=%s&limit=%s&appid=%s";
    private static final String WEATHER_BI_COORDINATED_URI_TEMPLATE = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=%s&lang=%s";
    private EditText enterCityField;
    private Button actionButton;
    private TextView weatherInfoHead;
    private TextView weatherInfoBody;
    private ObjectMapper objectMapper = new ObjectMapper();

    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        enterCityField = findViewById(R.id.enter_city_field);
        actionButton = findViewById(R.id.action_button);
        weatherInfoHead = findViewById(R.id.weather_info_head);
        weatherInfoBody = findViewById(R.id.weather_info_body);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enterCityField.getText().toString().trim().isBlank()) {
                    Toast.makeText(MainActivity.this, R.string.no_city_name_for_search,
                            Toast.LENGTH_LONG).show();
                } else {
                    String city = enterCityField.getText().toString();

                    String cityCoordUri = buildUri(CITY_COORD_URI_TEMPLATE, city, LIMIT, WEATHER_API_KEY);

                    new GetURIData().execute(cityCoordUri);
                }
            }
        });
    }

    private String buildUri(String template, Object... args) {
        return String.format(template, args);
    }

    class GetURIData extends AsyncTask<String, String, String> {
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
                        new TypeReference<List<CityCoordResponseDto>>() {});
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

                 String description = responseDto.weather.get(0).description;
                 Double temperature = responseDto.main.temp;
                 Double fellsLike = responseDto.main.feels_like;
                 Double tempMin = responseDto.main.temp_min;
                 Double tempMax = responseDto.main.temp_max;
                return "description: " + description +
                        "\n temperature: " + temperature +
                        "\n fells like: " + fellsLike +
                        "\n temp min: " + tempMin +
                        "\n temp max: " + tempMax;

            } catch (IOException e) {
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

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            weatherInfoBody.setText(result);
        }
    }
}