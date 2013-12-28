/*
Develop by Jose Gonzalez & Tomas Najun
2013 - Argentina
*/

package com.josetomas.client.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.josetomas.client.R;
import com.josetomas.client.activities.toolbarActivities.SkinSelector;
import com.josetomas.client.network.AbstractNetworkActivity;
import com.josetomas.client.network.SocketService;

import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.Socket;

public class MClient extends AbstractNetworkActivity {
    private boolean isConnected;
    private Socket clientSocket;
    private ObjectInputStream dataFromServer;
    private Button serverButton, disconnectButton;
    private TextView notificationArea;
    private EditText ipInput;
    private InetAddress serverAddress;
    private ProgressBar connectProgressBar;

    private static Context myContext;
    private InetAddress inetAddress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_conect);
        myContext = this;

        ipInput = (EditText) findViewById(R.id.ipInput);
        serverButton = (Button) findViewById(R.id.serverButton);
        serverButton.setClickable(false);
        connectProgressBar = (ProgressBar) findViewById(R.id.progressconnectionBar);
        connectProgressBar.setEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Bind to SocketService
        startSocketService();
    }

    private void startSocketService() {
//        Intent i = new Intent(MClient.this, SocketService.class);
//        startService(i);
//        Toast.makeText(this, "Entro en MClient | startSocketService", Toast.LENGTH_LONG).show();
        doBindService();
    }

    public void connect(View view) {
        connectProgressBar.setEnabled(true);
        connectProgressBar.setVisibility(View.VISIBLE);

        if (serverButton.isPressed()) {
            socketService.intTCPConnection(inetAddress.getHostAddress());
        } else {
            String ip = ipInput.getText().toString();
            socketService.intTCPConnection(ip);
        }

        //goToValidatePass();
//        Intent intent = new Intent(this, MousePad.class);
//        startActivity(intent); Todo
    }


    public void searchServers(View view) {
        socketService.initUDPConnection();
    }

    public void disconnect(View view) {
        if (socketService.disconnect()) {

            stopService(new Intent(this, SocketService.class));
            Toast.makeText(this, "Disconnecting...", Toast.LENGTH_LONG).show();

        } else Toast.makeText(this, " No Disconnecting...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder exitAlert = new AlertDialog.Builder(this);
        exitAlert.setIcon(android.R.drawable.ic_dialog_alert);
        exitAlert.setTitle(R.string.titleExitDialog);
        exitAlert.setMessage(R.string.MessageExit);
        exitAlert.setPositiveButton(R.string.yesButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (socketService != null) {
                    socketService.disconnect();
                    stopService(new Intent(MClient.this, SocketService.class));
                }
                finish();
            }

        }).setNegativeButton(R.string.noButton, null).show();
    }

    public static Context getMyContext() {
        return myContext;
    }

    public void updateNotificationArea() {
        connectProgressBar.setEnabled(false);
        connectProgressBar.setVisibility(View.INVISIBLE);
//        goToValidatePass();
        toSkinSelector();
    }

    private void goToValidatePass() {
        Intent intent = new Intent(this, ValidatePassword.class);
        startActivity(intent);
    }

    public void setInetaddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
        serverButton.setText(inetAddress.getHostName());
        serverButton.setFocusable(true);
        serverButton.setClickable(true);
    }

    public void toSkinSelector() {
        Intent intent = new Intent(this, SkinSelector.class);
        startActivity(intent);
    }
}
