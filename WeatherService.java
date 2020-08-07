import java.util.*;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WeatherService extends Remote {

  List getWeatherInformation() throws RemoteException;

}