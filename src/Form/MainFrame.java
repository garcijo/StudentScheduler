package Form;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextArea;

import data.MySQLConnect;



public class MainFrame extends JFrame {
	
	private DetailsPanel detailsPanel;
	
public MainFrame(String title){
	super(title);
	
	//Set layout manager
	setLayout(new BorderLayout());
	
	// Create Swing components
	final JTextArea textArea = new JTextArea();
	JButton button = new JButton("Submit");
	
	detailsPanel = new DetailsPanel();
	
	detailsPanel.addDetailListener(new DetailListener(){
		public void detailEventOccured(DetailEvent event){
		String text = event.getText();
		
		textArea.append("Input Received: " + text);
		
		}
	});
	
	//hides from task bar
	setType(Type.UTILITY);
	
	// Add swing components to content pane
	Container c = getContentPane();
	
	c.add(textArea, BorderLayout.CENTER);
	c.add(button, BorderLayout.SOUTH);
	c.add(detailsPanel, BorderLayout.WEST);
	
	//Add behavior
	button.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			MySQLConnect dal = new MySQLConnect();
			try {
				String name;
				String date;
				String desc;
				String course;
				String type;
				String prior;
				Scanner scan = new Scanner(System.in);
				System.out.println("Name: ");
				name = scan.nextLine();
				System.out.println("Due Date(YYYY-MM-DD): ");
				date = scan.nextLine();
				System.out.println("Description: ");
				desc = scan.nextLine();
				System.out.println("Course: ");
				course = scan.nextLine();
				System.out.println("Type: ");
				type = scan.nextLine();
				System.out.println("Priority: ");
				prior = scan.nextLine();
				System.out.println("The following form was added to the database:");
				String[] check = dal.addForm(name.toString(),"admin",date.toString(),desc.toString(),course.toString(),type.toString(),prior.toString());
				for(int i=0; i<check.length; i++){
					System.out.print(check[i] + "    ");
				}
				//test confirmation that button works
				textArea.append("Form Submitted!\n");
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			
			
			
		}
		
	}
	
			);
	
	
}
}
