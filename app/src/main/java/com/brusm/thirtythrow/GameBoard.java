/**
 * Created by brusm on 2017-07-05.
 *
 * Klass som hanterar Round-objekt (spelomgångar)
 */

package com.brusm.thirtythrow;

import android.os.Parcel;
import android.os.Parcelable;


public class GameBoard implements Parcelable {

    private int roundNum = 0;
    private Round[] rounds = new Round[] {
            new Round(0),
            new Round(1),
            new Round(2),
            new Round(3),
            new Round(4),
            new Round(5),
            new Round(6),
            new Round(7),
            new Round(8),
            new Round(9)
    };

    public GameBoard() {
    }

    public Round[] getRounds() {
        return rounds;
    }

    public void setRounds(Round[] rounds) {
        this.rounds = rounds;
    }

    public int getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(int roundNum) {
        roundNum = roundNum;
    }

    public void incrRoundNum() {
        roundNum += 1;
    }

    public Round getCurrentRound() {
        return rounds[roundNum];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.roundNum);
        dest.writeTypedArray(this.rounds, flags);
    }

    protected GameBoard(Parcel in) {
        this.roundNum = in.readInt();
        this.rounds = in.createTypedArray(Round.CREATOR);
    }

    public static final Parcelable.Creator<GameBoard> CREATOR = new Parcelable.Creator<GameBoard>() {
        @Override
        public GameBoard createFromParcel(Parcel source) {
            return new GameBoard(source);
        }

        @Override
        public GameBoard[] newArray(int size) {
            return new GameBoard[size];
        }
    };
}