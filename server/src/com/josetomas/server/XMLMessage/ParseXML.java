/*
Develop by Jose Gonzalez
2013 - Argentina
*/

package com.josetomas.server.xmlMessage;

import com.josetomas.server.xmlMessage.keyboardMessage.*;
import com.josetomas.server.xmlMessage.mouseMessage.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ParseXML {
    private XStream xstream = new XStream(new DomDriver());

    public ParseXML() {
        xstream = new XStream(new DomDriver());
        xstream.alias("CharacterMessage", CharacterMessage.class);
        xstream.alias("CoordinatesMessage", CoordinatesMessage.class);
        xstream.alias("ShortCutMessage", ShortCutMessage.class);
        xstream.alias("ShortCutLongPressMessage", ShortCutLongPressMessage.class);
        xstream.alias("SystemMessage", SystemMessage.class);
        xstream.alias("GestureMessage", GestureMessage.class);
        //xstream.aliasField("type", SystemMessage.class,"Sensibility");
        //xstream.aliasAttribute(SystemMessage.class,"type", "Sensibility");
        //xstream.aliasAttribute(SystemMessage.class,"type", "Resolution");

    }

    public String buildXMLMessage(Message message) {
        return xstream.toXML(message);
    }

    public Message parseMessage(String xml) {
        //We check the type of the messaje before parsing back
        if (xml.contains("CoordinatesMessage")) {
            return (CoordinatesMessage) xstream.fromXML(xml);
        } else if (xml.contains("CharacterMessage")) {
            return (CharacterMessage) xstream.fromXML(xml);
        }else if (xml.contains("ShortCutMessage")) {
            return (ShortCutMessage) xstream.fromXML(xml);
        } else if (xml.contains("ShortCutLongPressMessage")) {
            return (ShortCutLongPressMessage) xstream.fromXML(xml);
        }  else if (xml.contains("GestureMessage")) {
            return (GestureMessage) xstream.fromXML(xml);
        } else {
            return (SystemMessage) xstream.fromXML(xml);
        }
    }

    public static void main(String[] args) {
        ParseXML parseXML = new ParseXML();
        SystemMessage characterMessage = new SystemMessage(SystemMessage.SystemMessageTypes.SENSIBILITY,200 );
        CoordinatesMessage message = new CoordinatesMessage(20,4,5);
        System.out.println(parseXML.buildXMLMessage(characterMessage));
        System.out.println(parseXML.buildXMLMessage(message));

    }

}
