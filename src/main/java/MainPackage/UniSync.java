/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package MainPackage;
import JFramePackage.LoginPage;
import JFramePackage.LoginPage;
import JFramePackage.SignupPage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.sql.SQLException;

//binds for '' sql injection

import java.util.ArrayList;
import java.util.Scanner;
import java.awt.EventQueue;
import java.sql.*;
import com.mysql.jdbc.Driver;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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
					
					student.setGPA(calcGPA());
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
			PreparedStatement pstmt = conn.prepareStatement("Select * from students where StudentID = ?");
                        pstmt.setInt(1,id);
			result = pstmt.executeQuery();
			
			while (result.next()) {
				if (id == result.getInt("StudentID")) {
					return 2;
				}
					
			}
                        pstmt = conn.prepareStatement("INSERT INTO students (StudentID, LastName, FirstName, Program, Year, GPA, Password) VALUES (?,?,?,?,?,0.0,?)");
			pstmt.setInt(1,id);
                        pstmt.setString(2,ln);
                        pstmt.setString(3,fn);
                        pstmt.setString(4,program);
                        pstmt.setInt(5,year);
                        pstmt.setString(6,pass); 
                        pstmt.executeUpdate();
                        
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
                        PreparedStatement selectStmt = conn.prepareStatement("Select * from studentcourses where Code = ? and StudentID = ?");
                        selectStmt.setString(1, courseCode);
                        selectStmt.setInt(2, id);
                        result = selectStmt.executeQuery();

                        while(result.next()) {
                                if (courseCode.equals(result.getString("Code")) && id == result.getInt("StudentID") ) {
                                    if (result.getInt("Status")==0){
                                        PreparedStatement updateStmt = conn.prepareStatement("update studentcourses set Status=1 where StudentID = ? and Code= ?");
                                        updateStmt.setInt(1, id);
                                        updateStmt.setString(2, courseCode);
                                        updateStmt.executeUpdate();
                                        return 2;
                                    }
                                        return 1;
                                }
                        }

                        PreparedStatement courseStmt = conn.prepareStatement("Select * from courses where Code = ?");
                        courseStmt.setString(1, courseCode);
                        result = courseStmt.executeQuery();

                        while (result.next()) {
                                PreparedStatement insertCourseStmt = conn.prepareStatement("insert into studentcourses (StudentID, Code, Status) values (?,?, 1)");
                                insertCourseStmt.setInt(1, id);
                                insertCourseStmt.setString(2,courseCode);
                                insertCourseStmt.executeUpdate();
                                courseExists=1;
                                break;

                        }

                        if (courseExists==0){
                            return 0;
                        } 
                        else{
                            PreparedStatement deliverablesStmt = conn.prepareStatement("Select * from deliverables where Code = ?");
                            deliverablesStmt.setString(1, courseCode);
                            result = deliverablesStmt.executeQuery();

                            while (result.next()) {
                                int deliverableNum = result.getInt("DeliverableNum");
                                PreparedStatement insertDeliverablesStmt = conn.prepareStatement("insert into studentdeliverables (StudentID, DeliverableNum, Code, DeliverableStatus) values (?,?,?, 0)");
                                insertDeliverablesStmt.setInt(1, id);
                                insertDeliverablesStmt.setInt(2, deliverableNum);
                                insertDeliverablesStmt.setString(3, courseCode);
                                insertDeliverablesStmt.executeUpdate();
                            }
                            return 2;

                        }



                }catch(Exception e){
                        e.printStackTrace();
                        return 0;
                }
	}
        
        public static int addDeliverableDate(String courseCode, int deliverableNum, String date){
            try {   
                    
                    
                    
                    PreparedStatement checkStatusStatement = conn.prepareStatement("SELECT * FROM studentdeliverables WHERE DeliverableNum = ? AND Code = ? AND StudentID = ? AND DeliverableStatus > 0");                    checkStatusStatement.setInt(1, deliverableNum);
                    checkStatusStatement.setString(2, courseCode);
                    checkStatusStatement.setInt(3, student.getStudentID());
                    result = checkStatusStatement.executeQuery();
                    while(result.next()){
                        return 3;
                    }
                    //If due date has not already been added      
                    URL url = new URL("https://digidates.de/api/v1/checkdate?date=2023-01-01");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        StringBuilder response = new StringBuilder();

                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();

                        // Process the JSON response
                        String jsonResponse = response.toString();
                        // Parse the JSON and check the "checkdate" value
                        // Assuming you are using a JSON library like Gson
                        JsonObject jsonObject = new Gson().fromJson(jsonResponse, JsonObject.class);
                        boolean isDateValid = jsonObject.get("checkdate").getAsBoolean();

                        if (isDateValid) {
                            System.out.println("The date is valid.");
                            
                            PreparedStatement pstmt = conn.prepareStatement("update studentdeliverables set DueDate =?, DeliverableStatus = 1 where DeliverableNum=? and Code=? and StudentID = ?");
                            pstmt.setString(1, date);
                            pstmt.setInt(2, deliverableNum);
                            pstmt.setString(3, courseCode);
                            pstmt.setInt(4, student.getStudentID());
                            pstmt.executeUpdate();
                            connection.disconnect();
                            return 1;
                            
                        } else {
                            System.out.println("The date is invalid.");
                            connection.disconnect();
                            return 2;
                        }
                    } else {
                        System.out.println("Error: " + responseCode);
                        connection.disconnect();
                        return 0;
                    }

                   
                
                    

                }catch(Exception e){
                        e.printStackTrace();
                        return 0;
                }
        }
        
        public static int removeDeliverableDate(String courseCode, int deliverableNum){
            try {   
                    
                    PreparedStatement checkStatusStatement = conn.prepareStatement("SELECT * FROM studentdeliverables WHERE DeliverableNum = ? AND Code = ? AND StudentID = ? AND DeliverableStatus = 0");                    
                    checkStatusStatement.setInt(1, deliverableNum);
                    checkStatusStatement.setString(2, courseCode);
                    checkStatusStatement.setInt(3, student.getStudentID());
                    result = checkStatusStatement.executeQuery();
                    while(result.next()){
                        return 2; //Deliverable has not been added yet
                    }     

                    PreparedStatement pstmt = conn.prepareStatement("update studentdeliverables set DueDate = null, DeliverableStatus = 0 where DeliverableNum=? and Code=? and StudentID = ?");
                    pstmt.setInt(1, deliverableNum);
                    pstmt.setString(2, courseCode);
                    pstmt.setInt(3, student.getStudentID());
                    pstmt.executeUpdate();
                    return 1;
                       
            }catch(Exception e){
                    e.printStackTrace();
                    return 0;
            }
        }
        
        public static int completedDeliverableDate(String courseCode, int deliverableNum){
                
                try {   
                    
                    PreparedStatement checkStatusStatement = conn.prepareStatement("SELECT * FROM studentdeliverables WHERE DeliverableNum = ? AND Code = ? AND StudentID = ? AND (DeliverableStatus = 0 OR DeliverableStatus = 2)");                    
                    checkStatusStatement.setInt(1, deliverableNum);
                    checkStatusStatement.setString(2, courseCode);
                    checkStatusStatement.setInt(3, student.getStudentID());
                    result = checkStatusStatement.executeQuery();
                    while(result.next()){
                        return 2; //Deliverable has not been added yet
                    }     

                    PreparedStatement pstmt = conn.prepareStatement("update studentdeliverables set DeliverableStatus = 2 where DeliverableNum=? and Code=? and StudentID = ?");
                    pstmt.setInt(1, deliverableNum);
                    pstmt.setString(2, courseCode);
                    pstmt.setInt(3, student.getStudentID());
                    pstmt.executeUpdate();
                    return 1;
                       
                }catch(Exception e){
                        e.printStackTrace();
                        return 0;
                }

        }
        
        public static int numCourses(){
                int count =0;
                try {
                    PreparedStatement pstmt = conn.prepareStatement("select * from studentcourses where StudentID = ?");
                    pstmt.setInt(1,student.getStudentID());
                    result = pstmt.executeQuery();
                    
                    while (result.next()){
                        count++;
                    }
                    
                    return count;
                    
                    
                } catch (SQLException e) {
                    e.printStackTrace();
                    return 0;
                }
        }
        
        public static int deleteCourses(int id, String courseCode) {
                try {
                    PreparedStatement selectStmt = conn.prepareStatement("select * from studentcourses where Code = ? and StudentID = ?");
                    selectStmt.setString(1, courseCode);
                    selectStmt.setInt(2, id);
                    result = selectStmt.executeQuery();

                    while (result.next()) {
                        if (courseCode.equals(result.getString("Code")) && id == result.getInt("StudentID")) {
                            PreparedStatement updateStmt = conn.prepareStatement("update studentcourses set Status = 0 where StudentID = ? and Code = ?");
                            updateStmt.setInt(1, id);
                            updateStmt.setString(2, courseCode);
                            updateStmt.executeUpdate();
                            return 1;
                        }
                    }
                    return 0;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return 0;
                }
        }
        
        public static int updateGrade(int deliverableNum, Double grade, String courseCode) {
                    
                try {
                    PreparedStatement pstmt = conn.prepareStatement("select * from studentdeliverables where DeliverableNum=? and Code=? and StudentID = ? and DeliverableStatus<2");
                    pstmt.setInt(1, deliverableNum);
                    pstmt.setString(2, courseCode);
                    pstmt.setInt(3, student.getStudentID());
                    result = pstmt.executeQuery();
                    
                    while(result.next()){
                        return 2; //Deliverable not completed yet
                    }
                    
                    PreparedStatement updateStatement = conn.prepareStatement("update studentdeliverables set DeliverableGrade =? where DeliverableNum=? and Code=? and StudentID = ?");
                    updateStatement.setDouble(1, grade);
                    updateStatement.setInt(2, deliverableNum);
                    updateStatement.setString(3, courseCode);
                    updateStatement.setInt(4, student.getStudentID());
                    updateStatement.executeUpdate();
                    return 1;

                }catch(Exception e){
                        e.printStackTrace();
                        return 0;
                }
	}
        
        public static double calculateCourseGrade(String courseCode) {
                try {
                    double sum = 0;
                    double weightSum = 0;
                    double courseAverage = 0;

                    PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT * FROM studentdeliverables " +
                        "INNER JOIN deliverables ON studentdeliverables.Code = deliverables.Code AND studentdeliverables.DeliverableNum = deliverables.DeliverableNum " +
                        "WHERE StudentID = ? AND studentdeliverables.Code = ? AND DeliverableStatus = 2"
                    );
                    pstmt.setInt(1, student.getStudentID());
                    pstmt.setString(2, courseCode);
                    result = pstmt.executeQuery();

                    while (result.next()) {
                        double weight = result.getDouble("DeliverableWeight");
                        weightSum += weight;
                        sum += weight * result.getDouble("DeliverableGrade");
                        courseAverage = sum / weightSum;
                    }

                    pstmt = conn.prepareStatement("UPDATE studentcourses SET Grade = ? WHERE Code = ?");
                    pstmt.setDouble(1, courseAverage);
                    pstmt.setString(2, courseCode);
                    pstmt.executeUpdate();

                    return courseAverage;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0;
                }
        }
        
        public static double requiredGrade(double desiredGrade, String courseCode) {
                double sum = 0;
                double weightSum = 0;
                double courseAverage = 0;
                try {
                    PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT * FROM studentdeliverables " +
                        "INNER JOIN deliverables ON studentdeliverables.Code = deliverables.Code AND studentdeliverables.DeliverableNum = deliverables.DeliverableNum " +
                        "WHERE StudentID = ? AND studentdeliverables.Code = ? AND DeliverableStatus = 2"
                    );
                    pstmt.setInt(1, student.getStudentID());
                    pstmt.setString(2, courseCode);
                    result = pstmt.executeQuery();

                    while (result.next()) {
                        double weight = result.getDouble("DeliverableWeight");
                        weightSum += weight;
                        sum += weight * result.getDouble("DeliverableGrade");
                        courseAverage = sum / weightSum;
                    }

                    double remainingGrade = (desiredGrade * 100 - (courseAverage * weightSum)) / (100 - weightSum);
                    return remainingGrade;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0.0;
                }
        }
        
        public static double weightSum(String courseCode) {
                double sum = 0;
                double weightSum = 0;
                try {
                    PreparedStatement pstmt = conn.prepareStatement(
                        "SELECT * FROM studentdeliverables " +
                        "INNER JOIN deliverables ON studentdeliverables.Code = deliverables.Code AND studentdeliverables.DeliverableNum = deliverables.DeliverableNum " +
                        "WHERE StudentID = ? AND studentdeliverables.Code = ? AND DeliverableStatus = 2"
                    );
                    pstmt.setInt(1, student.getStudentID());
                    pstmt.setString(2, courseCode);
                    result = pstmt.executeQuery();

                    while (result.next()) {
                        double weight = result.getDouble("DeliverableWeight");
                        weightSum += weight;
                    }

                    return weightSum;
                } catch (Exception e) {
                    e.printStackTrace();
                    return 0.0;
                }
        }
        
	
	//Calculate user's GPA
	public static double calcGPA() {
                try {
                    Connection connection = stmt.getConnection();
                    PreparedStatement selectStmt = connection.prepareStatement("SELECT * FROM studentcourses INNER JOIN courses ON studentcourses.Code = courses.Code WHERE StudentID = ?");
                    PreparedStatement updateStmt = connection.prepareStatement("UPDATE students SET GPA = ? WHERE StudentID = ?");
                    selectStmt.setInt(1, student.getStudentID());

                    // Calculate students GPA 
                    double sum = 0;
                    double creditSum = 0;
                    double GPA = 0;
                    double grade;
                    String code;
                    result = selectStmt.executeQuery();

                    while (result.next()) {
                        double credit = result.getDouble("Credits");
                        creditSum += credit;
                        grade = result.getDouble("Grade");

                        if (grade >= 90.0)
                            grade = 4.3;
                        else if (grade < 90 && grade >= 85)
                            grade = 4.0;
                        else if (grade < 85 && grade >= 80)
                            grade = 3.7;
                        else if (grade < 80 && grade >= 77)
                            grade = 3.3;
                        else if (grade < 77 && grade >= 73)
                            grade = 3.0;
                        else if (grade < 73 && grade >= 70)
                            grade = 2.7;
                        else if (grade < 70 && grade >= 67)
                            grade = 2.3;
                        else if (grade < 67 && grade >= 63)
                            grade = 2.0;
                        else if (grade < 63 && grade >= 60)
                            grade = 1.7;
                        else if (grade < 60 && grade >= 57)
                            grade = 1.3;
                        else if (grade < 57 && grade >= 53)
                            grade = 1.0;
                        else if (grade < 53 && grade >= 50)
                            grade = 0.7;
                        else
                            grade = 0.0;

                        sum = sum + credit * grade;
                        GPA = sum / creditSum;
                    }

                    updateStmt.setDouble(1, GPA);
                    updateStmt.setInt(2, student.getStudentID());
                    updateStmt.executeUpdate();

                    return GPA;

                } catch (Exception e) {
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
