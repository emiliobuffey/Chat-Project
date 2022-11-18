import java.io.*;
import java.net.*;
import java.lang.*;
import javax.swing.*;

public class CTs
	implements Runnable
{
	ClientsFrame        cFrame;
    Talker              talk;
    Thread              thread;
    String              domain = "127.0.0.1";
    int                 port = 3737;    
    String              message = " ";
    BufferedReader      buffR;
    String              buttonC= "";   // if user selected login or register
    String              userN = "";    // username
    String              pass = "";     // password
    Boolean             messaging = true;
    String              buddyName = "";
    Chat                chat;
    Chat                saveChat;


    
	public CTs(String buttonClicked, String username, String password) throws IOException
	{
		buttonC = buttonClicked;
		userN = username;
		pass = password;

		talk = new Talker(domain,port,username);
        System.out.println("Client is ready to receive...");
        thread = new Thread(this);
        thread.start();
	}

	public void run()
	{
		try
		{
		//welcome 
		message = talk.receivedMessage();
		//button clicked
		talk.sendMessage(buttonC);


	//	message = talk.receivedMessage();

		// login
		if(buttonC.equals("Login"))
		{
			//username
			talk.sendMessage(userN);
			//message = talk.receivedMessage();

			//password
			talk.sendMessage(pass);

			message = talk.receivedMessage();

			if(message.equals("Logged in"))
			{
				talk.makeID(userN);
			}
			else
			{
				System.out.println("Not logged in.");
				System.exit(0);
			}
		}
		//register
		else if(buttonC.equals("Register"))
		{
			talk.sendMessage(userN);
			talk.sendMessage(pass);
		}

		cFrame = new ClientsFrame(this, userN);

		//asynchronous messages
		while(messaging == true)
		{
			message = talk.receivedMessage();
			if (message.equals("Logout"))
	        {
	        	messaging = false;
	        	System.out.println("User gone :(");
	        	thread.interrupt();
	        	System.exit(0);
	        }
	       else if(message.equals("Become Buddies"))
	        {
	        	System.out.println("New buddy.");
	        	buddyName = talk.receivedMessage();
	        	

				SwingUtilities.invokeLater( // invoke in the Dispatcher thread
				new Runnable() // inner class that only implements Runnable
				{
					public void run() 
					{
						try
						{
							//code goes here that updates the Swing GUI
							Object[] options = {"Accept","Reject"};

							int num = JOptionPane.showOptionDialog(null,"Accept/Reject","Accept/Reject",
			                JOptionPane.YES_NO_CANCEL_OPTION,
			                JOptionPane.PLAIN_MESSAGE,null, options,null);	

			                System.out.println("the num is:" + num + " ========");

			                if(num == 0)
			                {
			                	talk.sendMessage("Accept");
		        				talk.sendMessage(buddyName);
			                }
			                else
			                {
			                	System.out.println("You will never have any friends!");
			                }
		           		}
		           		catch (IOException io)
				        {
				            System.out.println("TKL - Error ....");
				            io.printStackTrace();
				        }
					}	
				}); // end of Runnable

			}
		    else if(message.equals("Accept"))
	        {
	        	message = talk.receivedMessage();
	        	System.out.println(message);
	        	cFrame.addFriend(message);
	        }
	        else if(message.equals("Reject"))
	        {
	        	message = talk.receivedMessage();
	        	System.out.println(message);
	        }
	        else if(message.equals("Add friends"))
	        {
	        	message = talk.receivedMessage();
	        	cFrame.addFriend(message);
	        }
	        else if(message.equals("Sending First Buddy Message"))
	        {
	        	String buddyN = talk.receivedMessage();
	        	message = talk.receivedMessage();
	        	chat = new Chat(buddyN, this);
	        	chat.keepMessaging(message);
	        }
	        else if(message.equals("Keep Messaging"))
	        {
	        	//String buddyN = talk.receivedMessage();
	        	message = talk.receivedMessage();
	        	//chat = new Chat(buddyN, this);
	        	cFrame.chat.keepMessaging(message);
	        }
		}

	    }
        catch (IOException io)
        {
            System.out.println("TKL - Error ....");
            io.printStackTrace();
        }
	}
	
}