import java.io.*;
import java.net.*;
import java.util.*;


public class User
{
	String 	         username;
	String           password;
	String           id;
	UserHashTable    hashT;
	CTc              userctc;
	Vector<String>  buddyList;


	User(String username, String password, CTc ctc) //create
	{
		buddyList = new Vector();
		this.username = username;
		this.password = password;
		this.id = username;
		userctc = ctc;
	}	

	User(DataInputStream dataIn) throws IOException //load
	{
		buddyList = new Vector();
		this.username = dataIn.readLine();
		this.password = dataIn.readLine();
		this.id = this.username; 
		int size = dataIn.readInt();

		for(int k = 0;k < size;k++)
		{
			buddyList.add(dataIn.readLine());
		}
	}

	public void store(DataOutputStream dataOut) throws IOException  // store
	{
		dataOut.writeBytes(this.username + '\n');
		dataOut.writeBytes(this.password + '\n');

		dataOut.writeInt(buddyList.size());
		for(int k = 0;k < buddyList.size();k++)
		{
			String myBuddy;
			myBuddy = buddyList.get(k);
			dataOut.writeBytes(myBuddy + "\n");
		}
	}
}
