package com.josetomas.server.xmlMessage.keyboardMessage;

import com.josetomas.server.xmlMessage.Message;
import com.josetomas.server.xmlMessage.MessageTypes;

public class CharacterMessage extends Message {
	private char character;
	
	public CharacterMessage(char character) {
        super(MessageTypes.CHARACTER);
		this.character=character;
	}
	
	public char getCharacter(){
		return character;
	}

}
