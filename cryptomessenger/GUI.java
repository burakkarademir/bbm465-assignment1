package cryptomessenger;

import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import java.awt.Panel;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;

public class GUI {
	
    public JFrame frame;
    private JButton sendButton;
    private JTextArea messageTextArea;
    private JTextArea encryptTextArea;
    private static JTextArea messagesBoard;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane_1;

    private JButton encryptButton;
    private JRadioButton aesRadioButton;
    private JRadioButton desRadioButton;
    private JRadioButton cbcRadioButton;
    private JRadioButton ofbRadioButton;
    private JButton disconnectButton;
    private Panel panel;
    private Panel panel_1;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private String username;
    private JLabel connectLabel;

    private int port = 8080;
    private Client client = null;
    private Crypter crypter = null;


    /**
     * Create the application.
     */
    public GUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Crypto Messenger");
        frame.setSize(689, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JLabel lblServer = new JLabel(" Server");
        lblServer.setBounds(0, 0, 582, 22);
        frame.getContentPane().add(lblServer);


        scrollPane = new JScrollPane();
        scrollPane.setBounds(12, 525, 218, 104);
        frame.getContentPane().add(scrollPane);

//message box for entering a message
        messageTextArea = new JTextArea();
        messageTextArea.getDocument().addDocumentListener(new DocumentListener() {
        	public void insertUpdate(DocumentEvent e) {

                if (messageTextArea.getText() == null || messageTextArea.getText().trim().isEmpty()) {
                	sendButton.setEnabled(false);
                	
                } else {
                	sendButton.setEnabled(false);
                } 

        	}
			@Override
			public void changedUpdate(DocumentEvent e) {
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				
			}	
        });
        scrollPane.setViewportView(messageTextArea);


        scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(242, 525, 218, 104);
        frame.getContentPane().add(scrollPane_1);

//encrypted message box
        encryptTextArea = new JTextArea();
        encryptTextArea.getDocument().addDocumentListener(new DocumentListener() {
        	public void insertUpdate(DocumentEvent e) {

                if (encryptTextArea.getText() == null || encryptTextArea.getText().trim().isEmpty()) {
                	sendButton.setEnabled(false);
                	
                } else {
                	sendButton.setEnabled(true);
                } 

        	}
			@Override
			public void changedUpdate(DocumentEvent e) {
				
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				
			}	
        });
        scrollPane_1.setViewportView(encryptTextArea);

//dialog box
        messagesBoard = new JTextArea();
        messagesBoard.setBounds(12, 125, 647, 357);
        frame.getContentPane().add(messagesBoard);
        messagesBoard.setColumns(10);
//connect button and action listener for connect button
        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String result = JOptionPane.showInputDialog("Enter user name:");
                if (result == null) {
                } else {
                    username = result;
                    try {
                        client = new Client(port);
                        connectLabel.setText("Connected:" + username);
                        connectButton.setEnabled(false);
                        disconnectButton.setEnabled(true);

                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null,"Connection error for customer");

                    }

                }
            }
        });
        connectButton.setBounds(38, 53, 97, 34);
        frame.getContentPane().add(connectButton);

//disconnect button and action listener for disconnect button
        disconnectButton = new JButton("Disconnect");
        disconnectButton.setEnabled(false);
        disconnectButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        	    Message message = new Message("Disconnect",false);
        	    client.send(message);
                connectButton.setEnabled(true);
                disconnectButton.setEnabled(false);
                username = null;
                connectLabel.setText("Not Connected");


        	}
        });
        disconnectButton.setBounds(167, 53, 105, 34);
        frame.getContentPane().add(disconnectButton);

        panel = new Panel();
        panel.setBounds(303, 28, 132, 70);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel label = new JLabel("Method");
        label.setBounds(12, 5, 42, 16);
        panel.add(label);//now add it
        
//aes and des jradiobuttons
        this.aesRadioButton = new JRadioButton("AES");
        this.aesRadioButton.setBounds(8, 24, 61, 25);
        panel.add(this.aesRadioButton);

        this.desRadioButton = new JRadioButton("DES");
        this.desRadioButton.setBounds(73, 24, 56, 25);
        panel.add(this.desRadioButton);

        ButtonGroup group = new ButtonGroup();
        group.add(this.aesRadioButton);
        group.add(this.desRadioButton);

        panel_1 = new Panel();
        panel_1.setBounds(456, 28, 140, 70);
        frame.getContentPane().add(panel_1);
        panel_1.setLayout(null);

        JLabel label2 = new JLabel("Mode");
        label2.setBounds(12, 5, 38, 16);
        panel_1.add(label2);

