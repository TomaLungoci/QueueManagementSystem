package com.example.asg2.Model;

public class Task implements Comparable {
    private int ID;
    private int arrivalTime;
    private int serviceTime;

    public Task(int ID, int arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Task){
            if(this.arrivalTime<((Task) o).arrivalTime){
                return -1;
            }else if(this.arrivalTime==((Task) o).arrivalTime){
                return 0;
            }else{
                return 1;
            }
        }else{
            return -1;
        }
    }

    @Override
    public String toString() {
        String s="";
        s+="("+this.ID+","+this.arrivalTime+","+this.serviceTime+")";
        return s;
    }

    public void decrementServiceTime(){
        this.serviceTime--;
    }

    public int getID() {
        return ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }
}
