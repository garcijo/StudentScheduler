package view;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import controller.HotKeys;
import Countdown.CountdownPanel;
import CourseForm.CourseTable;
import CourseForm.CreateCourse;
import Form.DetailsPanel;
import tasksTable.SimpleTable;
import tasksTable.TableModel;
import view.ContentPane;
import calendar.Alarm;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import logInfo.LoginView;
import main.Main;
import static java.awt.GraphicsDevice.WindowTranslucency.*;

/**
 * Creates the main overlay
 * @author The Studs
 *
 */
public class TranslucentGUI extends JFrame {
	LoginView loginfo;
	public static String user = Main.user;
	int checked = 0;

	GridBagLayout gridBag = new GridBagLayout();
	GridBagConstraints c = new GridBagConstraints();

	JPanel taskTable;
	JPanel smallTable;
	TableModel g;
	SimpleTable small;
	JFrame logWindow = new JFrame();
	JButton logRecieve;

	JPanel pnl0;
	CurrentTime ct;

	TransparencyCheck trans = new TransparencyCheck();

	JButton expandTasks;
	ActionListener expListen;
	
	JPanel cTable;
	CourseTable courses;
	JFrame cWindow = new JFrame();

	public TranslucentGUI() {
		super("Overlay");

		ct = new CurrentTime(this);
		final HotKeys h = new HotKeys(this, ct);

		//Hide overlay from task bar 
		setType(Type.UTILITY);

		//Adding icon to system tray
		if (SystemTray.isSupported()) {
			BufferedImage img = null;
			try {
				img = ImageIO.read(new URL("http://i.imgur.com/Aytj7Lf.png"));
			} catch (MalformedURLException e2) {
				e2.printStackTrace();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			SystemTray tray = SystemTray.getSystemTray();
			Toolkit toolkit = Toolkit.getDefaultToolkit();






			//popup menu on right click
			PopupMenu pMenu = new PopupMenu();
			MenuItem exitItem = new MenuItem("Exit");
			exitItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					h.cleanUp();
					dispose();
					System.exit(0);
				}
			});
			pMenu.add(exitItem);

			TrayIcon icon = new TrayIcon(img, "STUD", pMenu);
			icon.setImageAutoSize(true);
			try {
				tray.add(icon);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}

		//Border colors, enable for testing purposes
		Border redline = BorderFactory.createLineBorder(Color.red);
		Border greenline = BorderFactory.createLineBorder(Color.green);

		//Grab current screen dimensions
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();




		//JPanels
		pnl0 = new JPanel();	//Time
		JPanel pnl1 = new JPanel();			//Close/Transparency
		final JPanel pnl2 = new JPanel();	//Add Tasks
		final JPanel pnl3 = new JPanel();	//Log In
		final JPanel pnl4 = new JPanel();	//Tasks Table
		final JPanel pnl5 = new JPanel();	//Stopwatch
		final JPanel pnl6 = new JPanel();	//Countdown
		final JPanel pnl7 = new JPanel();	//Course Add
		//Set alpha levels so they are transparent
		pnl0.setBackground(new Color(0, 0, 0, 0));
		pnl1.setBackground(new Color(0, 0, 0, 0));
		pnl2.setBackground(new Color(0, 0, 0, 0));
		pnl3.setBackground(new Color(0, 0, 0, 0));
		pnl4.setBackground(new Color(0, 0, 0, 0));
		pnl5.setBackground(new Color(0, 0, 0, 0));
		pnl6.setBackground(new Color(0, 0, 0, 0));
		pnl7.setBackground(new Color(0, 0, 0, 0));

		//For Testing Purposes;
		//These enable borders to be seen
		//pnl0.setBorder(redline);
		//pnl1.setBorder(redline);
		//pnl2.setBorder(greenline);
		//pnl3.setBorder(redline);
		//pnl4.setBorder(redline);
		//pnl5.setBorder(greenline);
		//pnl6.setBorder(redline);
		//pnl7.setBorder(greenline);

		//Set window to top left
		setLocation(0, 0);

		//Set current Screen size
		setSize(dim);
		setUndecorated(true);

