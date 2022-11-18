import java.awt.*;           //contains classes for creating user interfaces
import javax.swing.*;        //needed for GUI components
import java.awt.event.*;     //used for action listener
import java.io.*;
import java.net.*;
import javax.swing.text.*;

public class Chat extends JDialog
	implements ActionListener
{
	boolean       first = true;

	GroupLayout   layout;


	JEditorPane  editTextPane;
	JScrollPane  scroll;

	JTextField    messagesTF;

	JButton       sendButton;

	JPanel        buttonPanel;
	JPanel        textPanel;
	CTs           buddyCts;
	String        currentBuddy;

	Chat(String buddyName, CTs cts)
	{

		editTextPane = new JEditorPane();
		editTextPane.setContentType("text/html");
		editTextPane.setEditable(false);

		scroll = new JScrollPane(editTextPane);

		currentBuddy = buddyName;
		buddyCts = cts;
		messagesTF = new JTextField(50);

		sendButton = new JButton("Send");
		sendButton.setActionCommand("Send");
		sendButton.addActionListener(this);

		buttonPanel = new JPanel();
		buttonPanel.add(sendButton);

		textPanel = new JPanel();
		textPanel.add(messagesTF);

		textPanel = new JPanel();
        layout = new GroupLayout(textPanel);
        textPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        //creating a sequential group for the horizontal axis
        GroupLayout.SequentialGroup horizontalG = layout.createSequentialGroup();

        horizontalG.addGroup(layout.createParallelGroup()
        .addComponent(sendButton));
        horizontalG.addGroup(layout.createParallelGroup()
        .addComponent(messagesTF));
        layout.setHorizontalGroup(horizontalG);

//creating a sequential group for the vertical axis
        GroupLayout.SequentialGroup verticalG = layout.createSequentialGroup();

        verticalG.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(sendButton).addComponent(messagesTF));
        layout.setVerticalGroup(verticalG);


		add(scroll,BorderLayout.CENTER);
		add(textPanel,BorderLayout.SOUTH);

		setUpChat(buddyName);	
	}
    public void setUpChat(String buddyName)
    {
        setTitle("Buddy " + buddyName);
        setLocation(250,400);
        setSize(400,400);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("Send"))
		{
			String message;
			message = messagesTF.getText();
			messagesTF.setText("");

			try
			{	
				if(first == true)
				{
					first = false;
					buddyCts.talk.sendMessage("Sending First Buddy Message");
					buddyCts.talk.sendMessage(currentBuddy);
					buddyCts.talk.sendMessage(message);
				}
				else
				{
					first = false;
					buddyCts.talk.sendMessage("Keep Messaging");
					buddyCts.talk.sendMessage(currentBuddy);
					buddyCts.talk.sendMessage(message);
				}
			}
			catch(IOException io)
			{
				System.out.println("Error with chat");
				io.printStackTrace();
			}
		}
	}

	public void keepMessaging(String message) 
	{
		// try
		// {
		// 	StyledDocument document = (StyledDocument) editTextPane.getDocument();
  //   		document.insertString(document.getLength(), message, null);
		// }
  //   	catch(BadLocationException bl)
  //   	{
  //   		System.out.println("Bad ness");
  //   	}	

		editTextPane.setText(message);	
		System.out.println(message);
		first = false;
	}
}
