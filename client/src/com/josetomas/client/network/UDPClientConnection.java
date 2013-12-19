package com.josetomas.client.network;


import android.os.AsyncTask;
import android.util.Log;
import com.josetomas.client.activities.MClient;

import java.io.IOException;
import java.net.*;
import java.nio.channels.IllegalBlockingModeException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPClientConnection extends AsyncTask<String, Void, UdpResult>{
    private static final String CONNECTION = "CONNECTION";
    private static final String NETWORK_ERROR = "Network Error: ";
    private static final String REQUEST_MESSAGE = "DISCOVER_FUIFSERVER_REQUEST";
    private static final String RESPONSE_MESSAGE = "DISCOVER_FUIFSERVER_RESPONSE";
    private NetworkException error = null;
    private SocketService service;

    public UDPClientConnection(SocketService service) {
        this.service = service;
    }

    private InetAddress broadCast() {

        DatagramSocket c = null;
        try {
            //Open a random port to send the package
            c = new DatagramSocket();
            c.setBroadcast(true);

            byte[] sendData = REQUEST_MESSAGE.getBytes();

            //Try the 255.255.255.255 first
            try {
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
                c.send(sendPacket);
                Log.i(getClass().getName(), ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
            } catch (SocketException e) {
                error = new NetworkException(e.getMessage(), e);
            } catch (IOException e) {
                error = new NetworkException(e.getMessage(), e);
            } catch (IllegalBlockingModeException e) {
                error = new NetworkException(e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                error = new NetworkException(e.getMessage(), e);
            }

            // Broadcast the message over all the network interfaces
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface networkInterface = interfaces.nextElement();

                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue; // Don't want to broadcast to the loopback interface
                }

                for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
                    InetAddress broadcast = interfaceAddress.getBroadcast();
                    if (broadcast == null) {
                        continue;
                    }

                    // Send the broadcast package!
                    try {
                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
                        c.send(sendPacket);
                    } catch (SocketException e) {
                        error = new NetworkException(e.getMessage(), e);
                    } catch (IOException e) {
                        error = new NetworkException(e.getMessage(), e);
                    } catch (IllegalBlockingModeException e) {
                        error = new NetworkException(e.getMessage(), e);
                    } catch (IllegalArgumentException e) {
                        error = new NetworkException(e.getMessage(), e);
                    }

                    Log.i(getClass().getName(), ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
                }
            }

            Log.i(getClass().getName(), ">>> Done looping over all network interfaces. Now waiting for a reply!");

            //Wait for a response
            byte[] recvBuf = new byte[15000];
            DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
            c.receive(receivePacket);

            //We have a response
            Log.i(getClass().getName(), ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());

            //Check if the message is correct
            String message = new String(receivePacket.getData()).trim();
            if (message.equals(RESPONSE_MESSAGE)) {
                Log.i("IP Server:", (receivePacket.getAddress().getHostName()).trim());
                return receivePacket.getAddress();
            }

            //Close the port!
            c.close();
        } catch (IOException ex) {
            Logger.getLogger(UDPClientConnection.class.getName()).log(Level.SEVERE, null, ex);
            error = new NetworkException(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    protected UdpResult doInBackground(String... strings) {
        InetAddress result = broadCast();
        return new UdpResult(result, error);
    }

    @Override
    protected void onPostExecute(UdpResult result) {
        if (result.hasError()) {
            Log.e(CONNECTION, NETWORK_ERROR + result.getError().getMessage());
        } else {
            service.updateUI(result.getInetAddress());


            MClient mClient = (MClient) MClient.getMyContext();
            mClient.setInetaddress(result.getInetAddress());
//            Log.i(CONNECTION, SUCCESSFUL_CONN);
        }
    }
}
