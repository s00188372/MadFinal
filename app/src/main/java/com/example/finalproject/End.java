package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class End extends AppCompatActivity {

    int score, round;
    TextView tvScore, tvRound;

    public DataBaseHandler db;
    public EditText enteredScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        enteredScore = findViewById(R.id.addNameToDB);


        tvScore = findViewById(R.id.tvScore);
        tvRound = findViewById(R.id.tvRounds);

        score = getIntent().getIntExtra("score", 0);
        round = getIntent().getIntExtra("round",0);

        tvScore.setText(String.valueOf(score));
        tvRound.setText(String.valueOf(round));


        db = new DataBaseHandler(this);
        db.emptyHiScores();
        Data();
        Log.i("Reading: ", "Reading all scores..");
        List<HiScore> hiScores = db.getAllHiScores();


        for (HiScore hs : hiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
        }

        Log.i("divider", "========================================");

        HiScore singleScore = db.getHiScore(5);
        Log.i("High Score 5 is by ", singleScore.getPlayer_name() + " with a score of " + singleScore.getScore());

        Log.i("divider", "========================================");

        // Calling SQL statement
        List<HiScore> top5HiScores = db.getTopFiveScores();
        for (HiScore hs : top5HiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();
            // Writing HiScore to log
            Log.i("Score: ", log);
        }

        HiScore lastScore = top5HiScores.get(top5HiScores.size() - 1);
        if (score > lastScore.score) {
            Toast.makeText(this,"You achieved a High score - Please Enter Your Name above", Toast.LENGTH_LONG).show();
        }

    }

    public void Data(){
        // Inserting hi scores
        Log.i("Insert: ", "Inserting Scores...");
        db.addHiScore(new HiScore("15 JUN 2021", "Carlos", 5));
        db.addHiScore(new HiScore("16 JUN 2021", "Lando", 6));
        db.addHiScore(new HiScore("17 JUN 2021", "Daniel", 4));
        db.addHiScore(new HiScore("18 JUN 2021", "Antonio", 4));
        db.addHiScore(new HiScore("20 JUN 2021", "Max", 25));

    }

    public void newUser(View view) {
        List<HiScore> top5HiScores = db.getTopFiveScores();
        HiScore lastScore = top5HiScores.get(top5HiScores.size() - 1);

        if(score > lastScore.score && enteredScore.getText().toString() != ""){
            String userName = enteredScore.getText().toString();
            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            db.addHiScore(new HiScore(date, userName, score));
            top5HiScores = db.getTopFiveScores();
            for (HiScore hs : top5HiScores) {
                String log =
                        "Id: " + hs.getScore_id() +
                                " , Player: " + hs.getPlayer_name() +
                                " , Score: " + hs.getScore();

                // Writing HiScore to log
                Log.i("Score: ", log);
            }
        }
        else{
            Toast.makeText(this,"Your Score isn't High Enough",Toast.LENGTH_SHORT).show();
        }

        viewHighScores(view);
    }

    public void viewHighScores(View view) {
        Intent intent = new Intent(view.getContext(), DisplayHiScore.class);

        startActivity(intent);
        finish();
    }

    public void playAgain(View view) {
        Intent in = new Intent(view.getContext(), MainActivity.class);

        startActivity(in);
    }
}