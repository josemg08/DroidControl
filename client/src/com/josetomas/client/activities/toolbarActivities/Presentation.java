package com.josetomas.client.activities.toolbarActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.josetomas.client.R;
import com.josetomas.client.xmlMessage.keyboardMessage.ShortCutMessage;

public class Presentation extends ToolbarActivities {
    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";
    ImageButton arrowLeft, arrowRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.black_presentation);

        preferences = getSharedPreferences(pref, 0);
        if(preferences.getString("colorApp", "Black").equals("Black")){
            setContentView(R.layout.black_presentation);
        }
        else{
            setContentView(R.layout.red_presentation);
        }

        arrowLeft = (ImageButton) findViewById(R.id.arrowLeftButton);
        arrowRight = (ImageButton) findViewById(R.id.arrowRightButton);
        doBindService();
    }

    public void changeSlide(View view) {
        String nameButton = arrowLeft.isPressed() ? LEFT : RIGHT;
        ShortCutMessage shortCutSms = new ShortCutMessage(nameButton);
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

    public void fullScreen(View view) {
        ShortCutMessage shortCutSms = new ShortCutMessage("FULLSCREEN");
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

    private void esc (View view) {
        ShortCutMessage shortCutSms = new ShortCutMessage("ESC");
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

}
