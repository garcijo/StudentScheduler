package logInfo;

import javax.swing.*;

import data.MySQLConnect;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Registration extends JFrame implements ActionListener 
{ 
	JLabel l1, l2, l3, l4, l5, l6, l7, l8;
	JTextField tf1, tf2, tf5, tf6, tf7;
	public static JButton btn1, btn2;
	JPasswordField p1, p2;
	public static String user;
	EmailValidator valid;

	public Registration()
	{
		//setAlwaysOnTop(true);
		getContentPane().setBackground(new Color(150,155,157));
		setVisible(true);
		setSize(550, 350);
		setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("StudentScheduler - Registration");
		setLocationRelativeTo(null);
		l1 = new JLabel("Register an account");
		l1.setForeground(Color.blue);
		l1.setFont(new Font("Serif", Font.BOLD, 20));

		user = null;
		valid = new EmailValidator();

		l2 = new JLabel("Name:");
		l3 = new JLabel("Username");
		l4 = new JLabel("Create Password:");
		l5 = new JLabel("Confirm Password:");
		l6 = new JLabel("E-mail address:");
		tf1 = new JTextField();
		tf2 = new JTextField();
		p1 = new JPasswordField();
		p2 = new JPasswordField();
		tf5 = new JTextField();

		btn1 = new JButton("Register");
		btn2 = new JButton("Clear");

		btn1.addActionListener(this);
		btn2.addActionListener(this);

		l1.setBounds(80, 20, 400, 30);
		l2.setBounds(80, 60, 200, 30);
		l3.setBounds(80, 100, 200, 30);
		l4.setBounds(80, 140, 200, 30);
		l5.setBounds(80, 180, 200, 30);
		l6.setBounds(80, 220, 200, 30);

		tf1.setBounds(300, 60, 200, 30);
		tf2.setBounds(300, 100, 200, 30);
		p1.setBounds(300, 140, 200, 30);
		p2.setBounds(300, 180, 200, 30);
		tf5.setBounds(300, 220, 200, 30);
		btn1.setBounds(170, 280, 100, 30);
		btn2.setBounds(310, 280, 100, 30);

		add(l1);
		add(l2);
		add(tf1);
		add(l3);
		add(tf2);
		add(l4);
		add(p1);
		add(l5);
		add(p2);
		add(l6);
		add(tf5);
		add(btn1);
		add(btn2);
	}

	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == btn1)
		{
			int x = 0;
			String s1 = tf1.getText();
			String s2 = tf2.getText();

			char[] s3 = p1.getPassword();
			char[] s4 = p2.getPassword(); 
			String s8 = new String(s3);
			String s9 = new String(s4);

			String s5 = tf5.getText();
			if (s8.equals(s9))
			{
				if (valid.validate(s5)){
					try
					{

						MySQLConnect dal = new MySQLConnect();
						if(dal.findUser(s2)){
							UIManager UI=new UIManager();
							 UI.put("OptionPane.background", new Color(150,155,157));
							 UI.put("Panel.background", new Color(150,155,157));
							JOptionPane.showMessageDialog(btn1, "The username '" +s2+ "' already exists!");
							UI.put("Panel.background", new Color(255,255,255));
						}
						else{
							dal.addUser(s2,s8,s1,s5);
							Registration.setUser(s2);
							UIManager UI=new UIManager();
							 UI.put("OptionPane.background", new Color(150,155,157));
							 UI.put("Panel.background", new Color(150,155,157));
							JOptionPane.showMessageDialog(btn1, "Your account has been created!");
							UI.put("Panel.background", new Color(255,255,255));
						}

					}
					catch (Exception ex) 
					{
						System.out.println(ex);
					}}
				else{ 
					UIManager UI=new UIManager();
					 UI.put("OptionPane.background", new Color(150,155,157));
					 UI.put("Panel.background", new Color(150,155,157));
					JOptionPane.showMessageDialog(btn1, "Incorrect e-mail format!");
					tf5.setText("");
					UI.put("Panel.background", new Color(255,255,255));
				}

			}
			else
			{
				JOptionPane.showMessageDialog(btn1, "Password Does Not Match");
				p1.setText("");
				p2.setText("");
			} 

		} 
		else
		{
			tf1.setText("");
			tf2.setText("");
			p1.setText("");
			p2.setText("");
			tf5.setText("");
		}
	} 

	public static void  setUser(String name){
		user = name;
	}

	public static String  getUser(){
		return user;
	}

	public static void main(String args[])
	{
		new Registration();
	}
}
