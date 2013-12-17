package com.josetomas.client.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.josetomas.client.R;

/**
 * User: Jose
 * Date: 9/10/13
 * Time: 5:16 PM
 */

public class Initial_page extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        long time = System.currentTimeMillis();
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, MClient.class);
        startActivity(intent);
    }

}

