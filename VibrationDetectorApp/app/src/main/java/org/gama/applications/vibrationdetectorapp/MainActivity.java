package org.gama.applications.vibrationdetectorapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.gama.applications.vibrationdetectorapp.bluetooth.BluetoothConnection;
import org.gama.applications.vibrationdetectorapp.bluetooth.IPostAppendScreen;
import org.gama.applications.vibrationdetectorapp.uncoupled.GloveSensors;
import org.gama.applications.vibrationdetectorapp.uncoupled.IPredictScreen;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ConcurrentModificationException;


public class MainActivity extends AppCompatActivity implements IPostAppendScreen {


    private BluetoothConnection bluetoothConnection = BluetoothConnection.getInstance(this);


    private GraphView mGraphx;
    private GraphView mGraphy;
    private GraphView mGraphz;
    private GraphView mGraphx_acc;
    private GraphView mGraphy_acc;
    private GraphView mGraphz_acc;
    private CheckBox cbg1;
    private CheckBox cbg2;
    private CheckBox cbg3;
    private CheckBox cbg4;
    private CheckBox cbg5;
    private CheckBox cbg6;
    private CheckBox cba1;
    private CheckBox cba2;
    private CheckBox cba3;
    private CheckBox cba4;
    private CheckBox cba5;
    private CheckBox cba6;

    static int max_x_points = 500;

    // Linhas giroscopios
    static LineGraphSeries<DataPoint> seriesx1 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy1 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz1 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesx2 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy2 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz2 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesx3 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy3 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz3 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesx4 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy4 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz4 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesx5 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy5 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz5 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesx6 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy6 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz6 = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});


    //accelerometros


    static LineGraphSeries<DataPoint> seriesx1_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy1_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz1_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesx2_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy2_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz2_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesx3_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy3_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz3_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesx4_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy4_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz4_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesx5_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy5_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz5_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesx6_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy6_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz6_acc = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, 0)});



    public static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        //configButton
        Button configButton = findViewById(R.id.btn_config);
        configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfigsActivity.class);
                startActivity(intent);
            }
        });


        //Open Button
        Button openButton = findViewById(R.id.btn_open);
        openButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (!PermissionUtils.INSTANCE.validate(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(MainActivity.this, "Please, give storage permission",Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        bluetoothConnection.tryToConnect(new PreferencesUtils(MainActivity.this).getSavedGlove());
                    } catch (IOException ex) {
                        Log.d("openButtonEX", ex.getMessage());
                    }
                }
            }
        });

        //Close button
        Button closeButton = findViewById(R.id.btn_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if(bluetoothConnection.isConnected())
                    bluetoothConnection.disconnect();
                    else
                        Toast.makeText(MainActivity.this, "Not Connected", Toast.LENGTH_SHORT).show();
                } catch (IOException ex) {
                    Log.d("closeButtonEX", ex.getMessage());

                }
            }
        });




        seriesx1.setColor(Color.GREEN);
        seriesy1.setColor(Color.GREEN);
        seriesz1.setColor(Color.GREEN);
        findViewById(R.id.csg1).setBackgroundColor(Color.GREEN);
        seriesx2.setColor(Color.RED);
        seriesy2.setColor(Color.RED);
        seriesz2.setColor(Color.RED);
        findViewById(R.id.csg2).setBackgroundColor(Color.RED);
        seriesx3.setColor(Color.BLUE);
        seriesy3.setColor(Color.BLUE);
        seriesz3.setColor(Color.BLUE);
        findViewById(R.id.csg3).setBackgroundColor(Color.BLUE);
        seriesx4.setColor(Color.YELLOW);
        seriesy4.setColor(Color.YELLOW);
        seriesz4.setColor(Color.YELLOW);
        findViewById(R.id.csg4).setBackgroundColor(Color.YELLOW);
        seriesx5.setColor(Color.MAGENTA);
        seriesy5.setColor(Color.MAGENTA);
        seriesz5.setColor(Color.MAGENTA);
        findViewById(R.id.csg5).setBackgroundColor(Color.MAGENTA);
        seriesx6.setColor(Color.GRAY);
        seriesy6.setColor(Color.GRAY);
        seriesz6.setColor(Color.GRAY);
        findViewById(R.id.csg6).setBackgroundColor(Color.GRAY);

        seriesx1_acc.setColor(Color.GREEN);
        seriesy1_acc.setColor(Color.GREEN);
        seriesz1_acc.setColor(Color.GREEN);
        findViewById(R.id.csa1).setBackgroundColor(Color.GREEN);
        seriesx2_acc.setColor(Color.RED);
        seriesy2_acc.setColor(Color.RED);
        seriesz2_acc.setColor(Color.RED);
        findViewById(R.id.csa2).setBackgroundColor(Color.RED);
        seriesx3_acc.setColor(Color.BLUE);
        seriesy3_acc.setColor(Color.BLUE);
        seriesz3_acc.setColor(Color.BLUE);
        findViewById(R.id.csa3).setBackgroundColor(Color.BLUE);
        seriesx4_acc.setColor(Color.YELLOW);
        seriesy4_acc.setColor(Color.YELLOW);
        seriesz4_acc.setColor(Color.YELLOW);
        findViewById(R.id.csa4).setBackgroundColor(Color.YELLOW);
        seriesx5_acc.setColor(Color.MAGENTA);
        seriesy5_acc.setColor(Color.MAGENTA);
        seriesz5_acc.setColor(Color.MAGENTA);
        findViewById(R.id.csa5).setBackgroundColor(Color.MAGENTA);
        seriesx6_acc.setColor(Color.GRAY);
        seriesy6_acc.setColor(Color.GRAY);
        seriesz6_acc.setColor(Color.GRAY);
        findViewById(R.id.csa6).setBackgroundColor(Color.GRAY);

