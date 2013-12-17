package com.josetomas.client.activities.toolbarActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.josetomas.client.R;
import com.josetomas.client.xmlMessage.keyboardMessage.ShortCutMessage;

public class Media extends ToolbarActivities {
    public static final String PLAY = "PLAY";
    public static final String FORWARD = "FORWARD";
    public static final String BACK = "BACK";
    private boolean play = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(pref, 0);
        String color = preferences.getString("colorApp", "Black");

        if(color.equals("Black")){
            setContentView(R.layout.black_media);
        }
        else if(color.equals("Green")){
            setContentView(R.layout.green_media);
        }
        else{
            setContentView(R.layout.red_media);
        }

        doBindService();
    }

    public void mediaPlayPause(View view) {
        if(!play){
            ShortCutMessage shortCutSms = new ShortCutMessage(PLAY);
            socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
            view.setBackgroundResource(R.drawable.black_pause_selector);

            if(preferences.getString("colorApp", "Black").equals("Black")){
                view.setBackgroundResource(R.drawable.black_pause_selector);
            }
            else{
                view.setBackgroundResource(R.drawable.red_pause_selector);
            }

            play = true;
        }
        else{
            ShortCutMessage shortCutSms = new ShortCutMessage(PLAY);
            socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));

            if(preferences.getString("colorApp", "Black").equals("Black")){
                view.setBackgroundResource(R.drawable.black_arrow_rigth_square_selector);
            }
            else{
                view.setBackgroundResource(R.drawable.red_arrow_rigth_square_selector);
            }

            play = false;
        }
    }

    public void mediaForward(View view){
        ShortCutMessage shortCutSms = new ShortCutMessage(FORWARD);
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

    public void mediaBack(View view){
        ShortCutMessage shortCutSms = new ShortCutMessage(BACK);
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

    public void fullScreen(View view) {
        ShortCutMessage shortCutSms = new ShortCutMessage("FULLSCREEN");
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

}
