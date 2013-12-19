package com.josetomas.server.my_iterator;

import com.josetomas.server.xmlMessage.keyboardMessage.ShortCutLongPressMessage;
import com.josetomas.server.my_iterator.mouse.TouchScreenIterator;
import com.josetomas.server.my_iterator.mouse.TrackPadIterator;
import com.josetomas.server.xmlMessage.keyboardMessage.CharacterMessage;
import com.josetomas.server.xmlMessage.keyboardMessage.ShortCutMessage;
import com.josetomas.server.xmlMessage.Message;
import com.josetomas.server.xmlMessage.mouseMessage.CoordinatesMessage;
import com.josetomas.server.xmlMessage.mouseMessage.GestureMessage;
import com.josetomas.server.xmlMessage.SystemMessage;

import java.awt.*;
import java.io.IOException;

public class IteratorXML extends MyIterator {
    private TrackPadIterator trackPadIterator;
    private TouchScreenIterator touchScreenIterator;
    private KeyBoardIterator keyBoardIterator;
    private PasswordValidation passwordValidation;

    public IteratorXML(Robot robot) {
        super(robot);
        trackPadIterator = new TrackPadIterator(robot);
        touchScreenIterator = new TouchScreenIterator(robot);
        keyBoardIterator = new KeyBoardIterator(robot);
        passwordValidation = new PasswordValidation();
    }

    //Receives the message and depending on the type performs the actions
    public void performAction(Message message) throws IllegalArgumentException{
        switch (message.getType()) {
            case COORDINATES:
                CoordinatesMessage coordinatesMessage = (CoordinatesMessage) message;
                trackPadIterator.moveMouse(coordinatesMessage.getPosX(),
                        coordinatesMessage.getPosY(), coordinatesMessage.getAction());
                break;

            case CHARACTER:
                keyBoardIterator.keyPress(((CharacterMessage) message).getCharacter());
                break;

            case SHORTCUT:
                try {
                    keyBoardIterator.shortCutPress(((ShortCutMessage) message).getShortCut(),
                            ((ShortCutMessage) message).getCustom());
                } catch (Exception e) {
                    //TODO

                }
                break;

            case LONGSHORTCUT:
                if(((ShortCutLongPressMessage) message).getPress()){
                    try {
                        keyBoardIterator.press(((ShortCutLongPressMessage) message).getShortCut());
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                else{
                    try {
                        keyBoardIterator.release(((ShortCutLongPressMessage) message).getShortCut());
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }

                break;

            case GESTURE:
                if (((GestureMessage) message).getGesture() == GestureMessage.GestureTypes.LEFT_CLICK) {
                    trackPadIterator.leftClick();
                } else if (((GestureMessage) message).getGesture() == GestureMessage.GestureTypes.RIGHT_CLICK) {
                    trackPadIterator.rightClick();
                } else if (((GestureMessage) message).getGesture() == GestureMessage.GestureTypes.SCROLL) {
                    scroll(((GestureMessage) message).getScrollX(),
                            ((GestureMessage) message).getScrollY());
                } else {
                    zoom(((GestureMessage) message).getZoom());
                }
                break;

            case SYSTEM:
                if (((SystemMessage) message).getSystemMessageType() == SystemMessage.SystemMessageTypes.RESOLUTION) {
                    touchScreenIterator.setMobileResolution(
                            ((SystemMessage) message).getWith(), ((SystemMessage) message).getHeight());
                } else if (((SystemMessage) message).getSystemMessageType() == SystemMessage.SystemMessageTypes.SENSIBILITY){
                    trackPadIterator.setSensibility(((SystemMessage) message).getSensibility());
                } else {
                    passwordValidation.comparePassword(((SystemMessage) message).getPass());

                }
                break;
        }
    }

}