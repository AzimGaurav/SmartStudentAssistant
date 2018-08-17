package com.azimgaurav.smartstudentassistant.model;

/**
 * Created by Avinash on 15-Aug-18.
 */

public class Note {

    private String title,desc,color,font;

    Note()
    {

    }

    public Note(String title, String desc, String color, String font) {
        this.title = title;
        this.desc = desc;
        this.color = color;
        this.font = font;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }
}