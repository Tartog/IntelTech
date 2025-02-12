package com.example.IntelTech.view;

import com.example.IntelTech.controller.WeatherController;
import com.example.IntelTech.model.WeatherModel;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@Route("")
public class WeatherView extends VerticalLayout {
    private final Div weatherDiv = new Div();
    private final Div weatherDescriptionDiv = new Div();
    private final Div temperatureDiv = new Div();
    private final Image weatherImage = new Image();

    public WeatherView(WeatherController weatherController) {

        TextField latitudeField = new TextField("Широта");
        TextField longitudeField = new TextField("Долгота");
        Button showWeatherButton = new Button("Показать погоду !");

        HorizontalLayout coordinatesLayout = new HorizontalLayout(latitudeField, longitudeField);
        coordinatesLayout.setSpacing(true);

        showWeatherButton.addClickListener(event -> {
            try {
                double latitude = Double.parseDouble(latitudeField.getValue());
                double longitude = Double.parseDouble(longitudeField.getValue());
                WeatherModel weather = weatherController.getWeather(latitude, longitude);

                String[] parts = weather.toString().split(", ");
                weatherDescriptionDiv.setText(parts[0]);
                if (parts.length > 1) {
                    temperatureDiv.setText(parts[1] + " градусов, ощущается как " + parts[2]);
                }

                setWeatherImage(parts[0]);
                weatherImage.setWidth("420px");
                weatherImage.setHeight("220px");
                weatherDescriptionDiv.getStyle().set("font-size", "35px");
                temperatureDiv.getStyle().set("font-size", "24px");
                temperatureDiv.getStyle().set("margin-top", "10px");

                weatherDiv.removeAll();
                weatherDiv.add(weatherDescriptionDiv);
                weatherDiv.add(temperatureDiv);
                weatherDiv.add(weatherImage);
            } catch (NumberFormatException e) {
                Notification.show("Введите корректные значения для широты и долготы.");
            }
            catch (Exception e) {
                Notification.show("Ошибка при получении данных о погоде: " + e.getMessage());
            }
        });

        coordinatesLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        coordinatesLayout.setAlignItems(Alignment.CENTER);

        weatherDiv.add(weatherDescriptionDiv, temperatureDiv);

        VerticalLayout weatherLayout = new VerticalLayout(weatherDiv);
        weatherLayout.setAlignItems(Alignment.CENTER);

        add(coordinatesLayout, showWeatherButton, weatherLayout);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);
        setHeight("100vh");
    }

    private void setWeatherImage(String weatherDescription) {
        if (weatherDescription.toLowerCase().contains("пасмурно")) {
            weatherImage.setSrc("images/cloudy.png");
        } else if (weatherDescription.toLowerCase().contains("облачно")) {
            weatherImage.setSrc("images/partlyCloudy.png");
        } else if (weatherDescription.toLowerCase().contains("ясно")) {
            weatherImage.setSrc("images/clear.png");
        } else if (weatherDescription.toLowerCase().contains("снег")) {
            weatherImage.setSrc("images/snow.png");
        } else if (weatherDescription.toLowerCase().contains("дождь")) {
            weatherImage.setSrc("images/rain.png");
        }
    }
}