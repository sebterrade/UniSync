/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package MainPackage;
import JFramePackage.LoginPage;
import JFramePackage.SignupPage;
import java.sql.SQLException;

//binds for '' sql injection

import java.util.ArrayList;
import java.util.Scanner;
import java.awt.EventQueue;
import java.sql.*;
import com.mysql.jdbc.Driver;
/**
 *
 * @author sebte
 */
public class UniSync {
        static Connection conn = null;
	static Statement stmt = null;
	static ResultSet result = null;
	
	public static Student student;
	
	public static Student getStudent() {
		return student;
	}
	
	//Connect to local database
	public static void dbConnection() {
		try {
                        //Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/unisync?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","Terrade9854*");
			stmt = conn.createStatement();
			
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//Verify the user's login information
	public static int verifyLogin(int id, String pass) {
		

		try {
			result = stmt.executeQuery("Select * from students");
			
			while (result.next()) {
				if (id == result.getInt("StudentID") && pass.equals(result.getString("Password")) ) {
					student.setStudentID(result.getInt("StudentID"));
					student.setPassword(result.getString("Password"));
					student.setFirstName(result.getString("FirstName"));
					student.setLastName(result.getString("LastName"));
					student.setProgram(result.getString("Program"));
					student.setYear(result.getInt("Year"));
					
					calcGPA(result.getInt("StudentID"));
					return 1;
				}
					
			}
			return 0;
			
				
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		
	}
	
	//Register new user
	public static int register(int id, String pass, String ln, String fn, String program, int year) {
		
		try {
			
			result = stmt.executeQuery("Select * from students");
			
			while (result.next()) {
				if (id == result.getInt("StudentID")) {
					return 2;
				}
					
			}
			stmt.execute("INSERT INTO students (StudentID, LastName, FirstName, Program, Year, GPA, Password) VALUES ("+id+",'"+ln+"','"+fn+"','"+program+"',"+year+",0.0,'"+pass+"')");
			System.out.println("Welcome "+ fn+ " "+ ln + "\nCurrent Term GPA: 0.0");
			student.setStudentID(id);
			student.setPassword(pass);
			student.setFirstName(fn);
			student.setLastName(ln);
			student.setProgram(program);
			student.setYear(year);
			return 1;
			
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		
		//Add a new course (function addCourse())
		
	}
	
	//Add another course 
	public static void addCourses(int id, String pass) {
		Scanner courseInfo = new Scanner(System.in);
		boolean courseExists = false;
		boolean courseAdded = false;
		
		while (true) {
			System.out.println("Enter Course Code (No spaces): ");
			String courseCode = courseInfo.next();
			
			
			try {
				result = stmt.executeQuery("Select * from studentcourses");
				while(result.next()) {
					if (courseCode.equals(result.getString("Code")) && id == result.getInt("StudentID") ) {
						System.out.println("Course exists");
						courseAdded = true;
						break;
					}
				}
				
				if (courseAdded) {
					System.out.println("Course has already been added");
				}else {
					result = stmt.executeQuery("Select * from courses");
					
					while (result.next()) {
						if (courseCode.equals(result.getString("Code")) ) {
							System.out.println("Course exists");
							courseExists = true;
							stmt.execute("insert into studentcourses (StudentID, Code) values ("+id+",'"+ courseCode+"')");
							System.out.println("Course succesfully added!");
							break;
						}
					}
					if (!courseExists)
						System.out.println("Course was not found in database");
				}
				
						
			}catch(Exception e){
				e.printStackTrace();
			}
			
			System.out.println("Do you want to add another course (yes or no)?");
			courseInfo.nextLine();
			if (courseInfo.next().equals("no"))
				break;
		}
		
	}
	
	public static void modifyCourse(int id) {
		ResultSet result2= null;
		Statement stmt2=null;
		
		Scanner scanner2 = new Scanner(System.in);
		String code;
		int deliverableNum=0;
		double deliverableGrade=0;
	
		while (true) {
			System.out.println("Select a course to modify");
			code = scanner2.next();
			
			while (true) {
				
				
				try {
					stmt2 = conn.createStatement();
					
					result = stmt.executeQuery("Select * from deliverables where Code='" + code + "'");
					System.out.println("Select a deliverable to modify");
					while (result.next()) {
						result2 = stmt2.executeQuery("Select * from studentdeliverables where Code='" + code + "' AND StudentID="+id+" AND DeliverableID = "+ result.getInt("DeliverableID"));
						
						double printGrade=0.0;
						while(result2.next()) {
							printGrade = result2.getDouble("DeliverableGrade");
						}
						
						System.out.println("("+result.getInt("DeliverableID") + ") " + result.getString("DeliverableName") + " - Grade: "+ printGrade + "%(Weight: "+ result.getDouble("DeliverableWeight")+ ")");
					}
					deliverableNum = scanner2.nextInt();
					System.out.println("Grade: ");
					deliverableGrade = scanner2.nextDouble();
					
					
					result = stmt.executeQuery("Select * from studentdeliverables where StudentID="+id);
					boolean entryExists=false;
					while (result.next()) {
						if (deliverableNum==result.getInt("DeliverableID") && code.equals(result.getString("Code"))) {
							entryExists = true;
							stmt.execute("update studentdeliverables set DeliverableGrade="+ deliverableGrade+ " where DeliverableID="+ deliverableNum );
							break;
						}
					}
					if (!entryExists) {
						stmt.execute("insert into studentdeliverables (StudentID, Code, DeliverableID, DeliverableGrade) values ("+ id + ",'"+ code + "',"+deliverableNum+","+ deliverableGrade+")");
					}
					
					//Calculate new grade for course
					double sum=0;
					double weightSum=0;
					double courseAverage=0;
					result = stmt.executeQuery("Select * from studentdeliverables where StudentID="+id+" and Code='"+code+"'");
					while (result.next()) {
						result2 = stmt2.executeQuery("Select * from deliverables where Code='" + code + "' AND DeliverableID = "+ result.getInt("DeliverableID"));
						
						double weight=0;
						while(result2.next()) {
							weight = result2.getDouble("DeliverableWeight");
						}
						weightSum= weightSum + weight;
						sum = sum + weight*result.getDouble("DeliverableGrade");
						courseAverage = sum/weightSum;
						
					}
					stmt.execute("update studentcourses set Grade="+ courseAverage + "where Code='"+code+"'");
					
					System.out.println("You currently have a "+courseAverage+ "% in "+code);
					
				
					for (int i=90; i>=80; i=i-5) {
						
						double remainingGrade=(i*100-(courseAverage*weightSum))/(100-weightSum);
						System.out.println("You need a "+remainingGrade+"% average for the remainder of your deliverables to get a "+i+"%");
					}
					
					for (int i=77; i>=50; i=i-3) {
						
						double remainingGrade=(i*100-(courseAverage*weightSum))/(100-weightSum);
						System.out.println("You need a "+remainingGrade+"% average for the remainder of your deliverables to get a "+i+"%");
						
						i-=4;
						
						remainingGrade=(i*100-(courseAverage*weightSum))/(100-weightSum);
						System.out.println("You need a "+remainingGrade+"% average for the remainder of your deliverables to get a "+i+"%");
						
						i-=3;
						
						remainingGrade=(i*100-(courseAverage*weightSum))/(100-weightSum);
						System.out.println("You need a "+remainingGrade+"% average for the remainder of your deliverables to get a "+i+"%");
					}
					
					
					
					
					
					System.out.println("Would you like to modify another grade (yes or no)");
					if (scanner2.next().equals("no"))
						break;
					
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
			System.out.println("Would you like to modify another course (yes or no)");
			if (scanner2.next().equals("no"))
				break;
			
		}
		
		
		
	}
	
	//Calculate user's GPA
	public static void calcGPA(int id) {
		try {
			ResultSet result2= null;
			Statement stmt2=null;
			ResultSet result3= null;
			Statement stmt3=null;
			stmt2 = conn.createStatement();
			stmt3 = conn.createStatement();
			//Calculate students GPA 
			double sum=0;
			double creditSum=0;
			double GPA=0;
			double grade;
			String code;
			result3 = stmt3.executeQuery("Select * from studentcourses where StudentID="+id);
			while (result3.next()) {
				code = result3.getString("Code");
				result2 = stmt2.executeQuery("Select * from courses where Code='" + code + "'");
				
				double credit=0;
				while(result2.next()) {
					credit = result2.getDouble("Credits");
				}
				creditSum= creditSum + credit;
				grade = result3.getDouble("Grade");
				
				if (grade>=90.0)
					grade=4.3;
				else if(grade<90 && grade>=85)
					grade=4.0;
				else if(grade<85 && grade>=80)
					grade=3.7;
				else if(grade<80 && grade>=77)
					grade=3.3;
				else if(grade<77 && grade>=73)
					grade=3.0;
				else if(grade<73 && grade>=70)
					grade=2.7;
				else if(grade<70 && grade>=67)
					grade=2.3;
				else if(grade<67 && grade>=63)
					grade=2.0;
				else if(grade<63 && grade>=60)
					grade=1.7;
				else if(grade<60 && grade>=57)
					grade=1.3;
				else if(grade<57 && grade>=53)
					grade=1.0;
				else if(grade<53 && grade>=50)
					grade=0.7;
				else
					grade=0.0;
				
				
				sum = sum + credit*grade;
				GPA = sum/creditSum;
				
			}
			stmt3.execute("update students set GPA="+ GPA + "where StudentID="+id);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
    

    public static void main(String[] args) {
       UniSync.dbConnection();
       UniSync.student = new Student();
       EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginPage frame = new LoginPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
}
