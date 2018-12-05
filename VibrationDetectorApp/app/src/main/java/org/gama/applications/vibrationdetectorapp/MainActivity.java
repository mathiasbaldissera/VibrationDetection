package org.gama.applications.vibrationdetectorapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.gama.applications.vibrationdetectorapp.bluetooth.BluetoothConnection;
import org.gama.applications.vibrationdetectorapp.bluetooth.IPostAppendScreen;
import org.gama.applications.vibrationdetectorapp.uncoupled.GloveSensors;
import org.gama.applications.vibrationdetectorapp.uncoupledprograms.DataSaveCsv;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ConcurrentModificationException;


public class MainActivity extends AppCompatActivity implements IPostAppendScreen {

    private BluetoothConnection bluetoothConnection = BluetoothConnection.getInstance(this);

    static int max_x_points = 600;

    // Linhas giroscopios
    static LineGraphSeries<DataPoint> seriesx1_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy1_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz1_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});

    static LineGraphSeries<DataPoint> seriesx2_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy2_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz2_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});

    static LineGraphSeries<DataPoint> seriesx3_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy3_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz3_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});

    static LineGraphSeries<DataPoint> seriesx4_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy4_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz4_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});

    static LineGraphSeries<DataPoint> seriesx5_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy5_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz5_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});

    static LineGraphSeries<DataPoint> seriesx6_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy6_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz6_giro = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});

    //Linhas accelerometros
    static LineGraphSeries<DataPoint> seriesx1_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy1_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz1_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});

    static LineGraphSeries<DataPoint> seriesx2_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy2_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz2_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});

    static LineGraphSeries<DataPoint> seriesx3_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy3_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz3_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});

    static LineGraphSeries<DataPoint> seriesx4_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy4_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz4_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});

    static LineGraphSeries<DataPoint> seriesx5_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy5_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz5_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});

    static LineGraphSeries<DataPoint> seriesx6_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesy6_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});
    static LineGraphSeries<DataPoint> seriesz6_acc = new LineGraphSeries<>(new DataPoint[]{new DataPoint(0, 0)});

    private GraphView graph_gx;
    private GraphView graph_gy;
    private GraphView graph_gz;

    private GraphView graph_ax;
    private GraphView graph_ay;
    private GraphView graph_az;

    private LinearLayout graph_acc;
    private LinearLayout graph_giro;
    private ToggleButton toggleAccGyro;
    private ToggleButton connect;
    private ToggleButton fastOptionsToggle;
    private ToggleButton stopChartToggle;
    private ToggleButton stopSavingToggle;
    private ToggleButton stopChartAndSavingToggle;
    private Button configBtn;
    private Button swapArchiveButton;
    private LinearLayout fast_options_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Carregamento da activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
        }

        //Carregamento e definição dos graficos

        graph_acc = findViewById(R.id.graph_list_a);
        graph_giro = findViewById(R.id.graph_list_g);

        graph_gx = findViewById(R.id.graphx_giro);
        graph_gy = findViewById(R.id.graphy_giro);
        graph_gz = findViewById(R.id.graphz_giro);

        graph_ax = findViewById(R.id.graphx_acc);
        graph_ay = findViewById(R.id.graphy_acc);
        graph_az = findViewById(R.id.graphz_acc);
        stopChartAndSavingToggle = findViewById(R.id.stop_chart_saving_toggle);

        setupGGraph();
        setupAGraph();

        toggleAccGyro = findViewById(R.id.switch_acc_gyro_toggle);
        configBtn = findViewById(R.id.config_btn);
        connect = findViewById(R.id.connect_toggle);
        fastOptionsToggle = findViewById(R.id.fast_options_toggle);
        stopChartToggle = findViewById(R.id.stop_chart_toggle);
        stopSavingToggle = findViewById(R.id.stop_saving_toggle);
        swapArchiveButton = findViewById(R.id.swap_archive_btn);
        fast_options_container = findViewById(R.id.fast_options_container);
