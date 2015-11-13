package edu.cuny.brooklyn.cisc3120.homework4.gui;

import javax.swing.JFrame;

import edu.cuny.brooklyn.cisc3120.homework4.core.*;

public class MainGUI {
    public static void main(String[] args) throws Exception {
        Configuration myConfig = new Configuration(16, 4);

        // Resolve and inject dependencies.
        IChooser myChooser = new RandomChooser(myConfig);
        //GuessingGameGUI gui = new GuessingGameGUI(myConfig);
        
        MyGUI myGUI = new MyGUI(myConfig);
        //JFrame settings
        myGUI.setSize(250,400);
		myGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myGUI.setVisible(true);
		myGUI.setResizable(false);
        //Game myGame = new Game(myChooser, gui, gui, myConfig);
        //gui.display();
        
        //myGame.play();
    }
}
