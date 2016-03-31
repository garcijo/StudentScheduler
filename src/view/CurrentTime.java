package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CurrentTime extends JPanel {
	private Timer timer;
	private int delay = 500;
	private int counter;
	private Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	private JLabel prevTime;
	private JLabel time = new JLabel();
	private JPanel panel;


	// Dimensions of the time box.
	Dimension dimTimeBox = new Dimension((int) (0.2 * dim.getWidth()),
			(int) (0.05 * dim.getHeight()));

	// Constructor
	public CurrentTime(TranslucentGUI o) {
		panel = new JPanel();
		prevTime = new JLabel();
		//time = new JLabel();
		delay = 500;


		// Sets up JFrame that will display the time.
		/**setLocation(
				(int) ((0.5 * dim.getWidth()) - (0.5 * dimTimeBox.getWidth())),
				15);**/

		Dimension size = getPreferredSize();
		size.width = (int) (0.2 * dim.getWidth());
		size.height = (int) (0.05 * dim.getHeight());
		setPreferredSize(size);


		this.setBackground(new Color(0, 0, 0, 0));
		//panel.setBackground(new Color(0, 0, 0, 0));
		//setAlwaysOnTop(true);



		//setContentPane(new ContentPane());
		//getContentPane().setBackground(getBackground());

		// Sets up the time.
		prevTime.setHorizontalAlignment(JLabel.CENTER);
		prevTime.setText("Loading...");
		prevTime.setFont(new Font("Tahoma", Font.PLAIN, (int) (0.5 * dimTimeBox.getHeight())));
		prevTime.setForeground(Color.WHITE);

		// Sets up timer.
		timer = new Timer(delay, tListener(o));
		timer.start();

		//getContentPane().add(prevTime);
		prevTime.setBackground(new Color(0, 0, 0, 0));
		time.setBackground(new Color(0, 0, 0, 0));
		this.add(prevTime);

		panel.setVisible(true);


	}

	// Prints to console every t seconds designated from timer.
	private ActionListener tListener(final TranslucentGUI o) {

			ActionListener l = new ActionListener(){
				public void actionPerformed(ActionEvent e) {

					remove(prevTime);

					/*
					 * Done this way since timer doesn't like delay over around 1000.
					 * Basically prints every second.
					 */
					if (counter % 1000 == 0) {
						counter = 0;

						display();
						o.timeRefresh();
					}
					counter += delay;

				}
			};
			return l;
		}

		// Displays content.
		public void display() {
			String full;

			panel.remove(prevTime);
			full = getMDY() + " " + getHMS();

			time.setFont(prevTime.getFont());
			time.setForeground(prevTime.getForeground());
			time.setBackground(new Color(0, 0, 0, 0));
			prevTime = time;
			prevTime.setBackground(new Color(0, 0, 0, 0));
			time.setText(full);


			this.add(time);

			revalidate();
			this.repaint();
			panel.setBackground(new Color(0,0,0,0));
			panel.setVisible(true);

		}

		// Returns information on current time.
		public Date getDate() {
			return Calendar.getInstance().getTime();
		}

		// Returns hours, minutes, and seconds at the time of the call.
		public String getHMS() {
			SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");

			return hms.format(getDate().getTime());
		}

		// Returns month, date, and year at the time of the call.
		public String getMDY() {
			SimpleDateFormat mdy = new SimpleDateFormat("MMMM dd, yyyy");

			return mdy.format(getDate().getTime());
		}

	}