package unitTests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import data.MySQLConnect;

public class SortDateTest {

	@Test
	public void test() {
		MySQLConnect dal = new MySQLConnect();

		try {
			ArrayList<String[]> tasks = dal.sortTasksByDate();
			String[] tsk = new String[6];
			System.out.println("Name     DueDate     Description         Course      Type     Priority");
			for (int i = 0; i < tasks.size(); i++) {
				tsk = tasks.get(i);
				
				System.out.println(tsk[0] + "    "+tsk[1]+"   "+tsk[2]+"   "+tsk[3]+"      "+tsk[4]+ "     " + tsk[5]);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
