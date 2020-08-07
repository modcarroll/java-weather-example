import java.util.*;

import javax.swing.AbstractListModel;

public class WeatherListModel extends AbstractListModel {

  private static final long serialVersionUID = 1;

  private List list;

  public WeatherListModel() {
    list = new ArrayList();
  }

  public WeatherListModel( List itemList ) {
    list = itemList;
  }

  public int getSize() {
    return list.size();
  }

  public Object getElementAt( int index ) {
    return list.get( index );
  }

  public void add( Object element ) {
    list.add( element );
    fireIntervalAdded( this, list.size(), list.size() );
  }

  public void remove( Object element ) {
    int index = list.indexOf( element );
    if ( index != -1 ) {
      list.remove( element );
      fireIntervalRemoved( this, index, index );
    }
  }

  public void clear() {
    int size = list.size();
    list.clear();
    fireContentsChanged( this, 0, size );
  }
  
} // end class WeatherListModel
