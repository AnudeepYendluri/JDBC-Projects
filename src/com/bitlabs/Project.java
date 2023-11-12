package com.bitlabs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

class Hospital {
    private Connection con;

    public Hospital(Connection con) {
        this.con = con;
    }

    public void addPatient(String name, int age, String gender, String aadharCardNo, long contactNumber, String city, String address, String guardiansName, String guardiansAddress, long guardiansContactNumber, String dateOfAdmission) throws SQLException {
        String sql1 = "insert into patients (name, age, gender, aadhar, contact_number, city, address, guardian_name, guardian_address, guardian_contact, date_of_admission, recovered) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql1);

        ps.setString(1, name);
        ps.setInt(2, age);
        ps.setString(3, gender);
        ps.setString(4, aadharCardNo);
        ps.setLong(5, contactNumber);
        ps.setString(6, city);
        ps.setString(7, address);
        ps.setString(8, guardiansName);
        ps.setString(9, guardiansAddress);
        ps.setLong(10, guardiansContactNumber);
        ps.setString(11, dateOfAdmission);
        ps.setBoolean(12, false);

        ps.executeUpdate();
        System.out.println("Patient added successfully");
    }
    
    public void getPatientById(int id) throws SQLException {
        String sql2 = "select * from patients where id = ?";
        PreparedStatement ps2 = con.prepareStatement(sql2);
        ps2.setInt(1, id);
        ResultSet rs = ps2.executeQuery();
        
        if (rs.next()) {
        	System.out.println(rs.getString(2) + " " + rs.getInt(3) + " " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getInt(6) + " " + rs.getString(7) + " " + rs.getString(8) + " " + rs.getString(9) + " " + rs.getString(10) + " " + rs.getInt(11) + " " + rs.getDate(12) + " " + rs.getBoolean(13));

        } else {
            System.out.println("Patient id not found");
        }
    }
    
    
    public void getPatientsByCity(String city) throws SQLException  {
    	
    	String sql3 = "select id,name from patients where city = ? ";
    	PreparedStatement ps3 = con.prepareStatement(sql3);
    	
    	ps3.setString(1,city);
    	
    	ResultSet rs1 = ps3.executeQuery();
    	
    	if(rs1.next()) {
    		System.out.println(rs1.getInt(1) + " " + rs1.getString(2));
    	}
    	else {
    		System.out.println("No patients found in this city");
    	}
    	
    }
    
    public void listOfAllPatients() throws SQLException  {
    	
    	String sql4 = "select * from patients";
    	PreparedStatement ps4 = con.prepareStatement(sql4);
    	ResultSet rs4 = ps4.executeQuery();
    	if(rs4.next()) {
    		System.out.println(rs4.getInt(1) + " " + rs4.getString(2) + " " + rs4.getInt(3) + " " + rs4.getString(4) + " " + rs4.getString(5) + " " + rs4.getInt(6) + " " + rs4.getString(7) + " " + rs4.getString(8) + " " + rs4.getString(9) + " " + rs4.getString(10) + " " + rs4.getInt(11) + " " + rs4.getDate(12) + " " + rs4.getBoolean(13));
    	}
    	else {
    		System.out.println("No patients in the hospitals");
    	}
    }
    
    public void getPateintsByAgeGroup(int minAge,int maxAge) throws SQLException {
    	
    	String sql5 = "select * from patients where age between ? and ?";
    	PreparedStatement ps5 = con.prepareStatement(sql5);
    	ps5.setInt(1, minAge);
    	ps5.setInt(2, maxAge);
    	ResultSet rs5 = ps5.executeQuery();
    	if(rs5.next()) {
    		System.out.println(rs5.getInt(1) + " " + rs5.getString(2) + " " + rs5.getInt(3) + " " + rs5.getString(4) + " " + rs5.getString(5) + " " + rs5.getInt(6) + " " + rs5.getString(7) + " " + rs5.getString(8) + " " + rs5.getString(9) + " " + rs5.getString(10) + " " + rs5.getInt(11) + " " + rs5.getDate(12) + " " + rs5.getBoolean(13));
    	}
    	else {
    		System.out.println("No patients in the hospitals");
    	}
    	
    }
    
    public void deletePatient(int id) throws SQLException {
    	String sql6 = "delete from patients where id = ? ";
    	PreparedStatement ps6 = con.prepareStatement(sql6);
    	ps6.setInt(1,id);
    	int res6 = ps6.executeUpdate();
    	if(res6==0) {
    		System.out.println("No pateint found with this id");
    	}
    	else {
    		System.out.println("Patient deleted succesfully");
    	}
    }
    
