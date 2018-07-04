package spotexchange.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @version     1.0
 * @since       1.0
 */
public class SpotExchangeServer
{
    public static void main(String[] args)
    {
        HashMap<String, Object> test = getUser("johndoe@gmail.com");
        System.out.println(test.get("eta"));
        System.out.println(((List)test.get("spots")).size());
        System.out.println(((HashMap)((List)test.get("spots")).get(1)).get("loc"));
        
        /*Database db = new Database();
        
        //add code to connect to other servers
        
        ServerSocket serverSocket = null;
        boolean socketInitialized = false;
        try
        {
            serverSocket = new ServerSocket(12345,0,InetAddress.getLocalHost());
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
        }*/
    }
    
    public static HashMap<String, Object> getUser(String username)
    {
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
    
    private static HashMap<String, Object> recJsonReader(String json)
    {
        HashMap<String, Object> user = new HashMap<String, Object>();
        String key;
        
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
    
    private static List<Object> recJsonListReader(String json)
    {
        List<Object> ar = new ArrayList<Object>();
        
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
}