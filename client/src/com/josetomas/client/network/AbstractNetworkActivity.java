/*
Develop by Jose Gonzalez & Tomas Najun
2013 - Argentina
*/

package com.josetomas.client.network;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.josetomas.client.xmlMessage.ParseXML;

public class AbstractNetworkActivity extends Activity {

    protected boolean mIsBound;
    protected SocketService socketService;
    protected ParseXML parseXML;

    public AbstractNetworkActivity() {
        parseXML = new ParseXML();
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    protected ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            // We've bound to SocketService, cast the IBinder and get SocketService instance
            socketService = ((SocketService.LocalBinder) service).getService();
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            socketService = null;
        }
    };

    protected void doBindService() {
        bindService(new Intent(AbstractNetworkActivity.this, SocketService.class), mConnection, Context.BIND_AUTO_CREATE);

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
