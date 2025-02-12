package com.example.IntelTech.model;

import lombok.Data;

@Data
public class WeatherModel {
    private final String description;
    private final double temperature;
    private final double feelsLike;

    public WeatherModel(String description, double temperature, double feelsLike) {
        this.description = description;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
    }

    @Override
    public String toString() {
        return description + ", " + temperature + ", " + feelsLike;
    }
}