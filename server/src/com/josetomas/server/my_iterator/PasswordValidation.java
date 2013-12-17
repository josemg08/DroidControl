package com.josetomas.server.my_iterator;

import com.josetomas.server.MServer;
import com.josetomas.server.xmlMessage.ParseXML;
import com.josetomas.server.xmlMessage.SystemMessage;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordValidation {

    ParseXML parseXML;

    public PasswordValidation() {
        parseXML = new ParseXML();
    }

    public void comparePassword(String password) {
        String pass = "1";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(pass.getBytes("UTF-8"));
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            pass = hexString.toString();

            if (pass.equals(password)) acceptClient();
            else refuseClient();


        } catch (UnsupportedEncodingException e) {
            //TODO
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            //TODO
            e.printStackTrace();
        }

    }

    private void refuseClient() {
        MServer server = MServer.getInstance();
        server.setConnected(true);                  //TODO Why true?
        SystemMessage message = new SystemMessage(SystemMessage.SystemMessageTypes.PASSWORD,"REFUSED");
        server.sendMessage(parseXML.buildXMLMessage(message));
    }

    private void acceptClient() {
        MServer server = MServer.getInstance();
        server.setConnected(true);
        SystemMessage message = new SystemMessage(SystemMessage.SystemMessageTypes.PASSWORD,"ACCEPTED");
        server.sendMessage(parseXML.buildXMLMessage(message));
    }
}
