/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package MainPackage;
import JFramePackage.LoginPage;
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
        public static Connection conn = null;
	public static Statement stmt = null;
	public static ResultSet result = null;
	
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
					
					calcGPA();
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
	public static int addCourses(int id, String courseCode) {
                        int courseExists = 0;
			
			try {
                                Statement selectStmt = stmt.getConnection().createStatement();
                                Statement updateStmt = stmt.getConnection().createStatement();
                                
				result = selectStmt.executeQuery("Select * from studentcourses");
				while(result.next()) {
					if (courseCode.equals(result.getString("Code")) && id == result.getInt("StudentID") ) {
                                            if (result.getInt("Status")==0){
                                                updateStmt.execute("update studentcourses set Status=1 where StudentID =" + id + " and Code='" + courseCode+ "'");
                                                return 2;
                                            }
						return 1;
					}
				}
                               
				
				result = selectStmt.executeQuery("Select * from courses");
					
				while (result.next()) {
					if (courseCode.equals(result.getString("Code")) ) {
                                            updateStmt.execute("insert into studentcourses (StudentID, Code, Status) values ("+id+",'"+ courseCode +"', 1)");
                                            courseExists=1;
                                            break;
					}
				}
                                
                                if (courseExists==0){
                                    return 0;
                                } 
                                else{
                                    result = selectStmt.executeQuery("Select * from deliverables where Code ='" + courseCode + "'");
                                  
                                    while (result.next()) {
                                        int deliverableNum = result.getInt("DeliverableNum");
                                        updateStmt.execute("insert into studentdeliverables (StudentID, DeliverableNum, Code, DeliverableStatus) values ("+id+",'"+ deliverableNum+"','" + courseCode+"', 0)");
                                    }
                                    return 2;

                                }
                                
				
						
			}catch(Exception e){
				e.printStackTrace();
                                return 0;
			}
	}
        
        public static int deleteCourses(int id, String courseCode) {
		
			try {
				result = stmt.executeQuery("Select * from studentcourses");
				while(result.next()) {
					if (courseCode.equals(result.getString("Code")) && id == result.getInt("StudentID") ) {
                                                stmt.execute("update studentcourses set Status=0 where StudentID =" + id + " and Code='" + courseCode+ "'");
						return 1;
					}
				}
				return 0;
				
						
			}catch(Exception e){
				e.printStackTrace();
                                return 0;
			}
	}
        
        public static int updateGrade(int deliverableNum, Double grade, String courseCode) {
                    
			try {
                            stmt.execute("update studentdeliverables set DeliverableGrade =" + grade + ", DeliverableStatus = 1 where DeliverableNum="+ deliverableNum + " and Code ='" + courseCode+"' and StudentID = " + student.getStudentID());
                            return 1;
		
			}catch(Exception e){
				e.printStackTrace();
                                return 0;
			}
	}
        
        public static double calculateCourseGrade(String courseCode) {
                    
			try {
                            //Calculate new grade for course
					double sum=0;
					double weightSum=0;
					double courseAverage=0;
					
                                        result = stmt.executeQuery("Select * from studentdeliverables inner join deliverables "
                                                + "on studentdeliverables.Code = deliverables.Code and studentdeliverables.DeliverableNum = deliverables.DeliverableNum "
                                                + "where StudentID="+student.getStudentID()+" and studentdeliverables.Code='"+courseCode+"' and DeliverableStatus=1");
                                        
					while (result.next()) {
						double weight= result.getDouble("DeliverableWeight");
						
						weightSum= weightSum + weight;
						sum = sum + weight*result.getDouble("DeliverableGrade");
						courseAverage = sum/weightSum;
						
					}
					stmt.execute("update studentcourses set Grade="+ courseAverage + "where Code='"+courseCode+"'");
                                        return courseAverage;
		
			}catch(Exception e){
				e.printStackTrace();
                                return 0;
			}
	}
        
        public static double requiredGrade(double desiredGrade, String courseCode){
                double sum=0;
                double weightSum=0;
                double courseAverage=0;
		try{
                    result = stmt.executeQuery("Select * from studentdeliverables inner join deliverables "
                              + "on studentdeliverables.Code = deliverables.Code and studentdeliverables.DeliverableNum = deliverables.DeliverableNum "
                              + "where StudentID="+student.getStudentID()+" and studentdeliverables.Code='"+courseCode+"' and DeliverableStatus=1");
                                        
                    while (result.next()) {
                        double weight= result.getDouble("DeliverableWeight");
						
                        weightSum= weightSum + weight;
                        sum = sum + weight*result.getDouble("DeliverableGrade");
                        courseAverage = sum/weightSum;
						
                    }
                                        
                    double remainingGrade = (desiredGrade*100-(courseAverage*weightSum))/(100-weightSum);
                    return remainingGrade;
                    
                }catch(Exception e){
                    e.printStackTrace();
                    return 0.0;
		}			
                
        }
        
        public static double weightSum( String courseCode){
                double sum=0;
                double weightSum=0;
		try{
                    result = stmt.executeQuery("Select * from studentdeliverables inner join deliverables "
                              + "on studentdeliverables.Code = deliverables.Code and studentdeliverables.DeliverableNum = deliverables.DeliverableNum "
                              + "where StudentID="+student.getStudentID()+" and studentdeliverables.Code='"+courseCode+"' and DeliverableStatus=1");
                                        
                    while (result.next()) {
                        double weight= result.getDouble("DeliverableWeight");
						
                        weightSum= weightSum + weight;
						
                    }
                    return weightSum;
                    
                }catch(Exception e){
                    e.printStackTrace();
                    return 0.0;
		}			
                
        }
        
	
	//Calculate user's GPA
	public static double calcGPA() {
		try {
			Statement selectStmt = stmt.getConnection().createStatement();
                        Statement updateStmt = stmt.getConnection().createStatement();
			//Calculate students GPA 
			double sum=0;
			double creditSum=0;
			double GPA=0;
			double grade;
			String code;
			result = selectStmt.executeQuery("Select * from studentcourses inner join courses on studentcourses.Code = courses.Code where StudentID="+ student.getStudentID());
			while (result.next()) {
                                double credit = result.getDouble("Credits");
				creditSum += credit;
				grade = result.getDouble("Grade");
				
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
                        updateStmt.execute("update students set GPA="+ GPA + "where StudentID="+student.getStudentID());
                        return GPA;
			
		}catch(Exception e){
			e.printStackTrace();
                        return 0.0;
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
