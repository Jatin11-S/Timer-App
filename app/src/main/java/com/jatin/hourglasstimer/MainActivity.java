package com.jatin.hourglasstimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seek;
    TextView time;
    Button start;
    CountDownTimer cdt;
    Boolean active = false;
    public void reset(){
        time.setText("0:30");
        seek.setProgress(30);
        seek.setEnabled(true);
        cdt.cancel();
        start.setText("START!");
        active = false;
    }

    public void buttonPressed(View view){
        Log.i("Button","Pressed");
        if(active){
            reset();
        }
        else {
            active=true;
            seek.setEnabled(false);
            start.setText("STOP!");
            cdt = new CountDownTimer(seek.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTime((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer media = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
                    media.start();
                }
            }.start();
        }
    }

    public void updateTime(int secLeft){
        int min = secLeft/60;
        int sec = secLeft - (min*60);

        String secStr = Integer.toString(sec);
        if(sec<=9){
            secStr =  "0" + secStr;
        }
        time.setText(Integer.toString(min) + ":" + secStr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.button);
        seek = findViewById(R.id.seekBar2);
        time = findViewById(R.id.timer);
        seek.setMax(600);
        seek.setProgress(10);

        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTime(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
