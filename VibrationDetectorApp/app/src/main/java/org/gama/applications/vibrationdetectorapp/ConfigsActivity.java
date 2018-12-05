package org.gama.applications.vibrationdetectorapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.util.Log;

import org.gama.applications.vibrationdetectorapp.uncoupledprograms.DataSaveCsv;


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

        final PreferencesUtils prefUtils = new PreferencesUtils(this);


        //todo set chart size spinner indice
        //todo set time interval spinner indice


        ((CheckBox) findViewById(R.id.sensor1g)).setChecked(prefUtils.getAnyBoolean("sensor1gCbxChecked", true));
        ((CheckBox) findViewById(R.id.sensor2g)).setChecked(prefUtils.getAnyBoolean("sensor2gCbxChecked", true));
        ((CheckBox) findViewById(R.id.sensor3g)).setChecked(prefUtils.getAnyBoolean("sensor3gCbxChecked", true));
        ((CheckBox) findViewById(R.id.sensor4g)).setChecked(prefUtils.getAnyBoolean("sensor4gCbxChecked", true));
        ((CheckBox) findViewById(R.id.sensor5g)).setChecked(prefUtils.getAnyBoolean("sensor5gCbxChecked", true));
        ((CheckBox) findViewById(R.id.sensor6g)).setChecked(prefUtils.getAnyBoolean("sensor6gCbxChecked", true));

        ((CheckBox) findViewById(R.id.sensor1a)).setChecked(prefUtils.getAnyBoolean("sensor1aCbxChecked", true));
        ((CheckBox) findViewById(R.id.sensor2a)).setChecked(prefUtils.getAnyBoolean("sensor2aCbxChecked", true));
        ((CheckBox) findViewById(R.id.sensor3a)).setChecked(prefUtils.getAnyBoolean("sensor3aCbxChecked", true));
        ((CheckBox) findViewById(R.id.sensor4a)).setChecked(prefUtils.getAnyBoolean("sensor4aCbxChecked", true));
        ((CheckBox) findViewById(R.id.sensor5a)).setChecked(prefUtils.getAnyBoolean("sensor5aCbxChecked", true));
        ((CheckBox) findViewById(R.id.sensor6a)).setChecked(prefUtils.getAnyBoolean("sensor6aCbxChecked", true));


        CheckBox saveAccDataCbx = findViewById(R.id.save_acc_data_cbx);
        CheckBox saveGyroDataCbx = findViewById(R.id.save_gyro_data_cbx);

        saveAccDataCbx.setChecked(prefUtils.isSavingAccEnabled());
        saveGyroDataCbx.setChecked(prefUtils.isSavingGyroEnabled());

        saveAccDataCbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prefUtils.setIsSavingAccEnabled(b);
            }
        });

        saveGyroDataCbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                prefUtils.setIsSavingGyroEnabled(b);
            }
        });


        if (prefUtils.isGraphViewScaleStaringFromZero()) {
            ((RadioGroup) findViewById(R.id.scale_selector)).check(R.id.start0);
        } else {
            ((RadioGroup) findViewById(R.id.scale_selector)).check(R.id.startscale);
        }

        ((RadioGroup) findViewById(R.id.scale_selector)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                Log.d("scale_selector", "" + checkedId);
                if (checkedId == R.id.start0) {
                    prefUtils.setGraphViewScaleStaringFromZero(true);
                } else {
                    prefUtils.setGraphViewScaleStaringFromZero(false);
                }
            }
        });


        Spinner graph_scale = findViewById(R.id.graph_scale);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.scale_in_g));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        graph_scale.setAdapter(spinnerArrayAdapter);
        graph_scale.setSelection(prefUtils.getGraphScaleIndex());
        graph_scale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prefUtils.setGraphScaleIndex(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final Spinner time_interval = findViewById(R.id.time_interval);
        time_interval.setSelection(prefUtils.getTimeIntervalIndex());
        time_interval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                prefUtils.setTimeIntervalIndex(i);
                DataSaveCsv.getInstance(ConfigsActivity.this).setSaveInterval(Integer.valueOf((String) time_interval.getSelectedItem()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
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

        PreferencesUtils prefUtils = new PreferencesUtils(this);

        prefUtils.saveAnyBoolean("sensor1gCbxChecked", ((CheckBox) findViewById(R.id.sensor1g)).isChecked());
        prefUtils.saveAnyBoolean("sensor2gCbxChecked", ((CheckBox) findViewById(R.id.sensor2g)).isChecked());
        prefUtils.saveAnyBoolean("sensor3gCbxChecked", ((CheckBox) findViewById(R.id.sensor3g)).isChecked());
        prefUtils.saveAnyBoolean("sensor4gCbxChecked", ((CheckBox) findViewById(R.id.sensor4g)).isChecked());
        prefUtils.saveAnyBoolean("sensor5gCbxChecked", ((CheckBox) findViewById(R.id.sensor5g)).isChecked());
        prefUtils.saveAnyBoolean("sensor6gCbxChecked", ((CheckBox) findViewById(R.id.sensor6g)).isChecked());

        prefUtils.saveAnyBoolean("sensor1aCbxChecked", ((CheckBox) findViewById(R.id.sensor1a)).isChecked());
        prefUtils.saveAnyBoolean("sensor2aCbxChecked", ((CheckBox) findViewById(R.id.sensor2a)).isChecked());
        prefUtils.saveAnyBoolean("sensor3aCbxChecked", ((CheckBox) findViewById(R.id.sensor3a)).isChecked());
        prefUtils.saveAnyBoolean("sensor4aCbxChecked", ((CheckBox) findViewById(R.id.sensor4a)).isChecked());
        prefUtils.saveAnyBoolean("sensor5aCbxChecked", ((CheckBox) findViewById(R.id.sensor5a)).isChecked());
        prefUtils.saveAnyBoolean("sensor6aCbxChecked", ((CheckBox) findViewById(R.id.sensor6a)).isChecked());

        this.finish();
    }

}
