// Author: 
package edu.cuny.brooklyn.cisc3120.homework4.gui;
import edu.cuny.brooklyn.cisc3120.homework4.core.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.Semaphore;

public class MyGUI extends JFrame implements IClient, IGuesser, ActionListener {
	int guess = 0;
	
	final Configuration config;
	final Semaphore semaphore = new Semaphore(1);
	
	private JTextField resultfield;
	private JButton [] numberPad =new JButton[10];
	private JButton submit = new JButton("Submit");
	private JButton backspace = new JButton("<-");
	
	final JLabel guessLabel = new JLabel("0");
	final JLabel mainLabel = new JLabel();
	
	//private String stemp1,stemp2,answer; // for type casting strings to doubles
	private JPanel contentPanel;
	
	// constructor
	public MyGUI(Configuration config) throws InterruptedException{
		super("Guessing Game"); // Title. Must be first statement of construction
		this.config=config;
		this.semaphore.acquire();
		
		//resultfield
		resultfield =new JTextField (null,20);
		resultfield.setEditable(false);
		
		Dimension dimension = new Dimension(75,25);
		
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.blue);
		contentPanel.setLayout(new FlowLayout()); //everything adds to the top
		contentPanel.add(resultfield, BorderLayout.NORTH);
		setupNumberPad(dimension);
		
		this.setContentPane(contentPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String guessString = guessLabel.getText();

		if (e.getActionCommand().equals("submit")) {
			guess = Integer.parseInt(guessString);
			semaphore.release();
		} else if (e.getActionCommand().equals("<")) {
			guessString = guessString.substring(0, guessString.length() - 1);
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
	@Override
	public int nextGuess() {
		// TODO Auto-generated method stub
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

	@Override
	public void win() {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				mainLabel.setText("You Win!");
				submit.setText("Cool");
				submit.setActionCommand("quit");
			}
		});
		
	}

	@Override
	public void lose() {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				mainLabel.setText("You Lose!");
				submit.setText("Shucks");
				submit.setActionCommand("quit");
			}
		});
	}

	@Override
	public void tooLow(final int guess) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				guessLabel.setText("0");
				mainLabel.setText(Integer.toString(guess) + " is too low!");
			}
		});
	}

	@Override
	public void tooHigh(final int guess) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				guessLabel.setText("0");
				mainLabel.setText(Integer.toString(guess) + " is too high!");
			}
		});
	}
	
	public void setupNumberPad(Dimension d)
	{
		for (int i = 0; i < numberPad.length; i++) {
			numberPad[i]=new JButton(Integer.toString(i));
			numberPad[i].setActionCommand(Integer.toString(i));
			numberPad[i].addActionListener(this);
			if (i!=0){
				contentPanel.add(numberPad[i]);
				numberPad[i].setPreferredSize(d);
			}
		}
		
		numberPad[0].setPreferredSize(new Dimension(225,25));
		contentPanel.add(numberPad[0]);
	
		backspace.setActionCommand("<");
		backspace.addActionListener(this);
		backspace.setPreferredSize(new Dimension(113,25));
		
		submit.setActionCommand("submit");
		submit.addActionListener(this);
		submit.setPreferredSize(new Dimension(113,25));
		
		contentPanel.add(submit);
		contentPanel.add(backspace);
	}
}
