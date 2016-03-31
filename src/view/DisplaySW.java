package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class DisplaySW extends JPanel {

	public StopWatchGUI SW = new StopWatchGUI();

	public DisplaySW() {
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		final JButton dispTimer = new JButton("Display Stopwatch");
		setBackground(new Color(0, 0, 0, 0));

		dispTimer.setSize((int) (0.1 * dim.getHeight()),
				(int) (0.1 * dim.getWidth()));

		// Makes shows/hides stopwatch
		dispTimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (SW.isVisible()) {
					SW.setVisible(false);
					dispTimer.setText("Show Stopwatch");
					System.out.println("Disabled");

				} else {
					SW.setVisible(true);
					dispTimer.setText("Hide Stopwatch");
					System.out.println("Enabled");
				}
			}
		});
		// Shows the options for stopwatch.
		
		add(dispTimer);
		add(SW);
		
	}

}
