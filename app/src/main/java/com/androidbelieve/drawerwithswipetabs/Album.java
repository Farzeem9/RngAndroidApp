package com.androidbelieve.drawerwithswipetabs;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Album {
    private String name;
    private int numOfSongs,rentw,rentm;
    private int thumbnail;
    private String link;
    private String aid,pid,subcat,date,per;

    public Album(String subcat,String pid,String name, int numOfSongs, int thumbnail,String aid,String timestamp,int rentw, int rentm,String per) throws ParseException {
        this.pid=pid;
        this.subcat=subcat;
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
        this.aid=aid;
        this.rentw=rentw;
        this.rentm=rentm;
        this.per=per;
        link=Config.link+"viewthumb.php?aid="+aid;
        Log.v("link in album",link);
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
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public int getrentweek() {
        return rentw;
    }

    public void setrentweek(int rentw) {
        this.rentw = rentw;
    }

    public int getrentmonth() {
        return rentm;
    }

    public void setrentmonth(int rentm) {
        this.rentm = rentm;
    }

    public String getper() {
        return per;
    }

    public void setper(String per) {
        this.per = per;
    }
}