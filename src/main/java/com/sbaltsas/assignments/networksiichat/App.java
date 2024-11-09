package com.sbaltsas.assignments.networksiichat;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class App extends Application {
    protected static int PORT = 6000;
    private static UDPServer server = null;
    private static AppGUIController guiController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AppGUI2.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("BingChat");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(server != null){
                    server.interrupt();
                }
                System.exit(0);
            }
        });
        stage.show();
        System.out.println("stage set!");
        guiController = fxmlLoader.getController();
    }

    public static void main(String[] args) {
        System.out.printf("App will listen to port %d for connections!%n",PORT);
        launch();
    }

    public static void startServer(int port){
        server = new UDPServer(port, guiController);
        server.start();
    }
}