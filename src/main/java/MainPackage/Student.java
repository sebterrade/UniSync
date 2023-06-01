/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MainPackage;

public class Student {
	private int studentID;
	private String password;
	private String firstName;
	private String lastName;
	private String program;
	private int year;
	private double gpa;
	
	public Student() {
		studentID=0;
		firstName="";
		lastName="";
		program="";
		password="";
		year=0;
		gpa=0.0;
	}
	
	public void setStudentID(int id) {
		studentID = id;
	}
	public void setPassword(String pass) {
		password = pass;
	}
	public void setFirstName(String fn) {
		firstName = fn;
	}
	public void setLastName(String ln) {
		lastName = ln;
	}
	public void setProgram(String prog) {
		program = prog;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	
	
	public int getStudentID() {
		return studentID;
	}
	public String getPassword() {
		return password;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getProgram() {
		return program;
	}
	public int getYear() {
		return year;
	}
        public double getGPA() {
		return gpa;
	}
	
	
	
}
