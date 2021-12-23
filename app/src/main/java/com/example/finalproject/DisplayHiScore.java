package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DisplayHiScore extends AppCompatActivity {

    ListView listView;
    List<String> scoresStr;
    private DataBaseHandler newDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_hi_score);

        listView = findViewById(android.R.id.list);


        newDB = new DataBaseHandler(this);


        // Inserting hi scores
        Log.i("Insert: ", "Inserting ..");
        newDB.addHiScore(new HiScore("15 JUN 2021", "Kimi", 5));
        newDB.addHiScore(new HiScore("16 JUN 2021", "Charles", 6));
        newDB.addHiScore(new HiScore("17 JUN 2021", "Piere", 5));
        newDB.addHiScore(new HiScore("18 JUN 2021", "Valteri", 5));
        newDB.addHiScore(new HiScore("20 JUN 2021", "Lewis", 25));


        // Reading all scores
        Log.i("Reading: ", "Reading all scores..");
        List<HiScore> hiScores = newDB.getAllHiScores();


        for (HiScore hs : hiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
        }

        Log.i("divider", "====================");

        HiScore singleScore = newDB.getHiScore(5);
        Log.i("High Score 5 is by ", singleScore.getPlayer_name() + " with a score of " +
                singleScore.getScore());

        Log.i("divider", "====================");

        // Calling SQL statement
        List<HiScore> top5HiScores = newDB.getTopFiveScores();

        for (HiScore hs : top5HiScores) {
            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // Writing HiScore to log
            Log.i("Score: ", log);
        }
        Log.i("divider", "====================");

        HiScore hiScore = top5HiScores.get(top5HiScores.size() - 1);
        // hiScore contains the 5th highest score
        Log.i("fifth Highest score: ", String.valueOf(hiScore.getScore()) );

        // simple test to add a hi score
        int myCurrentScore = 40;
        // if 5th highest score < myCurrentScore, then insert new score
        if (hiScore.getScore() < myCurrentScore) {
            newDB.addHiScore(new HiScore("20 DEC 2021", "Ruslan", 40));
        }

        Log.i("divider", "====================");

        // Calling SQL statement
        top5HiScores = newDB.getTopFiveScores();
        scoresStr = new ArrayList<>();

        int j = 1;
        for (HiScore hs : top5HiScores) {

            String log =
                    "Id: " + hs.getScore_id() +
                            ", Date: " + hs.getGame_date() +
                            " , Player: " + hs.getPlayer_name() +
                            " , Score: " + hs.getScore();

            // store score in string array
            scoresStr.add(j++ + " : "  +
                    hs.getPlayer_name() + "\t" +
                    hs.getScore());
            // Writing HiScore to log
            Log.i("Score: ", log);
        }

        Log.i("divider", "====================");
        Log.i("divider", "Scores in list <>>");
        for (String ss : scoresStr) {
            Log.i("Score: ", ss);
        }

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, scoresStr);
        listView.setAdapter(itemsAdapter);

    }

    public void backHome(View view) {
        Intent returnHome = new Intent(DisplayHiScore.this, MainActivity.class);
        startActivity(returnHome);
    }
}