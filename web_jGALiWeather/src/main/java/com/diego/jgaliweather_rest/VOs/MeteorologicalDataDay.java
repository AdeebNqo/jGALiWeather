
package com.diego.jgaliweather_rest.VOs;

import java.util.ArrayList;

public class MeteorologicalDataDay {
    
    private String name;
    private ArrayList<Integer> sky;
    private ArrayList<Integer> wind;
    private ArrayList<Integer> temp;
    private String comment;

    public MeteorologicalDataDay() {
    }

    public MeteorologicalDataDay(ArrayList<Integer> sky, ArrayList<Integer> wind, ArrayList<Integer> temp) {
        this.sky = sky;
        this.wind = wind;
        this.temp = temp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public ArrayList<Integer> getSky() {
        return sky;
    }

    public void setSky(ArrayList<Integer> sky) {
        this.sky = sky;
    }

    public ArrayList<Integer> getWind() {
        return wind;
    }

    public void setWind(ArrayList<Integer> wind) {
        this.wind = wind;
    }

    public ArrayList<Integer> getTemp() {
        return temp;
    }

    public void setTemp(ArrayList<Integer> temp) {
        this.temp = temp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
