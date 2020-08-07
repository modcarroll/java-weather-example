import java.io.*;
import java.net.URL;
import java.rmi.Remote;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeatherServiceServer implements WeatherService {

  public WeatherServiceServer() throws RemoteException {
    super();
    updateWeatherConditions();
  }

  private List weatherInformation;

  private void updateWeatherConditions() {
    weatherInformation = new ArrayList();

    String baseUrl = "https://forecast.weather.gov/product.php?site=CRH&product=SCS&issuedby=0";

    // loop through the 4 pages
    for (int i = 1; i < 5; i++) {
      try {
        System.out.println("---------------- Page " + i + " ----------------");
        URL url = new URL(baseUrl + i);

        BufferedReader in = new BufferedReader( new InputStreamReader( url.openStream() ) );

        String separator = "CITY";
        String line = "";
        for ( line = in.readLine(); !line.startsWith(separator); line = in.readLine() )
          System.out.println( line );

        String inputLine = "";
        inputLine = in.readLine();  // skip an empty line
        inputLine = in.readLine();  // first city info line

        String cityName = "";
        String temperatures = "";
        String condition = "";
        String forecast = "";
        String forecast2 = "";

        while ( inputLine.length() > 10 ) {
          System.out.println(inputLine.substring( 0, 17 )+"--"+inputLine.substring( 33, 38 )+"--"+inputLine.substring( 17, 20 ));

          cityName      = inputLine.substring(  0, 16 ).trim();
          temperatures  = inputLine.substring( 16, 23 ).trim();
          condition     = inputLine.substring( 32, 38 ).trim();
          forecast      = inputLine.substring(39, 48).trim();
          forecast2     = inputLine.substring(55, inputLine.length()).trim();

          System.out.println( "\n*************************\nCity : " + cityName + "\nCondition:" + condition + "\nTemp:" + temperatures);
          weatherInformation.add( new WeatherBean(cityName, condition, temperatures, forecast, forecast2, i) );
          inputLine = in.readLine();  // get next city's info
          System.out.println("---------------- Page " + i + " End ----------------");
        } // end while loop

        in.close();
        System.out.println( "Weather information updated." );

      } catch( java.net.ConnectException connectException ) {
        connectException.printStackTrace();
        System.exit( 1 );

      } catch( Exception exception ) {
        exception.printStackTrace();
        System.exit( 1 );
      }
    }
  }

  public List getWeatherInformation() throws RemoteException { return weatherInformation; }

  private static void run() {
		System.out.println("Begin run: " + new java.util.Date());
    try {
      System.out.println( "Initializing WeatherService..." );
      WeatherService stub = (WeatherService) UnicastRemoteObject.exportObject(new WeatherServiceServer(), 0);
      System.out.println("Stub initialized...");

      String host = "localhost";

      Registry registry = LocateRegistry.getRegistry(host, 1099);
      registry.rebind("WeatherService", stub);
      System.out.println("Registry bound...");
      System.out.println( "WeatherService running." );

    } catch (Exception e) {
      System.out.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }

	public static void main(String[] args) {
    // Schedule the run() method to run every hour
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(WeatherServiceServer::run, 0, 1, TimeUnit.HOURS);
	}
}
