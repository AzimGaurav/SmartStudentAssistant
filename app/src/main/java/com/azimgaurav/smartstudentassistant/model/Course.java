package com.azimgaurav.smartstudentassistant.model;

import java.util.HashMap;

/**
 * Created by Avinash on 16-Aug-18.
 */

public class Course {
    private String title,pre,textbooks;
    Course()
    {

    }

    public Course(String title, String pre, String textbooks) {
        this.title = title;
        this.pre = pre;
        this.textbooks = textbooks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getTextbooks() {
        return textbooks;
    }

    public void setTextbooks(String textbooks) {
        this.textbooks = textbooks;
    }
}