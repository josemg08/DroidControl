/*
Develop by Jose Gonzalez
2013 - Argentina
*/

package com.josetomas.server.xmlMessage.mouseMessage;

import com.josetomas.server.xmlMessage.Message;
import com.josetomas.server.xmlMessage.MessageTypes;

public class CoordinatesMessage extends Message {
    private int posX, posY, action;

    public CoordinatesMessage(int posX, int posY, int action) {
        super(MessageTypes.COORDINATES);
        this.posX = posX;
        this.posY = posY;
        this.action = action;
    }

    public int getAction(){
        return action;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

}