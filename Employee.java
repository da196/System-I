package com.meeting;

import java.sql.*;
import java.util.ArrayList;
import com.database.*;


public class Employee {
	
	private String id;
	private String fullName;
	private String emailAddress;
	
	public Employee(String id, String fullName, String emailAddress) {
		this.id = id;
		this.fullName = fullName;
		this.emailAddress = emailAddress;		
	}

	public String getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}
	
	 public static java.util.List<Employee> getEmployeeList() {
			java.util.List<Employee> list = new ArrayList<Employee>();
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			String query = "SELECT * FROM EMPLOYEE "
					+ "WHERE first_name NOT IN ('ARUSHA', 'BOARDLOUNGE','DIALLINGDIRECTFROM', 'DODOMA', 'INFORMATION', 'MBEYA' , 'MWZ', 'RECEPTION', 'SACCOSOFFICE', 'ZNZ') "
					+ "ORDER BY first_name ASC;";
			try {
		    connection = MySQLDBConnection.connect();
		    statement = connection.createStatement();
		    resultSet = statement.executeQuery(query);
		    Employee Employee;
		    while (resultSet.next()) {
		    	Employee = new Employee(
		    			resultSet.getString("id").toUpperCase(),
						resultSet.getString("first_name").toUpperCase() + " " + resultSet.getString("middle_name").toUpperCase() + " " + resultSet.getString("last_name").toUpperCase(),
						resultSet.getString("email_address")
				);
			  list.add(Employee);	
		    }

		    } catch(SQLException ex) {
		      ex.printStackTrace();
		    }
		    finally {
			  try {
				  if (statement != null) {
					  statement.close();
					}
				  if (resultSet != null) {
					  resultSet.close();
					}
				  if (connection != null) {
					  connection.close();
					}
			  } catch (SQLException ex2) {
				  ex2.printStackTrace();
			  }
			}		
			  return list;  
		  }

	 
	 public static java.util.List<Employee> getSecretaryList() {
			java.util.List<Employee> list = new ArrayList<Employee>();
			Connection connection = null;
			Statement statement = null;
			ResultSet resultSet = null;
			String query = "SELECT id,secretary_name,email FROM secretary";
			try {
			connection = PostgresDBConnection.connect();
		    statement = connection.createStatement();
		    resultSet = statement.executeQuery(query);
		    Employee Employee;
		    while (resultSet.next()) {
		    	Employee = new Employee(
		    			resultSet.getString("id").toUpperCase(),
						resultSet.getString("secretary_name").toUpperCase(),
						resultSet.getString("email")
				);
			  list.add(Employee);	
		    }

		    } catch(SQLException ex) {
		      ex.printStackTrace();
		    }
		    finally {
			  try {
				  if (statement != null) {
					  statement.close();
					}
				  if (resultSet != null) {
					  resultSet.close();
					}
				  if (connection != null) {
					  connection.close();
					}
			  } catch (SQLException ex2) {
				  ex2.printStackTrace();
			  }
			}		
			  return list;  
		  }

}
