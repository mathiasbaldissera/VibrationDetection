package org.gama.applications.vibrationdetectorapp.uncoupledprograms;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import org.gama.applications.vibrationdetectorapp.PreferencesUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class DataSaveCsv<E> {

    //os que começam com auto são para fazer uma rotina de salvar arquivos.
    //os que começarm mannually é pq eles salvam um ponto quando o usuário clicar em save

    private ArrayList<ArrayList<E>> autoAppendGyroList = new ArrayList<ArrayList<E>>();
    private ArrayList<ArrayList<E>> manuallyAppendGyroList = new ArrayList<ArrayList<E>>();
    private ArrayList<ArrayList<E>> autoAppendAccList = new ArrayList<ArrayList<E>>();
    private ArrayList<ArrayList<E>> manuallyAppendAccList = new ArrayList<ArrayList<E>>();
    private String gyroAutoSaveFileName;
    private String accAutoSaveFileName;
    private int sizeLimit;
    private boolean isAutoSavingGyro = false;
    private boolean isAutoSavingAcc = false;
    private static DataSaveCsv ME;
    private static long lastSaveMilliseconds;
    private int saveInterval;

    public static DataSaveCsv getInstance(Context context) {
        if (ME == null)
            ME = new DataSaveCsv(40);
        ME.saveInterval=new PreferencesUtils(context).getTimeIntervalValue();
        return ME;
    }

    private DataSaveCsv(int sizeLimit) {
        this.sizeLimit = sizeLimit;

    }

    /**
     * make a file name to save accelerometer data, using current time plus "acc.csv"
     * @return the archive name
     */
    private String getAccAutoSaveFileName() {
        String fileName = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()).replace("/", "_").replace(" ", "_").replace(":", "_");
        fileName += "acc.csv";
        return fileName;
    }


    /**
     * make a file name to save gyroscope data, using current time plus "gyro.csv"
     * @return the archive name
     */
    private String getGyroAutoSaveFileName() {
        String fileName = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()).replace("/", "_").replace(" ", "_").replace(":", "_");
        fileName += "gyro.csv";
        return fileName;
    }

    /**
     * turn on the auto saving gyroscope data feature
     */
    public void turnOnAutoSavingGyro() {
        Log.d("turnOnAutoSavingGyro", "turnOn");
        if (!this.isAutoSavingGyro) {
            this.gyroAutoSaveFileName = getGyroAutoSaveFileName();
            this.isAutoSavingGyro = true;
        }
    }

    /**
     * turn off the auto saving gyroscope data feature, save the pendent data and reset the list
     */
    public void turnOffAutoSavingGyro() {
        Log.d("turnOnAutoSavingGyro", "turnOff");
        if (this.isAutoSavingGyro) {
            this.appendToCsv(this.autoAppendGyroList, this.gyroAutoSaveFileName);
            this.autoAppendGyroList = new ArrayList<ArrayList<E>>();
            this.isAutoSavingGyro = false;
        }

    }

    /**
     * make the autosave function save on new files, changing the files name.
     */
    public String saveToNewFile(){
        this.gyroAutoSaveFileName = getGyroAutoSaveFileName();
        this.accAutoSaveFileName = getAccAutoSaveFileName();
        return gyroAutoSaveFileName.replace("gyro.csv","");
    }

    /**
     * turn on the auto saving accelerometer data feature
     */
    public void turnOnAutoSavingAcc() {
        if (!this.isAutoSavingAcc) {
            this.accAutoSaveFileName = getAccAutoSaveFileName();
            this.isAutoSavingAcc = true;
        }
    }

    /**
     * turn off the auto saving accelerometer data feature and save the pendent data
     */
    public void turnOffAutoSavingAcc() {
        if (this.isAutoSavingAcc) {
            this.appendToCsv(this.autoAppendAccList, this.accAutoSaveFileName);
            this.autoAppendAccList = new ArrayList<ArrayList<E>>();
            this.isAutoSavingAcc = false;
        }
    }


    public void appendGyroData(ArrayList<E> valuesToSave) {
        //so adiciona ao array e quando chega a um limite ele paras
        this.autoAppendGyroList.add(valuesToSave);
        if (this.sizeLimit >= this.autoAppendGyroList.size()) {
            this.appendToCsv(this.autoAppendGyroList, this.gyroAutoSaveFileName);
            this.autoAppendGyroList = new ArrayList<ArrayList<E>>();
        }
    }

    public void appendAccData(ArrayList<E> valuesToSave) {
        this.autoAppendAccList.add(valuesToSave);
        if (this.sizeLimit >= this.autoAppendAccList.size()) {
            this.appendToCsv(this.autoAppendAccList, this.accAutoSaveFileName);
            this.autoAppendAccList = new ArrayList<ArrayList<E>>();
        }
    }


    public void appendToCsv(final ArrayList<ArrayList<E>> arr, final String fileName) {
        new Runnable() {
            @Override
            public void run() {
                long currentTime=System.currentTimeMillis();
                if(TimeUnit.MILLISECONDS.toMinutes(lastSaveMilliseconds-currentTime)>=saveInterval){
                    saveToNewFile();
                    lastSaveMilliseconds=currentTime;
                }
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/VibrationDetectionExportedFiles/");
                String s = "";
                for (ArrayList<E> linha : arr) {
                    s += TextUtils.join(",", linha);
                    s += "\n";
                }
                File file = new File(dir, fileName);
                try {
                    dir.mkdirs();
                    BufferedWriter output = new BufferedWriter(new FileWriter(file.getAbsolutePath(), true));
                    output.append(s);
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.run();

    }

    public void appendJustLineGyro(ArrayList<E> valuesToSave) {
        this.manuallyAppendGyroList.add(valuesToSave);
    }

    public void appendJustLineAcc(ArrayList<E> valuesToSave) {
        this.manuallyAppendAccList.add(valuesToSave);
    }

    public boolean isAutoSavingAcc() {
        return this.isAutoSavingAcc;
    }

    public boolean isAutoSavingGyro() {
        return this.isAutoSavingGyro;
    }
    public int getSaveInterval() {
        return saveInterval;
    }

    public void setSaveInterval(int saveInterval) {
        this.saveInterval = saveInterval;
    }

}
