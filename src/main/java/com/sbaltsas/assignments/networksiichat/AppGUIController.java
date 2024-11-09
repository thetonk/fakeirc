package com.sbaltsas.assignments.networksiichat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
    private Spinner<Integer> portField;

    @FXML
    public void initialize(){
        portField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1,65535,6000));
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
        System.out.println("Hello ");
        System.out.println(portField.getValue());
        String message = msgToSend.getText();
        if (!message.isEmpty()){
            messages.appendText(msgToSend.getText()+"\n");
        }
        actionEvent.consume();
    }

    @FXML
    protected void testConnection(ActionEvent actionEvent){
        actionEvent.consume();
    }
}