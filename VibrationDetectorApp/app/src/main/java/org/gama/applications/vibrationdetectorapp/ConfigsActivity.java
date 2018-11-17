package org.gama.applications.vibrationdetectorapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.util.Log;


public class ConfigsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configs);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Configurations");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        SharedPreferences preferences = getSharedPreferences("configurations", MODE_PRIVATE);

        ((SeekBar)findViewById(R.id.graph_scale)).setProgress((int)preferences.getFloat("scale", 3));
        ((SeekBar)findViewById(R.id.time_interval)).setProgress(preferences.getInt("time", 0));

        ((CheckBox)findViewById(R.id.sensor1g)).setChecked(preferences.getBoolean("sensor1g", true));
        ((CheckBox)findViewById(R.id.sensor2g)).setChecked(preferences.getBoolean("sensor2g", true));
        ((CheckBox)findViewById(R.id.sensor3g)).setChecked(preferences.getBoolean("sensor3g", true));
        ((CheckBox)findViewById(R.id.sensor4g)).setChecked(preferences.getBoolean("sensor4g", true));
        ((CheckBox)findViewById(R.id.sensor5g)).setChecked(preferences.getBoolean("sensor5g", true));
        ((CheckBox)findViewById(R.id.sensor6g)).setChecked(preferences.getBoolean("sensor6g", true));

        ((CheckBox)findViewById(R.id.sensor1a)).setChecked(preferences.getBoolean("sensor1a", true));
        ((CheckBox)findViewById(R.id.sensor2a)).setChecked(preferences.getBoolean("sensor2a", true));
        ((CheckBox)findViewById(R.id.sensor3a)).setChecked(preferences.getBoolean("sensor3a", true));
        ((CheckBox)findViewById(R.id.sensor4a)).setChecked(preferences.getBoolean("sensor4a", true));
        ((CheckBox)findViewById(R.id.sensor5a)).setChecked(preferences.getBoolean("sensor5a", true));
        ((CheckBox)findViewById(R.id.sensor6a)).setChecked(preferences.getBoolean("sensor6a", true));

        if (preferences.getBoolean("start", true)){
            ((RadioButton)findViewById(R.id.start0)).setChecked(true);
            ((RadioButton)findViewById(R.id.startscale)).setChecked(false);
        } else {
            ((RadioButton)findViewById(R.id.start0)).setChecked(false);
            ((RadioButton)findViewById(R.id.startscale)).setChecked(true);
        }

        final SeekBar sk = findViewById(R.id.graph_scale);
        final TextView gtext = findViewById(R.id.graph_text);
        gtext.setText(String.format("%s (10^%d)", "Graph View Scale", -3 + sk.getProgress()));
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gtext.setText(String.format("%s (10^%d)", "Graph View Scale", -3 + sk.getProgress()));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        final SeekBar sk2 = findViewById(R.id.time_interval);
        final TextView ttext = findViewById(R.id.time_text);
        ttext.setText(String.format("%s %d s", "Save interval", 10 + sk2.getProgress()));
        sk2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ttext.setText(String.format("%s %d s", "Save interval", 10 + sk2.getProgress()));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        SharedPreferences preferences = getSharedPreferences("configurations", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        RadioGroup rg = findViewById(R.id.scale_selector);
        Log.d("test", String.format("%d", rg.getCheckedRadioButtonId()));

        editor.putFloat("scale", ((SeekBar)findViewById(R.id.graph_scale)).getProgress());
        editor.putInt("time", ((SeekBar)findViewById(R.id.time_interval)).getProgress());

        editor.putBoolean("sensor1g", ((CheckBox)findViewById(R.id.sensor1g)).isChecked());
        editor.putBoolean("sensor2g", ((CheckBox)findViewById(R.id.sensor2g)).isChecked());
        editor.putBoolean("sensor3g", ((CheckBox)findViewById(R.id.sensor3g)).isChecked());
        editor.putBoolean("sensor4g", ((CheckBox)findViewById(R.id.sensor4g)).isChecked());
        editor.putBoolean("sensor5g", ((CheckBox)findViewById(R.id.sensor5g)).isChecked());
        editor.putBoolean("sensor6g", ((CheckBox)findViewById(R.id.sensor6g)).isChecked());

        editor.putBoolean("sensor1a", ((CheckBox)findViewById(R.id.sensor1a)).isChecked());
        editor.putBoolean("sensor2a", ((CheckBox)findViewById(R.id.sensor2a)).isChecked());
        editor.putBoolean("sensor3a", ((CheckBox)findViewById(R.id.sensor3a)).isChecked());
        editor.putBoolean("sensor4a", ((CheckBox)findViewById(R.id.sensor4a)).isChecked());
        editor.putBoolean("sensor5a", ((CheckBox)findViewById(R.id.sensor5a)).isChecked());
        editor.putBoolean("sensor6a", ((CheckBox)findViewById(R.id.sensor6a)).isChecked());

        int checked = ((RadioGroup)findViewById(R.id.scale_selector)).getCheckedRadioButtonId();
        if (checked == R.id.start0){
            editor.putBoolean("start", true);
        }
        if (checked == R.id.startscale){
            editor.putBoolean("start", false);
        }

        editor.apply();

        this.finish();
    }

}
