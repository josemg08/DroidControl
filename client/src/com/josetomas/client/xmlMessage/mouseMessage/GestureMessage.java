package com.josetomas.client.xmlMessage.mouseMessage;

import com.josetomas.client.xmlMessage.MessageTypes;
import com.josetomas.client.xmlMessage.Message;

public class GestureMessage extends Message {
    private GestureTypes gesture;
    private int zoom = 0, scrollX = 0, scrollY = 0;

    public enum GestureTypes{
        ZOOM, SCROLL, RIGHT_CLICK, LEFT_CLICK;
    }

    //To be use for mouse click types
    public GestureMessage(GestureTypes gesture){
        super(MessageTypes.GESTURE);
        this.gesture = gesture;
    }

    //To be use for zoom
    public GestureMessage(GestureTypes gesture, int zoom) {
        super(MessageTypes.GESTURE);
        this.gesture = gesture;
        this.zoom = zoom;
    }

    //To be use for scroll
    public GestureMessage(GestureTypes gesture, int scrollX, int scrollY) {
        super(MessageTypes.GESTURE);
        this.gesture = gesture;
        this.scrollX = scrollX;
        this.scrollY = scrollY;
    }

    public GestureTypes getGesture(){
        return gesture;
    }

    public int getZoom(){
        return zoom;
    }

    public int getScrollY(){
        return scrollY;
    }

    public int getScrollX(){
        return scrollX;
    }

}