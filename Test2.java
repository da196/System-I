package com.meeting;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.database.PostgresDBConnection;


public class Test2 {

	public static void main(String[] args) {
		int cn = checker();
		System.out.println(cn);
	}
	public static int checker() {
		int cn = -1;
		
		Connection connection = null;
	    Statement statement = null;
	    String date ="12/10/2020";
	    String venue_id = "1";
	    String timestlot = "3";
	   
	    try {
		    connection = PostgresDBConnection.connect();
		    statement = connection.createStatement();
		    String sql = "SELECT COUNT(id) FROM booking WHERE date='" + date + "' AND timeslot_id="+Long.parseLong(timestlot)+ " and venue_id =" + Long.parseLong(venue_id);
		   
		    ResultSet resultSet = statement.executeQuery(sql);
		    if (resultSet.next()) {
			    cn = Integer.parseInt(resultSet.getString(1)) + 1;
			    
		    }
		} catch (SQLException ex) {
			//ErrorLogsAppender.appendSQLException("FileChecker class - checker()",ex, errorLogs);
		    ex.printStackTrace();
		} finally {
		  try {
		  if (connection != null) {
			connection.close();
		  }
		  } catch (SQLException ex) {
			 // ErrorLogsAppender.appendSQLException("FileChecker class - checker()",ex, errorLogs);
			  ex.printStackTrace();
		  }	
		  try {
		  if (statement != null) {
			statement.close();
		  }
		  } catch (SQLException ex) {
			 // ErrorLogsAppender.appendSQLException("FileChecker class - checker()",ex, errorLogs);
			  ex.printStackTrace();
		  }	
		}
	    
	   return cn;
	}
}
