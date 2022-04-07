package com.example.asg2.Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod=new AtomicInteger(0);
    private AtomicInteger numberOfClients=new AtomicInteger(0);
    private AtomicBoolean run;
    private int empty=1;

    public Server() {
        tasks=new LinkedBlockingDeque<>();
        run=new AtomicBoolean(true);
        waitingPeriod.set(0);
    }

    public void addTask(Task newTask){

        this.waitingPeriod.addAndGet(newTask.getServiceTime());
        //this.waitingPeriod.set(this.waitingPeriod.get()+newTask.getServiceTime());
        numberOfClients.getAndIncrement();
        tasks.add(newTask);
        //tasks.put(newTask);
    }

    public void terminate(){
        run.set(false);
    }

    @Override
    public void run() {
        while (run.get()) {
            Task t = tasks.peek();
            if(t!=null) {

                try {
                    Thread.sleep(1000);
                    // Task task=tasks.take();
//                    while(t.getServiceTime()!=0){
//                        t.decrementServiceTime();
//                        waitingPeriod.getAndDecrement();
//                        Thread.sleep(1000);
//                    }
//                    tasks.take();
                    if (t.getServiceTime() > 1) {
                        //Thread.sleep(5000);
                        t.decrementServiceTime();
                        this.waitingPeriod.getAndDecrement();
                    } else {
                        tasks.take();
                        numberOfClients.getAndDecrement();
                        this.waitingPeriod.getAndDecrement();
                    }
                    //Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String toString() {
        String s="Queue: ";
        for(Task t: tasks){
            s+=t.toString()+" ";
        }
        s+="   waiting time->"+this.waitingPeriod.get()+" size->"+this.tasks.size();
        return s;
    }

    public int size(){
        return tasks.size();
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
}
