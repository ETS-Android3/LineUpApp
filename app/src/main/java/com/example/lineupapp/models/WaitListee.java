package com.example.lineupapp.models;

public class WaitListee implements Cloneable{

    private String name;

    private String timeNeeded;

    public WaitListee(String name, String timeNeeded){
        this.name = name;
        this.timeNeeded = timeNeeded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeNeeded() {
        return timeNeeded;
    }

    public void setTimeNeeded(String timeNeeded) {
        this.timeNeeded = timeNeeded;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new WaitListee(name, timeNeeded);
    }

    @Override
    public String toString() {
        return name + " " + timeNeeded;
    }
}
