import java.awt.*;

import javax.swing.*;

public class WeatherCellRenderer extends DefaultListCellRenderer {

  public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean focus ) {
    return new WeatherItem( ( WeatherBean ) value );
  }

}