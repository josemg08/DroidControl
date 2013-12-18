package com.josetomas.client.activities.toolbarActivities;

import android.os.Bundle;
import android.util.FloatMath;
import android.view.*;
import android.widget.LinearLayout;
import com.josetomas.client.R;
import com.josetomas.client.xmlMessage.mouseMessage.CoordinatesMessage;
import com.josetomas.client.xmlMessage.mouseMessage.GestureMessage;

public class MousePad extends ToolbarActivities {
    private int touchCount;
    private float downX, downY, downX2, downY2, distance;
    private long timeDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(pref, 0);
        String color = preferences.getString("colorApp", "Black");

        if(color.equals("Black")){
            setContentView(R.layout.black_mousepad);
        }
        else if(color.equals("Green")){
            setContentView(R.layout.green_mousepad);
        }
        else if(color.equals("Blue")){
            setContentView(R.layout.blue_mousepad);
        }
        else{
            setContentView(R.layout.red_mousepad);
        }

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.touchPadLayout);
        linearLayout.setOnTouchListener(onTouchListener);
        doBindService();
    }

    private View.OnTouchListener onTouchListener = new View.OnTouchListener() {

        //Send a message with the position x and y touched on the device screen
        //also if it was the first timeDown that it was touched
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            //Uses it if the user ir presing more than 1 finger
            if(motionEvent.getPointerCount()>1){
                handleMultiTouch(motionEvent);
                return true;
            }

            //Send's message for moving the mouse or right click if long press or left click
            //if double press (All this must be in a radius of 2 between the first click)
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    downX = motionEvent.getX();
                    downY = motionEvent.getY();
                    timeDown = System.currentTimeMillis();
                    if (touchCount > 1) {
                        socketService.sendMessage(parseXML.buildXMLMessage(
                                new GestureMessage(GestureMessage.GestureTypes.LEFT_CLICK)));
                        touchCount = 0;
                        return true;
                    }
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (downX >= motionEvent.getX() - 2 || downX < motionEvent.getX() + 2) {
                        if (downY >= motionEvent.getY() - 2 || downY < motionEvent.getY() + 2) {
                            if((System.currentTimeMillis() - timeDown)>2000){
                                socketService.sendMessage(parseXML.buildXMLMessage
                                        (new GestureMessage(GestureMessage.GestureTypes.RIGHT_CLICK)));
                                timeDown = System.currentTimeMillis();
                                return true;
                            }
                        }
                    }
                    touchCount = 0;
                    break;

                case MotionEvent.ACTION_UP:
                    if (downX >= motionEvent.getX() - 3 || downX < motionEvent.getX() + 3) {
                        if (downY >= motionEvent.getY() - 3 || downY < motionEvent.getY() + 3) {
                            touchCount++;
                        }
                    }
                    break;
            }

            socketService.sendMessage(parseXML.buildXMLMessage(
                    new CoordinatesMessage((int) motionEvent.getX(),
                            (int) motionEvent.getY(), motionEvent.getActionMasked())));
            return true;
        }

    };

    public void handleMultiTouch(MotionEvent event){
        //On the first time that there user presses with 2 fingers this saves the distance between
        //them using the Pitagoras theorem and also the xy position of bough fingers
        if(event.getAction()==MotionEvent.ACTION_POINTER_DOWN ||
                event.getAction()==MotionEvent.ACTION_DOWN ||
                event.getAction()==MotionEvent.ACTION_POINTER_2_DOWN){
            timeDown = System.currentTimeMillis();
            downX = event.getX(0);
            downY = event.getY(0);
            downX2 = event.getX(1);
            downY2 = event.getX(1);
            float x = event.getX(0) - event.getX(1);
            float y = event.getY(0) - event.getY(1);
            distance = FloatMath.sqrt(x * x + y * y);
            return;
        }

        //gets the amount of movement between each x y position of each finger
        //between the fist time and the last
        int moveY1 = (int) (downY - event.getY(0));
        int moveY2 = (int) (downY2 - event.getY(1));
        int moveX1 = (int) (downX - event.getX(0));
        int moveX2 = (int) (downX2 - event.getX(1));

        //gets the difference between the fist distance between and the new
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        float newDistance = FloatMath.sqrt(x * x + y * y);
        float distanceBetween = newDistance - distance;

        int move = Math.abs(moveY1) > Math.abs(moveY2) ? moveY1 : moveY2;
        int move2 = Math.abs(moveX1) > Math.abs(moveX2) ? moveX1 : moveX2;
        move = Math.abs(move2) > Math.abs(move) ? move2 : move;

        //I ignore the small movements or changes and concentrate on the large ones
        //If the amount of change in the position x or y of une finger is
        //greater than the difference between the first distance between fingers and the new
        //a scroll is made otherwise a zoom
        if(Math.abs(move)>40 || Math.abs(distanceBetween)>40) {
            if(timeDown>2000){
                timeDown = System.currentTimeMillis();
                if(Math.abs(move) > Math.abs(distanceBetween)){
                    scroll(moveX1, moveY1, moveX2, moveY2);
                }
                else {
                    zoom(distanceBetween);
                }
            }
        }

    }

    //if the difference between the first distance between fingers and the new one is positive
    //(converting all to positive) the zoom is positive otherwise negative
    private void zoom(float distanceBetween){
        if(distanceBetween > 0){
            socketService.sendMessage(parseXML.buildXMLMessage(
                    new GestureMessage(GestureMessage.GestureTypes.ZOOM, 1)));
        }
        else if(distanceBetween < 0){
            socketService.sendMessage(parseXML.buildXMLMessage(
                    new GestureMessage(GestureMessage.GestureTypes.ZOOM, -1)));
        }
    }

    //compares tha amount of movement of every finger in each x and y
    //knowing witch one is greater (converting all to positive) performs a scroll
    // if the bigger change was in x tha scroll is side to side, in y is up to down
    //if it was negative would go down or left, positive up or right
    private void scroll(float moveX1, float moveY1, float moveX2, float moveY2){
        if(Math.abs(moveY1+moveY2) > Math.abs(moveX1+moveX2)){
            if(moveY1<0 && moveY2<0){
                socketService.sendMessage(parseXML.buildXMLMessage(
                        new GestureMessage(GestureMessage.GestureTypes.SCROLL, 0, 1)));
            }
            else if(moveY1>0 && moveY2>0){
                socketService.sendMessage(parseXML.buildXMLMessage(
                        new GestureMessage(GestureMessage.GestureTypes.SCROLL, 0, -1)));
            }
        }
        else{
            if(moveX1<0 && moveX2<0){
                socketService.sendMessage(parseXML.buildXMLMessage(
                        new GestureMessage(GestureMessage.GestureTypes.SCROLL, 1, 0)));
            }
            else if(moveX1>0 && moveX2>0){
                socketService.sendMessage(parseXML.buildXMLMessage(
                        new GestureMessage(GestureMessage.GestureTypes.SCROLL, -1, 0)));
            }
        }
    }

}
