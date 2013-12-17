package com.josetomas.client.xmlMessage;

public class SystemMessage extends Message {
    private int with, height, sensibility;
    private String pass;
    private SystemMessageTypes systemMessageType;


    public enum SystemMessageTypes{
        RESOLUTION, SENSIBILITY, PASSWORD;
    }

    public SystemMessage() {
        super(MessageTypes.SYSTEM);
    }

    public SystemMessage(SystemMessageTypes type ,int sensibility){
        super(MessageTypes.SYSTEM);
        this.systemMessageType = type;
        this.sensibility = sensibility;
    }

    public SystemMessage(SystemMessageTypes type, int with, int height) {
        super(MessageTypes.SYSTEM);
        this.systemMessageType = type;
        this.with = with;
        this.height = height;
    }

    public SystemMessage(SystemMessageTypes type, String pass) {
        super(MessageTypes.SYSTEM);
        this.systemMessageType = type;
        this.pass = pass;
    }

    public SystemMessageTypes getSystemMessageType(){
        return systemMessageType;
    }

    public int getSensibility(){
        return sensibility;
    }

    public int getWith() {
        return with;
    }

    public int getHeight() {
        return height;
    }

    public String getPass() {
        return pass;
    }
}
