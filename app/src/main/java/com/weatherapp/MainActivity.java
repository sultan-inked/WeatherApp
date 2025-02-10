package com.weatherapp;

import static com.weatherapp.util.Properties.CITY_COORD_URI_TEMPLATE;
import static com.weatherapp.util.Properties.LIMIT;
import static com.weatherapp.util.Properties.WEATHER_API_KEY;
import static com.weatherapp.util.UriBuilder.buildUri;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weatherapp.service.WeatherDataRequester;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import lombok.Getter;

@Getter
public class MainActivity extends AppCompatActivity {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Executor executor = Executors.newSingleThreadExecutor();

    private EditText enterCityField;
    private Button actionButton;
    private TextView weatherInfoHead;
    private TextView weatherInfoBody;

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

        actionButton.setOnClickListener(view -> {
            if (enterCityField.getText().toString().trim().isBlank()) {
                Toast.makeText(MainActivity.this, R.string.no_city_name_for_search,
                        Toast.LENGTH_LONG).show();
            } else {
                String city = enterCityField.getText().toString();

                String cityCoordUri = buildUri(CITY_COORD_URI_TEMPLATE, city, LIMIT, WEATHER_API_KEY);

                new WeatherDataRequester(enterCityField, actionButton, weatherInfoHead, weatherInfoBody, objectMapper).execute(cityCoordUri);
            }
        });
    }
}