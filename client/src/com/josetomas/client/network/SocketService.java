/*
Develop by Tomas Najun
2013 - Argentina
*/

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
import java.net.SocketException;

public class SocketService extends Service {
    private static final String END_MESSAGE = "\nEND_MESSAGE";
    private static final String DISCONNECT = "DISCONNECT";
    private InetAddress inetAddress;
    private boolean isConnected;
    private PrintWriter toServer = null;
    private BufferedReader fromServer = null;


    // Binder given to clients
    private final IBinder myBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }


    public class LocalBinder extends Binder {
        SocketService getService() {
            // Return this instance of LocalService so clients can call public methods
            return SocketService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Check if the service was already started
        //if (this.isStarted) return START_STICKY;
//        Toast.makeText(this, "Entro en SocketService", Toast.LENGTH_LONG).show();
        super.onStartCommand(intent, flags, startId);
        Toast.makeText(this, "Service created", Toast.LENGTH_LONG).show();

//        intTCPConnection();

        return START_STICKY;
    }

    public void initStreamConnection(Socket clSocket) {
        try {

            this.fromServer = new BufferedReader(new InputStreamReader(clSocket.getInputStream()));
            this.toServer = new PrintWriter(clSocket.getOutputStream(), true);
        } catch (IOException e) {
            Log.e("TCP", "S: Error", e);
            //TODO
        }

        try {
            clSocket.setSendBufferSize(32768);
            Log.i("SOCKET", "SendBufferSize: " + clSocket.getSendBufferSize());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        isConnected = true;
        Log.i("Client Socket: ", "Yes");
    }

    public void intTCPConnection(String hostAddress) {
        ThreadNetworkInitializer connect2 = new ThreadNetworkInitializer(this);
        connect2.execute(hostAddress);

        Toast.makeText(this, "Connected to ip: " + hostAddress, Toast.LENGTH_LONG).show();
    }

    public InetAddress initUDPConnection() {
        UDPClientConnection searchNetworks = new UDPClientConnection(this);
        searchNetworks.execute();
        return inetAddress;
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
            Log.e("SocketService", "Era null");
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

    public void updateUI(InetAddress inetAddress) {

    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}

