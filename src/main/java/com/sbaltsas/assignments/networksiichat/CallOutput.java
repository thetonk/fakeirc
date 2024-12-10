package com.sbaltsas.assignments.networksiichat;

import javax.sound.sampled.*;

public class CallOutput extends Thread {
    private AudioFormat format;
    private SourceDataLine speaker;
    private boolean outputAvailable = false;
    private byte[] audioBuffer;
    protected boolean isDataReady = false;
    public CallOutput(){
        format = new AudioFormat(44000, 8, 1, true, true);
        audioBuffer = new byte[1000];
        DataLine.Info info = new DataLine.Info(SourceDataLine.class,
                format);
        try {
            speaker = (SourceDataLine) AudioSystem.getLine(info);
            speaker.open(format);
            System.out.println("Speaker ready");
            System.out.println(info);
            outputAvailable = true;
        } catch (LineUnavailableException ex) {
            System.err.println("[CallClient] Speaker init failed!");
            System.err.println(ex);
        }
    }

    protected void loadBuffer(byte[] data){
        System.arraycopy(data, 0,audioBuffer,0, audioBuffer.length);
        isDataReady = true;
    }

    @Override
    public void run(){
        while (!Thread.interrupted()){
            if(isDataReady){
                if (!speaker.isRunning()){
                    System.out.println("speaker go");
                    speaker.start();
                }
            }
            if(outputAvailable && isDataReady){
                //System.out.println("play start");
                //speaker.start();

                speaker.write(audioBuffer, 0, 1000);
                //speaker.stop();
                //System.out.println("audio stop");
                isDataReady = false;
            }
        }
    }
}