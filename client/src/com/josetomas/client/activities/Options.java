package com.josetomas.client.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import com.josetomas.client.R;
import com.josetomas.client.activities.toolbarActivities.MousePad;
import com.josetomas.client.network.AbstractNetworkActivity;
import com.josetomas.client.xmlMessage.SystemMessage;


public class Options extends AbstractNetworkActivity {
    private int game_progress, trackPad_progress;
    private String[] colorOptions, gridSizeOptions;
    private Spinner spinnerGridSize, spinnerColor;
    private static final String pref = "Preferences";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.black_options);
        preferences = getPreferences(MODE_PRIVATE);

        preferences = getSharedPreferences(pref, 0);
        editor = preferences.edit();
        trackPad_progress = preferences.getInt("trackPad_sensibility", 2);
        game_progress = preferences.getInt("gameControl_sensibility", 1);

        //Behaviour of bought of the seekBars
        SeekBar trackPad_seek = (SeekBar) findViewById(R.id.trackPad_sensibility);
        trackPad_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            //Saves the trackPad sensibility on preferences and send's the new preference to the
            //server
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (trackPad_progress == 0) {
                    trackPad_progress = 1;
                }
                editor.putInt("trackPad_sensibility", trackPad_progress);
                editor.commit();
                socketService.sendMessage(parseXML.buildXMLMessage(
                        new SystemMessage(SystemMessage.SystemMessageTypes.SENSIBILITY,
                                trackPad_progress)));
            }

            //Keep's the changes from the seekBar on a variable
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                trackPad_progress = progress / 10;
            }
        });

        //Second seekBar, game control sensibility
        SeekBar gameControl_seek = (SeekBar) findViewById(R.id.gameControl_sensibility);
        gameControl_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(game_progress==0){
                    game_progress=1;
                }
                editor.putInt("gameControl_sensibility", game_progress);
                editor.commit();
                //TODO send xmlMessage with game sensibility
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                game_progress = progress/10;
            }
        });

        //Code for the spinners
        //Color chooser spinner
        colorOptions = new String[4];
        colorOptions[0] = "Black";
        colorOptions[1] = "Blue";
        colorOptions[2] = "Red";
        colorOptions[3] = "Green";
        spinnerColor = (Spinner) findViewById(R.id.chooseColor);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, colorOptions);
        spinnerColor.setAdapter(adapter);

        //Custom grid size chooser spinner
        gridSizeOptions = new String[2];
        gridSizeOptions[0] = "2 x 3";
        gridSizeOptions[1] = "3 x 4";
        spinnerGridSize = (Spinner) findViewById(R.id.chooseGridSize);
        ArrayAdapter adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, gridSizeOptions);
        spinnerGridSize.setAdapter(adapter2);

        doBindService();
    }

    public void saveChanges(View view){
        editor.putString("gridSize", spinnerGridSize.getSelectedItem().toString());
        editor.putString("colorApp", spinnerColor.getSelectedItem().toString());
        editor.commit();
        Intent intent = new Intent(this, MousePad.class);
        startActivity(intent);
    }

}