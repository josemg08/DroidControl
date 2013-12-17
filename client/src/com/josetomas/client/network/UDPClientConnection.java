package com.josetomas.client.network;


import android.util.Log;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UDPClientConnection {
    private static final String REQUEST_MESSAGE = "DISCOVER_FUIFSERVER_REQUEST";
    private static final String RESPONSE_MESSAGE = "DISCOVER_FUIFSERVER_RESPONSE";
    DatagramSocket c;
    public InetAddress broadCast() {
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
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
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
                    } catch (Exception e) {
                        Log.e("Error sending", e.getMessage());
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
        }
        return null;
    }
}
