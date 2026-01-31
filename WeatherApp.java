import javax.swing.*;
import java.awt.*;
import org.json.simple.JSONObject;

public class WeatherAppGui extends JFrame {

    private JTextField cityField;
    private JLabel tempLabel;
    private JLabel windLabel;
    private JLabel conditionLabel;
    private JLabel iconLabel;

    public WeatherAppGui() {
        setTitle("Weather App");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        cityField = new JTextField(15);
        JButton searchBtn = new JButton("Search");

        top.add(cityField);
        top.add(searchBtn);
        add(top, BorderLayout.NORTH);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        iconLabel = new JLabel();
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        tempLabel = new JLabel("Temperature: --");
        windLabel = new JLabel("Wind Speed: --");
        conditionLabel = new JLabel("Condition: --");

        tempLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        windLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        conditionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(Box.createVerticalStrut(20));
        center.add(iconLabel);
        center.add(Box.createVerticalStrut(15));
        center.add(tempLabel);
        center.add(windLabel);
        center.add(conditionLabel);

        add(center, BorderLayout.CENTER);

        searchBtn.addActionListener(e -> loadWeather());

        setVisible(true);
    }

    private void loadWeather() {
        String city = cityField.getText();
        JSONObject data = WeatherApp.getWeatherData(city);

        if (data == null) {
            JOptionPane.showMessageDialog(this, "City not found");
            return;
        }

        tempLabel.setText("Temperature: " + data.get("temperature") + " °C");
        windLabel.setText("Wind Speed: " + data.get("windspeed") + " km/h");
        conditionLabel.setText("Condition: " + data.get("weather_condition"));

        String condition = (String) data.get("weather_condition");
        iconLabel.setIcon(loadIcon(condition));
    }

    private ImageIcon loadIcon(String condition) {
        String file = switch (condition) {
            case "Clear" -> "clear.png";
            case "Cloudy" -> "cloudy.png";
            case "Rain" -> "rain.png";
            case "Snow" -> "snow.png";
            default -> null;
        };

        if (file == null) return null;

        try {
            var stream = getClass().getResourceAsStream("/assets/" + file);
            if (stream == null) return null;
            Image img = ImageIO.read(stream);
            return new ImageIcon(img.getScaledInstance(120, 120, Image.SCALE_SMOOTH));
        } catch (Exception e) {
            return null;
        }
    }
}
