package com.josetomas.client.activities;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.josetomas.client.R;
import com.josetomas.client.xmlMessage.SystemMessage;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;


public class ValidatePassword extends AbstractNetworkActivity {
    public static final String ALGORITHM = "MD5";
    public static final String CHARSET_NAME = "UTF-8";
    public static final String ACCEPTED = "ACCEPTED";
    private EditText passwordField;
    private CheckBox checkBoxShowPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.validate_password);
        passwordField = (EditText) findViewById(R.id.passwordText);

        doBindService();
    }

    private String encodePassword() {
        String result = "";
        String pass = passwordField.getText().toString();

        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            digest.update(pass.getBytes(CHARSET_NAME));
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            result = hexString.toString();


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //TODO
        } catch (Exception e) {
            e.printStackTrace();
            //TODO
        }
        return result;
    }



    public void sendPassword(View view) {
        SystemMessage password = new SystemMessage(SystemMessage.SystemMessageTypes.PASSWORD, encodePassword());
        socketService.sendMessage(parseXML.buildXMLMessage(password));
        String text = socketService.receiveMessage();
        Log.e("Tetxt\n", text);
        SystemMessage message = (SystemMessage) parseXML.parseMessage(text);
        if (message.getSystemMessageType() == SystemMessage.SystemMessageTypes.PASSWORD ) {
            if (message.getPass().equals(ACCEPTED)) {
                finish();
            }
            else {
                Toast.makeText(this, "Password incorrect", Toast.LENGTH_LONG).show();
                passwordField.setText("");
            }
        }
    }

    public void cancel(View view) {
        finish();
    }

    public void showPass(View view) {
        if (checkBoxShowPass.isChecked()) {
            passwordField.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else {
            passwordField.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }
}
