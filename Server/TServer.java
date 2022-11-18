import java.io.*;
import java.net.*;

public class TServer
{
    UserHashTable    hashT;

    public static void main(String[]  args)
    {
         try
        {   
            TServer s = new TServer();
            ServerSocket     serverSocket;
            Socket           normalSocket;
            String           welcomeMessage = "Hello user!";
            CTc              ctc;
            boolean          createUsers = true;

            serverSocket = new ServerSocket(3737);  // listen on port 3737
            System.out.println("Server is waiting to accept a request for a connection...");
            
            while(createUsers == true)
            {
                normalSocket = serverSocket.accept();
                //construct CTC, the CTC will construct the Talker
                ctc = new CTc(normalSocket, s);
            }
        }
        catch(IOException e)
        {
            System.out.println("TKL - Error trying to set up network streams....");
            e.printStackTrace();
        }
    } // end of main
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    TServer() // constructing server
    {
        getExistingUsers();
    }
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public void saveUsers()
    {
         try
         {
            DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("id.bin"));
            hashT.storeUsers(dataOut);
         }
         catch(IOException ioe)
         {
            System.out.println("Trouble reading from the file: " + ioe.getMessage());
         }
    }
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
    public void getExistingUsers()
    {
        try
        {
            DataInputStream dataIn = new DataInputStream(new FileInputStream ("id.bin"));
            hashT = new UserHashTable(dataIn);

            System.out.println("HashTable is constructed with existing users");

        }
        catch(IOException io)
        {
            System.out.println("Error " + io.getMessage());
            hashT = new UserHashTable();
            System.out.println("HashTable is constructed");
        }
    }

    public void storeMyUser(User user) // storing new user
    {
        hashT.storeNewUser(user);
    }

    public User findUserById(String id)
    {
        User user = hashT.getUserById(id);
        return(user);
    }
}




