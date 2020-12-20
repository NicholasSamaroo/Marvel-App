package com.example.marvel.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class ComicItems implements Parcelable {

    private String resourceURI;
    private String name;

    protected ComicItems(Parcel in) {
        resourceURI = in.readString();
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

    public void setResourceURI(String resourceURI) {
        this.resourceURI = resourceURI;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceURI() {
        return resourceURI;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resourceURI);
        dest.writeString(name);
    }
}
