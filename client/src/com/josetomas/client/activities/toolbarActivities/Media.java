package com.josetomas.client.activities.toolbarActivities;

import android.os.Bundle;
import android.view.View;
import com.josetomas.client.R;
import com.josetomas.client.xmlMessage.keyboardMessage.ShortCutMessage;

public class Media extends ToolbarActivities {

    public static final String PLAY = "PLAY";
    public static final String FORWARD = "FORWARD";
    public static final String BACK = "BACK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(pref, 0);
        if(preferences.getString("colorApp", "Black").equals("Black")){
            setContentView(R.layout.black_media);
        }
        else{
            setContentView(R.layout.red_media);
        }

        doBindService();
    }

    public void mediaPlayPause(View view) {
        ShortCutMessage shortCutSms = new ShortCutMessage(PLAY);
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

    public void mediaForward(View view){
        ShortCutMessage shortCutSms = new ShortCutMessage(FORWARD);
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

    public void mediaBack(View view){
        ShortCutMessage shortCutSms = new ShortCutMessage(BACK);
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

    private void fullScreen(View view) {
        ShortCutMessage shortCutSms = new ShortCutMessage("FULLSCREEN");
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

}