//        fast_options_container.animate().translationY(fast_options_container.getHeight()).setDuration(0);



        PreferencesUtils prefUtils = new PreferencesUtils(this);
        if (prefUtils.isSavingAccEnabled())
            DataSaveCsv.getInstance(this).turnOnAutoSavingAcc();
        else
            DataSaveCsv.getInstance(this).turnOffAutoSavingAcc();

        if (prefUtils.isSavingGyroEnabled())
            DataSaveCsv.getInstance(this).turnOnAutoSavingGyro();
        else
            DataSaveCsv.getInstance(this).turnOffAutoSavingGyro();


        listenerButtons();

        BluetoothConnection.getInstance(this).putPutDataAppendRunnable(this);

    }

    //Configuração de botoes
    private void listenerButtons() {
        toggleAccGyro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    graph_acc.setVisibility(View.VISIBLE);
                    graph_giro.setVisibility(View.GONE);
                } else {
                    graph_acc.setVisibility(View.GONE);
                    graph_giro.setVisibility(View.VISIBLE);
                }
            }
        });

        configBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent config = new Intent(MainActivity.this, ConfigsActivity.class);
                startActivity(config);
            }
        });

        connect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!PermissionUtils.INSTANCE.validate(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast.makeText(MainActivity.this, "Please, give storage permission", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            bluetoothConnection.tryToConnect(new PreferencesUtils(MainActivity.this).getSavedGlove());
                        } catch (IOException ex) {
                            Toast.makeText(MainActivity.this, "Problem with Bluetooth Connection", Toast.LENGTH_SHORT).show();
                            Log.d("tryToConnect", ex.getMessage());
                            connect.setChecked(false);
                        }
                    }
                } else {
                    if (bluetoothConnection.isConnected()) {
                        try {
                            bluetoothConnection.disconnect();
                        } catch (IOException ex) {
                            Log.d("tryDisconnect", ex.getMessage());
                        }
                    }
                }
            }
        });

        fastOptionsToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    fast_options_container.setVisibility(View.VISIBLE);
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_in);
                    fast_options_container.startAnimation(animSlide);

                } else {

                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(),
                            R.anim.slide_out);
                    animSlide.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            fast_options_container.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    fast_options_container.startAnimation(animSlide);
                }
            }
        });

        stopChartToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Log.d("stopChartToggle", "triggered");
                if (checked) {
                    BluetoothConnection.getInstance(MainActivity.this).removeMe(MainActivity.this);
                } else {
                    stopChartAndSavingToggle.setChecked(false);
                    BluetoothConnection.getInstance(MainActivity.this).putPutDataAppendRunnable(MainActivity.this);

                }

            }
        });
        stopSavingToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Log.d("stopSavingToggle", "triggered");
                if (checked) {
                    DataSaveCsv.getInstance(MainActivity.this).turnOffAutoSavingAcc();
                    DataSaveCsv.getInstance(MainActivity.this).turnOffAutoSavingGyro();
                } else {
                    if (new PreferencesUtils(MainActivity.this).isSavingAccEnabled())
                        DataSaveCsv.getInstance(MainActivity.this).turnOnAutoSavingAcc();

                    if (new PreferencesUtils(MainActivity.this).isSavingGyroEnabled())
                        DataSaveCsv.getInstance(MainActivity.this).turnOnAutoSavingGyro();

                    stopChartAndSavingToggle.setChecked(false);
                }
            }
        });


        stopChartAndSavingToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                Log.d("stopChartAndSavingToggl", "triggered");
                if (checked) {
                    stopChartToggle.setChecked(true);
                    stopSavingToggle.setChecked(true);
                } else if (stopSavingToggle.isChecked() && stopChartToggle.isChecked()) {
                    stopChartToggle.setChecked(false);
                    stopSavingToggle.setChecked(false);
                }
            }
        });


        swapArchiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = DataSaveCsv.getInstance(MainActivity.this).saveToNewFile();
                Toast.makeText(MainActivity.this, "Now archives name begins with " + result, Toast.LENGTH_LONG).show();
            }
        });

    }


    public static void copyStream(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        //Carregamento das preferencias
        PreferencesUtils prefUtils = new PreferencesUtils(this);

        //Carregamento do valor da escala
        double scale = prefUtils.getGraphScaleValue();
        Log.d("graphScale", String.format("%f %f", -scale, scale));

        //Carregamento do start(começa no 0 ou no -scale)
        //Definição da Viewport
        graph_ax.getViewport().setYAxisBoundsManual(true);
        graph_ay.getViewport().setYAxisBoundsManual(true);
        graph_az.getViewport().setYAxisBoundsManual(true);
        graph_gx.getViewport().setYAxisBoundsManual(true);
        graph_gy.getViewport().setYAxisBoundsManual(true);
        graph_gz.getViewport().setYAxisBoundsManual(true);
        graph_ax.getViewport().setScalableY(true);
        graph_ay.getViewport().setScalableY(true);
        graph_az.getViewport().setScalableY(true);
        graph_gx.getViewport().setScalableY(true);
        graph_gy.getViewport().setScalableY(true);
        graph_gz.getViewport().setScalableY(true);
        if (prefUtils.isGraphViewScaleStaringFromZero()) {
            graph_ax.getViewport().setMinY(-scale);
            graph_ay.getViewport().setMinY(-scale);
            graph_az.getViewport().setMinY(-scale);
            graph_gx.getViewport().setMinY(-scale);
            graph_gy.getViewport().setMinY(-scale);
            graph_gz.getViewport().setMinY(-scale);
        } else {
            graph_ax.getViewport().setMinY(0);
            graph_ay.getViewport().setMinY(0);
            graph_az.getViewport().setMinY(0);
            graph_gx.getViewport().setMinY(0);
            graph_gy.getViewport().setMinY(0);
            graph_gz.getViewport().setMinY(0);
        }

        graph_ax.getViewport().setMaxY(scale);
        graph_ay.getViewport().setMaxY(scale);
        graph_az.getViewport().setMaxY(scale);
        graph_gx.getViewport().setMaxY(scale);
        graph_gy.getViewport().setMaxY(scale);
        graph_gz.getViewport().setMaxY(scale);


        graph_ax.refreshDrawableState();
        //Configuração das series com base nos checkbox dos sensores
        if (prefUtils.getAnyBoolean("sensor1g", true)) {
            if (!graph_gx.getSeries().contains(seriesx1_giro)) {
                graph_gx.addSeries(seriesx1_giro);
                graph_gy.addSeries(seriesy1_giro);
                graph_gz.addSeries(seriesz1_giro);
            }
        } else {
            graph_gx.removeSeries(seriesx1_giro);
            graph_gy.removeSeries(seriesy1_giro);
            graph_gz.removeSeries(seriesz1_giro);
        }
        if (prefUtils.getAnyBoolean("sensor2g", true)) {
            if (!graph_gx.getSeries().contains(seriesx2_giro)) {
                graph_gx.addSeries(seriesx2_giro);
                graph_gy.addSeries(seriesy2_giro);
                graph_gz.addSeries(seriesz2_giro);
            }
        } else {
            graph_gx.removeSeries(seriesx2_giro);
            graph_gy.removeSeries(seriesy2_giro);
            graph_gz.removeSeries(seriesz2_giro);
        }
        if (prefUtils.getAnyBoolean("sensor3g", true)) {
            if (!graph_gx.getSeries().contains(seriesx3_giro)) {
                graph_gx.addSeries(seriesx3_giro);
                graph_gy.addSeries(seriesy3_giro);
                graph_gz.addSeries(seriesz3_giro);
            }
        } else {
            graph_gx.removeSeries(seriesx3_giro);
            graph_gy.removeSeries(seriesy3_giro);
            graph_gz.removeSeries(seriesz3_giro);
        }
        if (prefUtils.getAnyBoolean("sensor4g", true)) {
            if (!graph_gx.getSeries().contains(seriesx4_giro)) {
                graph_gx.addSeries(seriesx4_giro);
                graph_gy.addSeries(seriesy4_giro);
                graph_gz.addSeries(seriesz4_giro);
            }
        } else {
            graph_gx.removeSeries(seriesx4_giro);
            graph_gy.removeSeries(seriesy4_giro);
            graph_gz.removeSeries(seriesz4_giro);
        }
        if (prefUtils.getAnyBoolean("sensor5g", true)) {
            if (!graph_gx.getSeries().contains(seriesx5_giro)) {
                graph_gx.addSeries(seriesx5_giro);
                graph_gy.addSeries(seriesy5_giro);
                graph_gz.addSeries(seriesz5_giro);
            }
        } else {
            graph_gx.removeSeries(seriesx5_giro);
            graph_gy.removeSeries(seriesy5_giro);
            graph_gz.removeSeries(seriesz5_giro);
        }
        if (prefUtils.getAnyBoolean("sensor6g", true)) {
            if (!graph_gx.getSeries().contains(seriesx6_giro)) {
                graph_gx.addSeries(seriesx6_giro);
                graph_gy.addSeries(seriesy6_giro);
                graph_gz.addSeries(seriesz6_giro);
            }
        } else {
            graph_gx.removeSeries(seriesx6_giro);
            graph_gy.removeSeries(seriesy6_giro);
            graph_gz.removeSeries(seriesz6_giro);
        }
        if (prefUtils.getAnyBoolean("sensor1a", true)) {
            if (!graph_ax.getSeries().contains(seriesx1_acc)) {
                graph_ax.addSeries(seriesx1_acc);
                graph_ay.addSeries(seriesy1_acc);
                graph_az.addSeries(seriesz1_acc);
            }
        } else {
            graph_ax.removeSeries(seriesx1_acc);
            graph_ay.removeSeries(seriesy1_acc);
            graph_az.removeSeries(seriesz1_acc);
        }
        if (prefUtils.getAnyBoolean("sensor2a", true)) {
            if (!graph_ax.getSeries().contains(seriesx2_acc)) {
                graph_ax.addSeries(seriesx2_acc);
                graph_ay.addSeries(seriesy2_acc);
                graph_az.addSeries(seriesz2_acc);
            }
        } else {
            graph_ax.removeSeries(seriesx2_acc);
            graph_ay.removeSeries(seriesy2_acc);
            graph_az.removeSeries(seriesz2_acc);
        }
        if (prefUtils.getAnyBoolean("sensor3a", true)) {
            if (!graph_ax.getSeries().contains(seriesx3_acc)) {
                graph_ax.addSeries(seriesx3_acc);
                graph_ay.addSeries(seriesy3_acc);
                graph_az.addSeries(seriesz3_acc);
            }
        } else {
            graph_ax.removeSeries(seriesx3_acc);
            graph_ay.removeSeries(seriesy3_acc);
            graph_az.removeSeries(seriesz3_acc);
        }
        if (prefUtils.getAnyBoolean("sensor4a", true)) {
            if (!graph_ax.getSeries().contains(seriesx4_acc)) {
                graph_ax.addSeries(seriesx4_acc);
                graph_ay.addSeries(seriesy4_acc);
                graph_az.addSeries(seriesz4_acc);
            }
        } else {
            graph_ax.removeSeries(seriesx4_acc);
            graph_ay.removeSeries(seriesy4_acc);
            graph_az.removeSeries(seriesz4_acc);
        }
        if (prefUtils.getAnyBoolean("sensor5a", true)) {
            if (!graph_ax.getSeries().contains(seriesx5_acc)) {
                graph_ax.addSeries(seriesx5_acc);
                graph_ay.addSeries(seriesy5_acc);
                graph_az.addSeries(seriesz5_acc);
            }
        } else {
            graph_ax.removeSeries(seriesx5_acc);
            graph_ay.removeSeries(seriesy5_acc);
            graph_az.removeSeries(seriesz5_acc);
        }
        if (prefUtils.getAnyBoolean("sensor6a", true)) {
            if (!graph_ax.getSeries().contains(seriesx6_acc)) {
                graph_ax.addSeries(seriesx6_acc);
                graph_ay.addSeries(seriesy6_acc);
                graph_az.addSeries(seriesz6_acc);
            }
        } else {
            graph_ax.removeSeries(seriesx6_acc);
            graph_ay.removeSeries(seriesy6_acc);
            graph_az.removeSeries(seriesz6_acc);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("lockPredict", "locked");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BluetoothConnection.getInstance(this).removeMe(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bluetoothConnection.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public Runnable getPostAppendRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    seriesx1_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor1().getGx().getRealSize(), GloveSensors.getInstance().getSensor1().getGx().lastElement()), true, max_x_points);
                    seriesy1_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor1().getGy().getRealSize(), GloveSensors.getInstance().getSensor1().getGy().lastElement()), true, max_x_points);
                    seriesz1_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor1().getGz().getRealSize(), GloveSensors.getInstance().getSensor1().getGz().lastElement()), true, max_x_points);

                    seriesx2_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor2().getGx().getRealSize(), GloveSensors.getInstance().getSensor2().getGx().lastElement()), true, max_x_points);
                    seriesy2_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor2().getGy().getRealSize(), GloveSensors.getInstance().getSensor2().getGy().lastElement()), true, max_x_points);
                    seriesz2_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor2().getGz().getRealSize(), GloveSensors.getInstance().getSensor2().getGz().lastElement()), true, max_x_points);

                    seriesx3_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor3().getGx().getRealSize(), GloveSensors.getInstance().getSensor3().getGx().lastElement()), true, max_x_points);
                    seriesy3_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor3().getGy().getRealSize(), GloveSensors.getInstance().getSensor3().getGy().lastElement()), true, max_x_points);
                    seriesz3_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor3().getGz().getRealSize(), GloveSensors.getInstance().getSensor3().getGz().lastElement()), true, max_x_points);

                    seriesx4_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor4().getGx().getRealSize(), GloveSensors.getInstance().getSensor4().getGx().lastElement()), true, max_x_points);
                    seriesy4_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor4().getGy().getRealSize(), GloveSensors.getInstance().getSensor4().getGy().lastElement()), true, max_x_points);
                    seriesz4_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor4().getGz().getRealSize(), GloveSensors.getInstance().getSensor4().getGz().lastElement()), true, max_x_points);

                    seriesx5_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor5().getGx().getRealSize(), GloveSensors.getInstance().getSensor5().getGx().lastElement()), true, max_x_points);
                    seriesy5_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor5().getGy().getRealSize(), GloveSensors.getInstance().getSensor5().getGy().lastElement()), true, max_x_points);
                    seriesz5_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor5().getGz().getRealSize(), GloveSensors.getInstance().getSensor5().getGz().lastElement()), true, max_x_points);

                    seriesx6_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor6().getGx().getRealSize(), GloveSensors.getInstance().getSensor6().getGx().lastElement()), true, max_x_points);
                    seriesy6_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor6().getGy().getRealSize(), GloveSensors.getInstance().getSensor6().getGy().lastElement()), true, max_x_points);
                    seriesz6_giro.appendData(new DataPoint(GloveSensors.getInstance().getSensor6().getGz().getRealSize(), GloveSensors.getInstance().getSensor6().getGz().lastElement()), true, max_x_points);

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

    //Setup dos graficos dos Giroscopios
    public void setupGGraph() {
        //graficos giroscopio
        //cores
        seriesx1_giro.setColor(Color.GREEN);
        seriesy1_giro.setColor(Color.GREEN);
        seriesz1_giro.setColor(Color.GREEN);
        seriesx2_giro.setColor(Color.RED);
        seriesy2_giro.setColor(Color.RED);
        seriesz2_giro.setColor(Color.RED);
        seriesx3_giro.setColor(Color.BLUE);
        seriesy3_giro.setColor(Color.BLUE);
        seriesz3_giro.setColor(Color.BLUE);
        seriesx4_giro.setColor(Color.YELLOW);
        seriesy4_giro.setColor(Color.YELLOW);
        seriesz4_giro.setColor(Color.YELLOW);
        seriesx5_giro.setColor(Color.MAGENTA);
        seriesy5_giro.setColor(Color.MAGENTA);
        seriesz5_giro.setColor(Color.MAGENTA);
        seriesx6_giro.setColor(Color.GRAY);
        seriesy6_giro.setColor(Color.GRAY);
        seriesz6_giro.setColor(Color.GRAY);

        //Adiçao das linhas

        graph_gx.setTitle("Gyroscopes axis X");
        graph_gx.addSeries(seriesx1_giro);
        graph_gx.addSeries(seriesx2_giro);
        graph_gx.addSeries(seriesx3_giro);
        graph_gx.addSeries(seriesx4_giro);
        graph_gx.addSeries(seriesx5_giro);
        graph_gx.addSeries(seriesx6_giro);

        graph_gy.setTitle("Gyroscopes axis Y");
        graph_gy.addSeries(seriesy1_giro);
        graph_gy.addSeries(seriesy2_giro);
        graph_gy.addSeries(seriesy3_giro);
        graph_gy.addSeries(seriesy4_giro);
        graph_gy.addSeries(seriesy5_giro);
        graph_gy.addSeries(seriesy6_giro);

        graph_gz.setTitle("Gyroscopes axis Z");
        graph_gz.addSeries(seriesz1_giro);
        graph_gz.addSeries(seriesz2_giro);
        graph_gz.addSeries(seriesz3_giro);
        graph_gz.addSeries(seriesz4_giro);
        graph_gz.addSeries(seriesz5_giro);
        graph_gz.addSeries(seriesz6_giro);

        //Definiçao do viewport
        graph_gx.getViewport().setXAxisBoundsManual(true);
        graph_gx.getViewport().setYAxisBoundsManual(true);
        graph_gx.getViewport().setMinX(0);
        graph_gx.getViewport().setMaxX(max_x_points);

        graph_gy.getViewport().setXAxisBoundsManual(true);
        graph_gy.getViewport().setYAxisBoundsManual(true);
        graph_gy.getViewport().setMinX(0);
        graph_gy.getViewport().setMaxX(max_x_points);

        graph_gz.getViewport().setXAxisBoundsManual(true);
        graph_gz.getViewport().setYAxisBoundsManual(true);
        graph_gz.getViewport().setMinX(0);
        graph_gz.getViewport().setMaxX(max_x_points);
    }

    //Setup dos graficos dos Acelerometros
    public void setupAGraph() {

        //graficos acelerometros

        //Cores
        seriesx1_acc.setColor(Color.GREEN);
        seriesy1_acc.setColor(Color.GREEN);
        seriesz1_acc.setColor(Color.GREEN);
        seriesx2_acc.setColor(Color.RED);
        seriesy2_acc.setColor(Color.RED);
        seriesz2_acc.setColor(Color.RED);
        seriesx3_acc.setColor(Color.BLUE);
        seriesy3_acc.setColor(Color.BLUE);
        seriesz3_acc.setColor(Color.BLUE);
        seriesx4_acc.setColor(Color.YELLOW);
        seriesy4_acc.setColor(Color.YELLOW);
        seriesz4_acc.setColor(Color.YELLOW);
        seriesx5_acc.setColor(Color.MAGENTA);
        seriesy5_acc.setColor(Color.MAGENTA);
        seriesz5_acc.setColor(Color.MAGENTA);
        seriesx6_acc.setColor(Color.GRAY);
        seriesy6_acc.setColor(Color.GRAY);
        seriesz6_acc.setColor(Color.GRAY);

        //Adiçao das linhas
        graph_ax.setTitle("Accelerometers axis X");
        graph_ax.addSeries(seriesx1_acc);
        graph_ax.addSeries(seriesx2_acc);
        graph_ax.addSeries(seriesx3_acc);
        graph_ax.addSeries(seriesx4_acc);
        graph_ax.addSeries(seriesx5_acc);
        graph_ax.addSeries(seriesx5_acc);

        graph_ay.setTitle("Accelerometers axis Y");
        graph_ay.addSeries(seriesy1_acc);
        graph_ay.addSeries(seriesy2_acc);
        graph_ay.addSeries(seriesy3_acc);
        graph_ay.addSeries(seriesy4_acc);
        graph_ay.addSeries(seriesy5_acc);
        graph_ay.addSeries(seriesy6_acc);

        graph_az.setTitle("Accelerometers axis Z");
        graph_az.addSeries(seriesz1_acc);
        graph_az.addSeries(seriesz2_acc);
        graph_az.addSeries(seriesz3_acc);
        graph_az.addSeries(seriesz4_acc);
        graph_az.addSeries(seriesz5_acc);
        graph_az.addSeries(seriesz6_acc);

        //Definiçao da viewport
        graph_ax.getViewport().setXAxisBoundsManual(true);
        graph_ax.getViewport().setYAxisBoundsManual(true);
        graph_ax.getViewport().setMinX(0);
        graph_ax.getViewport().setMaxX(max_x_points);

        graph_ay.getViewport().setXAxisBoundsManual(true);
        graph_ay.getViewport().setYAxisBoundsManual(true);
        graph_ay.getViewport().setMinX(0);
        graph_ay.getViewport().setMaxX(max_x_points);

        graph_az.getViewport().setXAxisBoundsManual(true);
        graph_az.getViewport().setYAxisBoundsManual(true);
        graph_az.getViewport().setMinX(0);
        graph_az.getViewport().setMaxX(max_x_points);

    }

}