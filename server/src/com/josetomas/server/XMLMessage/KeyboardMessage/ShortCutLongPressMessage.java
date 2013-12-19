package com.josetomas.server.xmlMessage.keyboardMessage;

import com.josetomas.server.xmlMessage.Message;
import com.josetomas.server.xmlMessage.MessageTypes;

public class ShortCutLongPressMessage extends Message {
    private String shortCut;
    private boolean press;

    public ShortCutLongPressMessage(String ShortCut, boolean press) {
        super(MessageTypes.LONGSHORTCUT);
        this.shortCut = ShortCut;
        this.press = press;
    }

    public boolean getPress(){
        return press;
    }

    public String getShortCut() {
        return shortCut;
    }

}