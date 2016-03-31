package Form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.EventListenerList;

import view.TranslucentGUI;
import view.TransparencyCheck;
import data.MySQLConnect;


public class DetailsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6915622549267792262L; //In case we want to serialize classes.

	private EventListenerList listenerList = new EventListenerList();
	private int num;
	private JButton addBtn = new JButton("Add");
	private String str;
	private String user = TranslucentGUI.user;
	DateCheck dateCheck = new DateCheck();
	
	public DetailsPanel(){
		Dimension size = getPreferredSize();
		size.width = 350;
		size.height = 300;
		setPreferredSize(size);

		setBorder(BorderFactory.createTitledBorder("Student Scheduler Details"));

		JLabel nameLabel = new JLabel("Task Name: ");
		JLabel courseLabel = new JLabel("Course: ");
		JLabel dateLabel = new JLabel("Due date: ");
		JLabel descLabel = new JLabel("Description: ");
		JLabel typeLabel = new JLabel("Type: "); //may want to change to radio or even dropdown box in the future,
		//as people might not necessarily know what "type" is at first glance.
		JLabel priorityLabel = new JLabel("Priority");

		JRadioButton lowPriority = new JRadioButton("Low", false);
		JRadioButton medPriority = new JRadioButton("Med", false);
		JRadioButton highPriority = new JRadioButton("High", false);

		ButtonGroup priorityGroup = new ButtonGroup();
		priorityGroup.add(lowPriority);
		priorityGroup.add(medPriority);
		priorityGroup.add(highPriority);

		final JTextField nameField = new JTextField(15);
		final JTextField courseField = new JTextField(15);
		final JTextField dateField = new JTextField("YYYY-MM-DD",15);
		final JTextField descField = new JTextField(15);
		final JTextField typeField = new JTextField(15);


		lowPriority.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setPriority(0);
				setPriorityStr("Low");
			}

		});
		medPriority.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				setPriority(1);
				setPriorityStr("Medium");
			}

		});

		highPriority.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				setPriority(2);
				setPriorityStr("High");
			}

		});

		addBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub



				String name = nameField.getText();
				String course = courseField.getText();
				String date = dateField.getText();
				String descript = descField.getText();
				String type = typeField.getText();
				String priorityStr = str;
				String priorityNum = Integer.toString(num);
				MySQLConnect dal = new MySQLConnect();
				if(dateCheck.validate(date)){
				try {
					int repeat = dal.checkIfExists(name,user,date,descript,course,type,priorityStr);
					
					if (repeat == 0)
						dal.addForm(name,user,date,descript,course,type,priorityStr);
					else if (repeat == 1)
						PopUp.infoBox("The task you tried to create already exists!", "You're stupid");
				} catch (SQLException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				String[] arrayForDatabase = {name,date,descript,course,type,priorityStr}; //This is por Jorge.

				String text = name +" "+ course +" "+ date +" "+ descript + " "+ type +" "+ priorityStr;

				//System.out.println(text);//appears on CONSOLE, not on panel yet

				fireDetailEvent(new DetailEvent(this, text));
				}
				else{
					UIManager UI=new UIManager();
					UI.put("OptionPane.background", new Color(150,155,157));
					UI.put("Panel.background", new Color(150,155,157));
					JOptionPane.showMessageDialog(addBtn, "Please enter a valid date!");
					dateField.setText("");
					UI.put("Panel.background", new Color(255,255,255));
				}
			}


		});

		setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();

		//First Column
		gc.anchor = GridBagConstraints.LINE_START;
		gc.weightx=0.5;
		gc.weighty=0.5;

		gc.gridx=0;
		gc.gridy=0;

		add(nameLabel, gc);

		gc.gridx=0;
		gc.gridy=1;

		add(courseLabel, gc);

		gc.gridx=0;
		gc.gridy=2;

		add(dateLabel, gc);


		gc.gridx=0;
		gc.gridy=3;

		add(descLabel, gc);

		gc.gridx=0;
		gc.gridy=4;

		add(typeLabel, gc);

		gc.gridx=0;
		gc.gridy=5;
		add(priorityLabel, gc);
		//Add priority Label and RADIAL BUTTONS for priority.

		//second column
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx=1;
		gc.gridy=0;
		add(nameField,gc);

		gc.gridx=1;
		gc.gridy=1;
		add(courseField,gc);

		gc.gridx=1;
		gc.gridy=2;
		add(dateField,gc);

		gc.gridx=1;
		gc.gridy=3;
		add(descField, gc);

		gc.gridx=1;
		gc.gridy=4;
		add(typeField, gc);

		gc.gridx=1;
		gc.gridy=5;
		add(lowPriority, gc);
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridx=1;
		gc.gridy=5;
		add(medPriority, gc);
		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridx=1;
		gc.gridy=5;
		add(highPriority, gc);

		//Bottom row
		gc.weighty=10;

		//gc.fill = GridBagConstraints.BOTH; This is to fill up space, useful for text fields.
		gc.anchor= GridBagConstraints.FIRST_LINE_START;
		gc.gridx = 1;
		gc.gridy = 6;
		add(addBtn, gc);
	}

	public void fireDetailEvent(DetailEvent event){
		Object[] listeners = listenerList.getListenerList();

		for(int i = 0; i<listeners.length; i+=2){//step 2 at a time 
			if(listeners[i] == DetailListener.class){	//check if first item is detaillistener.class
				((DetailListener)listeners[i+1]).detailEventOccured(event);
			}
		}
	}

	public void addDetailListener(DetailListener listener){
		listenerList.add(DetailListener.class, listener);
	}

	public void removeDetailListener(DetailListener listener){
		listenerList.remove(DetailListener.class, listener);

	}

	//public void actionPerformed(ActionEvent e){
	//if(e.getSource() == "Low"){
	//	setPriority(1);
	//}else if(e.getSource() == "Medium"){
	//	setPriority(1);
	//}else if(e.getSource() == "High"){
	//	setPriority(2);
	//}
	//}

	public void setPriority(int i) {
		num = i;
	}

	public int getPriority(){
		return num;
	}
	public void setPriorityStr(String s) {
		str = s;
	}

	public String getPriorityStr(){
		return str;
	}

	public JButton getAddBtn() {
		// TODO Auto-generated method stub
		return addBtn;
	}


}