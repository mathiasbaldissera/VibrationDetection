package org.gama.applications.vibrationdetectorapp.uncoupledprograms;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_APPEND;


public class DataSaveCsv<E> {

    //os que começam com auto são para fazer uma rotina de salvar arquivos.
    //os que começarm mannually é pq eles salvam um ponto quando o usuário clicar em save

    private ArrayList<ArrayList<E>> autoAppendGyro = new ArrayList<ArrayList<E>>();
    private ArrayList<ArrayList<E>> manuallyAppendGyro = new ArrayList<ArrayList<E>>();
    private ArrayList<ArrayList<E>> autoAppendAcc = new ArrayList<ArrayList<E>>();
    private ArrayList<ArrayList<E>> manuallyAppendAcc = new ArrayList<ArrayList<E>>();
    private String gyroAutoSaveFile;
    private String accAutoSaveFile;
    private int sizeLimit;
    private boolean isAutoSavingGyro = false;
    private boolean isAutoSavingAcc = false;

    public DataSaveCsv(int sizeLimit) {
        this.sizeLimit = sizeLimit;

    }

    private String getSaveHour(boolean acelerometro) {

        //pega a hora atual para salvar o arquivo
        String currentDate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()).replace("/", "_").replace(" ", "_").replace(":", "_");
        if (acelerometro) {
            currentDate += "acc";
        } else {
            currentDate += "gyro";
        }
        return currentDate + ".csv";
    }

    public boolean getIsAutoSavingGyro() {

        return this.isAutoSavingGyro;
    }

    public void swapAutoSavingGyro(Context context) {
        //se ele está auto salvando ele para salvar (ATENÇÃO: TROCAR ISSO POR UM set VAI DAR MERDA)

        if (!this.isAutoSavingGyro) {
            this.gyroAutoSaveFile = getSaveHour(false);
        } else {
            this.appendCsv(this.autoAppendGyro, this.gyroAutoSaveFile, context);
            this.autoAppendGyro = new ArrayList<ArrayList<E>>();
        }
        this.isAutoSavingGyro = !this.isAutoSavingGyro;
    }


    public boolean getIsAutoSavingAcc() {
        return this.isAutoSavingAcc;
    }

    public void swapAutoSavingAcc(Context context) {
        //se ele está auto salvando ele para salvar (ATENÇÃO: TROCAR ISSO POR UM set VAI DAR MERDA)
        if (!this.isAutoSavingAcc) {
            this.accAutoSaveFile = getSaveHour(true);
        } else {
            this.appendCsv(this.autoAppendAcc, this.accAutoSaveFile, context);
            this.autoAppendAcc = new ArrayList<ArrayList<E>>();
        }

        this.isAutoSavingAcc = !this.isAutoSavingAcc;
    }


    public void appendJustLineGyro(ArrayList<E> valuesToSave) {

        this.manuallyAppendGyro.add(valuesToSave);

    }

    public void appendModeGyro(ArrayList<E> valuesToSave, Context context) {
        //so adiciona ao array e quando chega a um limite ele paras
        this.autoAppendGyro.add(valuesToSave);

        if (this.sizeLimit >= this.autoAppendGyro.size()) {

            this.appendCsv(this.autoAppendGyro, this.gyroAutoSaveFile, context);
            this.autoAppendGyro = new ArrayList<ArrayList<E>>();

        }
    }


    public void appendJustLineAcc(ArrayList<E> valuesToSave) {
        this.manuallyAppendAcc.add(valuesToSave);
    }

    public void appendModeAcc(ArrayList<E> valuesToSave, Context context) {
        this.autoAppendAcc.add(valuesToSave);


        if (this.sizeLimit >= this.autoAppendAcc.size()) {

            this.appendCsv(this.autoAppendAcc, this.accAutoSaveFile, context);
            this.autoAppendAcc = new ArrayList<ArrayList<E>>();

        }

    }


    public void appendCsv(final ArrayList<ArrayList<E>> arr, final String fileName, Context context) {

        new Runnable() {
            @Override
            public void run() {

        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/GLOVEAPP/");

        //salva em um csv
        String s = "";
        for (ArrayList<E> linha : arr) {
            s += TextUtils.join(",", linha);
            s += "\n";
        }

        long initialTime=System.currentTimeMillis();

        Log.d("salvando", "inico");
        File file = new File(dir, fileName);
        try {
            dir.mkdirs();
            if (!file.exists()) {
            }
            BufferedWriter output = new BufferedWriter(new FileWriter(file.getAbsolutePath(), true));
            output.append(s);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("salvando", "fim");
        long finalTime=System.currentTimeMillis();

        Log.d("elapsed time", ""+(finalTime-initialTime));

            }
        }.run();

    }

    //public void save(boolean manuallyChoose){}

}
