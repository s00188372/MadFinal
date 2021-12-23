package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Start extends AppCompatActivity implements SensorEventListener {

    int[] gameSequence = new int[120];
    int num = 0, uSequence = -1, score = 0, increase = 2, round = 0;
    String Name;
    TextView tvScore, tvRound;
    Button btnNorth, btnWest, btnEast, btnSouth;

    // experimental values for hi and lo magnitude limits
    private final double NORTH_MOVE_FORWARD = 9;     // upper mag limit
    private final double NORTH_MOVE_BACKWARD = 6;      // lower mag limit

    private final double SOUTH_MOVE_FORWARD =  1;     // upper mag limit
    private final double SOUTH_MOVE_BACKWARD = 4;      // lower mag limit

    private final double EAST_MOVE_FORWARD = 1;     // upper mag limit
    private final double EAST_MOVE_BACKWARD = 0;      // lower mag limit

    private final double WEST_MOVE_FORWARD = -1;     // upper mag limit
    private final double WEST_MOVE_BACKWARD = 0;      // lower mag limit

    boolean highLimitNorth = false;      // detect high limit
    boolean highLimitSouth = false;      // detect high limit
    boolean highLimitEast = false;      // detect high limit
    boolean highLimitWest = false;      // detect high limit

    int counterNorth = 0;
    int counterSouth = 0;
    int counterEast = 0;
    int counterWest = 0;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnWest = findViewById(R.id.btnFirst);
        btnNorth = findViewById(R.id.btnSecond);
        btnSouth= findViewById(R.id.btnThird);
        btnEast = findViewById(R.id.btnFourth);

        // we are going to use the sensor service
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);


        gameSequence = getIntent().getIntArrayExtra("sequence");

        round = getIntent().getIntExtra("round", 1);
        score = getIntent().getIntExtra("score", 0);
        increase = getIntent().getIntExtra("increase", 0);

        tvScore = findViewById(R.id.tvScore);
        tvRound = findViewById(R.id.tvRound);

        tvRound.setText(String.valueOf(round));
        tvScore.setText(String.valueOf(score));
    }

    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        // North Movement
        if ((x > NORTH_MOVE_FORWARD && z > 0) && (highLimitNorth == false)) {
            highLimitNorth = true;
        }
        if ((x < NORTH_MOVE_BACKWARD && z > 0) && (highLimitNorth == true)) {
            counterNorth++;
            highLimitNorth = false;

            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {

                    btnNorth.setPressed(true);
                    btnNorth.invalidate();
                    btnNorth.performClick();
                    Handler handler1 = new Handler();
                    Runnable r1 = new Runnable() {
                        public void run() {
                            btnNorth.setPressed(false);
                            btnNorth.invalidate();
                        }
                    };
                    handler1.postDelayed(r1, 600);

                } // end runnable
            };
            handler.postDelayed(r, 600);


        }

        // South Movement
        if ((x < SOUTH_MOVE_FORWARD && z < 0) && (highLimitSouth == false)) {
            highLimitSouth = true;
        }
        if ((x > SOUTH_MOVE_BACKWARD && z < 0) && (highLimitSouth == true)) {
            counterSouth++;
            highLimitSouth = false;


            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {

                    btnSouth.setPressed(true);
                    btnSouth.invalidate();
                    btnSouth.performClick();
                    Handler handler1 = new Handler();
                    Runnable r1 = new Runnable() {
                        public void run() {
                            btnSouth.setPressed(false);
                            btnSouth.invalidate();
                        }
                    };
                    handler1.postDelayed(r1, 600);

                } // end runnable
            };
            handler.postDelayed(r, 600);


        }

        // East Movement
        if (y > EAST_MOVE_FORWARD && highLimitEast == false) {
            highLimitEast = true;
        }
        if (y < EAST_MOVE_BACKWARD && highLimitEast == true) {
            counterEast++;
            highLimitEast = false;

            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {

                    btnEast.setPressed(true);
                    btnEast.invalidate();
                    btnEast.performClick();
                    Handler handler1 = new Handler();
                    Runnable r1 = new Runnable() {
                        public void run() {
                            btnEast.setPressed(false);
                            btnEast.invalidate();
                        }
                    };
                    handler1.postDelayed(r1, 600);

                } // end runnable
            };
            handler.postDelayed(r, 600);


        }

        // West Movement
        if (y < WEST_MOVE_FORWARD && highLimitWest == false) {
            highLimitWest = true;
        }
        if (y > WEST_MOVE_BACKWARD && highLimitWest == true) {
            counterWest++;
            highLimitWest = false;

            Handler handler = new Handler();
            Runnable r = new Runnable() {
                public void run() {

                    btnWest.setPressed(true);
                    btnWest.invalidate();
                    btnWest.performClick();
                    Handler handler1 = new Handler();
                    Runnable r1 = new Runnable() {
                        public void run() {
                            btnWest.setPressed(false);
                            btnWest.invalidate();
                        }
                    };
                    handler1.postDelayed(r1, 600);

                } // end runnable
            };
            handler.postDelayed(r, 600);


        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }




    public void doClick(View view){

        uSequence++;

        switch (view.getId())
        {
            case(R.id.btnFirst) :
                num = 1;
                Name ="West";
                break;
            case(R.id.btnSecond) :
                num = 2;
                Name ="North";
                break;
            case(R.id.btnThird) :
                num = 3;
                Name ="South";
                break;
            case(R.id.btnFourth) :
                num = 4;
                Name ="East";
                break;
        }


        for(int i : gameSequence)
        {
            if(num == gameSequence[uSequence])
            {
                score++;
                tvScore.setText(String.valueOf(score));

                if(uSequence > increase)
                {
                    increase = increase + 2;
                    round++;
                    Intent returnToMain = new Intent(Start.this, MainActivity.class);
                    returnToMain.putExtra("score", score);
                    returnToMain.putExtra("round", round);
                    returnToMain.putExtra("increase", increase);
                    startActivity(returnToMain);
                }
                return;
            }
            else if(num != gameSequence[uSequence])
            {
                Intent intent = new Intent(view.getContext(), End.class);

                intent.putExtra("score", score);
                intent.putExtra("round", round);

                startActivity(intent);

                return;
            }
        }
    }
}