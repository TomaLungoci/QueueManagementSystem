package com.example.asg2.Model;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers=new ArrayList<>();
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer) {
        this.maxNoServers=maxNoServers;
        this.maxTasksPerServer=maxTasksPerServer;
        for(int i=1; i<=maxNoServers; i++){
            servers.add(new Server());
            Thread thread=new Thread(servers.get(i-1));
            thread.start();

        }
    }

    @Override
    public String toString() {
        String s="";
        for(Server server: servers){
            s+=server.toString()+"\n";
        }
        return s;
    }

    public void terminate(){
        for(Server s:servers){
            s.terminate();
        }
    }

    public int totalClients(){
        int total=0;
        for(Server s1: servers){
            total+=s1.size();
        }
        return total;
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy==SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ConcreteStrategyQueue();
        }
        if(policy==SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();
        }
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void dispatchTask(Task t){
        strategy.addTask(servers, t);
    }

    public List<Server> getServers() {
        return servers;
    }
}
