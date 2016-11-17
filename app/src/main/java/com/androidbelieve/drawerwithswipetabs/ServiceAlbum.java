package com.androidbelieve.drawerwithswipetabs;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.androidbelieve.drawerwithswipetabs.MyAds.Month;

/**
 * Created by 20000136 on 11/18/2016.
 */

public class ServiceAlbum {
    private String name;
    private int numOfSongs;
    private int thumbnail;
    private String cat;
    private String date;
    private String link;
    private String sid;

    public ServiceAlbum(String name, int numOfSongs, int thumbnail, String sid,String timestamp,String cat) throws ParseException {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
        this.sid=sid;
        link="http://rng.000webhostapp.com/imgthumbnail.php?id="+sid;
        this.cat=cat;
        Date today=new Date();
        Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp);
        Log.v("timeStamp",timestamp);
        Log.v("date",date.toString());
        if(today.getDay()==date.getDay())
            this.date="Today ";
        else if(today.getDay()==date.getDay()+1)
            this.date="Yesterday ";
        else
        {

            this.date=date.getDay()+" "+Month(date)+" ";
            if(!(today.getYear()==date.getYear()))
                this.date+=date.getYear()+" ";
        }

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

    public String getsubCat()
    {return this.cat;}
    public String getDate(){return date;}
    public void setNumOfSongs(int numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSid(){return this.sid;}

    public String getLink(){
        return this.link;
    }
}
