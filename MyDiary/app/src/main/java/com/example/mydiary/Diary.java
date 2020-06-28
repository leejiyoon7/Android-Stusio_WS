package com.example.mydiary;

public class Diary {
    private String title;
    private String date;
    private String weather;
    private String picturelink;
    private String content;

    public Diary(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getPicturelink() { return picturelink; }

    public void setPicturelink(String picturelink) {
        this.picturelink = picturelink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
