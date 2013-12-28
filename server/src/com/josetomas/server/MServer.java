/*
Develop by Jose Gonzalez & Tomas Najun
2013 - Argentina
*/

package com.josetomas.server;

import com.josetomas.server.my_iterator.IteratorXML;
import com.josetomas.server.xmlMessage.ParseXML;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MServer extends Thread {
    private static final String END_MESSAGE = "END_MESSAGE";
    private static final String DISCONNECT = "DISCONNECT";
    private static MServer ourInstance = new MServer(5555);
    private ParseXML parseXML;
    private IteratorXML iterator;
    private Robot robot;
    private ServerSocket serverSocket;
    private Socket clientSocket = null;
    private boolean isConnected;
    private BufferedReader fromClient;
    private PrintWriter toClient;

    //Constructor, receives the port i'll be listening in
    private MServer(int port) {
        isConnected = false;

        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(60000);

        } catch (IOException e) {
            //TODO
            System.out.println("Could not listen on port " + port);
            System.exit(-1);
        }

        try {
            robot = new Robot();

        } catch (AWTException e) {
            //TODO
            e.printStackTrace();
        }
        iterator = new IteratorXML(robot);
        parseXML = new ParseXML();
    }

    public static MServer getInstance() {
        return ourInstance;
    }

    @Override
    //This method keeps running over and over while the app is on execution
    public void run() {
        setConnection();
        if (clientSocket.isConnected()) {
            try {
                fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                toClient = new PrintWriter(clientSocket.getOutputStream(), true);
                receiveMessage();
//                if (isConnected) receiveMessage();  //isConnected is set true when the client sends the correct password. See PasswordValidation
//                else disconnect();
            } catch (IOException e) {
                //TODO
                e.printStackTrace();
            }
        }

    }

    //This method sets the connection with the client and saves it's address
    public void setConnection() {
        System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");

        try {
            clientSocket = serverSocket.accept();
            System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());
        } catch (SocketTimeoutException e) {
            //TODO
            System.out.println("Socket timed out!");
            System.exit(1);
        } catch (IOException e) {
            //TODO
            System.out.println("Accept failed!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void sendMessage(String message) {
        try {
            System.out.println(message);
            toClient.println(message + END_MESSAGE);
        } catch (Exception e) {
            //TODO
            e.printStackTrace();
        }
    }

    private void receiveMessage() {
        String line = null;
        String result = "";
        if (fromClient == null) return;

        try {
            line = fromClient.readLine();
            while (line != null) {
                System.out.println(line);
                if (line.equals(DISCONNECT)) {
                    System.out.println(line);
                    disconnect();
                }
                result += line + "\n";

                line = fromClient.readLine();
                if (line != null && line.endsWith(END_MESSAGE)) {
                    line = "";
                    System.out.println(result);
                    iterator.performAction(parseXML.parseMessage(result));
                    result = "";
                }
            }
        } catch (IOException e) {
            //TODO
            e.printStackTrace();
        }  catch (IllegalArgumentException e) {
            //TODO
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
    }

    //This method sends a message and terminates the socket connection
    public void disconnect() throws IOException {
        //todo mandar mensaje a la app que se desconecto
        fromClient.close();
        clientSocket.close();
        serverSocket.close();
        System.out.println("Disconnecting");
        System.exit(0);
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
