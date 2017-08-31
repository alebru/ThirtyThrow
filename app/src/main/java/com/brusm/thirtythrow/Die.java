/**
 * Created by brusm on 2017-07-05.
 *
 * Klass som representerar tärningar
 *
 *
 */

package com.brusm.thirtythrow;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;


public class Die implements Parcelable {
    private int mDiceID;
    private int mDiceState;
    private boolean isSelected;

    public int getDiceID() {
        return mDiceID;
    }

    public Die(int ID) {
        this.mDiceState = 0;
        this.mDiceID = ID;
        this.isSelected = false;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getDiceState() {
        return mDiceState;
    }

    public void setDiceState(int state) {
        mDiceState = state;
    }

    public static int Sum(ArrayList<Die> dice)
    {
        int sum = 0;
        for (Die die:dice) {
            sum += die.getDiceState();
        }
        return sum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Skriver värden till parcelable
     *
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mDiceID);
        dest.writeInt(this.mDiceState);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }


    /**
     * Hämtar värden från parcelable
     *
     */
    protected Die(Parcel in) {
        this.mDiceID = in.readInt();
        this.mDiceState = in.readInt();
        this.isSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Die> CREATOR = new Parcelable.Creator<Die>() {
        @Override
        public Die createFromParcel(Parcel source) {
            return new Die(source);
        }

        @Override
        public Die[] newArray(int size) {
            return new Die[size];
        }
    };
}