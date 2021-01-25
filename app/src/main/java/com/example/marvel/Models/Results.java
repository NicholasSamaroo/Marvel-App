package com.example.marvel.Models;

public class Results {
    private int id;
    private String name;
    private String description;
    private ImageObject thumbnail;
    private Comics comics;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ImageObject getThumbnail() {
        return thumbnail;
    }

    public Comics getComics() {
        return comics;
    }
}
