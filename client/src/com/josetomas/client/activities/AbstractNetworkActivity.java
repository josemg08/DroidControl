package com.josetomas.client.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.josetomas.client.network.SocketService;
import com.josetomas.client.xmlMessage.ParseXML;

public class AbstractNetworkActivity extends Activity {

    protected boolean mIsBound;
    protected SocketService socketService;
    protected ParseXML parseXML;

    public AbstractNetworkActivity() {
        parseXML = new ParseXML();
    }

    protected ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            socketService = ((SocketService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            socketService = null;
        }
    };

    protected void doBindService() {
        bindService(new Intent(this, SocketService.class), mConnection, Context.BIND_AUTO_CREATE);
        mIsBound = true;
        if (socketService != null) {
            socketService.isBoundable();
        }
    }

    protected void doUnbindService() {
        if (mIsBound) {
            // Detach our existing connection.
            unbindService(mConnection);
            mIsBound = false;
        }
    }

    @Override
    protected void onStop() {
        doUnbindService();
        super.onStop();
    }
}
