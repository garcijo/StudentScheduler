package Countdown;

import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer; // had a big ass error since i imported java.util.Timer instead.... LOL

import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Countdown extends JFrame {

	JLabel promptLabel, timerLabel;
	int counter;
	JTextField tf;
	JButton button;
	Timer timer;
	
	public Countdown(){
		setLayout(new GridLayout(2,2,5,5));
		
		promptLabel = new JLabel("Enter seconds:", SwingConstants.CENTER);
		add(promptLabel);
		
		tf = new JTextField(5);
		add(tf);
		
		button = new JButton("Start timing");
		add(button);
		
		timerLabel = new JLabel("Watiting...", SwingConstants.CENTER);
		add(timerLabel);
		
		event e = new event();
		button.addActionListener(e);
	}
	
	public class event implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int count = (int) (Double.parseDouble(tf.getText())); //timer can only count in seconds, so gotta cast in case user types sometihng like 9.5 seconds... Rounds to integer instead of being a double
			timerLabel.setText("Time Left: " + count);
			
			TimeClass tc = new TimeClass(count);
			timer = new Timer(1000, tc);//every 1000 ms or every 1 s
			timer.start();
		}
	}
	
	public class TimeClass implements ActionListener {
		int counter;
		
		public TimeClass(int counter){
			this.counter = counter;
		}
		
		public void actionPerformed(ActionEvent tc){
			counter--;
			
			if(counter>=1){
				timerLabel.setText("Time left: " + counter);
			}else{
				timer.stop();
				timerLabel.setText("Done!");
				Toolkit.getDefaultToolkit().beep(); //BEEPING SOUND
			}
		}
	}
	
	public static void main(String args[]){
		Countdown gui = new Countdown();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setSize(250,100);
		gui.setTitle("STUD Timer");
		gui.setVisible(true);

	}
}
