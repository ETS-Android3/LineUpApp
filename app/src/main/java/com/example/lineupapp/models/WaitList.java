package com.example.lineupapp.models;

import java.util.ArrayList;

public class WaitList implements Cloneable {

    private ArrayList<WaitListee> list;
    private String name;
    private String timeInit, timeFinal;
    private String nextAvailTime;


    public WaitList(String name, String timeFrom, String timeTo) {
        this.nextAvailTime = timeFrom;
        this.timeInit = timeFrom;
        this.timeFinal = timeTo;
        list = new ArrayList<WaitListee>();
        this.name = name;
    }

    public ArrayList<WaitListee> getWaitListees(){
        ArrayList<WaitListee> clonedwle = new ArrayList<WaitListee>();
        clonedwle.addAll(list);
        return clonedwle;
    }

    public WaitListee getWaitListee(String name){
        for (WaitListee wl : list){
            if (wl.getName().equals(name))
                return wl;
        }
        System.out.println("Error finding wait-listee");
        return null;
    }
    public void addWaitListee(WaitListee waitListee){
        list.add(waitListee);
    }

    public boolean removeWaitListee(WaitListee waitListee){
        for (WaitListee wle : list){
            if (wle.getName().equals(waitListee.getName()) && wle.getTimeNeeded().equals(waitListee.getTimeNeeded())){
                list.remove(waitListee);
                return true;
            }
        }
        System.out.println("wait-listee not found. Will not remove.");
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeInit() {
        return timeInit;
    }

    public void setTimeInit(String timeInit) {
        this.timeInit = timeInit;
    }

    public String getTimeFinal() {
        return timeFinal;
    }

    public void setTimeFinal(String timeFinal) {
        this.timeFinal = timeFinal;
    }

    public String getNextAvailTime(){ return nextAvailTime; }

    public void setNextAvailTime(String nextAvailTime){ this.nextAvailTime = nextAvailTime; }

    @Override
    public String toString() {
        return name + ", "+ timeInit +" to "+ timeFinal;
    }
}
