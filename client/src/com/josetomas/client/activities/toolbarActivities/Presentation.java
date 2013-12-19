package com.josetomas.client.activities.toolbarActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import com.josetomas.client.R;
import com.josetomas.client.xmlMessage.keyboardMessage.ShortCutMessage;
import android.os.SystemClock;

public class Presentation extends ToolbarActivities {
    //private Button arrowLeft, arrowRight;
    private Chronometer chronometer;
    private boolean chronometerGoing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(pref, 0);
        String color = preferences.getString("colorApp", "Black");

        if(color.equals("Black")){
            setContentView(R.layout.black_presentation);
        }
        else if(color.equals("Green")){
            setContentView(R.layout.green_presentation);
        }
        else if(color.equals("Blue")){
            setContentView(R.layout.blue_presentation);
        }
        else{
            setContentView(R.layout.red_presentation);
        }

        //arrowLeft = (Button) findViewById(R.id.ArrowLeftButton);
        //arrowRight = (Button) findViewById(R.id.arrowRightButton);
        chronometer = (Chronometer) findViewById(R.id.chronometer);

        doBindService();
    }

    public void changeSlide(View view) {
        /*String nameButton = arrowLeft.isPressed() ? "LEFT" : "RIGHT";
        ShortCutMessage shortCutSms = new ShortCutMessage(nameButton);
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
        */
    }

    public void clickRightArrow (View view){
        ShortCutMessage shortCutSms = new ShortCutMessage("RIGHT");
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

    public void clickLeftArrow (View view){
        ShortCutMessage shortCutSms = new ShortCutMessage("LEFT");
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

    public void fullScreen(View view) {
        ShortCutMessage shortCutSms = new ShortCutMessage("FULLSCREEN");
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

    public void esc (View view) {
        ShortCutMessage shortCutSms = new ShortCutMessage("ESC");
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

    public void chronometerAction(View view){
        if(!chronometerGoing) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            chronometerGoing = true;
        }
        else{
            chronometer.stop();
            chronometerGoing = false;
        }
    }

}
