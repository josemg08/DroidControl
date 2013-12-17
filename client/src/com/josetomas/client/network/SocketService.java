package com.josetomas.client.network;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SocketService extends Service {
    private static final String END_MESSAGE = "\nEND_MESSAGE";
    private static final String DISCONNECT = "DISCONNECT";
    public static final String TAG_IP = "ip";
    public static String SERVERIP;
    public static final int SERVERPORT = 5555;
    private boolean isConnected;
    private PrintWriter toServer = null;
    private BufferedReader fromServer = null;
    private Socket clientSocket;
    private boolean isStarted = false;

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    private final IBinder myBinder = new LocalBinder();

    public class LocalBinder extends Binder {

        public SocketService getService() {
            return SocketService.this;
        }
    }

    public void initStreamConnection(Socket clSocket) {
        try {
            this.fromServer = new BufferedReader(new InputStreamReader(clSocket.getInputStream()));
            this.toServer = new PrintWriter(clSocket.getOutputStream(), true);
        }  catch (IOException e) {
            Log.e("TCP", "S: Error", e);
            //TODO
        }
        this.clientSocket = clSocket;
        isConnected = true;
        Log.i("Client Socket: ", "Yes");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Check if the service was already started
        if (this.isStarted) return START_STICKY;

        super.onStartCommand(intent, flags, startId);
        Toast.makeText(this, "Service created", Toast.LENGTH_LONG).show();
        isStarted = true;
//        intTCPConnection();

        return START_STICKY;
    }

    public void intTCPConnection(String hostAddress) {
        ThreadNetworkInitializer connect2 = new ThreadNetworkInitializer(this);
        connect2.execute(hostAddress);
        Toast.makeText(this, "Connected to ip: " + hostAddress, Toast.LENGTH_LONG).show();
    }

    public InetAddress initUDPConnection() {
        InetAddress serverAddress;
        UDPClientConnection udpConnection = new UDPClientConnection();
        serverAddress = udpConnection.broadCast();
        if(serverAddress != null) {
            return serverAddress;
        } else return null;
    }

//    public void setClientSocket(Socket clientSocket) {
//        if (clientSocket != null){
//            this.clientSocket = clientSocket;
//            Log.i("SocketService","gooool");
//        }
//    }

    public void isBoundable() {
        Toast.makeText(this, "I bind like butter", Toast.LENGTH_LONG).show();
    }

    public boolean disconnect() {
        if (isConnected) {
            sendMessage(DISCONNECT);
            return true;
        } else return false;
    }

    public void sendMessage(String message) {
        if (toServer != null) {    //Hacer catch de null pointer
            try {
                if (!message.equals(DISCONNECT)) {
                    toServer.println(message + END_MESSAGE);
                    Log.i("Message", message);
                } else {
                    Log.i("Message", message);
                    toServer.println(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                //TODO
            }
        } else {
            Log.e("Comunicacion", "Era null");
            //Toast.makeText(this,"Problem with te connection", Toast.LENGTH_LONG).show();
        }
    }

    public String receiveMessage() {
        String line = null;
        String result = "";
        try {
            line = fromServer.readLine();
            while (line != null) {
                result += line + "\n";

                line = fromServer.readLine();
                if (line != null && line.endsWith(END_MESSAGE)) {
                    line = "";
                    Log.e("Result =\n", result);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            //TODO
        }
        return result;
    }
}

