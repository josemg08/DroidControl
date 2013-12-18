package com.josetomas.client.activities.toolbarActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.josetomas.client.R;
import com.josetomas.client.activities.ChangeName;
import com.josetomas.client.xmlMessage.keyboardMessage.ShortCutMessage;

public class Custom extends ToolbarActivities{
    private Button button0, button1, button2, button3, button4, button5
                   ,button6, button7, button8, button9, button10, button11;
    private Button[] buttons;
    private static final String pref = "Preferences";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences(pref, 0);
        String color = preferences.getString("colorApp", "Black");

        if(preferences.getString("gridSize", "2 x 3").equals("2 x 3")){
            if(color.equals("Black")){
                setContentView(R.layout.black_custom2x3);
            }
            else if(color.equals("Green")){
                setContentView(R.layout.green_custom2x3);
            }
            else if(color.equals("Blue")){
                setContentView(R.layout.blue_custom2x3);
            }
            else{
                setContentView(R.layout.red_custom2x3);
            }
        }
        else{
            if(color.equals("Black")){
                setContentView(R.layout.black_custom3x4);
            }
            else if(color.equals("Green")){
                setContentView(R.layout.green_custom3x4);
            }
            else if(color.equals("Blue")){
                setContentView(R.layout.blue_custom3x4);
            }
            else{
                setContentView(R.layout.red_custom3x4);
            }
        }

        button0 = (Button) findViewById(R.id.Button0);
        button1 = (Button) findViewById(R.id.Button1);
        button2 = (Button) findViewById(R.id.Button2);
        button3 = (Button) findViewById(R.id.Button3);
        button4 = (Button) findViewById(R.id.Button4);
        button5 = (Button) findViewById(R.id.Button5);
        button6 = (Button) findViewById(R.id.Button6);
        button7 = (Button) findViewById(R.id.Button7);
        button8 = (Button) findViewById(R.id.Button8);
        button9 = (Button) findViewById(R.id.Button9);
        button10 = (Button) findViewById(R.id.Button10);
        button11 = (Button) findViewById(R.id.Button11);

        buttons = new Button[12];
        buttons[0] = button0;
        buttons[1] = button1;
        buttons[2] = button2;
        buttons[3] = button3;
        buttons[4] = button4;
        buttons[5] = button5;
        buttons[6] = button6;
        buttons[7] = button7;
        buttons[8] = button8;
        buttons[9] = button9;
        buttons[10] = button10;
        buttons[11] = button11;

        button0.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                performOnLongClick(0);

                return true;
            }
        });

        button1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                performOnLongClick(1);
                return true;
            }
        });

        button2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                performOnLongClick(2);
                return true;
            }
        });

        button3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                performOnLongClick(3);
                return true;
            }
        });

        button4.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                performOnLongClick(4);
                return true;
            }
        });

        button5.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                performOnLongClick(5);
                return true;
            }
        });

        button6.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                performOnLongClick(6);
                return true;
            }
        });

        button7.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                performOnLongClick(7);
                return true;
            }
        });

        button8.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                performOnLongClick(8);
                return true;
            }
        });

        button9.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                performOnLongClick(9);
                return true;
            }
        });

        button10.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                performOnLongClick(10);
                return true;
            }
        });

        button11.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                performOnLongClick(11);
                return true;
            }
        });

        doBindService();
        initializePreferences(preferences);
    }

    private void performOnLongClick(int buttonNumber){
        ShortCutMessage shortCutSms = new ShortCutMessage("BUTTON"+buttonNumber, true);
        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("editable", "Button"+buttonNumber);
        editor.commit();
        goToChangeName();
    }

    public void SendShortCut (View view) {
        ShortCutMessage shortCutSms = null;
        switch (view.getId()) {
            case R.id.Button0:
                shortCutSms = new ShortCutMessage("BUTTON0");
                break;
            case R.id.Button1:
                shortCutSms = new ShortCutMessage("BUTTON1");
                break;
            case R.id.Button2:
                shortCutSms = new ShortCutMessage("BUTTON2");
                break;
            case R.id.Button3:
                shortCutSms = new ShortCutMessage("BUTTON3");
                break;
            case R.id.Button4:
                shortCutSms = new ShortCutMessage("BUTTON4");
                break;
            case R.id.Button5:
                shortCutSms = new ShortCutMessage("BUTTON5");
                break;
            case R.id.Button6:
                shortCutSms = new ShortCutMessage("BUTTON6");
                break;
            case R.id.Button7:
                shortCutSms = new ShortCutMessage("BUTTON7");
                break;
            case R.id.Button8:
                shortCutSms = new ShortCutMessage("BUTTON8");
                break;
            case R.id.Button9:
                shortCutSms = new ShortCutMessage("BUTTON9");
                break;
            case R.id.Button10:
                shortCutSms = new ShortCutMessage("BUTTON10");
                break;
            case R.id.Button11:
                shortCutSms = new ShortCutMessage("BUTTON11");
                break;
        }

        socketService.sendMessage(parseXML.buildXMLMessage(shortCutSms));
    }

    private void initializePreferences(SharedPreferences preferences){
        SharedPreferences.Editor editor = preferences.edit();

        for(int i=0; i<buttons.length; i++){
            if(preferences.getString("Button"+i, null)!=null){
                buttons[i].setText(preferences.getString("Button"+i, null));
            }
            else{
                editor.putString("Button"+i, "");
                editor.commit();
                buttons[i].setText(preferences.getString("Button"+i, null));
            }
        }

    }

    private void goToChangeName() {
        Intent intent = new Intent(this, ChangeName.class);
        startActivity(intent);
    }

}
