package spotexchange.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

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
        try
        {
            System.out.println(input.read());
            output.write(7);
            output.write(8);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
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
        try
        {
            //gives us a 16 bits of options for functions.  shouldn't ever need to overhaul this
            return 255*input.read()+input.read();
        }
        catch(IOException e)
        {
            System.out.println(e);
            return -1;
        }
    }
    
    /**
    * TODO
    * Sends a list of spots to the client
    *
    * @since 1.0
    */
    private void sendSpots()
    {
        
    }
}