    public void updateDetails(String attributeName,String newValue,int id) throws SQLException {
    	String sql7 = "update patients set " + attributeName + " = ? where id = ?";
    	PreparedStatement ps7 = con.prepareStatement(sql7);
    	
    	if(attributeName.equals("name") || attributeName.equals("gender") || attributeName.equals("aadhar") || attributeName.equals("city") || attributeName.equals("address") || attributeName.equals("guardian_name") || attributeName.equals("guardian_address")) {
    		ps7.setString(1,newValue);
    		ps7.setInt(2,id);
    	}
    	else if(attributeName.equals("age")) {
    		int a = Integer.parseInt(newValue);
    		ps7.setInt(1,a);
    		ps7.setInt(2,id);
    	}
    	else if (attributeName.equals("contact_number") || attributeName.equals("guardian_contact")) {
    	    long newContactNumber = Long.parseLong(newValue);
    	    ps7.setLong(1, newContactNumber);
    	    ps7.setInt(2, id);
    	}

    	
    	int res7 = ps7.executeUpdate();
    	
    	if(res7==0) {
    		System.out.println("No patient found with this id");
    	}
    	else {
    		System.out.println("update succesful");
    	}
    }
    
    public void markAsRecovered(int id) throws SQLException {
    	String sql8 = "update patients set recovered = true where id = ? ";
    	PreparedStatement ps8 = con.prepareStatement(sql8);
    	ps8.setInt(1,id);
    	int res8 = ps8.executeUpdate();
    	if(res8 == 0) {
    		System.out.println("No patient found");
    	}
    	else {
    		System.out.println("Patients marked as recovered");
    	}
    }
    
    public void getPatientByName(String name) throws SQLException {
    	String sql9 = "select * from patients where lower(name) = lower(?) or upper(name) = upper(?) ";
    	PreparedStatement ps9 = con.prepareStatement(sql9);
    	ps9.setString(1,name);
    	ps9.setString(2,name);
    	ResultSet rs9 = ps9.executeQuery();
    	if(rs9.next()) {
    		System.out.println(rs9.getInt(1) + " " + rs9.getString(2) + " " + rs9.getInt(3) + " " + rs9.getString(4) + " " + rs9.getString(5) + " " + rs9.getInt(6) + " " + rs9.getString(7) + " " + rs9.getString(8) + " " + rs9.getString(9) + " " + rs9.getString(10) + " " + rs9.getInt(11) + " " + rs9.getDate(12) + " " + rs9.getBoolean(13));
    	}
    	else {
    		System.out.println("No patient found");
    	}

    	
    }
    
    
    public void listOfRecoveredOrNotRecovered(boolean recovered) throws SQLException {
    	String sql10 = "select * from patients where recovered = ?";
    	PreparedStatement ps10 = con.prepareStatement(sql10); 
    	ps10.setBoolean(1, recovered);
    	ResultSet rs10 = ps10.executeQuery();
    	if(rs10.next()) {
    		System.out.println(rs10.getInt(1) + " " + rs10.getString(2) + " " + rs10.getInt(3) + " " + rs10.getString(4) + " " + rs10.getString(5) + " " + rs10.getInt(6) + " " + rs10.getString(7) + " " + rs10.getString(8) + " " + rs10.getString(9) + " " + rs10.getString(10) + " " + rs10.getInt(11) + " " + rs10.getDate(12) + " " + rs10.getBoolean(13));
    	}
    	else {
    		System.out.println("No patient found");
    	}
	
    }
    
    //Age validation 
    
    public boolean checkAge(int age) {
    	try {
    		if(age > 0 && age <= 150) {
    			return true;
    		}
    		else {
    			throw new InvalidAgeException("The given Age is Invalid");
    		}
    	}catch(InvalidAgeException e) {
    		System.out.println(e.getMessage());
    		return false;
    	}
    }

    //contact Number validation 
    
    public boolean checkContactNo(long contactNo) {
    	String contactString = String.valueOf(contactNo);
    	try {
    		if(contactString.length() == 10) {
    			return true;
    		}
    		else {
    			throw new InvalidContactNoException("The given contact No is Invalid");
    		}
    	}catch(InvalidContactNoException e) {
    		System.out.println(e.getMessage());
    		return false;
    	}
    }
    
    // AadharNumber validation
    
    public boolean checkAadharNo(String aadharNo) {
    	try {
    		if(aadharNo.length() == 12) {
    			return true;
    		}
    		else {
    			throw new InvalidAadharException("The given aadhar no is Invalid");
    		}
    	}catch(InvalidAadharException e) {
    		System.out.println(e.getMessage());
    		return false;
    	}
    }
    
}

class InvalidAgeException extends Exception {
	InvalidAgeException(String emsg) {
		super(emsg);
	}
}

class InvalidContactNoException extends Exception {
	InvalidContactNoException(String emsg) {
		super(emsg);
	}
}

class InvalidAadharException extends Exception {
	InvalidAadharException(String emsg) {
		super(emsg);
	}
}



public class Project {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitals", "root", "anudeep123@");

