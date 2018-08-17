package com.azimgaurav.smartstudentassistant.model;

/**
 * Created by Avinash on 16-Aug-18.
 */

public class AttendanceCourse {
    private int sessions_taken,present,absent;
    private String title,percent;
    AttendanceCourse(){}

    public AttendanceCourse(int sessions_taken, int present, int absent,String title, String percent) {
        this.sessions_taken = sessions_taken;
        this.present = present;
        this.absent = absent;
        this.percent = percent;
        this.title=title;
    }

    public int getSessionsCount() {
        return sessions_taken;
    }

    public void setSessionsCount(int sessions_taken) {
        this.sessions_taken = sessions_taken;
    }

    public int getPresentCount() {
        return present;
    }

    public void setPresentCount(int present) {
        this.present = present;
    }

    public int getAbsentCount() {
        return absent;
    }

    public void setAbsentCount(int absent) {
        this.absent = absent;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}