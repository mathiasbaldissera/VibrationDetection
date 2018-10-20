package org.gama.applications.vibrationdetectorapp.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.gama.alessandrogirardi.comunicacao_bluetooth_luva.uncoupledprograms.DataSaveCsv;

import org.gama.applications.vibrationdetectorapp.uncoupled.GloveSensors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class BluetoothConnection {
    private BluetoothSocket mmSocket;
    private BluetoothDevice mmDevice;
    private OutputStream mmOutputStream;
    private InputStream mmInputStream;
    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 3284;

    private volatile boolean stopWorker;
    private double toRad = 10430.3783505;
    private double resist = 3276.8;
    private Activity callingActivity;
    private static BluetoothConnection ME;

    private BluetoothConnection() {
    }

    public static BluetoothConnection getInstance(Activity activity) {
        if (ME == null) {
            ME = new BluetoothConnection();
        }
        ME.callingActivity = activity;
        return ME;
    }

    private String deviceToConnect;

    public void tryToConnect(String s) throws IOException {

        deviceToConnect=s;

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(callingActivity, "No bluetooth adapter available", Toast.LENGTH_LONG).show();
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            callingActivity.startActivityForResult(enableBluetooth, REQUEST_ENABLE_BT);
        } else {

            findPairedDevices();
        }


    }


    public void disconnect() throws IOException {
        stopWorker = true;
        mmOutputStream.close();
        mmInputStream.close();
        mmSocket.close();
        mmDevice = null;
        Log.d("BT.closeBT:", "Bluetooth Closed");
    }

    public boolean isConnected() {
        if (mmSocket == null)
            return false;
        return mmSocket.isConnected();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            try {
                findPairedDevices();
            } catch (IOException e) {
                Log.d("onActivityResult", e.getMessage());
            }
        } else {
            Toast.makeText(callingActivity, "You need to turn on the Bluetooth to use the app functions with the glove", Toast.LENGTH_LONG).show();
        }

    }

    private void findPairedDevices() throws IOException {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals(deviceToConnect)) {
                    mmDevice = device;
                    break;
                }
            }
        }
        if (mmDevice == null) {
            Toast.makeText(callingActivity, "The glove isn't paired with this device", Toast.LENGTH_LONG).show();
        } else {
            connectWithTheGlove();
            readData();
        }
    }

    private void connectWithTheGlove() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //Standard SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();
        Log.d("BT.connectWithTheGlove", "Bluetooth Opened");
        // Envia o caractere 'U' para sincronizacao autobaud
        byte sinc[] = new byte[1];
        sinc[0] = 0x55;
        mmOutputStream.write(sinc);
        //mmOutputStream.write(msg.getBytes("US-ASCII"));
        Log.d("BT.sendAutoBaudCode", "Caractere U Sent");
    }

    DataSaveCsv saveCsv = new DataSaveCsv(40);


    private void readData() {

        saveCsv.swapAutoSavingGyro(callingActivity);
        stopWorker = false;

        Toast.makeText(callingActivity, "Connected", Toast.LENGTH_SHORT).show();
        Thread dataReceiverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean continua = false;
                int cont = 0;
                byte[] cod1 = new byte[1];
                byte[] cod2 = new byte[1];
                byte[] cod3 = new byte[1];
                byte[] cod4 = new byte[1];
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        //int bytesAvailable = mmInputStream.available();
                        //myLabel.setText("Nro de bytes: " + Integer.toString(bytesAvailable));
                        //if (bytesAvailable > 1) {

                        // ------------ Sincronizacao -------------
                        continua = false;
                        while (!continua && !stopWorker) {
                            mmInputStream.read(cod1);
                            if (cod1[0] == (byte) 0xFF) {  // 255
                                mmInputStream.read(cod2);
                                if (cod2[0] == (byte) 0x7F) {  // 127
                                    mmInputStream.read(cod3);
                                    if (cod3[0] == (byte) 0xFE) {  // 254
                                        mmInputStream.read(cod4);
                                        if (cod4[0] == (byte) 0xFF) {  // 255
                                            continua = true;

                                        }
                                    }
                                }
                            }
                        }

                        // ------------ FIM Sincronizacao -------------


                        // ------------ Leitura dados sensores --------------

                        cont = cont + 1;
                        GloveSensors.getInstance().appendDataWithToRadAndResist(
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue(),
                                readNextValue());

                        autoAppendValues();

                        executePostThreads();


                    } catch (IOException ex) {
                        stopWorker = true;
                        Log.d("EXCEPTION", ex.getMessage());
                        //continua=true;
                        //android.util.Log.d("YourApplicationName", ex.toString());

                    }
                }
            }
        }
        );
        dataReceiverThread.start();
    }


    private Map<IPostAppendScreen, Runnable> runnablesList = new HashMap<>();

    private void executePostThreads() {

        for (IPostAppendScreen p : runnablesList.keySet()) {
            Runnable r = runnablesList.get(p);
            if (r == null)
                return;
            callingActivity.runOnUiThread(r);
            System.out.println("graph append");
        }
    }


    public void putPutDataAppendRunnable(IPostAppendScreen screen) {
        runnablesList.put(screen, screen.getPostAppendRunnable());
    }

    public void removeMe(IPostAppendScreen screen) {
        runnablesList.remove(screen);
    }


    private double readNextValue() {
        try {
            byte[] buffer_low = new byte[1];
            byte[] buffer_high = new byte[1];
            //byte[] buffer_full = new byte[4];
            mmInputStream.read(buffer_low);
            mmInputStream.read(buffer_high);
            ByteBuffer wrapped = ByteBuffer.wrap((new byte[]{0, 0, buffer_high[0], buffer_low[0]}));
            double num_double = wrapped.getInt();
            double a = Math.pow(2, 15) - 1;
            if (num_double > (a)) {
                num_double = num_double - Math.pow(2, 16);
            }
            return num_double;
        } catch (IOException ex) {
            return 0;
        }
    }


    private void  autoAppendValues(){

        final GloveSensors glove = GloveSensors.getInstance();
        if (saveCsv.getIsAutoSavingGyro()){

            ArrayList<Double> arrGyro = new ArrayList<Double>(){{
                add(glove.getSensor1().getGx().lastElement());
                add(glove.getSensor1().getGy().lastElement());
                add(glove.getSensor1().getGz().lastElement());
                add(glove.getSensor2().getGx().lastElement());
                add(glove.getSensor2().getGy().lastElement());
                add(glove.getSensor2().getGz().lastElement());
                add(glove.getSensor3().getGx().lastElement());
                add(glove.getSensor3().getGy().lastElement());
                add(glove.getSensor3().getGz().lastElement());
                add(glove.getSensor4().getGx().lastElement());
                add(glove.getSensor4().getGy().lastElement());
                add(glove.getSensor4().getGz().lastElement());
                add(glove.getSensor5().getGx().lastElement());
                add(glove.getSensor5().getGy().lastElement());
                add(glove.getSensor5().getGz().lastElement());
                add(glove.getSensor6().getGx().lastElement());
                add(glove.getSensor6().getGy().lastElement());
                add(glove.getSensor6().getGz().lastElement());

            }};

            saveCsv.appendModeGyro(arrGyro,callingActivity);

        }

        if(saveCsv.getIsAutoSavingAcc()){
            ArrayList<Double> arrAcc = new ArrayList<Double>(){{
                add(glove.getSensor1().getAx().lastElement());
                add(glove.getSensor1().getAy().lastElement());
                add(glove.getSensor1().getAz().lastElement());
                add(glove.getSensor2().getAx().lastElement());
                add(glove.getSensor2().getAy().lastElement());
                add(glove.getSensor2().getAz().lastElement());
                add(glove.getSensor3().getAx().lastElement());
                add(glove.getSensor3().getAy().lastElement());
                add(glove.getSensor3().getAz().lastElement());
                add(glove.getSensor4().getAx().lastElement());
                add(glove.getSensor4().getAy().lastElement());
                add(glove.getSensor4().getAz().lastElement());
                add(glove.getSensor5().getAx().lastElement());
                add(glove.getSensor5().getAy().lastElement());
                add(glove.getSensor5().getAz().lastElement());
                add(glove.getSensor6().getAx().lastElement());
                add(glove.getSensor6().getAy().lastElement());
                add(glove.getSensor6().getAz().lastElement());

            }};
            saveCsv.appendModeAcc(arrAcc,callingActivity);

        }
        Log.d("teste", "teta");
    }
}















