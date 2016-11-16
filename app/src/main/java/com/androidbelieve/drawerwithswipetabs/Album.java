package com.androidbelieve.drawerwithswipetabs;

public class Album {
    private String name;
    private int numOfSongs;
    private int thumbnail;
    private String link;
    private String aid;

    public Album(String name, int numOfSongs, int thumbnail,String aid) {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
        this.aid=aid;
        link="http://rng.000webhostapp.com/viewthumb.php?aid="+aid;
    }

    public Album(String name, int numOfSongs, int thumbnail) {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
    }
    public void setLink(String link) { this.link = link; }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(int numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAid(){return this.aid;}

    public String getLink(){
        return this.link;
    }
}