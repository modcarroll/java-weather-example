import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.rmi.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class WeatherServiceClient extends JFrame {

  public WeatherServiceClient( String server, int pageNum ) {
    super( "RMI WeatherService Client Page " + pageNum );

    try {
      Registry registry = LocateRegistry.getRegistry(server);

      WeatherService stub = (WeatherService) registry.lookup("WeatherService");

      List<WeatherBean> weatherInformation = new ArrayList( stub.getWeatherInformation() );

      // Create 4 lists to divide the pages for the weather beans
      List<WeatherBean> weatherBeans1 = new ArrayList();
      List<WeatherBean> weatherBeans2 = new ArrayList();
      List<WeatherBean> weatherBeans3 = new ArrayList();
      List<WeatherBean> weatherBeans4 = new ArrayList();

      // assign the weather bean to the appropriate bean list by what page number it came from
      for (WeatherBean bean : weatherInformation) {
        if(bean.getPageNumber() == 1) {
          weatherBeans1.add(bean);
        } else if(bean.getPageNumber() == 2) {
          weatherBeans2.add(bean);
        } else if(bean.getPageNumber() == 3) {
          weatherBeans3.add(bean);
        } else {
          weatherBeans4.add(bean);
        }
      }

      List<WeatherBean> list = new ArrayList();

      // for looping through creating the windows, assign the weather bean list
      switch(pageNum) {
        case 1: list = weatherBeans1;
        break;
        case 2: list = weatherBeans2;
        break;
        case 3: list = weatherBeans3;
        break;
        case 4: list = weatherBeans4;
        break;
      }

      ListModel weatherListModel = new WeatherListModel( list );
      JList weatherJList = new JList( weatherListModel );
      weatherJList.setCellRenderer( new WeatherCellRenderer() );

      // Border empty = BorderFactory.createEmptyBorder();
      // TitledBorder title = BorderFactory.createTitledBorder(empty, "title");
      // weatherJList.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
      weatherJList.setBorder(BorderFactory.createTitledBorder(
         BorderFactory.createEmptyBorder(), "  City                       Today      Tom.       Next", TitledBorder.LEFT, TitledBorder.TOP));

      getContentPane().add( new JScrollPane( weatherJList ) );

    } catch ( ConnectException connectionException ) {
      System.err.println( "Connection to server failed. Server may be temporarily unavailable." );
      connectionException.printStackTrace();

    } catch ( Exception exception ) {
      exception.printStackTrace();
    }
  }

  public static void main( String args[] ) {

    WeatherServiceClient client = null;

    // create 4 windows, one for each page
    for(int i = 1; i < 5; i++) {
      if ( args.length == 0 ) client = new WeatherServiceClient( "localhost", i );
      else client = new WeatherServiceClient ( args[0], i );

      client.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      client.pack();
      client.setResizable( true );
      client.setVisible( true );

      // stagger the windows on the screen
      client.setLocation(i * 100, i * 100);
    }
  } // end main method
}
