package org.gama.applications.vibrationdetectorapp;

import android.content.Context;

import java.util.Locale;

public class PreferencesUtils {
    private Context context;
    public PreferencesUtils(Context c){
        context=c;
    }




    public String getSavedGlove(){

        switch (getSavedGloveId()){
            case R.id.config_rb_LM:
                return "LUVAMOUSE";
            case R.id.config_rb_LG:
                return "LuvaGestos";
            default:
                return "LUVAMOUSE";
        }
    }

    public int getSavedGloveId() {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getInt("glove", R.id.config_rb_LM);
    }

    public void saveGloveId(int i) {
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).edit()
                .putInt("glove", i).apply();
    }
}
