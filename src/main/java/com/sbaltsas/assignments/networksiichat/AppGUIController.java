package com.sbaltsas.assignments.networksiichat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.DatagramSocket;

public class AppGUIController {

    @FXML
    private Button sendBtn;
    @FXML
    private Label statusLabel;
    @FXML
    private TextArea messages;
    @FXML
    private TextField msgToSend;
    @FXML
    private TextField addressField;
    @FXML
    private Spinner<Integer> portField;

    @FXML
    public void initialize(){
        portField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,65535,6000));
        portField.getValueFactory().setValue(App.PORT);
        System.out.println("gui initialized!");
    }

    @FXML
    protected void onMessageKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            System.out.println("ENTER pressed!");
        }
        keyEvent.consume();
    }

    @FXML
    protected void sendMessageClicked(ActionEvent actionEvent) {
        System.out.println("Hello");
        String message = msgToSend.getText();
        if (!message.isEmpty()){
            messages.appendText(msgToSend.getText());
        }
        actionEvent.consume();
    }

    @FXML
    protected void connectButtonClick(ActionEvent actionEvent){
        int port = portField.getValue();
        App.startServer(port);
        actionEvent.consume();
    }

    @FXML
    protected void addMessage(String message){
        messages.appendText(message+"\n");
    }
}