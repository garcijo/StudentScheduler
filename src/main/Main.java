package main;


import static java.awt.GraphicsDevice.WindowTranslucency.TRANSLUCENT;

import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.mysql.jdbc.MySQLConnection;

import data.MySQLConnect;
import logInfo.LoginView;
import view.TranslucentGUI;

public class Main {
	static LoginView loginfo;
	static JFrame logWindow = new JFrame("Student Scheduler");
	public static String user;
	static JButton logRecieve;
	static JButton registerRecieve;
	static TranslucentGUI o;


	public static void main( String[] args){
		//Grab the current graphics environment for compatibility testing
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		final boolean translucencySupported = gd.isWindowTranslucencySupported(TRANSLUCENT);

		//Check to see if translucency is supported
		if (!translucencySupported){
			System.out.println("Translucency not supported, creating opaque window instead.");
		}

		//Begin creating overlay
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				loginfo = new LoginView();
				LoginView.placeComponents(loginfo);
				logWindow.getContentPane().add(loginfo);
				logWindow.setBackground(new Color(150,155,157));
				logWindow.setSize(350, 200);
				//logWindow.setAlwaysOnTop(true);
				logWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				loginfo.setVisible(true);
				logWindow.setVisible(true);
				logWindow.setLocationRelativeTo(null);
				user =  null;
				logRecieve = LoginView.loginButton;
				ActionListener[] list = logRecieve.getActionListeners();
				logRecieve.removeActionListener(list[0]);
				logRecieve.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						Main.user =  loginfo.getUser();
						if(Main.user != null){
						logWindow.dispose();
						o = new TranslucentGUI();}
							
					}
				});
				logRecieve.addActionListener(list[0]);
				
				registerRecieve = LoginView.registerButton;
				ActionListener[] lis = registerRecieve.getActionListeners();
				registerRecieve.removeActionListener(lis[0]);
				registerRecieve.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						System.out.println(Main.user);
						logWindow.dispose();
						
							
					}
				});
				registerRecieve.addActionListener(lis[0]);



					
					

				}
			});
		}
	}
