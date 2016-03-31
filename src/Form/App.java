package Form;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class App {

	public static void main(String[] args){
		
		SwingUtilities.invokeLater(new Runnable(){
		public void run(){
		
			JFrame frame = new MainFrame("Student Scheduler Form");
			frame.setSize(500,400);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
			
		}
			
		});
		

	}
	
}
