package view;

/* GUI portion of the stopwatch
 * 
 * Contains buttons to start, stop and restart the stopwatch.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StopWatchGUI extends JPanel {
	StopWatch st = new StopWatch();

	private boolean isRunning = true;

	// Time to be displayed on JPanel.
	long time = 0;

	private Timer t;
	private int delay = 1;

	// Will display the time here.
	JLabel timeDisplayed = new JLabel();
	JPanel tCounterFrame = new JPanel();

	// Constructor
	public StopWatchGUI() {
		setUpFrame();
		t = new Timer(delay, new TimerListener());
		t.start();
	}

	// Sets up the timer button frame
	public void setUpFrame() {
		setLayout(new GridLayout());

		// Grabs current screen dimension
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		setBackground(new Color(0, 0, 0, 0));
		setVisible(true);

		// Displays timer button
		add(tCounterFrame);

		timeDisplayed.setFont(new Font("Tahoma", Font.PLAIN, 20));
		timeDisplayed.setHorizontalAlignment(JLabel.RIGHT);
		tCounterFrame.setSize((int) (0.1 * dim.getHeight()),
				(int) (0.1 * dim.getWidth()));
		tCounterFrame.setBackground(Color.white);
		tCounterFrame.setLayout(new GridLayout());
		tCounterFrame.add(timeDisplayed, new GridBagConstraints());

		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!st.getStatus()) {
					st.start();
					isRunning = true;
					System.out.println("Start");
				} else {
					System.out.println("Stopwatch already started");
				}
//				System.out.println(st.toString());
			}
		});

		JButton stop = new JButton("Stop");
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (st.getStatus()) {
					st.pause();
					time += st.getElapsedTime();
					isRunning = false;
					System.out.println("Stopped");

				} else {
					System.out.println("Stopwatch is not running");
				}
//				System.out.println(st.toString());
			}
		});

		// Resets the count
		JButton reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				time = 0;
				st.reset();
				timeDisplayed.setText(Long.toString(time));
				System.out.println("Reset");
//				System.out.println(st.toString());
			}
		});

		tCounterFrame.add(start);
		tCounterFrame.add(stop);
		tCounterFrame.add(reset);
		tCounterFrame.add(timeDisplayed);
	}

	// Displays the duration while it stopwatch is running.
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			time = st.getElapsedTime();
			double newTime = time/1000;
			

			if (isRunning) {
				timeDisplayed.setText(Double.toString(newTime));
			}
		}
	}
}