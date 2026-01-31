import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;

public class WeatherAppGui extends JFrame {

    private JTextField cityInput;
    private JLabel temperatureLabel;
    private JLabel conditionLabel;
    private JLabel humidityLabel;
    private JLabel windLabel;
    private JLabel weatherIcon;

    public WeatherAppGui() {
        setTitle("Weather App");
        setSize(350, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1));
        setLocationRelativeTo(null);

        cityInput = new JTextField();
        JButton searchButton = new JButton("Search");

        weatherIcon = new JLabel();
        weatherIcon.setHorizontalAlignment(JLabel.CENTER);

        temperatureLabel = new JLabel("", JLabel.CENTER);
        conditionLabel = new JLabel("", JLabel.CENTER);
        humidityLabel = new JLabel("", JLabel.CENTER);
        windLabel = new JLabel("", JLabel.CENTER);

        add(cityInput);
        add(searchButton);
        add(weatherIcon);
        add(temperatureLabel);
        add(conditionLabel);
        add(humidityLabel);
        add(windLabel);

        searchButton.addActionListener(e -> fetchWeather());
    }

    private void fetchWeather() {
        String city = cityInput.getText().trim();
        JSONObject weather = WeatherApp.getWeatherData(city);

        if (weather == null) {
            JOptionPane.showMessageDialog(this, "City not found");
            return;
        }

        temperatureLabel.setText("Temperature: " + weather.get("temperature") + " °C");
        conditionLabel.setText("Condition: " + weather.get("weather_condition"));
        humidityLabel.setText("Humidity: " + weather.get("humidity") + "%");
        windLabel.setText("Wind Speed: " + weather.get("windspeed") + " km/h");

        setWeatherIcon(weather.get("weather_condition").toString());
    }

    // ✅ WEATHER → IMAGE MAPPING
    private String getImageForCondition(String condition) {
        switch (condition.toLowerCase()) {
            case "clear": return "clear.png";
            case "cloudy": return "cloudy.png";
            case "rain": return "rain.png";
            case "snow": return "snow.png";
            default: return "unknown.png";
        }
    }

    // ✅ SAFE IMAGE LOADER
    private void setWeatherIcon(String condition) {
        String imageName = getImageForCondition(condition);
        java.net.URL imgURL = getClass().getResource("/images/" + imageName);

        if (imgURL == null) {
            System.out.println("Image not found: " + imageName);
            weatherIcon.setText("No Image");
            weatherIcon.setIcon(null);
            return;
        }

        ImageIcon icon = new ImageIcon(imgURL);
        weatherIcon.setIcon(icon);
        weatherIcon.setText("");
    }
}
