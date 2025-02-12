package com.example.IntelTech.controller;

import com.example.IntelTech.model.WeatherModel;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
@AllArgsConstructor
public class WeatherController {
    private final String apiKey = "38742cb9cf6f26aa3452a185ba656752";
    private final String urlTemplate = "https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&lang=ru&appid=%s&units=metric";

    public WeatherModel getWeather(double latitude, double longitude) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(urlTemplate, latitude, longitude, apiKey);

        String jsonResponse = restTemplate.getForObject(url, String.class);
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        String description = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        double temperature = jsonObject.getAsJsonObject("main").get("temp").getAsDouble();
        double feelsLike = jsonObject.getAsJsonObject("main").get("feels_like").getAsDouble();

        return new WeatherModel(description, temperature, feelsLike);
    }
}