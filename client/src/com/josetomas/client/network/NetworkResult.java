/*
Develop by Tomas Najun
2013 - Argentina
*/

package com.josetomas.client.network;

import java.net.Socket;

public class NetworkResult {
    private Socket clientSocket;

    private NetworkException error;

    public NetworkResult(Socket clientSocket, NetworkException error) {
        this.clientSocket = clientSocket;
        this.error = error;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public Throwable getError() {
        return error;
    }

    public boolean hasError() {
        return getError() != null;
    }

}
