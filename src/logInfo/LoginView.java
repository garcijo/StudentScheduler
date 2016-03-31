package logInfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;

import main.Main;
import view.TranslucentGUI;
import data.MySQLConnect;

public class LoginView extends JPanel {
	public static String username;
	public static String loggedUser;
	public static String password = new String();
	public static int upt;
	public static JPanel panel;
	public static JButton loginButton;
	public static JButton registerButton;
	public static  JButton registerBtn;
	public static ActionListener loginButtonListener;
	public static ActionListener registerButtonListener;
	static Registration register;
	public static JFrame window;
	static TranslucentGUI o;
	
	
	public LoginView(){
		panel = new JPanel();
		panel.setBackground(new Color(150,155,157));
		username = "";
		Dimension size = getPreferredSize();
		size.width = 350;
		size.height = 200;
		setPreferredSize(size);
		setBackground(new Color(150,155,157));
		upt = 0;
		//panel = new JPanel(new BorderLayout());
		
		placeComponents(panel);
		//ActionListener test = loginButtonListener;
		panel.setVisible(true);
	}
	
	
	
	public static String getUser(){
		return username;
	}
	
	
	
	//Testing frame
	public static void main(String[] args) {
		JFrame frame = new JFrame("StudentScheduler - Login");
		frame.setSize(350, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		LoginView log = new LoginView();
		placeComponents(log);
		frame.add(log);
		frame.setVisible(true);
		System.out.println(log.getUser());
		
	}

	
	
	//adds all the elements to the login JPanel
	public static String placeComponents(JPanel panel) {
		panel.setLayout(null);

		JLabel userLabel = new JLabel("User");
		userLabel.setBounds(30, 20, 80, 25);
		panel.add(userLabel);

		final JTextField userText = new JTextField(20);
		userText.setBounds(120, 20, 160, 25);
		panel.add(userText);

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(30, 70, 80, 25);
		panel.add(passwordLabel);

		final JPasswordField passwordText = new JPasswordField(20);
		passwordText.setBounds(120, 70, 160, 25);
		panel.add(passwordText);

		loginButton = new JButton("login");
		loginButton.setBounds(30, 130, 80, 25);
		panel.add(loginButton);

		registerButton = new JButton("register");
		registerButton.setBounds(200, 130, 80, 25);
		panel.add(registerButton);

		loginButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton source = (JButton) e.getSource();
				username = userText.getText();
				password = passwordText.getText();
				MySQLConnect dal = new MySQLConnect();
				try {
						boolean check = dal.checkUserLogin(username,password);
						if (!check){
							
							username = null;
							password = null;
							loggedUser = "";
							UIManager UI=new UIManager();
							 UI.put("OptionPane.background", new Color(150,155,157));
							 UI.put("Panel.background", new Color(150,155,157));
							 
							JOptionPane.showMessageDialog(source, "Incorrect username or password");
							UI.put("Panel.background", new Color(255,255,255));
						}
						else{
							ImageIcon icon = new ImageIcon(getClass().getResource("/icon/stud.png"));
							Image img = icon.getImage();
							Image newimg = img.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
							ImageIcon newIcon = new ImageIcon(newimg);
							LoginView.setUser(username);
							loggedUser = username;
							UIManager UI=new UIManager();
							 UI.put("OptionPane.background", new Color(150,155,157));
							 UI.put("Panel.background", new Color(150,155,157));
							JOptionPane.showMessageDialog(source, "Welcome "+username, "Logged In", 
									JOptionPane.INFORMATION_MESSAGE, newIcon);
							 UI.put("Panel.background", new Color(255,255,255));
							
							
							//System.out.println(loggedUser);
							upt = 1;
						}
					}
				catch (SQLException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				
				
			}
		};
		
		loginButton.addActionListener(loginButtonListener);
		
		registerButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				register = new Registration();
				register.setBackground(new Color(150,155,157));
				window = register;
				window.setBackground(new Color(150,155,157));
				window.setVisible(true);
				registerBtn = register.btn1;
				ActionListener[] lis = registerBtn.getActionListeners();
				registerBtn.removeActionListener(lis[0]);
				registerBtn.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						LoginView.setUser(register.getUser());
						loggedUser = username;
						if(username != null){
							window.dispose();
							Main.user = username;
							System.out.println(Main.user);
							System.out.println(username);
							o = new TranslucentGUI();}
							
					}
				});
				registerBtn.addActionListener(lis[0]);
				
				
			}
		};
		
		registerButton.addActionListener(registerButtonListener);
		
		return loggedUser;
	}
	
	public static void  setUser(String name){
		username = name;
	}
	
	


}