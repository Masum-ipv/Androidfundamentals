package com.example.androidfundamentals.model;

public class Sport {
    private String title, info;
    private int imageResources;

    public static final String TITLE_KEY = "title";
    public static final String SUB_TITLE_KEY = "subtitle";
    public static final String IMAGE_KEY = "image_resource";

    public Sport(String title, String info, int imageResources) {
        this.title = title;
        this.info = info;
        this.imageResources = imageResources;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public int getImageResources() {
        return imageResources;
    }
}
