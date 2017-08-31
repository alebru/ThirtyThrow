package com.brusm.thirtythrow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.brusm.thirtythrow.R;

import java.util.Arrays;
import java.util.Random;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ImageButton[] imgBtns;
    public Spinner scoreSelector;
    private Button throwBtn;
    public static GameBoard gb = new GameBoard();
    Random rand = new Random();

    private ArrayList<Integer> values;
    private int randomNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView infoBar = (TextView) findViewById(R.id.info_bar);
        scoreSelector = (Spinner) findViewById(R.id.spinner1);
        throwBtn = (Button) findViewById(R.id.throw_button);

        throwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throwDice();
            }
        });

        imgBtns = new ImageButton[6];

        ImageButton die1 = (ImageButton) findViewById(R.id.die1);
        ImageButton die2 = (ImageButton) findViewById(R.id.die2);
        ImageButton die3 = (ImageButton) findViewById(R.id.die3);
        ImageButton die4 = (ImageButton) findViewById(R.id.die4);
        ImageButton die5 = (ImageButton) findViewById(R.id.die5);
        ImageButton die6 = (ImageButton) findViewById(R.id.die6);

        imgBtns[0] = die1;
        imgBtns[1] = die2;
        imgBtns[2] = die3;
        imgBtns[3] = die4;
        imgBtns[4] = die5;
        imgBtns[5] = die6;

        infoBar.setText("Round " + String.valueOf(gb.getRoundNum() + 1));

        // Sätter OnClickListener för tärningarna på spelplanen (ImageButton i imgBtns)
        setListenerDice();

        // Inaktiverar klick-lyssnare för ImageButtons (tärningar)
        disableGameField();

        if (savedInstanceState != null) {
            gb = savedInstanceState.getParcelable("Gameboard");

            // Läser in tärningsvärden (Die-objekt) och sätter ImageButton för varje tärning till motsvarande bild
            loadPreviousState();

            // Gör ImageButtons (tärningar) klickbara
            enableGameField();
        }

        else {
            throwBtn.setText("First throw");
        }
    }

    private void loadPreviousState() {
        for (int x = 0; x < imgBtns.length; x++) {
            Die die = gb.getRounds()[gb.getRoundNum()].getDice()[x];
            int diceVal = die.getDiceState();
            if (die.isSelected()) {
                switch (diceVal) {
                    case 1:
                        imgBtns[die.getDiceID()].setImageResource(R.drawable.grey1);
                        break;
                    case 2:
                        imgBtns[die.getDiceID()].setImageResource(R.drawable.grey2);
                        break;
                    case 3:
                        imgBtns[die.getDiceID()].setImageResource(R.drawable.grey3);
                        break;
                    case 4:
                        imgBtns[die.getDiceID()].setImageResource(R.drawable.grey4);
                        break;
                    case 5:
                        imgBtns[die.getDiceID()].setImageResource(R.drawable.grey5);
                        break;
                    case 6:
                        imgBtns[die.getDiceID()].setImageResource(R.drawable.grey6);
                        break;
                }
            } else {
                switch (diceVal) {

                    case 1:
                        imgBtns[die.getDiceID()].setImageResource(R.drawable.white1);
                        break;
                    case 2:
                        imgBtns[die.getDiceID()].setImageResource(R.drawable.white2);
                        break;
                    case 3:
                        imgBtns[die.getDiceID()].setImageResource(R.drawable.white3);
                        break;
                    case 4:
                        imgBtns[die.getDiceID()].setImageResource(R.drawable.white4);
                        break;
                    case 5:
                        imgBtns[die.getDiceID()].setImageResource(R.drawable.white5);
                        break;
                    case 6:
                        imgBtns[die.getDiceID()].setImageResource(R.drawable.white6);
                        break;
                }
            }
        }
    }

    private void setListenerDice() {
        for (int x = 0; x < imgBtns.length; x++) {

            imgBtns[x].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageButton btnSelected;
                    btnSelected = (ImageButton) v;
                    int index = Arrays.asList(imgBtns).indexOf(v);
                    Die die = gb.getRounds()[gb.getRoundNum()].getDice()[index];
                    int diceVal = die.getDiceState();
                    if (die.isSelected()) {
                        die.setSelected(false);
                        switch (diceVal) {
                            case 1:
                                imgBtns[die.getDiceID()].setImageResource(R.drawable.white1);
                                break;
                            case 2:
                                imgBtns[die.getDiceID()].setImageResource(R.drawable.white2);
                                break;
                            case 3:
                                imgBtns[die.getDiceID()].setImageResource(R.drawable.white3);
                                break;
                            case 4:
                                imgBtns[die.getDiceID()].setImageResource(R.drawable.white4);
                                break;
                            case 5:
                                imgBtns[die.getDiceID()].setImageResource(R.drawable.white5);
                                break;
                            case 6:
                                imgBtns[die.getDiceID()].setImageResource(R.drawable.white6);
                                break;
                        }
                    } else {
                        die.setSelected(true);
                        switch (diceVal) {
                            case 1:
                                imgBtns[die.getDiceID()].setImageResource(R.drawable.grey1);
                                break;
                            case 2:
                                imgBtns[die.getDiceID()].setImageResource(R.drawable.grey2);
                                break;
                            case 3:
                                imgBtns[die.getDiceID()].setImageResource(R.drawable.grey3);
                                break;
                            case 4:
                                imgBtns[die.getDiceID()].setImageResource(R.drawable.grey4);
                                break;
                            case 5:
                                imgBtns[die.getDiceID()].setImageResource(R.drawable.grey5);
                                break;
                            case 6:
                                imgBtns[die.getDiceID()].setImageResource(R.drawable.grey6);
                                break;
                        }
                    }
                    v.invalidate();
                }
            });
        }
    }

    /**
     * Inaktiverar samtliga ImageButton-instanser i imgBtns (spelfältets tärningar)
     *
     */
    private void disableGameField() {
        for (ImageButton btn:imgBtns) {
            btn.setEnabled(false);
        }
    }

    /**
     * Aktiverar samtliga IImageButton-instanser i imgBtns (spelfältets tärningar)
     *
     */
    private void enableGameField() {
        for (ImageButton btn:imgBtns) {
            btn.setEnabled(true);
        }
    }

    /**
     * Körs vid varje klick på First throw/Throw/Score-knappen,
     * Kontrollerar antalet kast (ThrowNum) i aktuell Round-instans
     * Om ThrowNum < 4 : nya tärningsvärden genereras och ny spelplan visas (via drawDice())
     * Om ThrowNum == 4 : Texten på "kast-knappen" ändras till Score/Next Round
     * Annars (Om ThrowNum > 4) : Spelplanen återställs (tärningarna visar värdet 1 och är inte längre klickbara),
     * texten på "kast-knappen" sätts till "First throw"
     *
     */
    private void throwDice() {
        throwBtn.setText("Throw");
        enableGameField();

        // Om antalet kast är färre än 4 ...

        if(gb.getCurrentRound().getThrowNum() <= 4) {

            gb.getCurrentRound().incrThrow();

            drawDice();

            if ((gb.getCurrentRound().getThrowNum() == 4)) {
                throwBtn.setText("Score/Next Round");
                gb.getCurrentRound().incrThrow();
            }
        }

        // Återställ spelfältets tärningar och ändra text på Throw-knappen (ny omgång inleds)
        else {
            resetDice();
            throwBtn.setText("First throw");
            disableGameField();
        }
    }

    /**
     * Slumpar nya värden för varje Die-objekt tillhörande den aktuella Round-instansen, ändrar bild tillhörande
     * motsvarande imgButton-objekt i imgBtns
     */
    public void drawDice() {
        for (Die die:gb.getRounds()[gb.getRoundNum()].getDice()) {

            randomNum = rand.nextInt(6) + 1;

            if (!die.isSelected()) {

                die.setDiceState(randomNum);

                switch (randomNum) {
                    case 1:  imgBtns[die.getDiceID()].setImageResource(R.drawable.white1);
                        break;
                    case 2:  imgBtns[die.getDiceID()].setImageResource(R.drawable.white2);
                        break;
                    case 3:  imgBtns[die.getDiceID()].setImageResource(R.drawable.white3);
                        break;
                    case 4:  imgBtns[die.getDiceID()].setImageResource(R.drawable.white4);
                        break;
                    case 5:  imgBtns[die.getDiceID()].setImageResource(R.drawable.white5);
                        break;
                    case 6:  imgBtns[die.getDiceID()].setImageResource(R.drawable.white6);
                        break;
                }
            }
        }
    }

    /**
     * Hämtar poäng och återställer spelfältet
     *
     */
    public void resetDice() {

        ArrayList<Die> values = new ArrayList<>(Arrays.asList(gb.getCurrentRound().getDice()));
        String score_val = scoreSelector.getSelectedItem().toString();

        switch (score_val) {
            case "Low": score(1, values);
                break;
            case "4":   score(4, values);
                break;
            case "5":   score(5, values);
                break;
            case "6":   score(6, values);
                break;
            case "7":   score(7, values);
                break;
            case "8":   score(8, values);
                break;
            case "9":   score(9, values);
                break;
            case "10":  score(10, values);
                break;
            case "11":  score(11, values);
                break;
            case "12":  score(12, values);
                break;
        }

        //Om omgångsnummer är mindre än 9 (färre än 10 omgångar har spelats)
        if (gb.getRoundNum() <  9) {
            gb.incrRoundNum();
            final TextView textViewToChange = (TextView) findViewById(R.id.info_bar);
            textViewToChange.setText(
                    "Round " + String.valueOf(gb.getRoundNum() + 1));

            for (Die die:gb.getRounds()[gb.getRoundNum()].getDice()) {
                die.setSelected(false);
                int diceVal = die.getDiceState();
                die.setSelected(false);
                die.setDiceState(1);
                imgBtns[die.getDiceID()].setImageResource(R.drawable.white1);
            }

            // Visa resultat om omgångsnummer är lika med 9
        } else {
            Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Skickar värdet på samtliga tärningar till poängräkning
     *
     * Om alternativ "Low" är markerat så görs poängräkning i metoden lowScore (som hör till Round-objektet)
     *
     */
    private void score(int option, ArrayList<Die> values) {
        if (option == 1) {
            ArrayList<ArrayList<Die>> newVals = new ArrayList<ArrayList<Die>>();
            newVals.add(values);
            gb.getRounds()[gb.getRoundNum()].lowScore(newVals);
        } else {
            ArrayList<ArrayList<Die>> results =  gb.getCurrentRound().calcScore(option, values, new ArrayList<Die>());
        }

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("Gameboard", gb);
    }
}