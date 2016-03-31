package CourseForm;

import javax.swing.JFrame;



public class Course {
	
	private String name;
	private String days;
	private int hour;	//time separated
	private String minutes;
	private String ampm;
	private String time;
	
	public Course (String nme, int hr, String min, String ap) {
		name = nme;
		hour = hr;
		minutes = min;
		ampm = ap;
		days = "";
		time = "";
	}
	
	public void setName (String nme) {
	name = nme; 
	}
	
	/**
	 *Adds appropriate days of the week to string
	 */
	public void setDays (Boolean mon, Boolean tue, Boolean wed, Boolean thur, Boolean fri) {
		if (mon == true) {
			days += "Mon, ";
		}
		if (tue == true) {
			days += "Tue, ";
		}
		if (wed == true) {
			days += "Wed, ";
		}
		if (thur == true) {
			days += "Thu, ";
		}
		if (fri == true) {
			days += "Fri, ";
		}
		days = days.substring(0,days.length()-2);
		//System.out.println(days);
	}
	
	/**
	 * Converts time to make it readable for the database
	 */
	public void timeConverter () {
	
		if (ampm.equals("pm")) {
			hour = hour+12;
			time = (hour+":"+minutes+":00");
		}
		else {
			if (hour != 10 && hour != 11 && hour != 12) {
				time = ("0" + hour+":"+minutes+":00");
			}
			else {
				time = (hour+":"+minutes+":00");
			}
		}
		//System.out.println(time);
	}
	
	public String getName () {
		return name;
	}
	
	public String getDays () {
		return days;
	}
	
	public String getTime() {
		return time;
	}
	
	/**
	public static void main (String args[]) {
		JFrame jf = new JFrame();
		CreateCourse cc = new CreateCourse();
		jf.getContentPane().add(cc.getPanel());
		jf.setVisible(true);
		jf.setSize(400,500);
	}  */
}