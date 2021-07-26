package com.meeting;
import com.database.*;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;


public class Booking {

  private String id;
  private String date;
  private String room;
  private String hour;
  private String day;
  private String person;
  private String reason;
  
  public Booking() {
	  
  }
  
  public Booking(String id, String date, String room, String hour, String day, String person, String reason) {
	 this.id = id;
     this.date = date;	
     this.room = room;
     this.hour = hour;
     this.day = day;
     this.person = person;	
     this.reason = reason;	 
  }
  
  public String getId() {
	 return id;
  }
  
  public String getDate() {
	return date;
  }
  
  public String getRoom() {
	return room;
  }
  
  public String getHour() {
	return hour;
  }
  
  public String getDay() {
	return day;
  } 
  
  public String getPerson() {
	return person;
  }
  public String getReason() {
	return reason;
  }
  
  public static java.util.List<Booking> getBookingList(String roomId) {
	java.util.List<Booking> bookingList = new ArrayList<Booking>();
	Connection connection = null;
	try {
    connection = PostgresDBConnection.connect();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("select booking.id, date, venue.name, hour, day, person, reason from venue INNER JOIN booking ON venue.id = booking.venue_id INNER JOIN hour ON booking.hour_id = hour.id INNER JOIN day ON hour.day_id = day.id WHERE venue.id = '"+roomId+"' ORDER BY date, day, hour ASC");
	Booking booking;
    while (resultSet.next()) {
		booking = new Booking(
		resultSet.getString("id"),
		resultSet.getString("date"),
		resultSet.getString("name"),
        resultSet.getString("hour"), 
        resultSet.getString("day"),
		resultSet.getString("person"), 
        resultSet.getString("reason")
		);
	  bookingList.add(booking);	
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
	  return bookingList;  
  }
  
  public static String getCurrentDate() {
	    SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
        Date now = new Date();
        sdf.format(now);
		return now.toString().substring(0, 10);
  }
  
  @SuppressWarnings("finally")
public static String getNextDate(String curDate, int increment) {
    String nextDate="";
	new SimpleDateFormat("dd-MMM-yyyy");
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
    try {
        LocalDate date = LocalDate.now();
        date = date.plusDays(increment);
		//nextDate = df.format(date);
        //nextDate = date.toString();
		nextDate = date.format(formatter);
    } finally {
        return nextDate;
    }
}


    public static java.util.List<String> getBooking(String venueId, String date, String slot) {
	Connection connection = null;
	java.util.List<String> temp = new ArrayList<String>();
	try {
    connection = PostgresDBConnection.connect();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("select reason, person from booking where date = '" + date + "' AND venue_id = '"+venueId+"' AND timeslot_id = '"+slot+"'");
    if (resultSet.next()) {
		temp.add(resultSet.getString("person"));
		temp.add(resultSet.getString("reason"));
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
	  return temp;  
  }


   public static String[] getBooking2(String venueId, String date) {
	   
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	new ArrayList<String>();
	new ArrayList<Integer>();
	String[] myArray = new String[10];
	try {
    connection = PostgresDBConnection.connect();
    statement = connection.createStatement();
    String query = "select reason, person, timeslot.id from booking INNER JOIN timeslot ON booking.timeslot_id = timeslot.id where date = '" + date + "' AND venue_id = '"+venueId+"' AND booking.active = 1";
    resultSet = statement.executeQuery(query);
    while (resultSet.next()) {
		String a = resultSet.getString("person") + " - " + resultSet.getString("reason");
		int b = Integer.parseInt(resultSet.getString("id"));
		myArray[b] = a;
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
	  return myArray;  
  }



   public static java.util.List<String> getTimeSlots() {
	Connection connection = null;
	java.util.List<String> temp = new ArrayList<String>();
	try {
    connection = PostgresDBConnection.connect();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("select timeslot from timeslot");
    while (resultSet.next()) {
		temp.add(resultSet.getString("timeslot"));
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
	  return temp;  
  }


    public static java.util.List<String> getRooms() {
	Connection connection = null;
	java.util.List<String> temp = new ArrayList<String>();
	try {
    connection = PostgresDBConnection.connect();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("select name from venue WHERE active =1 ORDER BY name ASC");
    while (resultSet.next()) {
		temp.add(resultSet.getString("name"));
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
	  return temp;  
  }

    public static String getSlotId(String timeslot) {
	Connection connection = null;
	String  id = null;
	try {
    connection = PostgresDBConnection.connect();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("select id from timeslot where timeslot = '" + timeslot + "'");
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

   public static String getRoomId(String room) {
	Connection connection = null;
	String  id = null;
	try {
    connection = PostgresDBConnection.connect();
    Statement statement = connection.createStatement();
    ResultSet resultSet = statement.executeQuery("select id from venue WHERE active =1 AND name = '" + room + "'");
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


   public static int setBooking(String name, String date, String room, String timeslot, String reason, String emailAddress, String link, String msgSubject, String msgHeader, 
		   String msgBody, String msgBodyEnd, String token) throws Exception {
    int status = 0;
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date utilDate = sdf1.parse(date);
    java.sql.Date sqlStartDate = new java.sql.Date(utilDate.getTime()); 

	long millis = System.currentTimeMillis();
	
    Connection connection = PostgresDBConnection.connect();
    PreparedStatement preparedStatementUser = null;
    Statement statement = null;
	ResultSet resultSet = null;
	String insertBooking = "INSERT INTO booking" 
	+ "(id, date, person, reason, timeslot_id, venue_id, email, token) VALUES"
	+ "(?,?,?,?,?,?,?,?)";
	
	try {
		connection.setAutoCommit(false);
        preparedStatementUser = connection.prepareStatement(insertBooking);
	    preparedStatementUser.setLong(1, millis);
	    preparedStatementUser.setDate(2, sqlStartDate);
		preparedStatementUser.setString(3, name);
		preparedStatementUser.setString(4, reason);
		preparedStatementUser.setInt(5, Integer.parseInt(Booking.getSlotId(timeslot)));
        preparedStatementUser.setInt(6, Integer.parseInt(Booking.getRoomId(room)));
        preparedStatementUser.setString(7, emailAddress);
        preparedStatementUser.setString(8, token);
		int checkUser = preparedStatementUser.executeUpdate();

		connection.commit();
		
		if (checkUser > 0) {
		  status = 1;
		} else {
		  status = 0;
		}
      } catch(SQLException ex) {
           ex.printStackTrace();
	       
		   try {
		   connection.rollback();
		   } catch (SQLException s) {
			   s.printStackTrace();
		   }
	} finally {
		try {
		
		if (preparedStatementUser != null) {
			preparedStatementUser.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (resultSet != null) {
			resultSet.close();
		}
		if (connection != null) {
			connection.close();
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}	
	   return status;	
	}  
   
   public static int setOnlineBooking(String name, String date, String room, String timeslot, String reason, String emailAddress, String secretaryName, String secretaryEmail, String link, String msgSubject, String msgHeader, 
		   String msgBody, String msgBodyEnd, String token) throws Exception {
    int status = 0;
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    java.util.Date utilDate = sdf1.parse(date);
    java.sql.Date sqlStartDate = new java.sql.Date(utilDate.getTime()); 

	long millis = System.currentTimeMillis();
	
    Connection connection = PostgresDBConnection.connect();
    PreparedStatement preparedStatementUser = null;
    Statement statement = null;
	ResultSet resultSet = null;
	String insertBooking = "INSERT INTO onlinebooking" 
	+ "(id, date, person, reason, timeslot_id, venue, email, token, secretary_name, secretary_email,active) VALUES"
	+ "(?,?,?,?,?,?,?,?,?,?,?)";
	
	try {
		connection.setAutoCommit(false);
        preparedStatementUser = connection.prepareStatement(insertBooking);
	    preparedStatementUser.setLong(1, millis);
	    preparedStatementUser.setDate(2, sqlStartDate);
		preparedStatementUser.setString(3, name);
		preparedStatementUser.setString(4, reason);
		preparedStatementUser.setInt(5, Integer.parseInt(Booking.getSlotId(timeslot)));
        preparedStatementUser.setString(6, room);
        preparedStatementUser.setString(7, emailAddress);
        preparedStatementUser.setString(8, token);
        preparedStatementUser.setString(9, secretaryName);
        preparedStatementUser.setString(10, secretaryEmail);
        preparedStatementUser.setInt(11, 0);
		int checkUser = preparedStatementUser.executeUpdate();

		connection.commit();
		
		if (checkUser > 0) {
		  status = 1;
		} else {
		  status = 0;
		}
      } catch(SQLException ex) {
           ex.printStackTrace();
	       
		   try {
		   connection.rollback();
		   } catch (SQLException s) {
			   s.printStackTrace();
		   }
	} finally {
		try {
		
		if (preparedStatementUser != null) {
			preparedStatementUser.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (resultSet != null) {
			resultSet.close();
		}
		if (connection != null) {
			connection.close();
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}	
	   return status;	
	}  
   
	public static int cancelBooking(String email, String token, String cancelReason, String  dates, String timeslot, String room) throws ParseException {
		int status = 0;
		Connection connection = null;
		
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	    java.util.Date utilDate = sdf1.parse(dates);
	    java.sql.Date sqlStartDate = new java.sql.Date(utilDate.getTime()); 

	    connection = PostgresDBConnection.connect();
	    PreparedStatement preparedStatement = null;
	    PreparedStatement preparedStatementComment = null;

	    String query = "UPDATE booking SET active = ? WHERE email = ? AND token = ? AND date = ? AND timeslot_id = ? AND venue_id = ?";
	    String qryComment = "INSERT INTO tblcomments (description,token) VALUES (?,?);";

	    try {
	    	connection.setAutoCommit(false);
	    	preparedStatement= connection.prepareStatement(query);
	    	preparedStatement.setInt(1, checker(sqlStartDate,Booking.getRoomId(room),Booking.getSlotId(timeslot)));
	    	preparedStatement.setString(2, email);
	    	preparedStatement.setString(3, token);
	    	preparedStatement.setDate(4, sqlStartDate);
	    	preparedStatement.setInt(5, Integer.parseInt(Booking.getSlotId(timeslot)));
	        preparedStatement.setInt(6, Integer.parseInt(Booking.getRoomId(room)));
	    	int checkRole = preparedStatement.executeUpdate();
	    	
	    	preparedStatementComment = connection.prepareStatement(qryComment);
	    	preparedStatementComment.setString(1,cancelReason);
	    	preparedStatementComment.setString(2, token);
	    	int checkComment = preparedStatementComment.executeUpdate();
	    	
	    	connection.commit();
			
			if (checkRole > 0  && checkComment > 0) {
			  status = 1;
			} else {
			  status = 0;
			}
	    	
	    } catch(SQLException ex) {
	    	ex.printStackTrace();
	    	//ErrorLogsAppender.appendSQLException("Channels class - updateChannel()",ex, errorLogs);
	    }
	    finally {
		  try {
			if (connection != null) {
				connection.close();
			}
			if(preparedStatement != null) {
				preparedStatement.close();
			}
		  } catch (SQLException ex) {
			  ex.printStackTrace();
			  //ErrorLogsAppender.appendSQLException("Channels class - updateChannel()",ex, errorLogs);
		  }
		}		
		  return status;
		}
	
	public static int checker(Date date,  String venue_id, String timestlot) {
		int cn = -1;
		
		Connection connection = null;
	    Statement statement = null;

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
