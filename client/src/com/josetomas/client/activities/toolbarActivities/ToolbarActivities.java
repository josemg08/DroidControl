package com.josetomas.client.activities.toolbarActivities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.josetomas.client.xmlMessage.keyboardMessage.CharacterMessage;
import com.josetomas.client.network.AbstractNetworkActivity;
import com.josetomas.client.activities.Options;
import com.josetomas.client.xmlMessage.keyboardMessage.ShortCutMessage;

public abstract class ToolbarActivities extends AbstractNetworkActivity {
    protected static final String pref = "Preferences";
    protected SharedPreferences preferences;

    public static final String DELETE = "DELETE";

    public void goToOptions(View view) {
        Intent intent = new Intent(this, Options.class);
        startActivity(intent);
    }

    //Changes to touch pad skin
    public void goToTouchPad(View view) {
        Intent intent = new Intent(this, MousePad.class);
        startActivity(intent);
    }

    public void goToSkinSelector(View view) {
        Intent intent = new Intent(this, SkinSelector.class);
        startActivity(intent);
    }

    public void showKeyboard(View view) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

    }

    //this method it used to send the key press un the keyboard
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_DEL) {
            socketService.sendMessage(parseXML.buildXMLMessage(
                    new ShortCutMessage(DELETE)));
            return true;
        } else {
            socketService.sendMessage(parseXML.buildXMLMessage(
                    new CharacterMessage(event.getDisplayLabel())));
            return true;
        }
    }

}
