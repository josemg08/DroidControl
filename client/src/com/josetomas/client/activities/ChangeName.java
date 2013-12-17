package com.josetomas.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.josetomas.client.R;
import com.josetomas.client.activities.toolbarActivities.Custom;

public class ChangeName extends Activity {
    private EditText text;
    private static final String pref = "Preferences";
    private SharedPreferences preferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_name);
        text = (EditText) findViewById(R.id.editText);
        preferences = getSharedPreferences(pref, 0);
    }

    public void newName(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(preferences.getString("editable", null), text.getText().toString());
        editor.commit();

        goToCustom();
    }

    private void goToCustom() {
        Intent intent = new Intent(this, Custom.class);
        startActivity(intent);
    }
}