            Hospital hos = new Hospital(con);

            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("1. Add a new patient ");
                System.out.println("2. Display patient details by id");
                System.out.println("3. Display list of patients by city");
                System.out.println("4. List of all patients in the hospital");
                System.out.println("5. Display list of pateints by age group");
                System.out.println("6. Delete the patient by id");
                System.out.println("7. update patient by id");
                System.out.println("8. Mark the patient as recoverd");
                System.out.println("9. Search patient by name");
                System.out.println("10. Display list of Recovered or Not recovered Pateints");
                System.out.println("11. Exit the application");
                System.out.println("Enter the choice ");
                int ch = sc.nextInt();

                switch (ch) {
                    case 1:
                    	sc.nextLine();
                    	System.out.println("Enter the name ");
                    	String name = sc.nextLine();
                    	System.out.println("Enter the age ");
                    	int age = sc.nextInt();
                    	sc.nextLine();
                    	while(!hos.checkAge(age)) {
                    		System.out.println("Re Enter the age");
                    		age=sc.nextInt();
                    	}
                    	sc.nextLine(); 
                    	System.out.println("Enter the gender ");
                    	String gender = sc.nextLine();
                    	System.out.println("Enter the Aadhar Card Number ");
                    	String aadharCardNo = sc.nextLine();
                    	while(! hos.checkAadharNo(aadharCardNo)) {
                    		System.out.println("Re enter the aadhar number");
                    		aadharCardNo = sc.nextLine();
                    	}
                    	System.out.println("Enter the contact number ");
                    	long contactNumber = sc.nextLong();
                    	sc.nextLine();
                    	while(! hos.checkContactNo(contactNumber)) {
                    		System.out.println("Enter the Contact no again");
                    		contactNumber = sc.nextLong();
                    	}
                    	sc.nextLine(); 
                    	System.out.println("Enter the city ");
                    	String city = sc.nextLine();
                    	System.out.println("Enter the address ");
                    	String address = sc.nextLine();
                    	System.out.println("Enter the guardian's name ");
                    	String guardiansName = sc.nextLine();
                    	System.out.println("Enter the guardian's address ");
                    	String guardiansAddress = sc.nextLine();
                    	System.out.println("Enter the guardian's contact number ");
                    	long guardiansContactNumber = sc.nextLong();
                    	sc.nextLine(); 
                    	System.out.println("Enter the date of admission ");
                    	String dateOfAdmission = sc.nextLine();

                        
                        hos.addPatient(name, age, gender, aadharCardNo, contactNumber, city, address, guardiansName, guardiansAddress, guardiansContactNumber, dateOfAdmission);
                        break;
                        
                    case 2: 
                        System.out.println("Enter the id");
                        int id2 = sc.nextInt();
                        hos.getPatientById(id2);
                        break;
                        
                    case 3 :
                    	
                    	System.out.println("Enter the city name");
                    	sc.nextLine();
                    	String city1 = sc.nextLine();
                    	hos.getPatientsByCity(city1);
                    	break;
                    	
                    case 4 :
                    	hos.listOfAllPatients();
                    	break;
                    	
                    case 5 :
                    	System.out.println("Enter the min Age");
                    	int minAge = sc.nextInt();
                    	System.out.println("Enter the max Age");
                    	int maxAge = sc.nextInt();
                    	
                    	hos.getPateintsByAgeGroup(minAge, maxAge);
                    	break;
                    	
                    case 6 : 
                    	System.out.println("Enter the patient id");
                    	int id6 = sc.nextInt();
                    	hos.deletePatient(id6);
                    	break;
                    	
                    case 7 :
                    	System.out.println("Enter the attribute name ");
                    	sc.nextLine();
                    	String attributeName= sc.nextLine();
                        System.out.println("Enter the new value");
                        String newValue = sc.nextLine();
                      	System.out.println("Enter the id");
                    	int id7 = sc.nextInt();
                    	hos.updateDetails(attributeName,newValue, id7);
                    	break;
                    	
                    case 8 :
                    	System.out.println("Enter the patient id");
                    	int id8 = sc.nextInt();
                    	hos.markAsRecovered(id8);
                    	break;
                    	
                    case 9 :
                    	
                    	System.out.println("Enter the name");
                    	sc.nextLine();
                    	String name9 = sc.nextLine();
                    	hos.getPatientByName(name9);
                    	break;
                    	
                    case 10 :
                    	System.out.println("Enter true for Recovered list and false for Not Recovered list");
                    	System.out.println("Enter the status ");
                    	boolean status = sc.nextBoolean();
                    	hos.listOfRecoveredOrNotRecovered(status);
                    	break;
                    	
                    case 11 :
                    	System.out.println("Exiting the application");
                    	con.close();
                    	System.exit(0);
                    	
                    
                    default:
                        System.out.println("Invalid option");
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
