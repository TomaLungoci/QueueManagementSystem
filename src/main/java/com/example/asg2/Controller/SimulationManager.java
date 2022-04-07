package com.example.asg2.Controller;

import com.example.asg2.Model.Scheduler;
import com.example.asg2.Model.SelectionPolicy;
import com.example.asg2.Model.Task;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimulationManager implements Runnable {
    //data read from UI
    public int timeLimit = 100;
    public int maxProcessingTime = 10;
    public int minProcessingTime = 2;
    public int nrOfServers = 3;
    public int nrOfClients = 100;
    public int maxArrivalTime = 0;
    public int minArrivalTime = 0;
    private int currentTime;
    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    public View1Controller view1Controller;
    public Scheduler scheduler;
    private AtomicBoolean run=new AtomicBoolean(true);
    private FileWriter fileWriter;
    private List<Task> generatedTasks;
    private int averageServiceTime=0;
    int n=0;


    public SimulationManager(int maxNoServers, int maxTaskPerServer, int n, int timeLimit, int nrOfClients, int nrOfServers, int minArrivalTime, int maxArrivalTime, int minProcessingTime, int maxProcessingTime, SelectionPolicy selectionPolicy, View1Controller controller) throws IOException {
        scheduler = new Scheduler(maxNoServers, maxTaskPerServer);
        scheduler.changeStrategy(selectionPolicy);
        this.timeLimit = timeLimit;
        this.nrOfClients = nrOfClients;
        this.nrOfServers = nrOfServers;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.view1Controller=controller;
        //printVales();
        this.n=n;
        generateNRandomTasks(n);
        fileWriter=new FileWriter("out.txt");
        fileWriter.write("SIMULATION LOG\n");
    }


    private void printVales() {
        System.out.println(timeLimit);
        System.out.println(minArrivalTime);
        System.out.println(maxArrivalTime);
        System.out.println(minProcessingTime);
        System.out.println(maxProcessingTime);
    }

    private void generateNRandomTasks(int n) {
        Random random = new Random();
        int sum=0;
        generatedTasks = new ArrayList<Task>();
        for (int i = 1; i <= n; i++) {
            int randProcessingTime = random.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime;
            sum+=randProcessingTime;
            int randArrivalTime = random.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime;
            Task task = new Task(i, randArrivalTime, randProcessingTime);
            generatedTasks.add(task);
        }
        this.averageServiceTime=sum/n;
        Collections.sort(generatedTasks);
        //System.out.println(listWaitingClients());
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void terminate(){
        run.set(false);
    }

    @Override
    public void run() {
        currentTime = 0;
        int peakHour=0;
        int max=0;
        int avgWaitingTime=0;
        List<Task> currentTimeTasks = new ArrayList<>();
        while (currentTime < timeLimit && run.get()) {
            for (Task t : generatedTasks) {
                if (t.getArrivalTime() == currentTime) {
                    scheduler.dispatchTask(t);
                    currentTimeTasks.add(t);
                }
            }
            if(scheduler.totalClients()>max){
                peakHour=currentTime;
                max=scheduler.totalClients();
            }
            generatedTasks.removeAll(currentTimeTasks);
            //update UI frame
            System.out.println("Time: " + currentTime);
            view1Controller.setViewText(String.valueOf(currentTime), listWaitingClients(), scheduler.toString());
            try {
                fileWriter.append('\n');
                fileWriter.append("Time: "+String.valueOf(currentTime)+"\n");
                fileWriter.append(listWaitingClients()+"\n");
                fileWriter.append(scheduler.toString());
                fileWriter.append('\n');
            } catch (IOException e) {
                e.printStackTrace();
            }
            currentTime++;
            System.out.println(listWaitingClients());
            System.out.println(scheduler.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        try {
            fileWriter.append("\nPeak hour: "+peakHour+"\n"+"Avg service time: "+averageServiceTime+"\n"+"Average waiting time: "+ scheduler.getStrategy().getSum()/n);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String listWaitingClients() {
        String s = "";
        s += "Waiting clients: ";
        for (Task t : generatedTasks) {
            s += "(" + t.getID() + "," + t.getArrivalTime() + "," + t.getServiceTime() + ") ";
        }
        //s+="\n";
        return s;
    }

}
