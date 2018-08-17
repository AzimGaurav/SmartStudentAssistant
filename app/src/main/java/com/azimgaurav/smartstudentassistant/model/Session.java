package com.azimgaurav.smartstudentassistant.model;

/**
 * Created by Avinash on 16-Aug-18.
 */

public class Session {
    private String date,time,status;
    private int sr;
    Session(){}

    public Session(String date, String time, String status, int sr) {
        this.date = date;
        this.time = time;
        this.status = status;
        this.sr = sr;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSr() {
        return sr;
    }

    public void setSr(int sr) {
        this.sr = sr;
    }
}
