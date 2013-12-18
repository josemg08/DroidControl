package com.josetomas.client.activities.toolbarActivities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import com.josetomas.client.R;
import com.josetomas.client.xmlMessage.keyboardMessage.ShortCutMessage;

public class GameControl extends ToolbarActivities implements SensorEventListener {
    public static final String UP = "UP";
    public static final String DOWN = "DOWN";
    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";
    private SensorManager sensorManager;
    private Sensor sensor;

    private float sensibility = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(pref, 0);
        String color = preferences.getString("colorApp", "Black");

        if(color.equals("Black")){
            setContentView(R.layout.black_game_control);
        }
        else if(color.equals("Green")){
            setContentView(R.layout.green_game_control);
        }
        else if(color.equals("Blue")){
            setContentView(R.layout.blue_game_control);
        }
        else{
            setContentView(R.layout.red_game_control);
        }

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        doBindService();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float y = event.values[1];
        float z = event.values[2];

        if(y<-3){
            left();
        }
        else if(y>3){
            right();
        }

        if(z<-3){
            down();
        }
        else if(z>3){
            up();
        }

    }

    public void setSensibility(int sensibility){
        this.sensibility = sensibility;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void left(View v) {
        left();
    }

    public void right(View v) {
        right();
    }

    public void up(View v) {
        up();
    }

    public void down(View v) {
        down();
    }

    private void up() {
        socketService.sendMessage(parseXML.buildXMLMessage(new ShortCutMessage(UP)));
    }
    private void down() {
        socketService.sendMessage(parseXML.buildXMLMessage(new ShortCutMessage(DOWN)));
    }

    private void left() {
        socketService.sendMessage(parseXML.buildXMLMessage(new ShortCutMessage(LEFT)));
    }

    private void right() {
        socketService.sendMessage(parseXML.buildXMLMessage(new ShortCutMessage(RIGHT)));
    }

}
