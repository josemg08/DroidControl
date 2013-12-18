package com.josetomas.client.activities.toolbarActivities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import com.josetomas.client.R;

public class SkinSelector extends Activity {
    private static final String pref = "Preferences";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences(pref, 0);
        String color = preferences.getString("colorApp", "Black");

        if(color.equals("Black")){
            setContentView(R.layout.black_skin_selector);
        }
        else if(color.equals("Green")){
            setContentView(R.layout.green_skin_selector);
        }
        else if(color.equals("Blue")){
            setContentView(R.layout.blue_skin_selector);
        }
        else{
            setContentView(R.layout.red_skin_selector);
        }
    }

    public void goToGameControl(View view) {
        Intent intent = new Intent(this, GameControl.class);
        startActivity(intent);
    }

    //changes to the presentation skin
    public void goToPresentation(View view) {
        Intent intent = new Intent(this, Presentation.class);
        startActivity(intent);
    }

    //Changes to the media skin
    public void goToMedia(View view) {
        Intent intent = new Intent(this, Media.class);
        startActivity(intent);
    }

    //Changes to custom skin
    public void goToCustom(View view) {
        Intent intent = new Intent(this, Custom.class);
        startActivity(intent);
    }

}