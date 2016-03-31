package calendar;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Alarm extends JPanel {

	JLabel title = new JLabel("Set Alarm");
	JLabel yearLabel = new JLabel("Enter year: ");
	JLabel monthLabel = new JLabel("Enter month: ");
	JLabel dayLabel = new JLabel("Enter day: ");
	JLabel hourLabel = new JLabel("Enter hour: ");
	JLabel minuteLabel = new JLabel("Enter minute: ");
	JLabel timerLabel = new JLabel("Alarm Timer");
	Timer timer;
	
	LocalDateTime cldt = currentLocal();

	static JTextField yearTF = new JTextField("yyyy",4);
	static JTextField monthTF = new JTextField("mm",2);
	static JTextField dayTF = new JTextField("dd",2);
	static JTextField hourTF = new JTextField("hh",2);
	static JTextField minuteTF = new JTextField("mm",2);

	private JButton button = new JButton("Create Alarm");
	private JPanel jp;

	static GregorianCalendar cal = new GregorianCalendar();
	static GregorianCalendar alarmCal = new GregorianCalendar();

	public static LocalDateTime currentLocal(){
		int year = cal.get(GregorianCalendar.YEAR);
		int month = cal.get(GregorianCalendar.MONTH);
		int dayOfMonth = cal.get(GregorianCalendar.DAY_OF_MONTH);
		int hour = cal.get(GregorianCalendar.HOUR_OF_DAY);
		int minute = cal.get(GregorianCalendar.MINUTE);

		return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
	}


	public static LocalDateTime currentAlarm(){

		int year = alarmCal.get(Integer.parseInt(yearTF.getText()));
		int month = alarmCal.get(Integer.parseInt(monthTF.getText()));
		int dayOfMonth = alarmCal.get(Integer.parseInt(dayTF.getText()));
		int hour = alarmCal.get(Integer.parseInt(hourTF.getText()));
		int minute = alarmCal.get(Integer.parseInt(minuteTF.getText()));

		return LocalDateTime.of(year, month, dayOfMonth, hour, minute);
	}

	public Alarm(){
		jp = new JPanel(new GridBagLayout());
		jp.setVisible(true);
		jp.setSize(400,500);;
		jp.setBackground(Color.LIGHT_GRAY);

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets (10, 10, 10, 10); 
		c.gridx = 0;
		c.gridy = 0;
		jp.add(title, c);

		c.gridx = 0;
		c.gridy = 1;
		jp.add(yearLabel);
		jp.add(monthLabel);
		jp.add(dayLabel);
		jp.add(hourLabel);
		jp.add(minuteLabel);

		c.gridx = 1;
		jp.add(yearTF);
		jp.add(monthTF);
		jp.add(dayTF);
		jp.add(hourTF);
		jp.add(minuteTF);

		c.gridx=0;
		c.gridy=2;
		jp.add(button);

		event e = new event();
		button.addActionListener(e);
	}

	public JPanel getPanel() {
		return jp;
	}
	
	public class event implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int year = alarmCal.get(Integer.parseInt(yearTF.getText()));
			int month = alarmCal.get(Integer.parseInt(monthTF.getText()));
			int dayOfMonth = alarmCal.get(Integer.parseInt(dayTF.getText()));
			int hour = alarmCal.get(Integer.parseInt(hourTF.getText()));
			int minute = alarmCal.get(Integer.parseInt(minuteTF.getText()));

			LocalDateTime ldtAlarm = LocalDateTime.of(year,month,dayOfMonth,hour,minute);
			long millisAlarm = ldtAlarm.toInstant(null).toEpochMilli();
			long millisCldt = cldt.toInstant(null).toEpochMilli();

			long count = millisCldt-millisAlarm;
			timerLabel.setText("Time Left: " + count);

			TimeClass tc = new TimeClass(count);
			timer = new Timer(1000, tc);//every 1000 ms or every 1 s
			timer.start();
		}
	}

	public	class TimeClass implements ActionListener {
		long counter;

		public TimeClass(long counter){
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
}




