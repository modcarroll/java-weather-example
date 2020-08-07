import java.awt.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

public class WeatherItem extends JPanel {

  private static final long serialVersionUID = 1;
  private WeatherBean weatherBean;

  private static ImageIcon backgroundImage;

  static {
    URL url = WeatherItem.class.getResource( "images/back.jpg" );
    backgroundImage = new ImageIcon( url );
  } // end static

  public WeatherItem( WeatherBean bean ) {
    weatherBean = bean;
  } // end constructor

  public void paintComponent( Graphics g ) {
    super.paintComponent( g );

    backgroundImage.paintIcon( this, g, 0, 0 );

    Font font = new Font( "SansSerif", Font.BOLD, 12 );
    g.setFont( font );
    g.setColor( Color.white );
    g.drawString( weatherBean.getCityName(), 10, 19 );
    g.drawString( weatherBean.getTemperature(), 130, 19 );
    g.drawString( weatherBean.getForecast(), 190, 19 );
    g.drawString( weatherBean.getForecast2(), 250, 19 );

    weatherBean.getImage().paintIcon( this, g, 300, 1 );
  } // end method paintComponent

  public Dimension getPreferredSize() {
    return new Dimension( backgroundImage.getIconWidth() + 35, backgroundImage.getIconHeight() );
  }

} // end class WeatherItem
