package CourseForm;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.MySQLConnect;

public class CreateCourse extends JPanel {

	//text shown
	private JLabel title = new JLabel ("Add a New Course");
	private JLabel aName = new JLabel("Name of Course:");
	private JLabel aTime = new JLabel ("Time:");
	private JLabel aDays = new JLabel ("Days:");
	
	//drop down menus for time
	private Integer [] hrs = new Integer []{12, 1, 2, 3, 4,
			5, 6, 7, 8, 9, 10, 11};
	private JComboBox <Integer> hours = new JComboBox <Integer> (hrs);
	private String [] mnts = new String []{"00", "15", "30", "45"};
	private JComboBox <String> minutes = new JComboBox <String> (mnts);
	private String [] ap = new String [] {"am", "pm"};
	private JComboBox <String> ampm = new JComboBox <String> (ap);
	
	//checkboxes for days
	private JCheckBox monday = new JCheckBox ("Monday");
	private JCheckBox tuesday = new JCheckBox ("Tuesday");
	private JCheckBox wednesday = new JCheckBox ("Wednesday");
	private JCheckBox thursday = new JCheckBox ("Thursday");
	private JCheckBox friday = new JCheckBox ("Friday");
	
	//misc.
	private JTextField tName = new JTextField(10);
	private JButton button = new JButton("Add New Course");
	static JPanel jp;
	
	public CreateCourse() {
		
		//appearance
		jp = new JPanel(new GridBagLayout());
		jp.setVisible(true);
		jp.setSize(400,500);;
	//	jp.setBackground(Color.LIGHT_GRAY);

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets (10, 10, 10, 10); 
		c.gridx = 1;
		c.gridy = 0;
		jp.add(title, c);
		
		//input name of task
		c.gridx = 0;
		c.gridy = 1;
		jp.add(aName, c);
		c.gridx = 1;
		jp.add(tName, c);

		//input time
		c.gridx = 0;
		c.gridy = 2;
		jp.add(aTime, c);
		
		c.gridy = 3;
		c.gridx = 0;
		jp.add(hours, c);
		c.gridx = 1;
		jp.add(minutes, c);
		c.gridx = 2;
		jp.add(ampm, c);
			
		//days of week checkboxes
		c.gridx = 0;
		c.gridy = 4;
		jp.add(aDays, c);
		c.gridy = 5;
		jp.add(monday,c);
		c.gridy = 5;
		c.gridx = 1;
		jp.add(tuesday,c);
		c.gridy = 6;
		c.gridx = 0;
		jp.add(wednesday,c);
		c.gridy = 6;
		c.gridx = 1;
		jp.add(thursday, c);
		c.gridy = 7;
		c.gridx = 0;
		jp.add(friday,c);
		
		//send button
		c.gridx = 1;
		c.gridy = 8;
		c.gridwidth =2;
		jp.add(button, c);
		
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getInputs();	//saves all inputs in a new form
				tName.setText("");		//resets all the text fields back to default
				monday.setSelected(false);
				tuesday.setSelected(false);
				wednesday.setSelected(false);
				thursday.setSelected(false);
				friday.setSelected(false);
				hours.setSelectedItem(hrs[0]);
				minutes.setSelectedItem(mnts[0]);
				ampm.setSelectedItem(ap[0]);
			}
		});
	}
	
	//returns jpanel
	public JPanel getPanel() {
		return jp;
	}
	
	//puts inputs into new course
	public void getInputs() {
		Course course = new Course(tName.getText(), (Integer) hours.getSelectedItem(), (String) minutes.getSelectedItem(), (String) ampm.getSelectedItem());
		course.setDays(monday.isSelected(), tuesday.isSelected(), wednesday.isSelected(), thursday.isSelected(), friday.isSelected());
		course.timeConverter();
		
		System.out.println(course.getName());
		System.out.println(course.getDays());
		System.out.println(course.getTime());
		
		MySQLConnect db = new MySQLConnect();
		try {
			db.addCourse(course.getName(), "admin", course.getDays(), course.getTime());
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		}

	
	
	public static void main (String args[]) {
		CreateCourse cc = new CreateCourse();
		JFrame jf = new JFrame();
		jf.getContentPane().add(jp);
		jf.setVisible(true);
		jf.setSize(400,500); 
	} 
}