package com.androidbelieve.drawerwithswipetabs;

import android.util.Log;

public class Album {
    private String name;
    private int numOfSongs;
    private int thumbnail;
    private String link;
    private String aid,pid,subcat;

    public Album(String subcat,String pid,String name, int numOfSongs, int thumbnail,String aid) {
        this.pid=pid;
        this.subcat=subcat;
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
        this.aid=aid;
        link=Config.link+"viewthumb.php?aid="+aid;
        Log.v("link in album",link);
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
    public String getPid()
    {
        return pid;
    }
    public int getNumOfSongs() {
        return numOfSongs;
    }
    public String getSubcat(){return subcat;}
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