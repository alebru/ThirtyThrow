/**
 * Aktivitet för resultatspresentation
 *
 *
 */

package com.brusm.thirtythrow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.brusm.thirtythrow.R;


public class ResultsActivity extends AppCompatActivity {
    private GameBoard gb;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        Button newBtn = (Button) findViewById(R.id.new_button);

        newBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                MainActivity.gb = new GameBoard();
                startActivity(intent);
            }
        });

        gb = MainActivity.gb;

        final TextView resultsView = (TextView) findViewById(R.id.textView2);
        resultsView.append("\n\n");

        //Loopar igenom  samtliga Round-objekt i GameBoard-objektet, skriver ut score med .getScore för varje omgång
        //Räknar samtidigt ihop total poängsumma för samtliga omgångar
        for (Round round:gb.getRounds()) {
            resultsView.append("Round " + (round.getRoundNum() + 1) + " :   " + round.getScore() + "\n");
            total += round.getScore();
        }
        resultsView.append("\n");
        resultsView.append("Total score " + total + "\n");



    }
}
