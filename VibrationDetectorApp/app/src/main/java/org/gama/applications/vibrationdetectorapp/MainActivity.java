package org.gama.applications.vibrationdetectorapp;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.gama.applications.vibrationdetectorapp.bluetooth.BluetoothConnection;
import org.gama.applications.vibrationdetectorapp.uncoupled.IPredictScreen;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, IPredictScreen {

    private TextToSpeech tts;
    private TextView predictedText;
    private TextView myLabel;
    private BluetoothConnection bluetoothConnection = BluetoothConnection.getInstance(this);


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
        tts = new TextToSpeech(this, this);

        // ------------- Predicts Things ------

        predictedText = findViewById(R.id.predictedText);

        Button btn_speak = findViewById(R.id.btn_speak);
        btn_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speak(predictedText.getText().toString());
            }
        });

        Button btn_apagar = findViewById(R.id.btn_apagar_ultima_letra);
        btn_apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String palavra = predictedText.getText().toString();
                StringBuilder novaPalavra = new StringBuilder();
                if (palavra.length() > 0) {
                    for (int i = 0; i < palavra.length() - 1; i++)
                        novaPalavra.append(palavra.charAt(i));

                }
                predictedText.setText(novaPalavra.toString());


            }
        });
        Button btn_apagar_palavra = findViewById(R.id.btn_apagar_palavra);
        btn_apagar_palavra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                predictedText.setText("");

            }
        });

        myLabel = findViewById(R.id.label);


        //configButton
        Button configButton = findViewById(R.id.btn_config);
        configButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfigsActivity.class);
                startActivity(intent);
            }
        });

        //graph button
        Button graphButton = findViewById(R.id.btn_graphs);
        graphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GraphActivity.class);
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
                    bluetoothConnection.disconnect();
                } catch (IOException ex) {
                    Log.d("closeButtonEX", ex.getMessage());

                }
            }
        });

        Button start = findViewById(R.id.start_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
///TODO: removed svc
           }
        });
        Button stop = findViewById(R.id.stop_btn);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
///TODO: removed svc
            }
        });
    }

    @Override
    public void onInit(int status) {
    }

    private void speak(String fala) {
        if (tts != null) {
            int result = tts.setLanguage(new PreferencesUtils(this).getArchivedLocale());
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Language not suported", Toast.LENGTH_LONG).show();
            } else {
                tts.speak(fala, TextToSpeech.QUEUE_FLUSH, null);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
///TODO: removed svc
        Log.d("lockPredict", "unlocked");
    }

    @Override
    public void onPause() {
        super.onPause();
///TODO: removed svc
        Log.d("lockPredict", "locked");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        bluetoothConnection.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getReadyToSpeak() {

    }

    @Override
    public void speak() {
        speak(predictedText.getText().toString());
    }

    @Override
    public void appendCharaterToScreen(final char character) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                predictedText.setText(predictedText.getText() + "" + character);
            }
        });

    }

    @Override
    public void closeTheSpeaker() {

    }
}

