import java.awt.*;           //contains classes for creating user interfaces
import javax.swing.*;        //needed for GUI components
import java.awt.event.*;     //used for action listener
import java.io.*;
import java.net.*;

public class TClient
{
    public static void main(String[] args)
    {
        System.out.println("Client is starting...");
        new MyFrame();
    }
}

class MyFrame extends JDialog
    implements ActionListener
{
    GroupLayout   layout;
    
    JPanel        textFieldPanel;

    JTextField    usernameTF;
    JLabel        usernameLabel;

    JTextField    passwordTF;
    JLabel        passwordLabel; 

    JButton       loginButton;
    JButton       registerButton;
    
    JPanel        idPanel;
    JPanel        buttonPanel;

    public MyFrame()
    {
        usernameTF = new JTextField(30);
        usernameLabel = new JLabel("Username: ");

        passwordTF = new JTextField(30);
        passwordLabel = new JLabel("Password: ");

        loginButton = new JButton("Login");
        loginButton.setActionCommand("Login");
        loginButton.addActionListener(this);

        registerButton = new JButton("Register");
        registerButton.setActionCommand("Register");
        registerButton.addActionListener(this);


        //Grouplayout
        textFieldPanel = new JPanel();
        layout = new GroupLayout(textFieldPanel);
        textFieldPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

//creating a sequential group for the horizontal axis
        GroupLayout.SequentialGroup horizontalG = layout.createSequentialGroup();

        horizontalG.addGroup(layout.createParallelGroup()
        .addComponent(usernameLabel).addComponent(passwordLabel));
        horizontalG.addGroup(layout.createParallelGroup()
        .addComponent(usernameTF).addComponent(passwordTF));
        layout.setHorizontalGroup(horizontalG);

//creating a sequential group for the vertical axis
        GroupLayout.SequentialGroup verticalG = layout.createSequentialGroup();

        verticalG.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(usernameLabel).addComponent(usernameTF));
        verticalG.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        .addComponent(passwordLabel).addComponent(passwordTF));
        layout.setVerticalGroup(verticalG);

        buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        add(textFieldPanel,BorderLayout.CENTER);
        add(buttonPanel,BorderLayout.SOUTH);


        setUpMainFrame();
    }

    //###############################################################
    public void setUpMainFrame()
    {
            setTitle("User page.");
            setLocation(250,400);
            setSize(400,400);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        try
        {
        CTs cts;
        String username = usernameTF.getText().trim();
        String password = passwordTF.getText().trim();
        String buttonClicked;

        if(e.getActionCommand().equals("Login"))
        {
            System.out.println("Logging in:");
            buttonClicked = "Login";

            cts = new CTs(buttonClicked, username, password);
            setVisible(false);
        }

        if(e.getActionCommand().equals("Register"))
        {
            System.out.println("Register new account:");
            buttonClicked = "Register";

            cts = new CTs(buttonClicked, username, password);
            setVisible(false);
        }
        }
        catch(IOException io)
        {
            System.out.println("CTS error");
        }
    }
}
