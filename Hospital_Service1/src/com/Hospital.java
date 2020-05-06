package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Hospital {
	// A common method to connect to the DB
	//Dbconnection db = new Dbconnection();
	
	private Connection connect()
	{
		Connection con = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");

			//Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/hospitals?useTimezone=true&serverTimezone=UTC",
					"root", "");
			System.out.println("Connect succefull");
			
		}
		catch (Exception e)
		{e.printStackTrace();}
		return con;
	} 

	
	
	public String insertHospital(String hosName, String hosTelephone, String hosEmail, String hosAddress) {

		String output = "";
		output = "Database Insert";

		try {

			Connection con = connect();
			System.out.println("Connect Db");

			if (con == null) {
				System.out.println("Connect Db2");

				return "Error while connecting to the database for inserting.";

			}
			// create a prepared statement
			String query = " insert into `hospitals`(`hosName`,`hosTelephone`,`hosEmail`,`hosAddress`)"
					+ " values (?, ?, ?, ?)";
			System.out.println("quary read");
			PreparedStatement preparedStmt = con.prepareStatement(query);
			System.out.println(preparedStmt);

			preparedStmt.setString(1, hosName);
			preparedStmt.setString(2, hosTelephone);
			preparedStmt.setString(3, hosEmail);
			preparedStmt.setString(4, hosAddress);
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
			System.out.println("Inserted successfully.......................................");
			
			
			String newhospitals = viewHospital();
			output = "{\"status\":\"success\", \"data\": \"" + newhospitals + "\"}";
			
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error  inserting the Hospital details.\"}";
			System.out.println("Error while inserting the Hospitals........." + e);
			System.err.println(e.getMessage());
		}
		return output;
	}
	//

	public String viewHospital() {

		String output = "";

		try {
			

			Connection con= connect();

			if (con == null) {

				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed

			output = "<table border='1'><tr><th>Hospital Name</th> <th>Telephone</th><th>Email</th>"+ "<th>Address</th><th>Update</th><th>Remove</th></tr>";

			
			String query = "SELECT * FROM `hospitals`";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String hosId = Integer.toString(rs.getInt("hosId"));
				String hosName = rs.getString("hosName");
				String hosTelephone = rs.getString("hosTelephone");
				String hosEmail = rs.getString("hosEmail");
				String hosAddress = rs.getString("hosAddress");

				// Add into the html table
				output += "<tr><td><input id='hidHospitalIDUpdate'name='hidHospitalIDUpdate' type='hidden'value='" + hosId + "'>" + hosName + "</td>";output += "<td>" + hosTelephone + "</td>";output += "<td>" + hosEmail + "</td>";output += "<td>" + hosAddress + "</td>";

				// buttons
				output += "<td><input name='btnUpdate' type='button'"+ "value='Update'"+"class='btnUpdate btn btn-secondary'></td>"+"<td><input name='btnRemove' type='button'"+" value='Remove'"+"class='btnRemove btn btn-danger' data-hosid='"+ hosId + "'>" + "</td></tr>";

				
			}
			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error reading Hospitals.";
			System.err.println(e.getMessage());
		}
		return output;

	}

	// update Hospital
	public String updateHospital(String hosId, String hosName, String hosTelephone, String hosEmail,
			String hosAddress) {

		String output = "";
		try {
			Connection con = connect();

			if (con == null)

			{
				return "Error while connecting to the database for updating.";

			}

			// create a prepared statement
			String query = "UPDATE `hospitals` SET `hosName`=?,`hosTelephone`=?,`hosEmail`=?,`hosAddress`=? WHERE `hosId`=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, hosName);
			preparedStmt.setString(2, hosTelephone);
			preparedStmt.setString(3, hosEmail);
			preparedStmt.setString(4, hosAddress);
			preparedStmt.setInt(5, Integer.parseInt(hosId));

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newhospitals = viewHospital();
			output = "{\"status\":\"success\", \"data\": \"" + newhospitals + "\"}";		}

		catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error  Update the Hospital details.\"}";
			System.err.println(e.getMessage());
		}

		return output;

	}

	// deleting hospital
	public String deletaHospitals(String hosId) {

		String output = "";
		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from hospitals where hosId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(hosId));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
			
			String newhospitals = viewHospital();
			output = "{\"status\":\"success\", \"data\": \"" + newhospitals + "\"}";
		}

		catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error  delete the Hospital details.\"}";
			System.err.println(e.getMessage());
		}

		return output;

	}

}