package org.gama.applications.vibrationdetectorapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RadioGroup;

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



        RadioGroup rg2 = findViewById(R.id.configs_rg2);
        rg2.check(new PreferencesUtils(this).getSavedGloveId());
        Log.d("bbbb", new PreferencesUtils(this).getSavedGloveId()+"") ;
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d("aaa", i+"");
                new PreferencesUtils(ConfigsActivity.this).saveGloveId(i);
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
        this.finish();
    }
}
