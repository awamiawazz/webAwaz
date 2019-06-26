package com.Beans;


/**
 * Created by Hp on 11/20/2018.
 */
public class Report {
    int r_id, user_id,p_id;
    private String title,description,severity,status,address,imageString,videoString;
    private double lat,log;

    //private InputStream image,video;
    private byte[] imagee,videoo;

    private Problemm problem;



    public Report() {
    }

    public Report(int r_id, int user_id, String title, String description, String severity, String status, String address, double lat, double log, String imageString, String videoString) {
        this.r_id = r_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.status = status;
        this.address = address;
        this.lat = lat;
        this.log = log;
        this.imageString = imageString;
        this.videoString = videoString;
    }

    // updated
    public Report(int r_id, int user_id, String title, String description, String severity, String status, String address, double lat, double log, byte[] imagee, byte[] videoo) {
        this.r_id = r_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.severity = severity;
        this.status = status;
        this.address = address;
        this.lat = lat;
        this.log = log;
        this.imagee = imagee;
        this.videoo = videoo;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getVideoString() {
        return videoString;
    }

    public void setVideoString(String videoString) {
        this.videoString = videoString;
    }

    public String getAddress() {
        return address;
    }

    public int getP_id() {
        return p_id;
    }

    public Problemm getProblem() {
        return problem;
    }

    public void setProblem(Problemm problem) {
        this.problem = problem;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getStatus() {
        return status;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public byte[] getImagee() {
        return imagee;
    }

    public void setImagee(byte[] imagee) {
        this.imagee = imagee;
    }

    public byte[] getVideoo() {
        return videoo;
    }

    public void setVideoo(byte[] videoo) {
        this.videoo = videoo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
