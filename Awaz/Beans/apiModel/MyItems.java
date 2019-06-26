package com.Beans.apiModel;

/**
 * Created by Asad on 3/31/2019.
 */
public class MyItems {
    double lat,log;
    String r_title;



    public MyItems() {
    }

    public MyItems( double lat, double log,String r_title) {
        this.r_title = r_title;
        this.lat = lat;
        this.log = log;
    }

    public String getR_title() {
        return r_title;
    }

    public void setR_title(String r_title) {
        this.r_title = r_title;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }
}
