package com.josetomas.client.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ThreadNetworkInitializer extends AsyncTask<String, Void, NetworkResult> {

    private static final int PORT = 5555;
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

        String msg = result.hasError() ? "network Error: " + result.getError().getMessage() : "Connection Successful";
        Log.i("   ENTRO", "entro");
        //MClient mClient = context.get();
        //mClient.setClientSocket(result.getClientSocket());
        //mClient.updateNotificationArea(msg);
        service.initStreamConnection(result.getClientSocket());
    }
}
