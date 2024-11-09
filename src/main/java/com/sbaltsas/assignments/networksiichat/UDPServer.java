package com.sbaltsas.assignments.networksiichat;

import javafx.application.Platform;

import java.io.IOException;
import java.net.*;

public class UDPServer extends Thread{
    private DatagramSocket socket;
    private final AppGUIController appGUIController;
    public final static int PACKET_SIZE = 65507; //65535 bytes - 20 bytes (ipv4) - 8 bytes (udp header)
    private final byte[] buffer;

    public UDPServer(int port, AppGUIController guiController){
        buffer = new byte[PACKET_SIZE];
        appGUIController = guiController;
        try{
            socket = new DatagramSocket(null);
            InetSocketAddress address = new InetSocketAddress("0.0.0.0", port);
            socket.bind(address);
            System.out.println("[SERVER] UDP server started!");
        } catch (SocketException e){
            System.err.printf("[SERVER] UDP server could not be started! %s", e);
        }
    }

    @Override
    public void run(){
        while (!Thread.interrupted()) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, PACKET_SIZE);
                socket.receive(packet);
                System.out.printf("Packet length: %d, offset: %d\n",packet.getLength(),packet.getOffset());
                InetAddress clientAddress = packet.getAddress();
                int clientPort = packet.getPort();
                String message = new String(packet.getData(),packet.getOffset(),packet.getLength());
                Platform.runLater(() -> {
                    appGUIController.addMessage(message);
                });
                packet = new DatagramPacket(buffer,PACKET_SIZE,clientAddress,clientPort);
                socket.send(packet);
            } catch (IOException e) {
                System.err.println("[SERVER] UDP packet could not be received!");
            }
        }
        socket.close();
    }
}
