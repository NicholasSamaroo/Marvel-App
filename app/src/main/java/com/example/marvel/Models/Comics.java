package com.example.marvel.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Comics implements Parcelable {
    private int available;
    private ArrayList<ComicItems> items;
    private int returned;

    protected Comics(Parcel in) {
        available = in.readInt();
        items = in.createTypedArrayList(ComicItems.CREATOR);
        returned = in.readInt();
    }

    public static final Creator<Comics> CREATOR = new Creator<Comics>() {
        @Override
        public Comics createFromParcel(Parcel in) {
            return new Comics(in);
        }

        @Override
        public Comics[] newArray(int size) {
            return new Comics[size];
        }
    };

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public ArrayList<ComicItems> getItems() {
        return items;
    }

    public void setItems(ArrayList<ComicItems> items) {
        this.items = items;
    }

    public int getReturned() {
        return returned;
    }

    public void setReturned(int returned) {
        this.returned = returned;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(available);
        dest.writeTypedList(items);
        dest.writeInt(returned);
    }
}
