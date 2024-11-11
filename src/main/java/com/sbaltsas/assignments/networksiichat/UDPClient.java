package com.sbaltsas.assignments.networksiichat;

import javafx.application.Platform;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UDPClient extends Thread{
    private DatagramSocket socket;
    private final AppGUIController appGUIController;
    private final int PEER_PORT;
    private final InetAddress PEER_ADDRESS;
    private final BlockingQueue<String> messageQueue;

    public UDPClient(InetAddress address,int port, AppGUIController guiController){
        PEER_ADDRESS = address;
        PEER_PORT = port;
        appGUIController = guiController;
        messageQueue = new LinkedBlockingQueue<String>();
        try{
            socket = new DatagramSocket();
        }
        catch (SocketException e){
            System.err.println("[CLIENT] Error! Cannot initialize UDP client!");
            Platform.runLater(() -> {
                appGUIController.showError("Peer connection failed","Cannot connect to peer! Is it online?");
            });
        }
    }

    @Override
    public void run(){
        while(!Thread.interrupted()) {
            try {
                final String finalMessage = messageQueue.take();
                DatagramPacket packet = new DatagramPacket(finalMessage.getBytes(),finalMessage.getBytes().length, PEER_ADDRESS, PEER_PORT);
                socket.send(packet);
                Platform.runLater(() -> {
                    appGUIController.addMessage("You",finalMessage);
                });
            } catch (IOException e) {
                System.err.println("[CLIENT] Packet could not be sent!");
            }
            catch (InterruptedException e) {
                messageQueue.clear();
            }
        }
        socket.close();
    }
    protected void addMessageToQueue(String message){
        String messageToAdd = new String(message.getBytes(), StandardCharsets.UTF_8);
        messageQueue.add(messageToAdd);
    }
}
