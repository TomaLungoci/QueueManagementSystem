package com.example.asg2.Model;

import com.example.asg2.Model.Server;
import com.example.asg2.Model.Strategy;
import com.example.asg2.Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    private int sum=0;
    @Override
    public void addTask(List<Server> servers, Task t) {
        Server minTaskServer=servers.get(0);
        for(Server s: servers){
            if(s.getTasks().size()<minTaskServer.getTasks().size()){
                minTaskServer=s;
            }
        }
        sum+= minTaskServer.size();
        minTaskServer.addTask(t);
    }

    public int getSum() {
        return sum;
    }
}
