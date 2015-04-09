package objects;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Course implements Comparable<Course> {

	private Map<DayOfWeek, Integer> timeOffered;
	private int credits = 0;
	private int maxTAcount = 0;
	private int courseNumber;
	private int section;
	private Instructor instructor;
	
	public Course(Instructor inst, int sec, int courseNum, int cred) {
		
		instructor = inst;
		credits = cred;
		section = sec;
		courseNumber = courseNum;
		
		timeOffered = new HashMap<DayOfWeek, Integer>();
	}
	
	public String toString() {
		
		String string = "Class: CS " + courseNumber + "\n" +
						"Taught by: " + instructor + "\n";
		
		DayOfWeek[] days = DayOfWeek.values();
		for (int i = 0; i < days.length; i++) {
			string += days[i] + " times: "; 
			for (int j = 0; j < days.length; j++)
				string += timeOffered.get(days[i]) + ", ";
			string += "\n";
		}
		return string;
	}
	
	public boolean equals(Course c) {
		
		if (this.hashCode() - c.hashCode() == 0)
			return true;
		
		else 
			return false;
	}
	
	public int compareTo(Course c) {
		
		return courseNumber - c.getCourseNumber();
	}

	public Map<DayOfWeek, Integer> getTimeOffered() {
		
		return timeOffered;
	}

	public void setTimeOffered(Map<DayOfWeek, Integer> timeOffered) {
		
		this.timeOffered = timeOffered;
	}

	public int getCredits() {
		
		return credits;
	}

	public void setCredits(int credits) {
		
		this.credits = credits;
	}

	public int getMaxTAcount() {
		
		return maxTAcount;
	}

	public void setMaxTAcount(int maxTAcount) {
		
		this.maxTAcount = maxTAcount;
	}

	public int getCourseNumber() {
		
		return courseNumber;
	}

	public void setCourseNumber(int courseNumber) {
		
		this.courseNumber = courseNumber;
	}

	public Instructor getInstructor() {
		
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		
		this.instructor = instructor;
	}

	public int getSection() {
		
		return section;
	}

	public void setSection(int section) {
		
		this.section = section;
	}
}