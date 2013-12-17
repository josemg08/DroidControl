package com.josetomas.client.activities.toolbarActivities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.josetomas.client.R;
import com.josetomas.client.activities.toolbarActivities.Custom;
import com.josetomas.client.activities.toolbarActivities.GameControl;
import com.josetomas.client.activities.toolbarActivities.Media;
import com.josetomas.client.activities.toolbarActivities.Presentation;

public class SkinSelector extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.skin_selector);
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