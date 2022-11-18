import java.io.*;
import java.net.*;

public class Talker
{
	private BufferedReader    buffR;       // reading messages
	private DataOutputStream  outStream;   // writing messages
	private String            id;          // used for threads
	private String            myId;

	//###############################################################
	public Talker(Socket socket) throws IOException
	{
		outStream = new DataOutputStream (socket.getOutputStream());
        buffR = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	//###############################################################

	public Talker(String domain, int portNumber, String id) throws IOException
	{
		Socket echoSocket = new Socket(domain, portNumber);
		myId = id;
		outStream = new DataOutputStream(echoSocket.getOutputStream());
		buffR = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
	}
	//###############################################################
	public void sendMessage(String message) throws IOException
	{
		outStream.writeBytes(message + '\n');
		System.out.println("Sent: " + message);
	}
	//###############################################################
	public String receivedMessage() throws IOException
	{
		String message =  "";
		message = buffR.readLine();
		System.out.println("-------------------");
		System.out.println("Received: " + message);
		return message;
	}
	public void makeID(String id)
	{
		this.id = id;
	}

} // end of Talker class