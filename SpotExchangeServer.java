package spotexchange.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @version     1.0
 * @since       1.0
 */
public class SpotExchangeServer
{
    public static void main(String[] args)
    {
        Database db = new Database();
        //add code to connect to other servers
        
        ServerSocket serverSocket = null;
        boolean socketInitialized = false;
        try
        {
            System.out.println(InetAddress.getLocalHost());
            serverSocket = new ServerSocket(12345,0);
            socketInitialized = true;
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        
        Socket s = null;
        ClientHandler ch;
        
        while(socketInitialized)
        {
            try
            {
                s=serverSocket.accept();
                s.setSoTimeout(15000);
                ch = new ClientHandler(s,db);
                ch.start();
                //break is for testing only
                break;
            }
            catch(IOException e)
            {
                System.out.println(e);
            }
        }
    }
}