package com.josetomas.client.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.josetomas.client.R;
import com.josetomas.client.activities.toolbarActivities.SkinSelector;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_conect);

        ipInput = (EditText) findViewById(R.id.ipInput);
        serverButton = (Button) findViewById(R.id.serverButton);
        serverButton.setClickable(false);
        //disconnectButton = (Button) findViewById(R.id.buttonDisconnect);

        Intent i = new Intent(MClient.this, SocketService.class);
        startService(i);
        doBindService();
    }

    public void toSkinSelector(View view) {
        //todo take out, when connected just jump to trackpad
        Intent intent = new Intent(this, SkinSelector.class);
        startActivity(intent);
    }

    public void connect(View view) {
        if(serverButton.isPressed()) {
            socketService.intTCPConnection(serverAddress.getHostAddress());
        } else {
            String ip = ipInput.getText().toString();
            socketService.intTCPConnection(ip);
        }
        //goToValidatePass();
    }

    public void searchServers(View view) {
        serverAddress = socketService.initUDPConnection();
        serverButton.setText(serverAddress.getHostName());
        serverButton.setClickable(true);
    }



    public void disconnect(View view) {
        if (socketService.disconnect()) {

            stopService(new Intent(this, SocketService.class));
            Toast.makeText(this, "Disconnecting...", Toast.LENGTH_LONG).show();

        } else Toast.makeText(this, " No Disconnecting...", Toast.LENGTH_LONG).show();
    }

//    public void getNetworkComputers(View view) {
//        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
//        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//
//        int ipAddress = wifiInfo.getIpAddress();
//
//        String ip = null;
//
//        ip = String.format("%d.%d.%d.%d",
//                (ipAddress & 0xff),
//                (ipAddress >> 8 & 0xff),
//                (ipAddress >> 16 & 0xff),
//                (ipAddress >> 24 & 0xff));
//        Toast.makeText(this, "IP: " + ip, Toast.LENGTH_LONG).show();
//    }

//    private void goToValidatePass() {
//        Intent intent = new Intent(this, ValidatePassword.class);
//        startActivity(intent);
//    }
@Override
public void onBackPressed() {
    AlertDialog.Builder exitAlert = new AlertDialog.Builder(this);
    exitAlert.setIcon(android.R.drawable.ic_dialog_alert);
    exitAlert.setTitle(R.string.titleExitDialog);
    exitAlert.setMessage(R.string.MessageExit);
    exitAlert.setPositiveButton(R.string.yesButton, new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            socketService.disconnect();
            stopService(new Intent(MClient.this, SocketService.class));
            finish();
        }

    }).setNegativeButton(R.string.noButton, null).show();
}
}
