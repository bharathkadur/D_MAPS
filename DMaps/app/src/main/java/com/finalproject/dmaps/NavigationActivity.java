package com.finalproject.dmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mapbox.api.directions.v5.models.DirectionsRoute;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

public class NavigationActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        textToSpeech=new TextToSpeech(getBaseContext(),this);
        textToSpeech.setLanguage(Locale.ENGLISH);

        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mute();
                    // The toggle is enabled
                } else {
                    unmute();
                    // The toggle is disabled
                }
            }
        });

        Intent intent = getIntent();
        address = intent.getStringExtra(MainActivity.EXTRA_ADDRESS);

        new NavigationActivity.ConnectBT().execute();


        String data = getIntent().getStringExtra(MainActivity.EXTRA_DATA);
        DirectionsRoute route = DirectionsRoute.fromJson(data);
        TextView textView = findViewById(R.id.responseTextView);
        //final boolean[] flag = {false};
        final int[] count = {0};
        Thread t=new Thread(){


            @Override
            public void run(){

                while( count[0] < route.legs().get(0).steps().size()-1 ){

                    try {
                        if(count[0]==0){
                            Thread.sleep(10000);
                            sendSignal("b");
                            Thread.sleep(10000);
                        }
                        else {
                            double time = route.legs().get(0).steps().get(count[0] - 1).duration() * 1000 + 10000;
                            Thread.sleep((int) time);
                        }
//                        //1000ms = 1 sec
                        //1000ms = 1 sec

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                String string = route.legs().get(0).steps().get(count[0]).maneuver().instruction();
                                double dist = route.legs().get(0).steps().get(count[0]).distance();
                                textView.setText(string + "\n" + dist + " m");

//                                for(int k=0; k<route.legs().get(0).steps().get(count[0]).voiceInstructions().size(); k++)
                                textToSpeech.speak(string, TextToSpeech.QUEUE_FLUSH, null);


                                if(string.contains("north")) sendSignal("n");
                                else if (string.contains("south")) sendSignal("s");
                                else if (string.contains("east")) sendSignal("e");
                                else if (string.contains("west")) sendSignal("w");
                                else if (string.contains("left")) sendSignal("l");
                                else if (string.contains("right")) sendSignal("r");
                                else sendSignal("m");


                                if (dist == 0.0){
                                    sendSignal("m");
                                }

                            }
                        });
//                        double time = route.legs().get(0).steps().get(count[0]).duration() * 1000+10000;
//                        Thread.sleep((int)time);

                        count[0] += 1;
                    }

                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        };

        t.start();
    }

    public void goback(View v) {
        Disconnect();
        Intent i=new Intent(NavigationActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    private void sendSignal ( String number ) {
        if ( btSocket != null ) {
            try {
                btSocket.getOutputStream().write(number.toString().getBytes());
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void Disconnect () {
        if ( btSocket!=null ) {
            try {
                btSocket.close();
            } catch(IOException e) {
                msg("Error");
            }
        }

        finish();
    }

    private void msg (String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInit(int status) {
        if(status!=TextToSpeech.ERROR){
            Toast.makeText(getBaseContext(),"Success",Toast.LENGTH_LONG).show();
        }
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> {
        private boolean ConnectSuccess = true;

        @Override
        protected  void onPreExecute () {
            progress = ProgressDialog.show(NavigationActivity.this, "Connecting...", "Please Wait!!!");
        }

        @Override
        protected Void doInBackground (Void... devices) {
            try {
                if ( btSocket==null || !isBtConnected ) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                }
            } catch (IOException e) {
                ConnectSuccess = false;
            }

            return null;
        }

        @Override
        protected void onPostExecute (Void result) {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            } else {
                msg("Connected");
                isBtConnected = true;
            }

            progress.dismiss();
        }
    }
    private void mute() {
        //mute audio
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
    }

    public void unmute() {
        //unmute audio
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
    }
}