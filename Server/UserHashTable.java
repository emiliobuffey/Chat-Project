import java.io.*;
import java.net.*;
import java.util.*;

public class UserHashTable extends Hashtable<String, User>
{
	public UserHashTable()
	{
		System.out.println("Empty UserHashTable");
	}

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public UserHashTable(DataInputStream dis) throws IOException
	{
		//first read number of elements

		int elements = dis.readInt();
		//adding users to hashtable
		for(int i = 0; i < elements; i++)
		{
			User tmpUser = new User(dis);
			put(tmpUser.username, tmpUser);
		}
	}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public void storeNewUser(User user) // store new user
	{
		System.out.println("5");
		put(user.id, user);
	}
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// store new user in hashtable
	public void storeUsers(DataOutputStream dos) throws IOException
	{
		Enumeration<User>        myEnum;  
		myEnum = elements();
		dos.writeInt(size());

		while(myEnum.hasMoreElements())
		{
			myEnum.nextElement().store(dos);
		}
	}

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public User getUserById(String id)
	{
		User user; 
		user = get(id);
		return(user);
	}
}