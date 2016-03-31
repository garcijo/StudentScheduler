package Countdown;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Countdown.Countdown.TimeClass;
import Countdown.Countdown.event;


public class CountdownPanel extends JPanel {
	
	private JPanel cdPanel = new JPanel(new GridBagLayout(), false); //(layout, double-buffering bool), check online if we should use double-buffering or not.
	
	private JLabel header = new JLabel("Countdown Timer");
	private JLabel queryHours = new JLabel("Enter hours: ");
	private JLabel queryMinutes = new JLabel("minutes: ");
	private JLabel timerLabel = new JLabel("Watiting...", SwingConstants.CENTER);
	
	public JTextField minsField = new JTextField("0",3);
	public JTextField hoursField = new JTextField("0",3);

	private Timer countdownTimer;
		
	private JButton startButton = new JButton("Start");
	private JButton stopButton = new JButton("Stop");
	private JButton resetButton = new JButton("Reset");
	
	public CountdownPanel(){
		
	cdPanel.setVisible(true);
	cdPanel.setSize(400,500);;
	setBackground(new Color(0, 0, 0, 0));
	
	GridBagConstraints constraints = new GridBagConstraints();
	constraints.anchor = GridBagConstraints.WEST;
	constraints.insets = new Insets(10,10,10,10);
	constraints.gridx = 0;
	constraints.gridy = 0;
	
	cdPanel.add(header, constraints);
	
	constraints.gridx = 0;
	constraints.gridy = 0;
	cdPanel.add(queryHours);
	
	constraints.gridy = 1;
	constraints.gridx = 1;
	cdPanel.add(hoursField);
	
	constraints.gridx = 2;
	cdPanel.add(queryMinutes);
	
	constraints.gridx = 3;
	cdPanel.add(minsField);
	
	constraints.gridy = 2;
	constraints.gridx = 0;
	cdPanel.add(startButton);
	event e = new event();
	startButton.addActionListener(e);

	
	constraints.gridx = 1;
	cdPanel.add(stopButton); //no such method, would have to "emulate" one.
	stopButton.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			countdownTimer.stop();
			timerLabel.setText("0");
		}
		
	});
	
	constraints.gridx = 2;
	cdPanel.add(resetButton);
	resetButton.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			countdownTimer.restart();
		}
		
	}); //reset button doesn't work although restart() is a method from the timer class itself, every other method works..
	
	constraints.gridy = 2;
	constraints.gridx = 0;
	cdPanel.add(timerLabel);
	}
	
	public JPanel getPanel() {
		return cdPanel;
	}
	
	/*class TimerListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {

			// TODO Auto-generated method stub
			convertedTotal --;
			if(convertedTotal>=1){
				timerLabel.setText("Time left: " + convertedTotal + " s");
			}else{
				countdownTimer.stop();
				timerLabel.setText("Done!");
				Toolkit.getDefaultToolkit().beep(); //BEEPING SOUND
			}
		}
		
	}*/
	
	public class event implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int convertMins = (int) Double.parseDouble(minsField.getText());
			int mins = convertMins;
			int convertHours = (int) Double.parseDouble(hoursField.getText());
			int hours = convertHours;
			
			int convertedMins = mins * 60;
			int convertedHours = hours * 60 * 60;
			int convertedTotal = convertedMins + convertedHours;			
			
			TimeClass tc = new TimeClass(convertedTotal);
			countdownTimer = new Timer(1000, tc);//every 1000 ms or every 1 s
			countdownTimer.start();
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
				timerLabel.setText("Time left: " + counter + " s");
			}else{
				countdownTimer.stop();
				timerLabel.setText("Done!");
				Toolkit.getDefaultToolkit().beep(); //BEEPING SOUND
			}
		}
	}
	
	
}