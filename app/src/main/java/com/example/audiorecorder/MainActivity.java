package com.example.audiorecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Magnifier;
import android.widget.Toast;

import java.io.File;


public class MainActivity extends AppCompatActivity {

    Button startBtn,stopBtn,playBtn;
    private static int MICROPHONE_PERMISSION_CODE=200;

    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn=findViewById(R.id.startBtn);
        playBtn=findViewById(R.id.playBtn);
        stopBtn=findViewById(R.id.stopBtn);

        if(isMicrophonePresent())
        {
            getMicrophonePermission();
        }

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaRecorder=new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setOutputFile(getRecordingPath());
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                    mediaRecorder.prepare();
                    mediaRecorder.start();

                    Toast.makeText(MainActivity.this,"Recording is Started",Toast.LENGTH_LONG).show();

                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder=null;
                Toast.makeText(MainActivity.this,"Recording is Stop",Toast.LENGTH_LONG).show();

            }
        });
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(getRecordingPath());
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this,"Recording is Played",Toast.LENGTH_LONG).show();

                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }

    private String getRecordingPath(){
        ContextWrapper contextWrapper=new ContextWrapper(getApplicationContext());
        File musicDirectory=contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file=new File(musicDirectory,"testRecordingFile"+".mp3");
        return file.getPath();

    }

    public void getMicrophonePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.RECORD_AUDIO},MICROPHONE_PERMISSION_CODE);
        }


    }
    private boolean isMicrophonePresent(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE))
        {
            return true;
        }
        else {
            return false;
        }
    }

}