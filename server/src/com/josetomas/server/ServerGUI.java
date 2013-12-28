/*
Develop by Jose Gonzalez & Tomas Najun
2013 - Argentina
*/

package com.josetomas.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Locale;

//If you are going to add a text to the GUI add it on the resource Bundle
//then get it with Properties.getValue() method
public class ServerGUI extends JApplet {
    private JTextField textField;
    private Color appColor = Color.decode("#200A5A");
    private JFrame mainFrame;
    private Locale locale;

    public ServerGUI() throws HeadlessException {
        //initialize locale
        locale = Locale.getDefault();

        //initialize frame
        mainFrame = new JFrame(Properties.displayValue(locale, "titleID"));
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initGUI();
    }

    private void initGUI() {
        //Add ip to the textField
        textField = showIP();

        //Initialize panel for text and list's
        JPanel panel = new JPanel(new BorderLayout());
        panel.setSize(400, 300);
        panel.setBackground(appColor);

        JLabel label = new JLabel(Properties.displayValue(locale, "projDirecID"), JLabel.RIGHT);
        panel.add(label);
        panel.add(textField);

        //Creates toolbar
        JToolBar toolbar = createToolbar();

        //Add everything to the main frame
        mainFrame.add(toolbar, BorderLayout.NORTH);
        mainFrame.add(panel, BorderLayout.WEST);
        mainFrame.getContentPane().setBackground(appColor);
        mainFrame.setSize(400, 400);
        mainFrame.setVisible(true);
    }

    //Return toolbar with all the specifications for this app
    private JToolBar createToolbar(){
        //initialize buttons
        ImageIcon connectButtonImage = new ImageIcon(getClass().getResource
                ("drawable\\button.png"));
        final JButton newConnectButton = createAppButton(Properties.displayValue(locale, "connectID"), connectButtonImage);

        ImageIcon disconnectButtonImage = new ImageIcon(getClass().getResource
                ("drawable\\button.png"));
        final JButton disconnectButton = createAppButton(Properties.displayValue(locale, "disconnectID"), disconnectButtonImage);
        disconnectButton.setEnabled(false);

        //Connect button action
        newConnectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                newConnectButton.setEnabled(false);
                disconnectButton.setEnabled(true);
                int port = 5555;

                //Me parece que no esta bien hecho esto

                Thread tcpThread = MServer.getInstance();
                tcpThread.start();
                UDPServerConnection connection = new UDPServerConnection();
                Thread udpThread = new Thread(connection);
                udpThread.start();
                try {
                    udpThread.join(3000);
                    udpThread.interrupt();
                } catch (InterruptedException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

            }
        });

        //Exit button
        ImageIcon exitIcon = new ImageIcon(getClass().getResource
                ("drawable\\button.png"));
        JButton exitButton = createAppButton("Exit", exitIcon);

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        //Disconnect button action
        disconnectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                disconnectButton.setEnabled(false);
                newConnectButton.setEnabled(true);
                try {
                    MServer.getInstance().disconnect();
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(mainFrame, Properties.displayValue(locale, "errDisconnID"),
                            Properties.displayValue(locale, "errorID"), JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        //initialize toolbar and add buttons
        JToolBar toolbar = new JToolBar();
        toolbar.add(newConnectButton);
        toolbar.add(disconnectButton);
        toolbar.add(exitButton);
        toolbar.setBackground(appColor);
        toolbar.setBorderPainted(false);

        return toolbar;
    }

    //Returns a button with all the properties we want for an uniform design
    private JButton createAppButton(String message, ImageIcon image){
        JButton button = new JButton(message, image);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setForeground(appColor);
        return button;
    }

    //Get's the ip address and shows it on the JTextField
    private JTextField showIP(){
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            return new JTextField(Properties.displayValue(locale, "mssgIPID")+ip.getHostAddress(), 100);
        } catch (UnknownHostException e) {
            //TODO AGREGAR PARA OTRO IDIOMA
            return new JTextField(Properties.displayValue(locale, "errIPID"), 100);
        }
    }
    
}