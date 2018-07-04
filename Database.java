package spotexchange.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @version     1.0
 * @since       1.0
 */
public class Database
{
    List<ParkingSpot> spots = new ArrayList<ParkingSpot>();
    
    public Database()
    {
        
    }
    
    /**
    * TODO
    * Opens the file associated with that user and converts the file into a HashMap
    *
    * @param  username String that is the username of the user
    * @return HashMap containing all of the users key value pairs
    * @since 1.0
    */
    public HashMap<String, Object> getUser(String username)
    {
        
        return null;
    }
    
    /**
    * TODO FOR NIC
    * Adds a parkingspot to the tree
    *
    * @param  spot ParkingSpot to add
    * @since 1.0
    */
    public synchronized void addParkingSpot(ParkingSpot spot)
    {
        spots.add(spot);
    }
    
    /**
    * TODO FOR NIC
    * Returns a list of the spots in the vicinity
    *
    * @param  x double x coordinate
    * @param  y double y coordinate
    * @return List<ParkingSpot> has all the parking spots
    * @since 1.0
    */
    public synchronized List<ParkingSpot> getSpots(double x, double y)
    {
        return null;
    }
    //below we should have all of the more specific data base functions like getting nodes from the root as well as specific things from files.  we can add as we go.  Some of these files will need to be synchronized
}
