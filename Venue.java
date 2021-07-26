package com.meeting;
import com.database.*;

import java.sql.*;
import java.util.*;

public class Venue {

  private String id;
  private String name;
  private String floor;
  private String capacity;
  private String admin;
  
  public Venue() {
	  
  }
  
  public Venue(String id, String name, String floor, String capacity, String admin) {
	 this.id = id;
     this.name = name;	
     this.floor = floor;
     this.capacity = capacity;
     this.admin = admin;	 
  }
  
  public String getId() {
	 return id;
  }
  
  public String getName() {
	return name;
  }
  
  public String getFloor() {
	return floor;
  }
  
  public String getCapacity() {
	return capacity;
  }
  
  public String getAdmin() {
	return admin;
  }
  
  public static String getVenueId(String name) {
	Connection connection = null;
	String  id = null;
	try {
    connection = PostgresDBConnection.connect();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("select id from venue where active = 1 AND name = '" + name + "'");
    if (resultSet.next()) {
		  id = resultSet.getString("id");
    }
    } catch(SQLException ex) {
      ex.printStackTrace();
    }
    finally {
	  try {
		if (connection != null) {
			connection.close();
		}
	  } catch (SQLException ex2) {
		  ex2.printStackTrace();
	  }
	}	
	  return id;  
  }
  
  public static Venue getVenue(String venueId) {
	Connection connection = null;
	Venue  venue = null;
	try {
    connection = PostgresDBConnection.connect();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("select * from venue where active = 1 AND id = '" + venueId + "'");
    if (resultSet.next()) {
		venue = new Venue (
		resultSet.getString("id"),
        resultSet.getString("name"),
		resultSet.getString("floor"),
        resultSet.getString("capacity"), 
        resultSet.getString("admin")		
		);
    }
    } catch(SQLException ex) {
      ex.printStackTrace();
    }
    finally {
	  try {
		if (connection != null) {
			connection.close();
		}
	  } catch (SQLException ex2) {
		  ex2.printStackTrace();
	  }
	}	
	  return venue;  
  }
  
  public static java.util.List<Venue> getVenueList() {
	java.util.List<Venue> venueList = new ArrayList<Venue>();
	Connection connection = null;
	try {
    connection = PostgresDBConnection.connect();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("select * from venue WHERE active = 1 AND ORDER BY venue ASC");
	Venue venue;
    while (resultSet.next()) {
		venue = new Venue(
		resultSet.getString("id"),
		resultSet.getString("name"),
		resultSet.getString("floor"),
        resultSet.getString("capacity"), 
        resultSet.getString("admin")	
		);
	  venueList.add(venue);	
    }

    } catch(SQLException ex) {
      ex.printStackTrace();
    }
    finally {
	  try {
		if (connection != null) {
			connection.close();
		}
	  } catch (SQLException ex2) {
		  ex2.printStackTrace();
	  }
	}		
	  return venueList;  
  }

}

