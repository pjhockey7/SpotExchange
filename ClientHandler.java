package spotexchange.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

/**
 * @version     1.0
 * @since       1.0
 */
public class ClientHandler extends Thread
{
    private Socket socket;
    private Database db;
    private InputStream input;
    private OutputStream output;
    //maximum number of attempts to connect to a client before giving up
    private final int TIMEOUT=25;
    private final double LOG256=5.545177444488;
    
    public ClientHandler(Socket socket, Database db)
    {
        this.socket=socket;
        this.db=db;
        try
        {
            input=socket.getInputStream();
            output=socket.getOutputStream();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
    
    /**
    * TODO
    * Handles a specific request and then closes itself
    *
    * @since 1.0
    */
    public void run()
    {
        System.out.println(readString());
        writeString("test");
        /*int function = determineRequest();
        //call function for each type of request this can be altered as we get more functions
        switch(function)
        {
            case 1:
                sendSpots();
                break;
        }
        try
        {
            socket.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }*/
    }
    
    /**
    * Determines what type of request it is
    *
    * @return int corresponding to a type of request or -1 for bad connection
    * @since 1.0
    */
    private int determineRequest()
    {
        return readInt();
    }
    
    /**
    * Writes the int passed to it in the output stream
    *
    * @param num int the number to send
    * @since 1.0
    */
    private void writeFloat(float num)
    {
        int bits = Float.floatToIntBits(num);
        writeInt(bits);
    }
    
    /**
    * Writes the int passed to it in the output stream assuming a 32 bit int
    *
    * @param num int the number to send
    * @since 1.0
    */
    private void writeInt(int num)
    {
        try
        {
            for(int i=0;i<4;i++)
            {
                output.write(num%256);
                num=num>>>8;
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
    
    /**
    * Reads in a double that takes multiple bytes
    *
    * @return double made from the incoming bytes
    * @since 1.0
    */
    private float readFloat()
    {
        int bits = readInt();
        return Float.intBitsToFloat(bits);
    }
    
    /**
    * Reads in an int from the input stream
    *
    * @return int made from the incoming bytes
    * @since 1.0
    */
    private int readInt()
    {
        int[] bytes = new int[4];
        int sum = 0;
        try
        {
            for(int i=0;i<4;i++)
            {
                bytes[i]=input.read();
            }
        }
        catch (IOException e)
        {
            System.out.println(e);
            return -1;
        }
        for(int i=3;i>=0;i--)
        {
            sum<<=8;
            sum+=bytes[i];
        }
        
        return sum;
    }
    
    /**
    * Writes a string to the client
    *
    * @param String the word to send
    * @since 1.0
    */
    private void writeString(String word)
    {
        writeInt(word.length());
        try
        {
            for(int i=0;i<word.length();i++)
            {
                output.write(word.charAt(i));
            }
        }
        catch(IOException e)
        {
            
        }
    }
    
    /**
    * Reads a string from the client
    *
    * @return String the string sent
    * @since 1.0
    */
    private String readString()
    {
        int length=readInt();
        char[] word = new char[length];
        for(int i=0;i<length;i++)
        {
            try
            {
                word[i]=(char)input.read();
            }
            catch(IOException e)
            {
                
            }
        }
        return String.valueOf(word);
    }
    
    /**
    * TODO
    * Sends a list of spots to the client
    *
    * @since 1.0
    */
    private void sendSpots()
    {
        List<ParkingSpot> spots = db.getSpots(readFloat(),readFloat());
        writeInt(spots.size());
        for(int i=0;i<spots.size();i++)
        {
            
        }
    }
}