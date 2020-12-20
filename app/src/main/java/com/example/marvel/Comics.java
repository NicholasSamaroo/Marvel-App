package com.example.marvel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Comics implements Parcelable {
    private String available;
    private String collectionURI;
    private ArrayList<ComicItems> items;

    protected Comics(Parcel in) {
        available = in.readString();
        collectionURI = in.readString();
        items = in.createTypedArrayList(ComicItems.CREATOR);
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

    public void setAvailable(String available) {
        this.available = available;
    }

    public void setCollectionURI(String collectionURI) {
        this.collectionURI = collectionURI;
    }

    public void setItems(ArrayList<ComicItems> items) {
        this.items = items;
    }

    public String getAvailable() {
        return available;
    }

    public String getCollectionURI() {
        return collectionURI;
    }

    public ArrayList<ComicItems> getItems() {
        return items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(available);
        dest.writeString(collectionURI);
        dest.writeTypedList(items);
    }
}
