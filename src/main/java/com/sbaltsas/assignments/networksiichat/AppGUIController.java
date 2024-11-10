package com.sbaltsas.assignments.networksiichat;

import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class AppGUIController {
    @FXML
    private Button sendBtn;
    @FXML
    private Label statusLabel;
    @FXML
    private ScrollPane textcontainer;
    @FXML
    private TextFlow messages;
    @FXML
    private TextField msgToSend;
    @FXML
    private TextField addressField;
    @FXML
    private Spinner<Integer> portField;

    @FXML
    public void initialize(){
        portField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,65535,6000));
        addressField.setText("localhost");
        System.out.println("gui initialized!");
        messages.getChildren().addListener(new ListChangeListener<Node>() {
            @Override
            public void onChanged(Change<? extends Node> c) {
                //messages.layout();
                textcontainer.layout();
                textcontainer.setVvalue(1.0); //scroll down
            }
        });
    }

    @FXML
    protected void onMessageKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            System.out.println("ENTER pressed!");
            sendMessageToClient(msgToSend.getText());
        }
        keyEvent.consume();
    }

    @FXML
    protected void sendMessageClicked(ActionEvent actionEvent) {
        sendMessageToClient(msgToSend.getText());
        actionEvent.consume();
    }

    @FXML
    protected void connectButtonClick(ActionEvent actionEvent){
        int port = portField.getValue();
        try{
            InetAddress address = InetAddress.getByName(addressField.getText());
            App.startClient(address,port);
        }
        catch (UnknownHostException e){
            System.err.println("Error! Peer address is invalid!");
        }
        actionEvent.consume();
    }

    @FXML
    protected synchronized void addMessage(String address,String message){
        Text text = new Text("<"+address+"> "+message+"\n");
        if(address.equalsIgnoreCase("you")){
            text.setFill(Color.GREEN);
        }
        else{
            text.setFill(Color.BLUE);
        }
        messages.getChildren().add(text);
    }

    private synchronized void sendMessageToClient(String message){
        if(!message.isEmpty()){
            App.client.addMessageToQueue(message);
        }
    }
}