// ------------- Gráfico GIRO--------------
        mGraphx = findViewById(R.id.graphx_giro);
        mGraphy = findViewById(R.id.graphy_giro);
        mGraphz = findViewById(R.id.graphz_giro);


        mGraphx.setTitle("Gyroscopes axis X");
        mGraphx.addSeries(seriesx1);
        mGraphx.addSeries(seriesx2);
        mGraphx.addSeries(seriesx3);
        mGraphx.addSeries(seriesx4);
        mGraphx.addSeries(seriesx5);
        mGraphx.addSeries(seriesx6);

        mGraphy.setTitle("Gyroscopes axis Y");
        mGraphy.addSeries(seriesy1);
        mGraphy.addSeries(seriesy2);
        mGraphy.addSeries(seriesy3);
        mGraphy.addSeries(seriesy4);
        mGraphy.addSeries(seriesy5);
        mGraphy.addSeries(seriesy6);

        mGraphz.setTitle("Gyroscopes axis Z");
        mGraphz.addSeries(seriesz1);
        mGraphz.addSeries(seriesz2);
        mGraphz.addSeries(seriesz3);
        mGraphz.addSeries(seriesz4);
        mGraphz.addSeries(seriesz5);
        mGraphz.addSeries(seriesz6);

        mGraphx.getViewport().setXAxisBoundsManual(true);
        mGraphx.getViewport().setYAxisBoundsManual(true);
        mGraphx.getViewport().setMinX(0);
        mGraphx.getViewport().setMaxX(max_x_points);
        mGraphx.getViewport().setMinY(-3.2);
        mGraphx.getViewport().setMaxY(3.2);

        mGraphy.getViewport().setXAxisBoundsManual(true);
        mGraphy.getViewport().setYAxisBoundsManual(true);
        mGraphy.getViewport().setMinX(0);
        mGraphy.getViewport().setMaxX(max_x_points);
        mGraphy.getViewport().setMinY(-3.2);
        mGraphy.getViewport().setMaxY(3.2);

        mGraphz.getViewport().setXAxisBoundsManual(true);
        mGraphz.getViewport().setYAxisBoundsManual(true);
        mGraphz.getViewport().setMinX(0);
        mGraphz.getViewport().setMaxX(max_x_points);
        mGraphz.getViewport().setMinY(-3.2);
        mGraphz.getViewport().setMaxY(3.2);


        // -------------Fim Gráfico Giro--------------

        // ------------- Gráfico ACC--------------


        mGraphx_acc = findViewById(R.id.graphx_acc);
        mGraphy_acc = findViewById(R.id.graphy_acc);
        mGraphz_acc = findViewById(R.id.graphz_acc);

        mGraphx_acc.setTitle("Accelerometers axis X");
        mGraphx_acc.addSeries(seriesx1_acc);
        mGraphx_acc.addSeries(seriesx2_acc);
        mGraphx_acc.addSeries(seriesx3_acc);
        mGraphx_acc.addSeries(seriesx4_acc);
        mGraphx_acc.addSeries(seriesx5_acc);
        mGraphx_acc.addSeries(seriesx6_acc);

        mGraphy_acc.setTitle("Accelerometers axis Y");
        mGraphy_acc.addSeries(seriesy1_acc);
        mGraphy_acc.addSeries(seriesy2_acc);
        mGraphy_acc.addSeries(seriesy3_acc);
        mGraphy_acc.addSeries(seriesy4_acc);
        mGraphy_acc.addSeries(seriesy5_acc);
        mGraphy_acc.addSeries(seriesy6_acc);

        mGraphz_acc.setTitle("Accelerometers axis Z");
        mGraphz_acc.addSeries(seriesz1_acc);
        mGraphz_acc.addSeries(seriesz2_acc);
        mGraphz_acc.addSeries(seriesz3_acc);
        mGraphz_acc.addSeries(seriesz4_acc);
        mGraphz_acc.addSeries(seriesz5_acc);
        mGraphz_acc.addSeries(seriesz6_acc);


        mGraphx_acc.getViewport().setXAxisBoundsManual(true);
        mGraphx_acc.getViewport().setYAxisBoundsManual(true);
        mGraphx_acc.getViewport().setMinX(0);
        mGraphx_acc.getViewport().setMaxX(max_x_points);
        mGraphx_acc.getViewport().setMinY(-10);
        mGraphx_acc.getViewport().setMaxY(10);

        mGraphy_acc.getViewport().setXAxisBoundsManual(true);
        mGraphy_acc.getViewport().setYAxisBoundsManual(true);
        mGraphy_acc.getViewport().setMinX(0);
        mGraphy_acc.getViewport().setMaxX(max_x_points);
        mGraphy_acc.getViewport().setMinY(-10);
        mGraphy_acc.getViewport().setMaxY(10);

        mGraphz_acc.getViewport().setXAxisBoundsManual(true);
        mGraphz_acc.getViewport().setYAxisBoundsManual(true);
        mGraphz_acc.getViewport().setMinX(0);
        mGraphz_acc.getViewport().setMaxX(max_x_points);
        mGraphz_acc.getViewport().setMinY(-10);
        mGraphz_acc.getViewport().setMaxY(10);
        // -------------Fim Gráfico Giro--------------


        // -------------Boxes----------


        cbg1 = findViewById(R.id.checkBox_sg1);
        cbg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbg1.isChecked()) {
                    if (!mGraphx.getSeries().contains(seriesx1)) {
                        mGraphx.addSeries(seriesx1);
                        mGraphy.addSeries(seriesy1);
                        mGraphz.addSeries(seriesz1);
                    }

                } else {
                    mGraphx.removeSeries(seriesx1);
                    mGraphy.removeSeries(seriesy1);
                    mGraphz.removeSeries(seriesz1);
                }
            }
        });

        cbg2 = findViewById(R.id.checkBox_sg2);
        cbg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbg2.isChecked()) {
                    if (!mGraphx.getSeries().contains(seriesx2)) {
                        mGraphx.addSeries(seriesx2);
                        mGraphy.addSeries(seriesy2);
                        mGraphz.addSeries(seriesz2);
                    }

                } else {
                    mGraphx.removeSeries(seriesx2);
                    mGraphy.removeSeries(seriesy2);
                    mGraphz.removeSeries(seriesz2);
                }
            }
        });

        cbg3 = findViewById(R.id.checkBox_sg3);
        cbg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbg3.isChecked()) {
                    if (!mGraphx.getSeries().contains(seriesx3)) {
                        mGraphx.addSeries(seriesx3);
                        mGraphy.addSeries(seriesy3);
                        mGraphz.addSeries(seriesz3);
                    }

                } else {
                    mGraphx.removeSeries(seriesx3);
                    mGraphy.removeSeries(seriesy3);
                    mGraphz.removeSeries(seriesz3);
                }
            }
        });

        cbg4 = findViewById(R.id.checkBox_sg4);
        cbg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbg4.isChecked()) {
                    if (!mGraphx.getSeries().contains(seriesx4)) {
                        mGraphx.addSeries(seriesx4);
                        mGraphy.addSeries(seriesy4);
                        mGraphz.addSeries(seriesz4);
                    }
                } else {
                    mGraphx.removeSeries(seriesx4);
                    mGraphy.removeSeries(seriesy4);
                    mGraphz.removeSeries(seriesz4);
                }
            }
        });

        cbg5 = findViewById(R.id.checkBox_sg5);
        cbg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbg5.isChecked()) {
                    if (!mGraphx.getSeries().contains(seriesx5)) {
                        mGraphx.addSeries(seriesx5);
                        mGraphy.addSeries(seriesy5);
                        mGraphz.addSeries(seriesz5);
                    }
                } else {
                    mGraphx.removeSeries(seriesx5);
                    mGraphy.removeSeries(seriesy5);
                    mGraphz.removeSeries(seriesz5);
                }
            }
        });

        cbg6 = findViewById(R.id.checkBox_sg6);
        cbg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbg6.isChecked()) {
                    if (!mGraphx.getSeries().contains(seriesx6)) {

                        mGraphx.addSeries(seriesx6);
                        mGraphy.addSeries(seriesy6);
                        mGraphz.addSeries(seriesz6);
                    }
                } else {
                    mGraphx.removeSeries(seriesx6);
                    mGraphy.removeSeries(seriesy6);
                    mGraphz.removeSeries(seriesz6);
                }
            }
        });


        cba1 = findViewById(R.id.checkBox_sa1);
        cba1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cba1.isChecked()) {
                    if (!mGraphx_acc.getSeries().contains(seriesx1_acc)) {
                        mGraphx_acc.addSeries(seriesx1_acc);
                        mGraphy_acc.addSeries(seriesy1_acc);
                        mGraphz_acc.addSeries(seriesz1_acc);
                    }
                } else {
                    mGraphx_acc.removeSeries(seriesx1_acc);
                    mGraphy_acc.removeSeries(seriesy1_acc);
                    mGraphz_acc.removeSeries(seriesz1_acc);
                }
            }
        });

        cba2 = findViewById(R.id.checkBox_sa2);
        cba2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cba2.isChecked()) {
                    if (!mGraphx_acc.getSeries().contains(seriesx2_acc)) {
                        mGraphx_acc.addSeries(seriesx2_acc);
                        mGraphy_acc.addSeries(seriesy2_acc);
                        mGraphz_acc.addSeries(seriesz2_acc);
                    }
                } else {
                    mGraphx_acc.removeSeries(seriesx2_acc);
                    mGraphy_acc.removeSeries(seriesy2_acc);
                    mGraphz_acc.removeSeries(seriesz2_acc);
                }
            }
        });

        cba3 = findViewById(R.id.checkBox_sa3);
        cba3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cba3.isChecked()) {
                    if (!mGraphx_acc.getSeries().contains(seriesx3_acc)) {
                        mGraphx_acc.addSeries(seriesx3_acc);
                        mGraphy_acc.addSeries(seriesy3_acc);
                        mGraphz_acc.addSeries(seriesz3_acc);
                    }
                } else {
                    mGraphx_acc.removeSeries(seriesx3_acc);
                    mGraphy_acc.removeSeries(seriesy3_acc);
                    mGraphz_acc.removeSeries(seriesz3_acc);
                }
            }
        });

        cba4 = findViewById(R.id.checkBox_sa4);

        cba4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cba4.isChecked()) {
                    if (!mGraphx_acc.getSeries().contains(seriesx4_acc)) {
                        mGraphx_acc.addSeries(seriesx4_acc);
                        mGraphy_acc.addSeries(seriesy4_acc);
                        mGraphz_acc.addSeries(seriesz4_acc);
                    }
                } else {
                    mGraphx_acc.removeSeries(seriesx4_acc);
                    mGraphy_acc.removeSeries(seriesy4_acc);
                    mGraphz_acc.removeSeries(seriesz4_acc);
                }
            }
        });

        cba5 = findViewById(R.id.checkBox_sa5);
        cba5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cba5.isChecked()) {
                    if (!mGraphx_acc.getSeries().contains(seriesx5_acc)) {
                        mGraphx_acc.addSeries(seriesx5_acc);
                        mGraphy_acc.addSeries(seriesy5_acc);
                        mGraphz_acc.addSeries(seriesz5_acc);
                    }
                } else {
                    mGraphx_acc.removeSeries(seriesx5_acc);
                    mGraphy_acc.removeSeries(seriesy5_acc);
                    mGraphz_acc.removeSeries(seriesz5_acc);
                }
            }
        });

        cba6 = findViewById(R.id.checkBox_sa6);
        cba6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cba6.isChecked()) {
                    if (!mGraphx_acc.getSeries().contains(seriesx6_acc)) {
                        mGraphx_acc.addSeries(seriesx6_acc);
                        mGraphy_acc.addSeries(seriesy6_acc);
                        mGraphz_acc.addSeries(seriesz6_acc);
                    }
                } else {
                    mGraphx_acc.removeSeries(seriesx6_acc);
                    mGraphy_acc.removeSeries(seriesy6_acc);
                    mGraphz_acc.removeSeries(seriesz6_acc);
                }
            }
        });


        final LinearLayout llg = findViewById(R.id.graphs_containerG);
        final LinearLayout lla = findViewById(R.id.graphs_containerA);

        Button btn_acc = findViewById(R.id.btn_acc);
        btn_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llg.setVisibility(View.GONE);
                lla.setVisibility(View.VISIBLE);
            }
        });
        Button btn_gyro = findViewById(R.id.btn_giros);
        btn_gyro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lla.setVisibility(View.GONE);
                llg.setVisibility(View.VISIBLE);
            }
        });


        BluetoothConnection.getInstance(this).putPutDataAppendRunnable(this);
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
    public void onDestroy() {
        BluetoothConnection.getInstance(this).removeMe(this);
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    public Runnable getPostAppendRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    seriesx1.appendData(new DataPoint(GloveSensors.getInstance().getSensor1().getGx().getRealSize(), GloveSensors.getInstance().getSensor1().getGx().lastElement()), true, max_x_points);
                    seriesy1.appendData(new DataPoint(GloveSensors.getInstance().getSensor1().getGy().getRealSize(), GloveSensors.getInstance().getSensor1().getGy().lastElement()), true, max_x_points);
                    seriesz1.appendData(new DataPoint(GloveSensors.getInstance().getSensor1().getGz().getRealSize(), GloveSensors.getInstance().getSensor1().getGz().lastElement()), true, max_x_points);

                    seriesx2.appendData(new DataPoint(GloveSensors.getInstance().getSensor2().getGx().getRealSize(), GloveSensors.getInstance().getSensor2().getGx().lastElement()), true, max_x_points);
                    seriesy2.appendData(new DataPoint(GloveSensors.getInstance().getSensor2().getGy().getRealSize(), GloveSensors.getInstance().getSensor2().getGy().lastElement()), true, max_x_points);
                    seriesz2.appendData(new DataPoint(GloveSensors.getInstance().getSensor2().getGz().getRealSize(), GloveSensors.getInstance().getSensor2().getGz().lastElement()), true, max_x_points);

                    seriesx3.appendData(new DataPoint(GloveSensors.getInstance().getSensor3().getGx().getRealSize(), GloveSensors.getInstance().getSensor3().getGx().lastElement()), true, max_x_points);
                    seriesy3.appendData(new DataPoint(GloveSensors.getInstance().getSensor3().getGy().getRealSize(), GloveSensors.getInstance().getSensor3().getGy().lastElement()), true, max_x_points);
                    seriesz3.appendData(new DataPoint(GloveSensors.getInstance().getSensor3().getGz().getRealSize(), GloveSensors.getInstance().getSensor3().getGz().lastElement()), true, max_x_points);

                    seriesx4.appendData(new DataPoint(GloveSensors.getInstance().getSensor4().getGx().getRealSize(), GloveSensors.getInstance().getSensor4().getGx().lastElement()), true, max_x_points);
                    seriesy4.appendData(new DataPoint(GloveSensors.getInstance().getSensor4().getGy().getRealSize(), GloveSensors.getInstance().getSensor4().getGy().lastElement()), true, max_x_points);
                    seriesz4.appendData(new DataPoint(GloveSensors.getInstance().getSensor4().getGz().getRealSize(), GloveSensors.getInstance().getSensor4().getGz().lastElement()), true, max_x_points);

                    seriesx5.appendData(new DataPoint(GloveSensors.getInstance().getSensor5().getGx().getRealSize(), GloveSensors.getInstance().getSensor5().getGx().lastElement()), true, max_x_points);
                    seriesy5.appendData(new DataPoint(GloveSensors.getInstance().getSensor5().getGy().getRealSize(), GloveSensors.getInstance().getSensor5().getGy().lastElement()), true, max_x_points);
                    seriesz5.appendData(new DataPoint(GloveSensors.getInstance().getSensor5().getGz().getRealSize(), GloveSensors.getInstance().getSensor5().getGz().lastElement()), true, max_x_points);

                    seriesx6.appendData(new DataPoint(GloveSensors.getInstance().getSensor6().getGx().getRealSize(), GloveSensors.getInstance().getSensor6().getGx().lastElement()), true, max_x_points);
                    seriesy6.appendData(new DataPoint(GloveSensors.getInstance().getSensor6().getGy().getRealSize(), GloveSensors.getInstance().getSensor6().getGy().lastElement()), true, max_x_points);
                    seriesz6.appendData(new DataPoint(GloveSensors.getInstance().getSensor6().getGz().getRealSize(), GloveSensors.getInstance().getSensor6().getGz().lastElement()), true, max_x_points);

                    seriesx1_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor1().getAx().getRealSize(), GloveSensors.getInstance().getSensor1().getAx().lastElement()), true, max_x_points);
                    seriesy1_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor1().getAy().getRealSize(), GloveSensors.getInstance().getSensor1().getAy().lastElement()), true, max_x_points);
                    seriesz1_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor1().getAz().getRealSize(), GloveSensors.getInstance().getSensor1().getAz().lastElement()), true, max_x_points);

                    seriesx2_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor2().getAx().getRealSize(), GloveSensors.getInstance().getSensor2().getAx().lastElement()), true, max_x_points);
                    seriesy2_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor2().getAy().getRealSize(), GloveSensors.getInstance().getSensor2().getAy().lastElement()), true, max_x_points);
                    seriesz2_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor2().getAz().getRealSize(), GloveSensors.getInstance().getSensor2().getAz().lastElement()), true, max_x_points);

                    seriesx3_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor3().getAx().getRealSize(), GloveSensors.getInstance().getSensor3().getAx().lastElement()), true, max_x_points);
                    seriesy3_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor3().getAy().getRealSize(), GloveSensors.getInstance().getSensor3().getAy().lastElement()), true, max_x_points);
                    seriesz3_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor3().getAz().getRealSize(), GloveSensors.getInstance().getSensor3().getAz().lastElement()), true, max_x_points);

                    seriesx4_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor4().getAx().getRealSize(), GloveSensors.getInstance().getSensor4().getAx().lastElement()), true, max_x_points);
                    seriesy4_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor4().getAy().getRealSize(), GloveSensors.getInstance().getSensor4().getAy().lastElement()), true, max_x_points);
                    seriesz4_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor4().getAz().getRealSize(), GloveSensors.getInstance().getSensor4().getAz().lastElement()), true, max_x_points);

                    seriesx5_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor5().getAx().getRealSize(), GloveSensors.getInstance().getSensor5().getAx().lastElement()), true, max_x_points);
                    seriesy5_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor5().getAy().getRealSize(), GloveSensors.getInstance().getSensor5().getAy().lastElement()), true, max_x_points);
                    seriesz5_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor5().getAz().getRealSize(), GloveSensors.getInstance().getSensor5().getAz().lastElement()), true, max_x_points);

                    seriesx6_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor6().getAx().getRealSize(), GloveSensors.getInstance().getSensor6().getAx().lastElement()), true, max_x_points);
                    seriesy6_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor6().getAy().getRealSize(), GloveSensors.getInstance().getSensor6().getAy().lastElement()), true, max_x_points);
                    seriesz6_acc.appendData(new DataPoint(GloveSensors.getInstance().getSensor6().getAz().getRealSize(), GloveSensors.getInstance().getSensor6().getAz().lastElement()), true, max_x_points);

                } catch (ConcurrentModificationException e) {
                    System.out.println("erro onde tu acha q ta dando: " + e.getMessage());
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        bluetoothConnection.onActivityResult(requestCode, resultCode, data);
    }

}

