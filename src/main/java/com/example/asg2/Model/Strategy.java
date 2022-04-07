package com.example.asg2.Model;
import com.example.asg2.Model.Server;
import com.example.asg2.Model.Task;

import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, Task t);
    public int getSum();
}
