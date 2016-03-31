package unitTests;
import java.sql.SQLException;
import java.util.Scanner;

import logInfo.LoginView;
import data.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class AddFormTest {

	@Test
	public void test() {
		MySQLConnect dal = new MySQLConnect();
		try {
			String name;
			String user = LoginView.username;
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
			int checkExists = dal.checkIfExists(name,user,date,desc,course,type,prior);
			if (checkExists == 1){
				System.out.println("Current task is already in database");}
			else{
			System.out.println("The following form was added to the database:");
			String[] check = dal.addForm(name,user,date.toString(),desc,course,type,prior);
			for(int i=1; i<check.length; i++){
				System.out.print(check[i] + "    ");
			}}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
