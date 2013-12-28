/*
Develop by Jose Gonzalez
2013 - Argentina
*/

package com.josetomas.server.xmlMessage.keyboardMessage;

import com.josetomas.server.xmlMessage.Message;
import com.josetomas.server.xmlMessage.MessageTypes;

public class ShortCutMessage extends Message {
    private String shortCut;
    private boolean custom = false;

    public ShortCutMessage(String ShortCut, boolean custom) {
        super(MessageTypes.SHORTCUT);
        this.shortCut = ShortCut;
        this.custom = custom;
    }

    public ShortCutMessage(String ShortCut) {
        super(MessageTypes.SHORTCUT);
        this.shortCut = ShortCut;
    }

    public boolean getCustom(){
        return custom;
    }

    public String getShortCut() {
        return shortCut;
    }

}