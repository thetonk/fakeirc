package com.sbaltsas.assignments.networksiichat;

import javax.sound.sampled.*;

public class CallInput extends Thread {
    private AudioFormat format;
    private TargetDataLine microphone;
    private boolean inputAvailable = false;
    private byte[] audioData;
    protected boolean isOnCall = false;

    public CallInput(){
        format = new AudioFormat(44000, 8, 1, true, true);
        audioData = new byte[1000];

        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();    //get available mixers
        System.out.println("Available mixers:");
        Mixer mixer = null;
        for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
            System.out.println(cnt + " " + mixerInfo[cnt].getName());
            mixer = AudioSystem.getMixer(mixerInfo[cnt]);

            Line.Info[] lineInfos = mixer.getTargetLineInfo();
            if (lineInfos.length >= 1 && lineInfos[0].getLineClass().equals(TargetDataLine.class)) {
                System.out.println(cnt + " Mic is supported!");
                break;
            }
        }

        DataLine.Info info = new DataLine.Info(TargetDataLine.class,
                format); // format is an AudioFormat object
        try {
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);
            System.out.println("Mic ready");
            System.out.println(info);
            inputAvailable = true;
        } catch (LineUnavailableException ex) {
            System.err.println("[CallClient] Microphone init failed!");
            System.err.println(ex);
        }
    }


    @Override
    public void run(){
        while (!Thread.interrupted()){
            if(isOnCall){
                if(!microphone.isRunning()){
                    System.out.println("mic opening");
                    microphone.start();
                }
            }
            if(inputAvailable && isOnCall){
                //System.out.println("sampling");
                microphone.read(audioData, 0, 1000);
                //System.out.println("sending");
                //System.out.println(audioData);
                //isOnCall = false;
                App.client.sendVoicePacket(audioData);
            }
        }
    }
}