//cbc and ofb jradiobuttons
        this.cbcRadioButton = new JRadioButton("CBC");
        this.cbcRadioButton.setBounds(12, 25, 61, 25);
        panel_1.add(this.cbcRadioButton);

        this.ofbRadioButton = new JRadioButton("OFB");
        this.ofbRadioButton.setBounds(75, 25, 57, 25);
        panel_1.add(this.ofbRadioButton);

        ButtonGroup group2 = new ButtonGroup();
        group2.add(this.cbcRadioButton);
        group2.add(this.ofbRadioButton);

        lblNewLabel = new JLabel("Text");
        lblNewLabel.setBounds(12, 507, 56, 16);
        frame.getContentPane().add(lblNewLabel);

        lblNewLabel_1 = new JLabel("Crypted Message");
        lblNewLabel_1.setBounds(242, 507, 120, 16);
        frame.getContentPane().add(lblNewLabel_1);

//send button and action listener for send button
        sendButton = new JButton("Send");
    	sendButton.setEnabled(false);

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	try {
                Message message = new Message(encryptTextArea.getText(), username,crypter);
                client.send(message);
                messageTextArea.selectAll();
                messageTextArea.replaceSelection("");
                encryptTextArea.selectAll();
                encryptTextArea.replaceSelection("");
            	}
            	catch(Throwable e) {
                    JOptionPane.showMessageDialog(null,"Please connect to server");
            		
            	}
            }
        });
        sendButton.setBounds(568, 562, 79, 22);
        frame.getContentPane().add(sendButton);

//encrypt button and action listener for encrypt button
        encryptButton = new JButton("Encrypt");
        encryptButton.setBounds(480, 562, 79, 24);
        encryptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                boolean ret = encryptProcess();
            }
        });
        frame.getContentPane().add(encryptButton);

        connectLabel = new JLabel();
        if (username == null) {
            connectLabel.setText("Not Connected");

        }

        connectLabel.setBounds(0, 631, 671, 22);
        frame.getContentPane().add(connectLabel);
        
       

    }

    public static void messagePrinter(Message message) {
        Crypter crypter = message.getCrypter();
        String decryptedMessage = crypter.decyrpt();
        getMessagesBoard().append(message.getMessage() + "\n");
        getMessagesBoard().append(message.getUsername() + ">");
        getMessagesBoard().append(decryptedMessage + "\n");
    }

    private Boolean encryptProcess() {
        String message = null;
        Formatter formatter = new Formatter();
        if (messageTextArea.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Please write a message to encrypt");
            //TODO : Show alert for this error
            return false;
        } else {
            message = messageTextArea.getText().trim();
        }
        if (aesRadioButton.isSelected() || desRadioButton.isSelected()) {
            if (cbcRadioButton.isSelected() || ofbRadioButton.isSelected()) {
                if (aesRadioButton.isSelected()) {
//					Process for aes
                    if (cbcRadioButton.isSelected()) {
                        //Process for AES/CBS
                        Crypter crypter = new Crypter();
                        byte[] encodedMessage = crypter.encyrpt(message, "AES", "CBC");
                        encryptTextArea.setText(formatter.toHexString((encodedMessage)));
                        this.crypter = crypter;

                    } else {
//						Process for AES/OFB
                        Crypter crypter = new Crypter();
                        byte[] encodedMessage = crypter.encyrpt(message, "AES", "OFB");
                        encryptTextArea.setText(formatter.toHexString((encodedMessage)));
                        this.crypter = crypter;

                    }
                } else {
//					Process for DES
                    if (aesRadioButton.isSelected()) {
//						Process for DES/CBC
                        Crypter crypter = new Crypter();
                        byte[] encodedMessage = crypter.encyrpt(message, "DES", "CBC");
                        encryptTextArea.setText(formatter.toHexString((encodedMessage)));
                        this.crypter = crypter;

                    } else {
//						Process for DES/OFB
                        Crypter crypter = new Crypter();
                        byte[] encodedMessage = crypter.encyrpt(message, "DES", "OFB");
                        encryptTextArea.setText(formatter.toHexString((encodedMessage)));
                        this.crypter = crypter;

                    }
                }
            } else {
                //TODO : Create an alert window for that error
                JOptionPane.showMessageDialog(null,"Please select an alghorithm for process");
            }
        } else {
            //TODO : Create an alert window for that error
            JOptionPane.showMessageDialog(null,"Please select a method for process");
        }
        return false;
    }

    public static JTextArea getMessagesBoard() {
        return messagesBoard;
    }

}
