//connection to the client 

import java.io.*;
import java.net.*;
import java.lang.*;

public class CTc
	implements Runnable
{
	Talker  talk;
	TServer server;
	Thread  thread;
	boolean chat = true;
	Socket  normalSocket;
	String  message = "";
	String  username, pass, id;
	String  bName = "";
	User    buddy;

	public CTc(Socket normalSocket, TServer tserver) throws IOException
	{
		server = tserver;
		talk = new Talker(normalSocket);
		thread = new Thread(this);
		thread.start();
	}

	public void handleBuddyRequest(String buddyName) throws IOException
	{
		// splits orginal command
		// find buddy username in hashTable
		// if not found "Error message"
		// if found instance of User is retrieved 

		buddy = server.findUserById(buddyName);

		if(buddy == null)
		{
			talk.sendMessage("Sorry buddy not found! ");
		}
		else
		{	
			if(buddy.userctc != null)
			{
				buddy.userctc.talk.sendMessage("Become Buddies");   // try to send budddy message to corresponding input name
				buddy.userctc.talk.sendMessage(username);
			}
			else
			{
				System.out.println("CTC is null!");
			}
		}
	}

	public void run()
	{
		//talker needs to communicate with client to see if they want to 
		//login or register account
		User   user;

		try
		{
		//sending welcome message to user
		talk.sendMessage("Welcome new user:");

		//sends back login or register
		message = talk.receivedMessage();

		if(message.equals("Login"))
		{
			username = talk.receivedMessage();
			
			pass = talk.receivedMessage();

			user = server.findUserById(username);
			if(user != null)
			{
				if(user.password.equals(pass))
				{				 
					talk.sendMessage("Logged in");
					talk.myId = username;
					user.userctc = this;

					int size = user.buddyList.size();
					for(int k = 0;k < size;k++)
					{
						talk.sendMessage("Add friends");
						String myBuddy;
						myBuddy = user.buddyList.get(k);
						talk.sendMessage(myBuddy);
					}
					server.saveUsers();
				}
			}
			else
			{
				talk.sendMessage("Sorry no user found.");
			}
		}
		else if (message.equals("Register"))
		{
			username = talk.receivedMessage();
			user = server.findUserById(username);

			talk.myId = username;
			pass = talk.receivedMessage();
			//if server accapts account
			user = new User(username, pass, this);
			//store new user
			server.storeMyUser(user);
			System.out.println("User is created and storeddddd in hashtable");
			server.saveUsers();
			System.out.println("User is saved to file!!");

		// make sure username isnt in the list
		//	if(username == currentUsers)
		//	{
				//System.out.println("Sorry that username already exist try again.");
				//talk.sendMessage("Enter Username:");
				//username = talk.receivedMessage();
		//	}
			//else
			//{
				//create new user
			//}

		}
			//asynchronous messages
			while(chat == true)
			{
				message = talk.receivedMessage();
				if(message.equals("Become Buddies"))
				{
					// send list of buddies
					// if user doesnt exist reject 
					// if user isnt logged in (null), then user not online
					// else send message "please be my buddy" also have from ID attached to message
					bName = talk.receivedMessage();
        			System.out.println("buddy request " + bName);
        			handleBuddyRequest(bName);
				}
				else if (message.equals("Accept"))
		        {
		        	message = talk.receivedMessage();
		        	System.out.println("accept was caught");
		        	buddy = server.findUserById(message);
					if(buddy == null)
					{
						talk.sendMessage("Sorry buddy not found! ");
					}
					else
					{
			        	buddy.userctc.talk.sendMessage("Accept");
			        	buddy.userctc.talk.sendMessage(username);
			        	talk.sendMessage("Accept");
						talk.sendMessage(message);
						user = server.findUserById(username);
						user.buddyList.add(message);
						buddy.userctc.talk.sendMessage(username);
						buddy.buddyList.add(username);
						server.saveUsers();
					}
		        }
		        else if(message.equals("Reject"))
		        {
		        	System.out.println("reject was caught");
		        	talk.sendMessage("Reject");
		        }
		        else if (message.equals("Logout"))
		        {
		        	chat = false;
		        	System.out.println("User: " + username + " is no longer with us.");
					talk.sendMessage("Logout");
					thread.stop();
		        }
		        else if (message.equals("Sending First Buddy Message"))
		        {
		        	String buddyName = talk.receivedMessage();
		        	message = talk.receivedMessage();
		        	user = server.findUserById(buddyName);
		        	user.userctc.talk.sendMessage("Sending First Buddy Message");
		        	user.userctc.talk.sendMessage(username);
		        	user.userctc.talk.sendMessage(message);
		        }
		        else if(message.equals("Keep Messaging"))
		        {
		        	String buddyName = talk.receivedMessage();
		        	message = talk.receivedMessage();
		        	user = server.findUserById(buddyName);
		        	user.userctc.talk.sendMessage("Keep Messaging");
		        	user.userctc.talk.sendMessage(message);	
		        }
		    }
		}
		catch (IOException e)
        {
            System.out.println("TKL - Error ....");
            e.printStackTrace();
        }
	}
}