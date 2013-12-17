package com.josetomas.server.xmlMessage;

public abstract class Message {
    protected MessageTypes type;

    public Message(MessageTypes type){
        this.type = type;
    }

    public MessageTypes getType(){
        return type;
    }

}
