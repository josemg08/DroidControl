/*
Develop by Jose Gonzalez & Tomas Najun
2013 - Argentina
*/

package com.josetomas.client.xmlMessage.keyboardMessage;

import com.josetomas.client.xmlMessage.Message;
import com.josetomas.client.xmlMessage.MessageTypes;

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