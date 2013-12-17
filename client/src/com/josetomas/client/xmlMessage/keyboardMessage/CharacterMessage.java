package com.josetomas.client.xmlMessage.keyboardMessage;

import com.josetomas.client.xmlMessage.Message;
import com.josetomas.client.xmlMessage.MessageTypes;

public class CharacterMessage extends Message {
    private char character;

    public CharacterMessage(char character) {
        super(MessageTypes.CHARACTER);
        this.character = character;
    }

    public char getCharacter() {
        return character;
    }

}
