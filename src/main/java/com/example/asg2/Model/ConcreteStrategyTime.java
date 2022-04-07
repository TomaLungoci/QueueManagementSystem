package com.example.asg2.Model;

import com.example.asg2.Model.Server;
import com.example.asg2.Model.Strategy;
import com.example.asg2.Model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    private int sum=0;
    @Override
    public void addTask(List<Server> servers, Task t) {
        Server minWaitingTimeServer=servers.get(0);
        int waitingTime=10000;
        for(Server s: servers){
            if(s.getWaitingPeriod().intValue()<waitingTime){
                minWaitingTimeServer=s;
                waitingTime=s.getWaitingPeriod().intValue();
            }
        }
        sum+=waitingTime;
        minWaitingTimeServer.addTask(t);
    }

    @Override
    public int getSum() {
        return sum;
    }
}
