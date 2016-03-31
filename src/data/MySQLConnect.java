package data;

import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;

public class MySQLConnect {

	//Connection method. Both the username and password are the default ones assigned by the server.
	private Connection getConnection() throws SQLException{
		Connection con = null;
		String username = "sql370333";
		String password = "lK3!bX9*";
		String connectionUrl = "jdbc:mysql://sql3.freemysqlhosting.net/sql370333";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = (Connection)DriverManager.getConnection(connectionUrl, username, password);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}

	//Verifies if a user's login information is correct
	public boolean checkUserLogin(String username,String password) throws SQLException{
		Connection con = getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Users WHERE uName = '" + username+ "' AND pWord = '"+password+"'");

		if (!rs.next() ) {
			con.close();
			return false;

		} else {
			con.close();
			return true;
		}
	}
	
	public boolean findUser(String username) throws SQLException{
		Connection con = getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT uName FROM Users WHERE uName = '" + username+ "'");

		if (!rs.next() ) {
			con.close();
			return false;

		} else {
			con.close();
			return true;
		}
	}

	//Used for users registration
	public void addUser(String user,String pass, String name, String email)throws SQLException{
		Connection con = getConnection();
		String SQL = "INSERT INTO Users VALUES (?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(SQL); 

		pstmt.setString(1, user);
		pstmt.setString(2, pass);
		pstmt.setString(3, name);
		pstmt.setString(4, email);
		pstmt.executeUpdate();
		con.close();
	}

	//Looks for a username and returns all the respective information
	public int findUser(String user,String pass, String name, String email)throws SQLException{
		Connection con = getConnection();
		Statement stmt = con.createStatement();
		String check = null;
		ResultSet rs = stmt.executeQuery("Select uName FROM Users WHERE uName='"+user+"'");
		while(rs.next()){
			check = rs.getString("uName");
		}
		if (check == null){
			con.close();
			return 0;
		}
		else {
			con.close();
			return 1;
		}
	}

	//Checks if a task already exists to prevent redundance
	public int checkIfExists(String name,String user, String dueDate, String description, String course, String type, String priority)throws SQLException{
		Connection con = getConnection();
		Statement stmt = con.createStatement();
		String check = null;
		ResultSet rs = stmt.executeQuery("SELECT * FROM Tasks WHERE taskName='"+name+"' AND user='"+user+"' AND dueDate='"+dueDate+"' AND course='"+course+"' AND taskType='"+type+"' AND priority='"+priority+"'");
		while(rs.next()){
			check = rs.getString("taskName");
			System.out.println(check);
		}

		if (check == null){
			con.close();
			return 0;

		}
		else {
			con.close();
			return 1;
		}

	}

	//Form creating
	public String[] addForm(String name,String user, String dueDate, String description, String course, String type, String priority)throws SQLException{
		Connection con = getConnection();
		String SQL = "INSERT INTO Tasks VALUES (null,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(SQL); 


		pstmt.setString(1, name);
		pstmt.setString(2, user);
		pstmt.setString(3, dueDate);
		pstmt.setString(4, description);
		pstmt.setString(5, course);
		pstmt.setString(6, type);
		pstmt.setString(7, priority);
		pstmt.executeUpdate();
		String[] latestForm = {name, user, dueDate, description, course, type, priority};
		MySQLConnect dal = new MySQLConnect();
		String[] verify = dal.latestTask(latestForm);
		con.close();
		return verify;
	}

	//Returns the last task that was created by the user
	public String[] latestTask(String[] latestForm){
		String[] lastTask = new String[8];
		for(int i=1; i<latestForm.length; i++){
			lastTask[i] = latestForm[i];
		}
		return lastTask;
	}


	public void deleteTask(String name, String user, String date, String course) throws SQLException{
		Connection con = getConnection();
		String SQL = ("DELETE FROM Tasks WHERE taskName = '"+name+"' AND user = '"+user+"' AND dueDate = '"+date+"' AND course = '"+course+"'");
		PreparedStatement pstmt = con.prepareStatement(SQL); 	
		pstmt.executeUpdate();
		con.close();
	}




	public void addCourse(String name,String user, String date, String time)throws SQLException{
		Connection con = getConnection();
		String SQL = "INSERT INTO Courses VALUES (?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(SQL); 


		pstmt.setString(1, name);
		pstmt.setString(2, user);
		pstmt.setString(3, date);
		pstmt.setString(4, time);
		pstmt.executeUpdate();
		con.close();

	}

	public void deleteCourse(String name, String user, String date, String time) throws SQLException{
		Connection con = getConnection();
		String SQL = ("DELETE FROM Courses WHERE cName = '"+name+"' AND user = '"+user+"' AND days = '"+date+"' AND time = '"+time+"'");
		PreparedStatement pstmt = con.prepareStatement(SQL); 	
		pstmt.executeUpdate();
		con.close();
	}



	//Returns an ArrayList with all courses
	public ArrayList<String[]> sortCourses() throws SQLException{

		Connection con = getConnection();
		ArrayList<String[]> allCourses = new ArrayList<String[]>();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Courses ORDER BY cName ASC");
		while( rs.next()) {
			String[] course = new String[4];
			for( int i = 1; i <= 4; i++ ){
				course[i-1] = rs.getObject(i).toString();
			}
			allCourses.add(course);
		}

		con.close();
		return allCourses;
	}



	//Returns an ArrayList with all tasks sorted alphabetically
	public ArrayList<String[]> sortTasksAlphabetic(String user) throws SQLException{

		Connection con = getConnection();
		ArrayList<String[]> allTasks = new ArrayList<String[]>();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Tasks WHERE user = '"+user+"' ORDER BY taskName ASC");
		while( rs.next()) {
			String[] task = new String[8];
			for( int i = 1; i <= 8; i++ ){
				task[i-1] = rs.getObject(i).toString();
			}
			allTasks.add(task);
		}

		con.close();
		return allTasks;
	}
	
	//Returns an ArrayList with all tasks sorted by their course
	public ArrayList<String[]> sortTasksByCourse() throws SQLException{

		Connection con = getConnection();
		ArrayList<String[]> allTasks = new ArrayList<String[]>();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Tasks ORDER BY course ASC");
		while( rs.next()) {
			String[] task = new String[8];
			for( int i = 1; i <= 8; i++ ){
				task[i-1] = rs.getObject(i).toString();
			}
			allTasks.add(task);
		}

		con.close();
		return allTasks;
	}

	//Returns an ArrayList with all tasks sorted by date
	public ArrayList<String[]> sortTasksByDate() throws SQLException{

		Connection con = getConnection();
		ArrayList<String[]> allTasks = new ArrayList<String[]>();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Tasks ORDER BY dueDate ASC");
		while( rs.next()) {
			String[] task = new String[8];
			for( int i = 1; i <= 8; i++ ){
				task[i-1] = rs.getObject(i).toString();
			}
			allTasks.add(task);
		}

		con.close();
		return allTasks;
	}

	//Returns an ArrayList with all tasks sorted by type
	public ArrayList<String[]> sortTasksByType() throws SQLException{

		Connection con = getConnection();
		ArrayList<String[]> allTasks = new ArrayList<String[]>();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM Tasks ORDER BY taskType ASC");
		while( rs.next()) {
			String[] task = new String[8];
			for( int i = 1; i <= 8; i++ ){
				task[i-1] = rs.getObject(i).toString();
			}
			allTasks.add(task);
		}

		con.close();
		return allTasks;
	}

}
