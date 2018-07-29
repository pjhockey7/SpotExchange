package spotexchange.server;

import java.util.HashMap;

/**
 * @version     1.0
 * @since       1.0
 */
public class ParkingSpot
{
    private String details;
    private boolean paid;
    private int startTime;
    private int waitTime;
    private final HashMap<String, Object> seller;
    private HashMap<String, Object> buyer;
    private double latitude;
    private double longitude;
    
    public ParkingSpot(HashMap<String, Object> user, boolean paid, int startTime, int waitTime, double latitude, double longitude)
    {
        this.seller = user;
        this.paid=paid;
        this.startTime=startTime;
        this.waitTime=waitTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public ParkingSpot(HashMap<String, Object> user, boolean paid, int startTime, int waitTime, double latitude, double longitude, String details)
    {
        this.seller = user;
        this.paid=paid;
        this.startTime=startTime;
        this.waitTime=waitTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.details = details;
    }
    
    /**
    * Resets the details
    *
    * @param String the details
    * @since 1.0
    */
    public void setDetails(String details)
    {
        this.details=details;
    }
    
    /**
    * Returns details
    *
    * @return String the details
    * @since 1.0
    */
    public String getDetails()
    {
        return details;
    }
    
    /**
    * Returns longitdue
    *
    * @return double the longitude
    * @since 1.0
    */
    public double getLong()
    {
        return longitude;
    }
    
    /**
    * Sets the longitude
    *
    * @param longitude double the longitude
    * @since 1.0
    */
    public void setLong(double longitude)
    {
        this.longitude = longitude;
    }
    
    /**
    * Returns latitude
    *
    * @return double the latitude
    * @since 1.0
    */
    public double getLat()
    {
        return latitude;
    }
    
    /**
    * Sets the latitude
    *
    * @param latitude double the latitude
    * @since 1.0
    */
    public void setLat(double latitude)
    {
        this.latitude = latitude;
    }
    
    /**
    * Returns the user's HashMap
    *
    * @return HashMap<String, Object>  the seller
    * @since 1.0
    */
    public HashMap<String, Object> getSeller()
    {
        return seller;
    }
    
    /**
    * Returns the user's HashMap
    *
    * @param buyer HashMap<String, Object>  the buyer
    * @since 1.0
    */
    public void setBuyer(HashMap<String, Object> buyer)
    {
        this.buyer = buyer;
    }
    
    /**
    * Returns the user's HashMap
    *
    * @return HashMap<String, Object>  the buyer
    * @since 1.0
    */
    public HashMap<String, Object> getBuyer()
    {
        return buyer;
    }
    
    /**
    * Returns if the parking spot is paid
    *
    * @return boolean if the parking space requires any kind of payment
    * @since 1.0
    */
    public boolean isPaid()
    {
        return paid;
    }
    
    /**
    * Sets whether the parking spot is paid
    *
    * @param paid boolean of whether there is any sort of payment
    * @since 1.0
    */
    public void setPaid(boolean paid)
    {
        this.paid=paid;
    }
    
    /**
    * Returns start time
    *
    * @return int the startTime
    * @since 1.0
    */
    public int getStartTime()
    {
        return startTime;
    }
    
    /**
    * Sets the start time
    *
    * @param startTime int the starTime
    * @since 1.0
    */
    public void setStartTime(int startTime)
    {
        this.startTime=startTime;
    }
    
    /**
    * Returns wait time
    *
    * @return int the wait time
    * @since 1.0
    */
    public int getWaitTime()
    {
        return waitTime;
    }
    
    /**
    * Sets the wait time
    *
    * @param waitTime int the wait time
    * @since 1.0
    */
    public void setWaitTime(int waitTime)
    {
        this.waitTime=waitTime;
    }
}
