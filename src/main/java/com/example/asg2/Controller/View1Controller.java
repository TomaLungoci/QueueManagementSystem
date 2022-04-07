package com.example.asg2.Controller;

import com.example.asg2.Model.SelectionPolicy;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class View1Controller {
    @FXML
    private TextField numberOfClientsText;
    @FXML
    private TextField numberOfQueuesText;
    @FXML
    private TextField simulationIntervalText;
    @FXML
    private TextField arrivalTimeMinText;
    @FXML
    private TextField arrivalTimeMaxText;
    @FXML
    private TextField serviceTimeMinText;
    @FXML
    private TextField serviceTimeMaxText;
    @FXML
    private Button startButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button timeButton;
    @FXML
    private TextArea waitingClientsLabel;
    @FXML
    private TextArea timeLabel;
    @FXML
    private TextArea queues;

    private SimulationManager simulationManager;



    @FXML
    public void onResetButtonClick(ActionEvent event){
        numberOfClientsText.setText("");
        numberOfQueuesText.setText("");
        simulationIntervalText.setText("");
        arrivalTimeMaxText.setText("");
        arrivalTimeMinText.setText("");
        serviceTimeMaxText.setText("");
        serviceTimeMinText.setText("");
    }

    @FXML
    public void onStartButtonClick(ActionEvent event) throws IOException {
        if(this.validateInput()!=0){
            numberOfClientsText.setText("");
            numberOfQueuesText.setText("");
            simulationIntervalText.setText("");
            arrivalTimeMaxText.setText("");
            arrivalTimeMinText.setText("");
            serviceTimeMaxText.setText("");
            serviceTimeMinText.setText("");
            return;
        }else{
            int arrivalTimeMax=Integer.parseInt(arrivalTimeMaxText.getText());
            int arrivalTimeMin=Integer.parseInt(arrivalTimeMinText.getText());
            int serviceTimeMax=Integer.parseInt(serviceTimeMaxText.getText());
            int serviceTimeMin=Integer.parseInt(serviceTimeMinText.getText());
            if(arrivalTimeMin>arrivalTimeMax || serviceTimeMin>serviceTimeMax){
                numberOfClientsText.setText("");
                numberOfQueuesText.setText("");
                simulationIntervalText.setText("");
                arrivalTimeMaxText.setText("");
                arrivalTimeMinText.setText("");
                serviceTimeMaxText.setText("");
                serviceTimeMinText.setText("");
                return;
            }
        }
        int numberOfClients=Integer.parseInt(numberOfClientsText.getText());
        int numberOfQueues=Integer.parseInt(numberOfQueuesText.getText());
        int simulationInterval=Integer.parseInt(simulationIntervalText.getText());
        int arrivalTimeMax=Integer.parseInt(arrivalTimeMaxText.getText());
        int arrivalTimeMin=Integer.parseInt(arrivalTimeMinText.getText());
        int serviceTimeMax=Integer.parseInt(serviceTimeMaxText.getText());
        int serviceTimeMin=Integer.parseInt(serviceTimeMinText.getText());
        simulationManager=new SimulationManager(numberOfQueues, 4, numberOfClients, simulationInterval, numberOfClients, numberOfQueues, arrivalTimeMin, arrivalTimeMax, serviceTimeMin, serviceTimeMax, SelectionPolicy.SHORTEST_TIME, this);
        Thread t=new Thread(simulationManager);
        t.start();
    }

    @FXML
    public void setViewText(String timeLimit, String waitingClients, String queue){
        timeLabel.setText(timeLimit);
        waitingClientsLabel.setText(waitingClients);
        queues.setText(queue);
    }

    public int validateInput(){
        try{
            Integer.parseInt(numberOfClientsText.getText());
            Integer.parseInt(numberOfQueuesText.getText());
            Integer.parseInt(simulationIntervalText.getText());
            Integer.parseInt(arrivalTimeMinText.getText());
            Integer.parseInt(arrivalTimeMinText.getText());
            Integer.parseInt(serviceTimeMaxText.getText());
            Integer.parseInt(serviceTimeMinText.getText());
        }catch(NumberFormatException e){
            return -1;
        }
        return 0;
    }

    @FXML
    public void onStopButtonClick(ActionEvent event){
        simulationManager.getScheduler().terminate();
        simulationManager.terminate();
    }
//    @FXML
//    public void onTimeButtonClick(ActionEvent event){
//
//    }



}