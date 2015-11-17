package edu.cuny.brooklyn.cisc3120.homework4.gui;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

import edu.cuny.brooklyn.cisc3120.homework4.core.*;

public final class GuessingGameGUI implements IClient, IGuesser, ActionListener {
	int guess = 0;
	
	final Configuration config;
	final Semaphore semaphore = new Semaphore(1);
	
	JFrame frame;
	final JButton submitButton = new JButton();
	final JPanel numberPad = new JPanel();
	final JPanel submitPanel = new JPanel();
	final JLabel mainLabel = new JLabel();
	final JLabel guessLabel = new JLabel("0");

	GuessingGameGUI(Configuration config) throws InterruptedException 
	{
		this.config = config;
		this.semaphore.acquire();
	}

	/*
	// Example of explicit event:
	static public class WinEvent implements Runnable
	{
		JLabel label;
		JButton submit;
		
		public WinEvent(JLabel label, JButton submit)
		{
			this.label = label;
			this.submit = submit;
		}
		
		public void run()
		{
			label.setText("You Win!");
			submit.setText("Cool");
			submit.setActionCommand("quit");
		}
	}
	*/
	
	public void win()
	{
		// Example of explicit event:
		// EventQueue.invokeLater(new WinEvent(mainLabel, submitButton));
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				mainLabel.setText("You Win!");
				mainLabel.setForeground(Color.green);
				submitButton.setText("Cool");
				Audio.music('w');
				JOptionPane.showMessageDialog(null, "Congrats, you win!");
				//Audio.music('w');
				submitButton.setActionCommand("quit");
			}
		});
	}

	public void lose()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				mainLabel.setText("You Lose!");
				mainLabel.setForeground(Color.red);
				submitButton.setText("Shucks");
				Audio.music('l');
				JOptionPane.showMessageDialog(null, "Sorry, next time!");
				//Audio.music('l');
				submitButton.setActionCommand("quit");
			}
		});
	}

	public void tooLow(final int guess)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				guessLabel.setText("0");
				mainLabel.setText(Integer.toString(guess) + " is too low!");
			}
		});
	}

	public void tooHigh(final int guess)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				guessLabel.setText("0");
				mainLabel.setText(Integer.toString(guess) + " is too high!");
			}
		});
	}

	public int nextGuess()
	{
		while (true) {
			try {
				semaphore.acquire();
				try {
					return this.guess;
				} finally {
					// Why do we *not* call semaphore.release()?
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		String guessString = guessLabel.getText();
		
		if (e.getActionCommand().equals("submit")) {
			this.guess = Integer.parseInt(guessString);
			semaphore.release();
//------------//			if (e.getActionCommand().equals("submit")) {
				this.guess = Integer.parseInt(guessString);
				semaphore.release();
//----------	//		if(guess >= 32)
				//guessString = guessString.substring(0, guessString.length() - 2);
		} else if (e.getActionCommand().equals("<")) {
			guessString = guessString.substring(0, guessString.length() - 1);
			//if(guessnumber is 
			//guessString = guessString.substring(0, guessString.length() - 2);
		} else if (e.getActionCommand().equals("quit")) {
			System.exit(0);
		} else {
			if (guessString.equals("0")) {
				guessString = "";
			}
			if (!guessString.equals("") || !e.getActionCommand().equals("0")) {
				guessString += e.getActionCommand();
			}
		}

		if (guessString.equals("")) {
			guessString = "0";
		}
		
		guessLabel.setText(guessString);
	}

	private void setupFrame()
	{
		frame = new JFrame("The Guessing Game!");
		frame.setMinimumSize(new Dimension(400,300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
	}
	
	private void setupLabels()
	{
		mainLabel.setText(
			"Guess a number between 1 and " +
				Integer.toString(this.config.getMaxNumber())
		);

		mainLabel.setFont(new Font("Arial", Font.BOLD, 18));
		mainLabel.setOpaque(true);
		mainLabel.setMinimumSize(new Dimension(30, 400));
		//mainLabel.paint(Color.red)

		guessLabel.setFont(new Font("Arial", Font.BOLD, 18));
		guessLabel.setOpaque(true);
	}

	
	public void setupNumberPad()
	{
		numberPad.setLayout(new GridLayout(0, 3));

		for (int i = 1; i < 10; i++) {
			JButton button = new JButton(Integer.toString(i));
			numberPad.add(button);
			button.setActionCommand(Integer.toString(i));
			button.addActionListener((ActionListener) this);
		}

		JButton zero = new JButton("0");
		zero.setActionCommand("0");
		zero.addActionListener((ActionListener) this);

		JButton backSpace = new JButton("<");
		backSpace.setActionCommand("<");
		backSpace.addActionListener((ActionListener) this);

		numberPad.add(zero);
		numberPad.add(backSpace);
	}

	public void setupSubmitPanel()
	{
		submitButton.setText("Submit Guess");
		submitButton.setActionCommand("submit");
		submitButton.addActionListener((ActionListener) this);
		submitPanel.setLayout(new GridLayout(2, 2));
		submitPanel.add(guessLabel);
		submitPanel.add(submitButton);
		//submitPanel.getColor(green);
	}

	public void display()
	{
		setupFrame();
		setupLabels();
		setupNumberPad();
		setupSubmitPanel();

		// Forces the main label to the left.
		JPanel labelLefter = new JPanel();
		labelLefter.setLayout(new BoxLayout(labelLefter, BoxLayout.X_AXIS));
		labelLefter.add(mainLabel);

		frame.add(labelLefter);
		frame.add(numberPad);
		frame.add(submitPanel);
		frame.setVisible(true);
		//color.
		
//}
//	public void display() {
		// TODO Auto-generated method stub
		
	}//}

  }
