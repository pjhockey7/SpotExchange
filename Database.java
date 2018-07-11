package spotexchange.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @version     1.0
 * @since       1.0
 */
public class Database
{
    //about a half a kilometer
    private final double RELATIVE_MAX_DISTANCE = .004;
    private final int MAX_RELEVANT_SPOTS = 5;
    
    List<ParkingSpot> spots = new ArrayList<ParkingSpot>();
    
    public Database()
    {
        
    }
    
    /**
    * Opens the file associated with that user and converts the file into a HashMap
    *
    * @param username String that is the username of the user
    * @return HashMap containing all of the users key value pairs
    * @since 1.0
    */
    public static HashMap<String, Object> getUser(String username)
    {
        //this function just puts the file into a string and then uses the recursive method
        try
        {
            InputStream input = new FileInputStream(new File("C:\\Users\\PJ\\Documents\\NetBeansProjects\\SpotExchange Server\\UserFiles\\"+username+".txt"));
            char in = (char)input.read();
            StringBuffer sb = new StringBuffer(50);
            //this seems to mean the file is over. not printing -1 like it shoould
            while(in!=65535)
            {
                sb.append(in);
                in = (char)input.read();
            }
            return recJsonReader(sb.toString());
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        return null;
    }
    
    /**
    * Recursive helper method for getUser
    *
    * @param json String containing the file to convert
    * @return HashMap containing all of the users key value pairs
    * @since 1.0
    */
    private static HashMap<String, Object> recJsonReader(String json)
    {
        HashMap<String, Object> user = new HashMap<String, Object>();
        String key;
        
        //It just adds key value pairs to the hashmap until the end of the file.  if it finds an object it calls itself recursively.  if it finds an array it calls that
        
        int index=json.indexOf("\"");
        int nextIndex=json.indexOf("\"",index+1);
        while(index!=-1)
        {
            key=json.substring(index+1,nextIndex);
            if(json.contains("[")&&json.indexOf("\"",nextIndex+1)>json.indexOf("[",nextIndex+1))
            {
                int ctr =0;
                int loc=json.indexOf("[",nextIndex+1)+1;
                while(ctr>=0)
                {
                    if(json.charAt(loc)=='[')
                        ctr++;
                    else if(json.charAt(loc)==']')
                        ctr--;
                    loc++;
                }
                String recString = json.substring(json.indexOf("[",nextIndex+1)+1,loc-1);
                List<Object> value = recJsonListReader(recString);
                user.put(key, value);
                index = json.indexOf("\"",loc);
            }
            else if(json.indexOf("{",index)!=-1&&json.indexOf("\"",nextIndex+1)>json.indexOf("{",nextIndex+1))
            {
                int ctr =0;
                int loc=json.indexOf("{",nextIndex+1)+1;
                while(ctr>=0)
                {
                    if(json.charAt(loc)=='{')
                        ctr++;
                    else if(json.charAt(loc)=='}')
                        ctr--;
                    loc++;
                }
                String recString = json.substring(json.indexOf("{",nextIndex+1),loc);
                HashMap<String, Object> value = recJsonReader(recString);
                user.put(key, value);
                index = json.indexOf("\"",loc);
            }
            else if(json.contains("null")&&(json.indexOf("\"",nextIndex+1)>json.indexOf("null",nextIndex+1)||json.indexOf("\"",nextIndex+1)==-1))
            {
                user.put(key, null);
                index=json.indexOf("\"",nextIndex+1);
            }
            else
            {
                int valueIndex=json.indexOf("\"",nextIndex+1);
                int endIndex=json.indexOf("\"",valueIndex+1);
                String value=json.substring(valueIndex+1,endIndex);
                user.put(key, value);
                index=json.indexOf("\"",endIndex+1);
            }
            nextIndex=json.indexOf("\"",index+1);
        }
        return user;
    }
    
    /**
    * Recursive helper method for recJsonReader
    *
    * @param json String containing the file to convert
    * @return List containing all of values in a json array
    * @since 1.0
    */
    private static List<Object> recJsonListReader(String json)
    {
        List<Object> ar = new ArrayList<Object>();
        
        //It just adds values to the list.  if it finds an array it calls itself recursively and if it finds an object calls recjsonreader
        
        if(!json.contains("\""))
        {
            return ar;
        }
        else if(json.contains("{")&&json.indexOf("\"")>json.indexOf("{"))
        {
            int ctr;
            int loc=json.indexOf("{");
            int startIndex=loc;
            while(loc!=-1)
            {
                loc++;
                ctr=0;
                while(ctr>=0)
                {
                    if(json.charAt(loc)=='{')
                    {
                        ctr++;
                    }
                    else if(json.charAt(loc)=='}')
                    {
                        ctr--;
                    }
                    loc++;
                }
                String recString = json.substring(startIndex-1,loc);
                HashMap<String, Object> value = recJsonReader(recString);
                ar.add(value);
                loc=json.indexOf("{",loc);
            }
        }
        else
        {
            int index=json.indexOf("\"");
            int nextIndex=json.indexOf("\"",index+1);
            while(index!=-1)
            {
                ar.add(json.substring(index+1, json.indexOf("\"",index+1)));
                index=json.indexOf("\"",nextIndex+1);
                nextIndex=json.indexOf("\"",index+1);
            }
            return ar;
        }
        return ar;
    }
    
    /**
    * Adds a ParkingSpot to the list
    *
    * @param spot ParkingSpot to add
    * @since 1.0
    */
    public synchronized void addParkingSpot(ParkingSpot spot)
    {
        spots.add(spot);
    }
    
    /**
    * Returns a list of the spots in the vicinity
    *
    * @param latitude double latitude coordinate
    * @param longitude double longitude coordinate
    * @return List<ParkingSpot> has all the parking spots
    * @since 1.0
    */
    public List<ParkingSpot> getSpots(double latitude, double longitude)
    {
        List<ParkingSpot> closeSpots = new ArrayList<ParkingSpot>();
        ParkingSpot temp;
        double spotLat;
        double spotLong;
        
        int i=0;
        while(closeSpots.size()<MAX_RELEVANT_SPOTS)
        {
            try
            {
                temp=closeSpots.get(i);
                //did a bunch of math.  this isn't the actual distance but the approximation is 100% close enough
                if(temp.getLat()-latitude<RELATIVE_MAX_DISTANCE&&temp.getLong()-longitude<RELATIVE_MAX_DISTANCE)
                {
                    closeSpots.add(temp);
                }
            }
            catch(IndexOutOfBoundsException e)
            {
                break;
            }
            i++;
        }
        
        return spots;
    }
    //below we should have all of the more specific data base functions like getting nodes from the root as well as specific things from files.  we can add as we go.  Some of these files will need to be synchronized
}