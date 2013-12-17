package com.josetomas.client.xmlMessage;

import com.josetomas.client.xmlMessage.keyboardMessage.CharacterMessage;
import com.josetomas.client.xmlMessage.keyboardMessage.ShortCutMessage;
import com.josetomas.client.xmlMessage.mouseMessage.CoordinatesMessage;
import com.josetomas.client.xmlMessage.mouseMessage.GestureMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ParseXML {
    private XStream xstream = new XStream(new DomDriver());

    public ParseXML() {
        xstream = new XStream(new DomDriver());
        xstream.alias("CharacterMessage", CharacterMessage.class);
        xstream.alias("CoordinatesMessage", CoordinatesMessage.class);
        xstream.alias("ShortCutMessage", ShortCutMessage.class);
        xstream.alias("SystemMessage", SystemMessage.class);
        xstream.alias("GestureMessage", GestureMessage.class);
//        xstream.alias("Password", SystemMessage.SystemMessageTypes.class);
//        xstream.alias("Sensibility", SystemMessage.SystemMessageTypes.class);
//        xstream.alias("Resolution", SystemMessage.SystemMessageTypes.class);
    }

    public String buildXMLMessage(Message message) {
        return xstream.toXML(message);
    }

    public Message parseMessage(String xml) {
        //We check the type of the message before parsing back
        if (xml.contains("CoordinatesMessage")) {
            return (CoordinatesMessage) xstream.fromXML(xml);
        } else if (xml.contains("CharacterMessage")) {
            return (CharacterMessage) xstream.fromXML(xml);
        } else if (xml.contains("ShortCutMessage")) {
            return (ShortCutMessage) xstream.fromXML(xml);
        } else if (xml.contains("GestureMessage")) {
            return (GestureMessage) xstream.fromXML(xml);
        } else {
            return (SystemMessage) xstream.fromXML(xml);
        }
    }

}
