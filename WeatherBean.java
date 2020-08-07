import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class WeatherBean implements Serializable {

  private static final long serialVersionUID = 1;
  private String cityName;
  private String temperature;
  private String description;
  private ImageIcon image;
  private int pageNumber;
  private String forecast;
  private String forecast2;

  private static Properties imageNames;

  static {
    imageNames = new Properties();

    try {
      imageNames.load( new FileInputStream( new File("imagenames.properties") ));
    } catch ( IOException ioException ) {
      ioException.printStackTrace();
    }

  } // end static

  public WeatherBean( String city, String weatherDescription, String cityTemperature, String forecast01, String forecast02, int pageNum ) {

    cityName = city;
    temperature = cityTemperature;
    description = weatherDescription.trim();
    pageNumber = pageNum;
    forecast = forecast01;
    forecast2 = forecast02;

    System.out.println("Update weather bean: City = " + city + "\n" +
                       "                     Condition = " + weatherDescription + "\n" +
                       "                     Temp = " + cityTemperature + "\n" +
                       "                     Description = " + description + "\n" +
                       "                     Forecast 1 = " + forecast + "\n" +
                       "                     Forecast 2 = " + forecast2 + "\n" +
                       "                     Page Number = " + pageNumber );

    URL url = WeatherBean.class.getResource( "images/" + imageNames.getProperty( description, "noinfo.jpg" ) );

    image = new ImageIcon( url );
  } // end constructor

  public String getCityName() { return cityName; }
  public String getTemperature() { return temperature; }
  public String getDescription() { return description; }
  public String getForecast() { return forecast; }
  public String getForecast2() { return forecast2; }
  public int getPageNumber() { return pageNumber; }
  public ImageIcon getImage() { return image; }

} // end class WeatherBean
