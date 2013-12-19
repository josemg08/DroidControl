package com.josetomas.client.network;

import android.os.AsyncTask;
import android.util.Log;
import com.josetomas.client.activities.MClient;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ThreadNetworkInitializer extends AsyncTask<String,Void, NetworkResult> {

    private static final int PORT = 5555;
    private static final String CONNECTION = "CONNECTION";
    private static final String NETWORK_ERROR = "Network Error: ";
    private static final String SUCCESSFUL_CONN = "Connection Successful";
    private SocketService service;


    public ThreadNetworkInitializer(SocketService service) {
        this.service = service;
    }

    protected NetworkResult doInBackground(String... ips) {
        Socket clientSocket = null;
        NetworkException error = null;

        try {
            clientSocket = new Socket(ips[0], PORT);
            Log.i("Connection", "is connected");
            clientSocket.setSendBufferSize(64);

        } catch (UnknownHostException e) {
            error = new NetworkException(e.getMessage(), e);

        } catch (IOException e) {
            error = new NetworkException(e.getMessage(), e);

        } catch (Throwable t) {
            error = new NetworkException(t.getMessage(), t);
        }

        return new NetworkResult(clientSocket, error);

    }

    protected void onPostExecute(NetworkResult result) {

        if (result.hasError()) {
            Log.e(CONNECTION, NETWORK_ERROR + result.getError().getMessage());
            service.setConnected(false);
        } else {
            service.initStreamConnection(result.getClientSocket());
            service.setConnected(true);
            Log.i(CONNECTION, SUCCESSFUL_CONN);
        }
        MClient mClient = (MClient) MClient.getMyContext();
        mClient.updateNotificationArea();
    }
}
