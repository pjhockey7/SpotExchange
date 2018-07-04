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
    private final HashMap<String, Object> user;
    //need to add a location but researching google api first
    
    public ParkingSpot(HashMap<String, Object> user, boolean paid, int startTime, int waitTime)
    {
        this.user = user;
        this.paid=paid;
        this.startTime=startTime;
        this.waitTime=waitTime;
    }
    
    public ParkingSpot(HashMap<String, Object> user, boolean paid, int startTime, int waitTime, String details)
    {
        this.user = user;
        this.paid=paid;
        this.startTime=startTime;
        this.waitTime=waitTime;
        this.details = details;
    }
    
    /**
    * Returns the user's HashMap
    *
    * @return HashMap<String, Object>  the user
    * @since 1.0
    */
    public HashMap<String, Object> getUser()
    {
        return user;
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
    * @param  startTime int the starTime
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
    * @param  waitTime int the wait time
    * @since 1.0
    */
    public void setWaitTime(int waitTime)
    {
        this.waitTime=waitTime;
    }
}
