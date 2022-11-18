import java.awt.*;           //contains classes for creating user interfaces
import javax.swing.*;        //needed for GUI components
import java.awt.event.*;     //used for action listener
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.event.*;

public class ClientsFrame extends JFrame
	implements ActionListener, EventListener, MouseListener
{
	JTextField         buddiesTF;

	JButton            logoutButton;
 	JButton            buddiesButton;
	
	JPanel             buttonPanel;
	Talker             talk;
	String             message;

	JList              buddiesList;
    DefaultListModel   buddiesListModel;
    JScrollPane        scrollPane;

    JPanel             pane;
    CTs                myCts;
    Chat               chat;


	public ClientsFrame(CTs cts, String username)
	{
		myCts = cts;
		logoutButton = new JButton("Logout");
		logoutButton.setActionCommand("Logout");
		logoutButton.addActionListener(this);

		buddiesButton = new JButton("Become buddies with someone?");
		buddiesButton.setActionCommand("Become Buddies");
		buddiesButton.addActionListener(this);

		pane = new JPanel();
		buddiesListModel = new DefaultListModel();
		buddiesList = new JList(buddiesListModel);
		buddiesList.addMouseListener(this);
		scrollPane = new JScrollPane(buddiesList);

		buttonPanel = new JPanel();
        buttonPanel.add(logoutButton);
        buttonPanel.add(buddiesButton);

        scrollPane = new JScrollPane(buddiesList);
        pane.add(scrollPane);

        add(buttonPanel,BorderLayout.SOUTH);
        add(pane,BorderLayout.CENTER);

        setUpMainFrame(username);
	}
    public void setUpMainFrame(String username)
    {
        setTitle(username);
        setLocation(250,400);
        setSize(400,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

 	public void actionPerformed(ActionEvent e)
    {
    	try
    	{
	        if(e.getActionCommand().equals("Logout"))
	        {
	        	System.out.println("logout button clicked");
	        	System.out.println("User logged off.");
	        	myCts.talk.sendMessage("Logout");
	        	setVisible(false);
	        	System.exit(0);
	        }

	        if(e.getActionCommand().equals("Become Buddies"))
	        {
	        	System.out.println("Buddies availabe.");
	        	String input = JOptionPane.showInputDialog("Enter username:");
	        	myCts.talk.sendMessage("Become Buddies");
	        	myCts.talk.sendMessage(input);
	        }

    	}
       catch(IOException io)
       {
            System.out.println("Error " + io.getMessage());
       }

    }
    public void addFriend(String buddyName)
    {
    	buddiesListModel.addElement(buddyName);
    }

    public void mouseClicked(MouseEvent e)
    {
    	if(e.getClickCount() == 2)
    	{
    	    System.out.println("User clicked on a buddy.");
    		int index = buddiesList.getSelectedIndex();
    		String findBuddy = buddiesListModel.get(index).toString();
    		System.out.println(findBuddy + " ==================================");
    		chat = new Chat(findBuddy, myCts);
    	}
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
}