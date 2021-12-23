package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    // Adding buttons
    Button btnBLUE, btnYELLOW, btnGREEN, btnRED, FB;

    int score, round, increase;

    TextView tvtest;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    int sequenceCount = 4, n = 0;
    int[] gameSequence = new int[120];
    int arrayIndex = 0;

    private final int BLUE = 1;
    private final int YELLOW = 2;
    private final int RED = 3;
    private final int GREEN = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tvtest = findViewById(R.id.tvResult3);
        btnBLUE = findViewById(R.id.btnBlue);
        btnYELLOW = findViewById(R.id.btnYellow);
        btnRED = findViewById(R.id.btnRed);
        btnGREEN = findViewById(R.id.btnGreen);



        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);



        score = getIntent().getIntExtra("score", 0);
        round = getIntent().getIntExtra("round", 1);
        increase = getIntent().getIntExtra("increase", 2);

    }

    CountDownTimer cdtRound1 = new CountDownTimer(6000,  1500) {

        public void onTick(long millisUntilFinished) {

            tvtest.setText(String.valueOf("Round : "+round));
            //mTextField.setText("seconds remaining: " + millisUntilFinished / 1500);
            oneButton();
            //here you can have your logic to set text to edittext
        }

        @Override
        public void onFinish() {

            for (int i = 0; i< arrayIndex; i++)
                Log.d("game sequence", String.valueOf(gameSequence[i]));
            // start next activity

            // put the sequence into the next activity
            Intent i = new Intent(MainActivity.this, Start.class);
            i.putExtra("sequence", gameSequence);
            i.putExtra("round", round);
            i.putExtra("score", score);
            i.putExtra("increase", increase);
            startActivity(i);

            // start the next activity
        }
    };
    CountDownTimer cdtRound2 = new CountDownTimer(9000,  1500) {




        public void onTick(long millisUntilFinished) {
            tvtest.setText(String.valueOf("Round : "+round));
            //mTextField.setText("seconds remaining: " + millisUntilFinished / 1500);
            oneButton();
            //here you can have your logic to set text to edittext
        }

        @Override
        public void onFinish() {

            for (int i = 0; i< arrayIndex; i++)
                Log.d("game sequence", String.valueOf(gameSequence[i]));
            // start next activity

            // put the sequence into the next activity
            Intent i = new Intent(MainActivity.this, Start.class);
            i.putExtra("sequence", gameSequence);
            i.putExtra("round", round);
            i.putExtra("score", score);
            i.putExtra("increase", increase);
            startActivity(i);

            // start the next activity
        }
    };
    CountDownTimer cdtRound3 = new CountDownTimer(12000,  1500) {

        public void onTick(long millisUntilFinished) {
            tvtest.setText(String.valueOf("Round : "+round));
            //mTextField.setText("seconds remaining: " + millisUntilFinished / 1500);
            oneButton();
            //here you can have your logic to set text to edittext
        }

        @Override
        public void onFinish() {

            for (int i = 0; i< arrayIndex; i++)
                Log.d("game sequence", String.valueOf(gameSequence[i]));
            // start next activity

            // put the sequence into the next activity
            Intent i = new Intent(MainActivity.this, Start.class);
            i.putExtra("sequence", gameSequence);
            i.putExtra("sequence", gameSequence);
            i.putExtra("round", round);
            i.putExtra("score", score);
            i.putExtra("increase", increase);
            startActivity(i);

            // start the next activity
        }
    };

    public void doPlay(View view) {

        switch  (round)
        {
            case(1):
                cdtRound1.start();
                break;
            case(2):
                cdtRound2.start();
                break;
            case(3):
                cdtRound3.start();
                break;
        }
    }

    private void oneButton() {
        n = getRandom(sequenceCount);


        switch (n) {
            case 1:
                flashButton(btnBLUE);
                gameSequence[arrayIndex++] = BLUE;
                break;
            case 2:
                flashButton(btnYELLOW);
                gameSequence[arrayIndex++] = YELLOW;
                break;
            case 3:
                flashButton(btnRED);
                gameSequence[arrayIndex++] = RED;
                break;
            case 4:
                flashButton(btnGREEN);
                gameSequence[arrayIndex++] = GREEN;
                break;
            default:
                break;
        }   // end switch
    }

    // return a number between 1 and maxValue
    private int getRandom(int maxValue) {
        return ((int) ((Math.random() * maxValue) + 1));
    }

    private void flashButton(Button button) {
        FB = button;
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {

                FB.setPressed(true);
                FB.invalidate();
                FB.performClick();
                Handler handler1 = new Handler();
                Runnable r1 = new Runnable() {
                    public void run() {
                        FB.setPressed(false);
                        FB.invalidate();
                    }
                };
                handler1.postDelayed(r1, 600);

            } // end runnable
        };
        handler.postDelayed(r, 600);
    }
};
