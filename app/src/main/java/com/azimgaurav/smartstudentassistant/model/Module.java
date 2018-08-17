package com.azimgaurav.smartstudentassistant.model;

/**
 * Created by Avinash on 16-Aug-18.
 */

public class Module {
    private String name,points;

    Module()
    {

    }

    public Module(String name, String points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
