package com.androidbelieve.drawerwithswipetabs;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.androidbelieve.drawerwithswipetabs.MyAds.Month;

public class Ads {
    private String status;
    private String specs;
    private String price;
    private String date;
    private String aid;
    private int image_ads=R.drawable.image_placeholder;
    String link;

    public Ads() {
    }

    public Ads(String status,String specs,String price,String date,int image_ads) {
        this.status = status;
        this.specs = specs;
        this.price = price;
        this.date = date;
        this.image_ads=image_ads;
    }
    public Ads(String status,String specs,String price,String timestamp,String aid) throws ParseException {
        this.status = status;
        this.specs = specs;
        this.price = price;
        this.aid=aid;
        this.link=Config.link+"viewthumb.php?aid="+aid;
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
    public Ads(String status,String specs,String price,String timestamp,String sid,String imagelink) throws ParseException {
        this.status = status;
        this.specs = specs;
        this.price = price;
        this.aid=sid;
        this.link=Config.link+"imgthumbnail.php?sid="+sid;
        Date today=new Date();
        Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp);
        date.setTime(date.getTime()+19800000);
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
    public String getStatus() {
        return status;
    }

    public String getAid()
    {
        return this.aid;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getImage_ads() {
        return image_ads;
    }

    public void setImage_ads(int image_ads) {
        this.image_ads = image_ads;
    }

    public String getlink(){return this.link;}
}