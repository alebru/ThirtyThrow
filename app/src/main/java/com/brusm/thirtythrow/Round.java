/**
 * Created by brusm on 2017-07-05.
 *
 * Klass som hanterar tärningsobjekt (Die), poäng per omgång och antal kast som utförts
 * En "Round" motsvarar en "omgång" i spelet
 *
 */

package com.brusm.thirtythrow;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Arrays;


public class Round implements Parcelable {
    private int roundNum;
    private int mThrowNum;
    private int score;
    private static final int maxThrowNum = 3;
    private Die[] dice;
    private int[] diceStates;

    public Round(int roundNumber) {
        roundNum = roundNumber;
        mThrowNum = 1;
        dice = new Die[]{
                new Die(0),
                new Die(1),
                new Die(2),
                new Die(3),
                new Die(4),
                new Die(5)
        };
    }

    public int getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(int roundNum) {
        this.roundNum = roundNum;
    }

    public Die[] getDice() {
        return dice;
    }

    public void setDice(Die[] dice) {
        this.dice = dice;
    }

    public int getScore() {
        return score;
    }

    public int getThrowNum() {
        return mThrowNum;
    }

    public void setThrowNum(int throwNum) {
        mThrowNum = throwNum;
    }

    public void incrThrow() {
        mThrowNum += 1;
    }

    public void setScore(int value) {
        score = value;
    }

    /**
     * Beräkningar poäng om alternativet "Low" har valts, lagrar poäng i instansvariabeln "score"
     *
     */
    public void lowScore(ArrayList<ArrayList<Die>> values) {
        int score = 0;
        for (ArrayList<Die> solution:values) {
            for (Die die: solution) {
                if (die.getDiceState() <= 3) {
                    score += die.getDiceState();
                }
            }
        }
        setScore(score);
    }

    /**
     * Beräkningar poäng för samtliga poängberäkningsalternativ förutom "Low", går rekursivt igenom samtliga potentiella lösningar och returnerar
     * @param Int option - målvärdet för poängberäkning (4 - 12), ArrayList<Die> dice - lista med Die-objekt (tärningar), ArrayList<Die> previous - lista med Die-objekt (tärningar)
     * @return ArrayList<ArrayList<Die>> Innehållandes någon av de lösningar innehållandes flest kombinationer av värden som är lika med målvärdet
     */
    public ArrayList<ArrayList<Die>> calcScore(int option, ArrayList<Die> dice, ArrayList<Die> previous) {
        if (dice.size() == 0) {
            return new ArrayList<ArrayList<Die>>();
        }

        else {

            ArrayList<ArrayList<ArrayList<Die>>> subtrees = new ArrayList<ArrayList<ArrayList<Die>>>();

            for (Die d:dice) {
                ArrayList<Die> newDice = new ArrayList<Die>();

                for (Die d2:dice) {
                    if (d2 != d) {
                        newDice.add(d2);
                    }
                }

                if (d.getDiceState() == option) {

                    ArrayList<ArrayList<Die>> recursiveSolutions = calcScore(option, newDice, new ArrayList<Die>());
                    recursiveSolutions.add(new ArrayList<Die>(Arrays.asList(d)));
                    subtrees.add(recursiveSolutions);
                }

                else if ((d.getDiceState() + Die.Sum(previous)) == option) {

                    ArrayList<ArrayList<Die>> recursiveSolutions = calcScore(option, newDice, new ArrayList<Die>());

                    ArrayList<Die> solution = new ArrayList<>(previous);

                    solution.add(d);

                    recursiveSolutions.add(solution);

                    subtrees.add(recursiveSolutions);
                }

                else {
                    ArrayList<Die> newPrevious = new ArrayList<>(previous);
                    newPrevious.add(d);
                    subtrees.add(calcScore(option, newDice, newPrevious));
                }
            }

            ArrayList<ArrayList<Die>> theSolution = new ArrayList<ArrayList<Die>>();

            int max = 0;

            for (ArrayList<ArrayList<Die>> subtree:subtrees) {

                if (subtree.size() > max) {
                    max = subtree.size();
                    theSolution = subtree;
                }

            }

            saveScore(theSolution);
            return theSolution;
        }
    }

    /**
     * @param ArrayList<ArrayList<Die>> (resultat av poängberäkningar i getScore
     * Sparar poängen i Round-objektets score-variabel
     */
    public void saveScore(ArrayList<ArrayList<Die>> result) {
        int score = 0;
        for (ArrayList<Die> solution:result) {
            for (Die die: solution) {
                score += die.getDiceState();
            }
        }
        setScore(score);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mThrowNum);
        dest.writeInt(this.score);
        dest.writeInt(this.roundNum);
        dest.writeTypedArray(this.dice, flags);
        dest.writeIntArray(this.diceStates);
    }

    protected Round(Parcel in) {
        this.mThrowNum = in.readInt();
        this.score = in.readInt();
        this.roundNum = in.readInt();
        this.dice = (Die[]) in.readArray(Die.class.getClassLoader());
        this.diceStates = in.createIntArray();
    }

    public static final Parcelable.Creator<Round> CREATOR = new Parcelable.Creator<Round>() {
        @Override
        public Round createFromParcel(Parcel source) {
            return new Round(source);
        }

        @Override
        public Round[] newArray(int size) {
            return new Round[size];
        }
    };
}