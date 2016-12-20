package com.androidbelieve.drawerwithswipetabs;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 20000136 on 11/18/2016.
 */

public class ServiceAlbum {
    private String name;
    private int numOfSongs;
    private int thumbnail;
    private String cat;
    private String pid;
    private String date;
    private String link;
    private String sid;
    private String city;

    public ServiceAlbum(String pid,String name, int numOfSongs, int thumbnail, String sid,String timestamp,String cat,String city) throws ParseException {
        this.name = name;
        this.pid=pid;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
        this.sid=sid;
        this.city=city;
        link=Config.link+"imgthumbnail.php?id="+sid;
        this.cat=cat;
        Date today=new Date();
        Date yesterday=new Date();
        yesterday.setTime(today.getTime()-((long)864E5));
        Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp);
        date.setTime(date.getTime()+19800000);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        Log.v("timeStamp",timestamp);
        Log.v("date",date.toString());
        if(sdf.format(today).equals(sdf.format(date)))
            this.date="Today ";
        else if(sdf.format(yesterday).equals(sdf.format(date)))
            this.date="Yesterday ";
        else
        {

            //this.date=date.getDay()+" "+Month(date)+" ";
            this.date=new SimpleDateFormat("d MMMM").format(date);
            if(!(today.getYear()==date.getYear()))
                this.date+=" "+date.getYear()+" ";
        }
        Log.v("Date written",this.date);


    }

    public void setLink(String link) { this.link = link; }
    public String getName() {
        return name;
    }
    public String getPid(){return pid;}
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
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
