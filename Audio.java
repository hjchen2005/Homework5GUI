package edu.cuny.brooklyn.cisc3120.homework4.gui;
import sun.audio.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Audio{

    public static void music(char gameResult) 
    {       
        AudioPlayer MGP = AudioPlayer.player;
        AudioStream BGM,BGM1;
        AudioData MD;

        ContinuousAudioDataStream loop = null;

        try
        {
            InputStream winAudio = new FileInputStream("Audio/winrevised.wav");
            InputStream loseAudio = new FileInputStream("Audio/loserevised.wav");
            BGM = new AudioStream(winAudio);
            BGM1=new AudioStream(loseAudio);
            if (gameResult=='w')
                AudioPlayer.player.start(BGM);
            else if (gameResult=='l')
                AudioPlayer.player.start(BGM1);
            //MD = BGM.getData();
            //loop = new ContinuousAudioDataStream(MD);

        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
        catch(IOException error)
        {
           error.printStackTrace();
        }
        MGP.start(loop);
    }
}