		//Enable alpha levels
		setBackground(new Color(0, 0, 0, 0));
		setContentPane(new ContentPane());
		getContentPane().setBackground(Color.BLACK);
		//setLayout(new GridBagLayout());

		//Changes panels background
		final UIManager UI=new UIManager();

		//Add buttons panel, want this to be opaque
		JPanel buttons = new JPanel();
		buttons.setOpaque(false);

		//Create close button (to close overlay until hotkeys are added)
		JButton b = new JButton("Close");


		//Add listener to close overlay when button pressed
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				h.cleanUp();
				dispose();
				System.exit(0);
			}
		});
		buttons.add(b);
		//buttons.setLocation(15,30);

		//Add transparency slider
		final JButton b1 = new JButton("Set Trans");



		final JTextField textField = new JTextField(20);
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String text = textField.getText();
				if(trans.validate(text)){
					float f = Float.parseFloat(text);
					((ContentPane) getContentPane()).setAlpha(f/100);
					getContentPane().repaint();
				}
				else{
					UIManager UI=new UIManager();
					UI.put("OptionPane.background", new Color(150,155,157));
					UI.put("Panel.background", new Color(150,155,157));
					JOptionPane.showMessageDialog(b1, "Please enter a number between 0-100!");
					textField.setText("");
					UI.put("Panel.background", new Color(255,255,255));
				}
			}
		});

		final JButton b2 = new JButton("Change Color");
		ActionListener colorListener = new ActionListener(){
			public void actionPerformed(ActionEvent actionEvent) {
				Color initialBackground = getContentPane().getBackground();
				Color background = JColorChooser.showDialog(null, "Change Overlay Background", initialBackground);
				if (background != null) {
					getContentPane().setBackground(background);

				}
			}
		};
		b2.addActionListener(colorListener);




		//Add Region 1
		this.setLayout(new GridBagLayout());

		addComp(this, pnl0, 0, 0, 3, 1, GridBagConstraints.HORIZONTAL, 0, 0);//Time
		//addComp(this, pnl3, 0, 1, 1, 1, GridBagConstraints.HORIZONTAL, 0, 0);//Log In
		addComp(this, pnl2, 0, 3, 1, 5, GridBagConstraints.HORIZONTAL, 0, 0);//Add Form
		addComp(this, pnl4, 0, 1, 1, 1, GridBagConstraints.HORIZONTAL, 0, 0);//Tasks Table
		addComp(this, pnl1, 1, 3, 2, 1, GridBagConstraints.HORIZONTAL, 0, 0);//Close
		addComp(this, pnl5, 1, 4, 2, 1, GridBagConstraints.HORIZONTAL, 0, 0);//Stopwatch
		addComp(this, pnl6, 1, 5, 2, 1, GridBagConstraints.HORIZONTAL, 0, 0);//Countdown
		addComp(this, pnl7, 2, 1, 1, 1, GridBagConstraints.HORIZONTAL, 0, 0);//Course Table




		pnl0.add(ct);





		pnl1.add(buttons);
		pnl1.add(textField);
		pnl1.add(b1);
		pnl1.add(b2);
		//pnl1.setLocation(15,50);







		CountdownPanel cdpanel = new CountdownPanel();
		pnl6.add(cdpanel.getPanel());





		//Add tasks table
		g = new TableModel();
		small = new SimpleTable();

		taskTable = g.getTable();
		smallTable = small.getTable();
		expandTasks = new JButton("More Details");

		pnl4.setLayout(new BorderLayout());
		pnl4.add(smallTable,BorderLayout.CENTER);
		pnl4.add(expandTasks,BorderLayout.SOUTH);
		
		expListen = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(expandTasks.getText().equals("More Details")){
					pnl4.remove(smallTable);
					pnl4.remove(expandTasks);
					//pnl4.revalidate();
					pnl4.add(refreshList(),BorderLayout.CENTER);
					expandTasks = new JButton("Less Details");
					pnl4.add(expandTasks,BorderLayout.SOUTH);
					pnl4.revalidate();
					pnl4.setVisible(true);
					expandTasks.addActionListener(expListen);
				}
				else if(expandTasks.getText().equals("Less Details")){
					pnl4.remove(taskTable);
					pnl4.remove(expandTasks);
					pnl4.add(refreshLess(),BorderLayout.CENTER);
					expandTasks = new JButton("More Details");
					pnl4.add(expandTasks,BorderLayout.SOUTH);
					pnl4.revalidate();
					pnl4.setVisible(true);
					expandTasks.addActionListener(expListen);
				}
			}
		};
		expandTasks.addActionListener(expListen);



		JButton btn = g.btn;
		ActionListener[] listen = btn.getActionListeners();
		btn.removeActionListener(listen[0]);
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				pnl4.remove(taskTable);
				//pnl4.revalidate();
				pnl4.add(refreshList());
				pnl4.revalidate();
				pnl4.setVisible(true);
			}
		});
		btn.addActionListener(listen[0]);

		//pnl1.add(ct);


		//Add tasks form
		final DetailsPanel a = new DetailsPanel();
		pnl2.add(a);
		JButton addBtn = a.getAddBtn();
		ActionListener[] lis = addBtn.getActionListeners();
		addBtn.removeActionListener(lis[0]);
		addBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(expandTasks.getText().equals("More Details")){
					pnl4.remove(smallTable);
					//pnl4.revalidate();
					pnl4.add(refreshLess(),BorderLayout.CENTER);
					pnl4.revalidate();
					pnl4.setVisible(true);

				}
				else if(expandTasks.getText().equals("Less Details")){
					pnl4.remove(taskTable);
					pnl4.add(refreshList(),BorderLayout.CENTER);
					pnl4.revalidate();
					pnl4.setVisible(true);
				}
			}
		});
		addBtn.addActionListener(lis[0]);

		final CreateCourse cc = new CreateCourse();
		
		courses = new CourseTable();
		cTable = courses.getCourseTable();
		pnl7.add(cTable);
		//pnl7.add(cc.getPanel());



		// Stopwatch. Dumped it here for now...
		DisplaySW dsw = new DisplaySW();
		// it doesn't like pnl3 for unknown reasons...
		pnl5.add(dsw);
		// Sets location of the stopwatch itself
		//		pnl4.add(dsw.SW);





		//Alarm alarmPanel = new Alarm();
		//pnl4.add(alarmPanel);




		//pnl4.add(tpnel);
		//Enable Overlay!

		setVisible(true);
	}

	/**
	 * This adds a component to the GUI, since we are using GridBagLayout we need to specify constraints before adding
	 * @param panel
	 * @param comp
	 * @param gridx
	 * @param gridy
	 * @param gridwidth
	 * @param gridheight
	 * @param fill
	 * @param weightx
	 * @param weighty
	 */
	private void addComp(TranslucentGUI panel, JComponent comp, int gridx, int gridy, int gridwidth, int gridheight, int fill, double weightx, double weighty){
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.fill = fill;
		c.weightx = weightx;
		c.weighty = weighty;

		panel.add(comp, c);
	}

	private void addComp(TranslucentGUI panel, JComponent comp, int gridx, int gridy, int gridwidth, int gridheight, int fill, int anchor, double weightx, double weighty){
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = gridwidth;
		c.gridheight = gridheight;
		c.fill = fill;
		c.anchor = anchor;
		c.weightx = weightx;
		c.weighty = weighty;

		panel.add(comp, c);
	}

	private JPanel refreshList(){
		g = new TableModel();
		taskTable = g.getTable();
		//tpnel.setBackground(new Color(0,0,0,0));
		System.out.println("123");
		System.out.println(user);
		return taskTable;
	}
	
	private JPanel refreshLess(){
		small = new SimpleTable();
		smallTable = small.getTable();
		//tpnel.setBackground(new Color(0,0,0,0));
		System.out.println("123");
		System.out.println(user);
		return smallTable;
	}

	private LoginView refreshLog(){
		loginfo = new LoginView();
		loginfo.setVisible(true);
		return loginfo;
	}

	private void refresh(){
		//getContentPane.repaint();
	}

	public void timeRefresh(){
		ct.repaint();
		pnl0.repaint();
		this.repaint();
	}
}
