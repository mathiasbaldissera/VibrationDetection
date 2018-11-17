package org.gama.applications.vibrationdetectorapp;

import android.content.Context;

import java.util.Locale;

public class PreferencesUtils {
    private Context context;
    public PreferencesUtils(Context c){
        context=c;
    }

    /*public int getSavedLocaleCheckboxID(){
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getInt("locale", LocaleConsts.LOCALE_US);
    }*/

    /*public Locale getArchivedLocale(){
        Locale retorno;

        switch (getSavedLocaleCheckboxID()){
            case R.id.config_rb_ptBR:
                retorno = new Locale("pt", "BR");
                break;
            case R.id.config_rb_US:
                retorno = Locale.US;
                break;
            default: retorno=Locale.US;
        }
        return retorno;
    }*/

    public void saveLocaleCheckboxID(int id){
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).edit()
                .putInt("locale", id).apply();
    }



    public String getSavedGlove(){
        return "LUVAMOUSE";
    }

    /*public int getSavedGloveId() {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
                .getInt("glove", R.id.config_rb_LM);
    }*/

    public void saveGloveId(int i) {
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE).edit()
                .putInt("glove", i).apply();
    }
}
