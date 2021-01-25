package com.example.marvel.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ComicItems implements Parcelable {
    private String name;

    protected ComicItems(Parcel in) {
        name = in.readString();
    }

    public static final Creator<ComicItems> CREATOR = new Creator<ComicItems>() {
        @Override
        public ComicItems createFromParcel(Parcel in) {
            return new ComicItems(in);
        }

        @Override
        public ComicItems[] newArray(int size) {
            return new ComicItems[size];
        }
    };

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
