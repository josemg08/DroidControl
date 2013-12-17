package com.josetomas.client.xmlMessage.mouseMessage;

import com.josetomas.client.xmlMessage.Message;
import com.josetomas.client.xmlMessage.MessageTypes;

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
