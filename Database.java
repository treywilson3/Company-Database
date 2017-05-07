import java.math.BigDecimal;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.plaf.synth.SynthSeparatorUI;
/**
 * @author Trey, Farris, Casey
 * class Database has all static methods that can be called
 * All of the functions will staticly call Database's methods. 
 * Result set is used in any method that returns data. Result set
 * is just the data that the queries get returned to them. So you can print
 * that data onto the console using the result set. Prepared Statements
 * and Statements are also used in nearly every method, which communicates
 * with the database. 
 */
public class Database {
	/**
	 * Create a connection to mysql database
	 * @return connection
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static java.sql.Connection createConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		final String DB_URL = "jdbc:mysql://webdev.cislabs.uncw.edu/narayan3?noAccessToProcedureBodies=true";
		final String USER = "gdw1833";
		final String PASS = "I3pn62Acv";

		return DriverManager.getConnection(DB_URL, USER, PASS);
	}
	/**
	 * Need to close connection after use so call this method
	 * when needed
	 * @param conn - connection to database
	 */
	public static void close(java.sql.Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Need to close statements after use so call this method
	 * @param st - query statement
	 */
	public static void close(java.sql.Statement st) {
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Need to close prepared statements after use so call this method
	 * @param pst - the prepared statement
	 */
	public static void close(PreparedStatement pst) {
		try {
			if (pst != null) {
				pst.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * To print information, you need the result set. 
	 * That result set needs to be closed after use so call this method
	 * @param rs - the result set
	 */
	public static void close(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * When using transactions, you need to rollback if the transaction
	 * was not successful. Call this class if needed
	 * @param conn - the connection to the database
	 */
	public static void rollback(java.sql.Connection conn) {
		try {
			if (conn != null) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Prints all member information when called
	 * @param memberEmail - the members email address
	 */
	public static void getMemberInformation(String memberEmail) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement("select * from Members where EMAIL = ?");
			pStmt.setString(1, memberEmail);
			ResultSet rs2 = pStmt.executeQuery();
			while (rs2.next()) {
				// Retrieve by column name
				int id = rs2.getInt("MEMBER_ID");
				String email = rs2.getString("EMAIL");
				String password = rs2.getString("PASSWORD");
				String first = rs2.getString("FNAME");
				String last = rs2.getString("LNAME");
				String phone = rs2.getString("PHONE");
				String address = rs2.getString("ADDRESS");
				String city = rs2.getString("CITY");
				String state = rs2.getString("STATE");
				String zip = rs2.getString("ZIP");

				// Display values
				System.out.println("Member_ID: " + id);
				System.out.println("Email: " + email);
				System.out.println("Password " + password);
				System.out.println("First Name: " + first);
				System.out.println("Last Name: " + last);
				System.out.println("Phone: " + phone);
				System.out.println("Address: " + address);
				System.out.println("City: " + city);
				System.out.println("State: " + state);
				System.out.println("Zip: " + zip);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try
	}
	/**
	 * Prints the members to watch list 
	 * @param memberEmail - the members email address
	 */
	public static void toWatch(String memberEmail) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement(
					"select TITLE from Movies natural join ToWatch where MEMBER_ID in (select MEMBER_ID from Members where EMAIL = ?)");
			pStmt.setString(1, memberEmail);
			ResultSet rs2 = pStmt.executeQuery();
			if (rs2.next()) {
				do {
					String title = rs2.getString("TITLE");
					System.out.println("Title: " + title);
				} while (rs2.next());
			} else {
				System.out.println("You don't have anything in 'To Watch'");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try

	}
	/**
	 * Prints the members watch history list
	 * @param memberEmail - the members email address
	 */
	public static void watchHistory(String memberEmail) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement(
					"select TITLE, DATE from Movies natural join WatchHistory where MEMBER_ID in (select MEMBER_ID from Members where EMAIL = ?)");
			pStmt.setString(1, memberEmail);
			ResultSet rs2 = pStmt.executeQuery();
			if (rs2.next()) {
				do {
					String title = rs2.getString("TITLE");
					String date = rs2.getString("DATE");
					System.out.println("Title: " + title);
					System.out.println("Date: " + date);
					System.out.println("\n");
				} while (rs2.next());
			} else {
				System.out.println("You don't have anything in 'Watch History");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try

	}
	/**
	 * Prints the users favorite movies list
	 * @param memberEmail - the members email address
	 */
	public static void favoriteMovies(String memberEmail) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement(
					"select TITLE from Movies natural join FavoriteMovies where MEMBER_ID in (select MEMBER_ID from Members where EMAIL = ?)");
			pStmt.setString(1, memberEmail);
			ResultSet rs2 = pStmt.executeQuery();
			if (rs2.next()) {
				do {
					String title = rs2.getString("TITLE");
					System.out.println("Title: " + title);	
				} while (rs2.next());
			} else {
				System.out.println("You don't have anything in 'Favorite Movies'");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try

	}
	/**
	 * Updates the members phone number
	 * @param memberEmail - the members email address
	 * @param phoneNumber - the new phone number the member wants
	 */
	public static void updateMemberPhone(String memberEmail, String phoneNumber) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();
			int memberID = 0;

			pStmt = conn.prepareStatement("select MEMBER_ID from Members where EMAIL = ?");
			pStmt.setString(1, memberEmail);
			ResultSet rs2 = pStmt.executeQuery();
			while (rs2.next()) {
				memberID = rs2.getInt("MEMBER_ID");
			}
			pStmt = conn.prepareStatement(
					"update Members set PHONE = ? where MEMBER_ID = ?");
			pStmt.setString(1, phoneNumber);
			pStmt.setInt(2, memberID);
			pStmt.executeUpdate();
			System.out.println("Here is your new information: ");
			getMemberInformation(memberEmail);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try

	}
	/**
	 * Updates the members address. If any part fails, then 
	 * all info is rolled back. Transaction
	 * @param memberEmail - the members email address
	 * @param address - new address
	 * @param city - new city
	 * @param state - new state
	 * @param zip - new zipcode
	 */
	public static void updateMemberAddress(String memberEmail, String address, String city, String state, String zip) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();
			conn.setAutoCommit(false);
			int memberID = 0;

			pStmt = conn.prepareStatement("select MEMBER_ID from Members where EMAIL = ?");
			pStmt.setString(1, memberEmail);
			ResultSet rs2 = pStmt.executeQuery();
			while (rs2.next()) {
				memberID = rs2.getInt("MEMBER_ID");
			}
			pStmt = conn.prepareStatement(
					"update Members set ADDRESS = ? where MEMBER_ID = ?");
			pStmt.setString(1, address);
			pStmt.setInt(2, memberID);
			pStmt.executeUpdate();
			pStmt = conn.prepareStatement(
					"update Members set CITY = ? where MEMBER_ID = ?");
			pStmt.setString(1, city);
			pStmt.setInt(2, memberID);
			pStmt.executeUpdate();
			pStmt = conn.prepareStatement(
					"update Members set STATE = ? where MEMBER_ID = ?");
			pStmt.setString(1, state);
			pStmt.setInt(2, memberID);
			pStmt.executeUpdate();
			pStmt = conn.prepareStatement(
					"update Members set ZIP = ? where MEMBER_ID = ?");
			pStmt.setString(1, zip);
			pStmt.setInt(2, memberID);
			pStmt.executeUpdate();
			conn.commit();
			System.out.println("Here is your new information: ");
			getMemberInformation(memberEmail);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			rollback(conn);
			System.out.println("There was an issue updating your address.");
			System.out.println("Please try again.");
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try

	}
	/**
	 * Adds a movie to the database
	 * @param movieID - given movieID
	 * @param title - given title
	 * @param rating - given rating
	 * @param genre - given genre
	 * @param releaseDate - given releaseDate
	 */
	public static void addMovie(String movieID, String title, String rating, String genre, String releaseDate) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement(
					"insert into Movies(MOVIE_ID, TITLE, RATING, GENRE, RELEASEDATE) VALUES" + "(?, ?, ?, ? , ?)");
			pStmt.setString(1, movieID);
			pStmt.setString(2, title);
			pStmt.setString(3, rating);
			pStmt.setString(4, genre);
			pStmt.setString(5, releaseDate);
			pStmt.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
		    System.out.println("Duplicate Entry");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
			System.out.println("Movie Added");
		} // end finally try
	}
	/**
	 * Deletes a movie from the database
	 * @param movieID - movie id to be deleted
	 */
	public static void deleteMovie(String movieID) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement("delete from Movies where MOVIE_ID = ?");
			pStmt.setString(1, movieID);
			pStmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
			System.out.println("Movie Deleted");
		} // end finally try
	}
	/**
	 * Prints out the employees information
	 * @param employeeEmail - employee email address
	 */
	public static void getEmployeeInformation(String employeeEmail) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement(
					"select * from EmployeesGroup where EMPLOYEE_ID in (select EMPLOYEE_ID from EmployeesGroup where EMAIL = ?)");
			pStmt.setString(1, employeeEmail);
			ResultSet rs2 = pStmt.executeQuery();
			while (rs2.next()) {
				// Retrieve by column name
				int id = rs2.getInt("EMPLOYEE_ID");
				String email = rs2.getString("EMAIL");
				String password = rs2.getString("PASSWORD");
				String hireDate = rs2.getString("HIRE_DATE");
				String first = rs2.getString("FNAME");
				String last = rs2.getString("LNAME");
				String jobLocation = rs2.getString("JOB_LOCATION");
				String position = rs2.getString("POSITION");
				String salary = rs2.getString("SALARY");

				// Display values
				System.out.println("Employee_ID: " + id);
				System.out.println("Email: " + email);
				System.out.println("Password: " + password);
				System.out.println("Hire Date: " + hireDate);
				System.out.println("First Name: " + first);
				System.out.println("Last Name: " + last);
				System.out.println("Job Location: " + jobLocation);
				System.out.println("Position: " + position);
				System.out.println("Salary: " + salary);

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try
	}
	/**
	 * Will create a new member and update the database 
	 * @param memberEmail - given member email
	 * @param password - given member password
	 * @param fname - given member first name
	 * @param lname - given member last name
	 * @param phone - given member phone
	 * @param address - given member address
	 * @param city - given member city
	 * @param state - given member state
	 * @param zip - given member zipcode
	 */
	public static void newUser(String memberEmail, String password, String fname, String lname, String phone,
			String address, String city, String state, String zip) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement(
					"insert into Members(EMAIL, PASSWORD, FNAME, LNAME, PHONE, ADDRESS, CITY, STATE, ZIP) values"
							+ "(?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pStmt.setString(1, memberEmail);
			pStmt.setString(2, password);
			pStmt.setString(3, fname);
			pStmt.setString(4, lname);
			pStmt.setString(5, phone);
			pStmt.setString(6, address);
			pStmt.setString(7, city);
			pStmt.setString(8, state);
			pStmt.setString(9, zip);
			pStmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
			System.out.println("Member Added");
		} // end finally try

	}
	/**
	 * Will delete a member from the database
	 * @param memberID - member id to be deleted
	 */
	public static void deleteMember(String memberID) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement("delete from Members where MEMBER_ID = ?");
			pStmt.setString(1, memberID);
			pStmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
			System.out.println("Member Deleted");
		} // end finally try

	}
	/**
	 * Will verify if employee is CEo
	 * @param ceoEmail - email of potential ceo
	 * @param ceoPassword - password of potention ceo
	 * @return if CEO or not 
	 */
	public static boolean ceo(String ceoEmail, String ceoPassword) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		String position;
		boolean ceo = false;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement("select POSITION from EmployeesGroup where EMAIL = ? and PASSWORD = ?");
			pStmt.setString(1, ceoEmail);
			pStmt.setString(2, ceoPassword);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				// Retrieve by column name
				position = rs.getString("POSITION");
				// System.out.println("POSITION : " + position);
				if (position.equals("CEO")) {
					ceo = true;
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try
		return ceo;
	}
	/**
	 * Adds a new employee to the database 
	 * @param email - given employee email
	 * @param password - given employee password 
	 * @param hireDate - given hire date of employee
	 * @param fname - given employee first name
	 * @param lname - given employee last name
	 * @param jobLocation - given employee job location
	 * @param position - given employee position
	 * @param salary - given employee salary
	 */
	public static void addEmployee(String email, String password, String hireDate, String fname, String lname,
			String jobLocation, String position, BigDecimal salary) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(hireDate);
			// because PreparedStatement#setDate(..) expects a java.sql.Date
			// argument
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			pStmt = conn.prepareStatement(
					"insert into EmployeesGroup(EMAIL, PASSWORD, HIRE_DATE, FNAME, LNAME, JOB_LOCATION, POSITION, SALARY) VALUES"
							+ "(?, ?, ?, ?, ? , ?, ?, ?)");
			pStmt.setString(1, email);
			pStmt.setString(2, password);
			pStmt.setDate(3, sqlDate);
			pStmt.setString(4, fname);
			pStmt.setString(5, lname);
			pStmt.setString(6, jobLocation);
			pStmt.setString(7, position);
			pStmt.setBigDecimal(8, salary);
			pStmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
			System.out.println("Employee Added");
		} // end finally try
	}
	/**
	 * Deletes given emloye
	 * @param employeeID - employee id to be deleted
	 */
	public static void deleteEmployee(String employeeID) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement("delete from EmployeesGroup where EMPLOYEE_ID = ?");
			pStmt.setString(1, employeeID);
			pStmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
			System.out.println("Employee Deleted");
		} // end finally try
	}
	/**
	 * Adds a movie to the member's to watch list
	 * @param memberEmail - the members email address
	 * @param movieToWatch - the members chosen movie to add
	 */
	public static void addToWatch(String memberEmail, String movieToWatch) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		String id = null;
		try {
			conn = createConnection();
			pStmt = conn.prepareStatement("select MOVIE_ID from Movies where TITLE = ?");
			pStmt.setString(1, movieToWatch);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				id = rs.getString("MOVIE_ID");
			}
			int memberID = 0;
			pStmt = conn.prepareStatement("select MEMBER_ID from Members where EMAIL = ?");
			pStmt.setString(1, memberEmail);
			ResultSet rs2 = pStmt.executeQuery();
			while (rs2.next()) {
				memberID = rs2.getInt("MEMBER_ID");
			}
			pStmt = conn.prepareStatement("insert into ToWatch(MOVIE_ID, MEMBER_ID) VALUES" + "(?, ?)");
			pStmt.setString(1, id);
			pStmt.setInt(2, memberID);
			pStmt.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
		    System.out.println(movieToWatch + " is already in 'To Watch'");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
			System.out.println("To Watch Movie Added");
		} // end finally try

	}
	/**
	 * Adds a movie to the members favorite movie list
	 * @param memberEmail - the members email address
	 * @param movieFav - members chosen favorite movie
	 */
	public static void addFavoriteMovie(String memberEmail, String movieFav) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		String id = null;
		String recommendation = null;
		try {
			conn = createConnection();
			pStmt = conn.prepareStatement("select MOVIE_ID from Movies where TITLE = ?");
			pStmt.setString(1, movieFav);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				id = rs.getString("MOVIE_ID");
			}
			int memberID = 0;
			pStmt = conn.prepareStatement("select MEMBER_ID from Members where EMAIL = ?");
			pStmt.setString(1, memberEmail);
			ResultSet rs2 = pStmt.executeQuery();
			while (rs2.next()) {
				memberID = rs2.getInt("MEMBER_ID");
			}
			pStmt = conn.prepareStatement("insert into FavoriteMovies(MOVIE_ID, MEMBER_ID) VALUES" + "(?, ?)");
			pStmt.setString(1, id);
			pStmt.setInt(2, memberID);
			pStmt.executeUpdate();
			pStmt = conn.prepareStatement(
					"select MOVIE_ID from (select Movies.MOVIE_ID from Movies natural join Actors as a natural join ActsIn where concat(a.fname, ' ', a.lname)  = (select concat(a.fname, ' ', a.lname) from Movies natural join FavoriteMovies natural join Actors as a natural join ActsIn where MEMBER_ID = ? and Movies.MOVIE_ID = ? limit 1) order by rand() limit 1)a");
			pStmt.setInt(1, memberID);
			pStmt.setString(2, id);
			ResultSet rs3 = pStmt.executeQuery();
			while (rs3.next()) {
				recommendation = rs3.getString("MOVIE_ID");
			}
			if (!(recommendation.equals(id))) {
				pStmt = conn.prepareStatement("insert into Recommendations(MOVIE_ID, MEMBER_ID) VALUES" + "(?, ?)");
				pStmt.setString(1, recommendation);
				pStmt.setInt(2, memberID);
				pStmt.executeUpdate();
			}
		} catch (SQLIntegrityConstraintViolationException e) {
		    System.out.println("Duplicate Entry");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
			System.out.println("Favorite Movie Added");
		} // end finally try
	}
	/**
	 * Adds to members watch history list
	 * @param memberEmail - the members email address
	 * @param watched - members watched movie to be added
	 * @param dayWatched - what day the member said they watched it
	 */
	public static void addWatchHistory(String memberEmail, String watched, String dayWatched) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		java.sql.PreparedStatement pStmt2 = null;
		String id = null;
		String recommendation = null;
		try {
			conn = createConnection();
			java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(dayWatched);
			// because PreparedStatement#setDate(..) expects a java.sql.Date
			// argument
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			pStmt = conn.prepareStatement("select MOVIE_ID from Movies where TITLE = ?");
			pStmt.setString(1, watched);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				id = rs.getString("MOVIE_ID");
			}
			int memberID = 0;
			pStmt = conn.prepareStatement("select MEMBER_ID from Members where EMAIL = ?");
			pStmt.setString(1, memberEmail);
			ResultSet rs2 = pStmt.executeQuery();
			while (rs2.next()) {
				memberID = rs2.getInt("MEMBER_ID");
			}
			pStmt2 = conn.prepareStatement("insert into WatchHistory(MOVIE_ID, MEMBER_ID, DATE) VALUES" + "(?, ?, ?)");
			pStmt2.setString(1, id);
			pStmt2.setInt(2, memberID);
			pStmt2.setDate(3, sqlDate);
			pStmt2.executeUpdate();
			pStmt = conn.prepareStatement(
					"select MOVIE_ID from (select Movies.MOVIE_ID from Movies natural join Actors as a natural join ActsIn where concat(a.fname, ' ', a.lname)  = (select concat(a.fname, ' ', a.lname) from Movies natural join WatchHistory natural join Actors as a natural join ActsIn where MEMBER_ID = ? and Movies.MOVIE_ID = ? limit 1) order by rand() limit 1)a");
			pStmt.setInt(1, memberID);
			pStmt.setString(2, id);
			ResultSet rs3 = pStmt.executeQuery();
			while (rs3.next()) {
				recommendation = rs3.getString("MOVIE_ID");
			}
			if (!(recommendation.equals(id))) {
				pStmt = conn.prepareStatement("insert into Recommendations(MOVIE_ID, MEMBER_ID) VALUES" + "(?, ?)");
				pStmt.setString(1, recommendation);
				pStmt.setInt(2, memberID);
				pStmt.executeUpdate();
			}
		} catch (SQLIntegrityConstraintViolationException e) {
		    System.out.println("Duplicate Entry");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(pStmt2);
			close(conn);
			System.out.println("Watched Movie Added");
		} // end finally try
	}
	/**
	 * Returns a random movie to the member or guest
	 */
	public static void randomMovie() {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement("select TITLE from Movies order by RAND() limit 1");
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				// Retrieve by column name
				String movieTitle = rs.getString("TITLE");
				System.out.println("Your Randomly Selected Movie is: " + movieTitle);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try
	}
	/**
	 * Will verify a member
	 * @param memberEmail - possible member email
	 * @param memberPassword - possible member password
	 * @return if member or not (boolean)
	 */
	public static boolean verifiedMember(String memberEmail, String memberPassword) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		String password;
		boolean member = false;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement("select PASSWORD from Members where EMAIL = ?");
			pStmt.setString(1, memberEmail);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				// Retrieve by column name
				password = rs.getString("PASSWORD");
				if (password.equals(memberPassword)) {
					member = true;
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try
		return member;
	}
	/**
	 * Add an actor to the database
	 * @param actorID - given actor id
	 * @param fname - given actor first name
	 * @param lname - given actor last name
	 */
	public static void addActor(String actorID, String fname, String lname) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn
					.prepareStatement("insert into Actors(ACTOR_ID, FNAME, LNAME) VALUES" + "(?, ?, ?)");
			pStmt.setString(1, actorID);
			pStmt.setString(2, fname);
			pStmt.setString(3, lname);
			pStmt.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
		    System.out.println("Duplicate Entry");
		}  catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
			System.out.println("Actor Added");
		} // end finally try

	}
	/**
	 * Will verify an employee 
	 * @param employeeEmail - possible employee email
	 * @param employeePassword - possible employee password
	 * @return boolean - if employee or not
	 */
	public static boolean verifiedEmployee(String employeeEmail, String employeePassword) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		String password;
		boolean employee = false;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement("select PASSWORD from EmployeesGroup where EMAIL = ?");
			pStmt.setString(1, employeeEmail);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				// Retrieve by column name
				password = rs.getString("PASSWORD");
				if (password.equals(employeePassword)) {
					employee = true;
				}
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try
		return employee;
	}
	/**
	 * Will print the top 10 rated movies
	 */
	public static void top10RatedMovies() {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = createConnection();

			String sql = "select TITLE, RATING from Movies order by RATING DESC LIMIT 10";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			int counter = 1;
			while (rs.next()) {
				// Retrieve by column name
				String title = rs.getString("TITLE");
				String rating = rs.getString("RATING");

				// Display values
				System.out.println(counter + ": " + title + " Rating: " + rating);
				counter++;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(st);
			close(conn);
		} // end finally try

	}
	/**
	 * Will print the top 10 watched movies
	 */
	public static void top10WatchedMovies() {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = createConnection();

			String sql = "select TITLE, count(MOVIE_ID) as count from Movies natural join WatchHistory group by TITLE order by count desc limit 10";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			int counter = 1;
			while (rs.next()) {
				// Retrieve by column name
				String title = rs.getString("TITLE");

				// Display values
				System.out.println(counter + ": " + title);
				counter++;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(st);
			close(conn);
		} // end finally try

	}
	/**
	 * Will print the 10 most popular Actors
	 */
	public static void mostPopularActors() {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = createConnection();

			String sql = "select concat(a.fname, ' ', a.lname) as ACTOR_OR_ACTRESS, count(a.fname) as MOVIE_COUNT from Actors a natural join ActsIn group by a.fname order by MOVIE_COUNT desc limit 10";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			int counter = 1;
			while (rs.next()) {
				// Retrieve by column name
				String name = rs.getString("ACTOR_OR_ACTRESS");
				String count = rs.getString("MOVIE_COUNT");

				// Display values
				System.out.println(counter + ": " + name + " Movies: " + count);
				counter++;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(st);
			close(conn);
		} // end finally try

	}
	/**
	 * Prints the movies that have not been put into any member's watch list
	 */
	public static void moviesNotWatched() {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = createConnection();

			String sql = "select TITLE from Movies where TITLE not in (select TITLE from Movies natural join WatchHistory)";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				// Retrieve by column name
				String title = rs.getString("TITLE");
				// Display values
				System.out.println(title);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(st);
			close(conn);
		} // end finally try

	}
	/**
	 * Will print the movies that have been favorited and watched by
	 * any member
	 */
	public static void moviesWatchedAndFavorited() {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = createConnection();

			String sql = "select TITLE from FavoriteMovies natural join Movies where TITLE in (select TITLE from Movies natural join WatchHistory)";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				// Retrieve by column name
				String title = rs.getString("TITLE");
				// Display values
				System.out.println(title);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(st);
			close(conn);
		} // end finally try
	}
	public static void combinedWatchedandFavorited(){
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = createConnection();

			String sql = "select TITLE from FavoriteMovies natural join Movies UNION select TITLE from WatchHistory natural join Movies";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				// Retrieve by column name
				String title = rs.getString("TITLE");
				// Display values
				System.out.println(title);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(st);
			close(conn);
		} // end finally try
	}
	/**
	 * Stored procedure that gets the count of members
	 */
	public static void getMembers() {
		java.sql.Connection conn = null;
		try {
			conn = createConnection();

			java.sql.CallableStatement stm = conn.prepareCall("{ call CountMembers(?) }");
			stm.registerOutParameter(1, Types.INTEGER);
			stm.execute();
			int m_count = stm.getInt(1);
			System.out.println(m_count);
			stm.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(conn);
		} // end finally try

	}
	/**
	 * Stored procedure that gets the count of employees
	 */
	public static void getEmployees() {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = createConnection();
			java.sql.CallableStatement stm = conn.prepareCall("{ call CountEmployees(?) }");
			stm.registerOutParameter(1, Types.INTEGER);
			stm.execute();
			int m_count = stm.getInt(1);
			System.out.println(m_count);
			stm.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(st);
			close(conn);
		} // end finally try

	}
	/**
	 * Stored procedure that gets the count of movies
	 */
	public static void getAllMovies() {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = createConnection();

			java.sql.CallableStatement stm = conn.prepareCall("{ call CountMovies(?) }");
			stm.registerOutParameter(1, Types.INTEGER);
			stm.execute();
			int m_count = stm.getInt(1);
			System.out.println(m_count);
			stm.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(st);
			close(conn);
		} // end finally try
	}
	/**
	 * View that allows the employee (not CEO) to only see certain
	 * information for all employees
	 */
	public static void employeeView() {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = createConnection();

			String sql = "select * from EmployeeToEmployeeView";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				// Retrieve by column name
				String fname = rs.getString("FNAME");
				String lname = rs.getString("LNAME");
				String position = rs.getString("POSITION");
				// Display values
				System.out.println("Name: " + fname + " " + lname + ": Position: " + position);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(st);
			close(conn);
		} // end finally try

	}
	/**
	 * Calls function RateGrade and will give the movies
	 * a rating depending on their rate from 0-10
	 */
	public static void rateGrade() {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = createConnection();

			String sql = "select TITLE, RateGrade(RATING) as RATING from Movies";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				// Retrieve by column name
				String title = rs.getString("TITLE");
				String rating = rs.getString("RATING");
				// Display values
				System.out.println("Title: " + title + ": Rating: " + rating);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(st);
			close(conn);
		} // end finally try

	}
	/**
	 * Computes and prints the average salary of the developers
	 */
	public static void developerAverage() {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement("select avg(salary) as avg from EmployeesGroup where POSITION = ?");
			pStmt.setString(1, "Developer");
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				// Retrieve by column name
				String average = rs.getString("avg");
				// Display values
				System.out.println("Average Developer Salary: " + average);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try

	}
	/**
	 * Computers and prints who is paid the most in the company
	 */
	public static void paidTheMost() {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement(
					"select FNAME, LNAME, POSITION from EmployeesGroup where SALARY > all (select SALARY from EmployeesGroup where POSITION = ?)");
			pStmt.setString(1, "Developer");
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) {
				// Retrieve by column name
				String fname = rs.getString("FNAME");
				String lname = rs.getString("LNAME");
				String position = rs.getString("POSITION");
				// Display values
				System.out.println("Name: " + fname + " " + lname + " Position: " + position);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try
	}
	/**
	 * Calls function PayGrade that determines the paygrade of all employees
	 * Only visible to CEO.
	 */
	public static void payGrade() {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = createConnection();

			String sql = "select FNAME, LNAME, PayGrade(SALARY) from EmployeesGroup";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				// Retrieve by column name
				String fname = rs.getString("FNAME");
				String lname = rs.getString("LNAME");
				String pay = rs.getString("PayGrade(SALARY)");
				// Display values
				System.out.println("Name: " + fname + " " + lname + " Pay Grade: " + pay);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		} finally {
			// finally block used to close resources
			close(st);
			close(conn);
		} // end finally try

	}
	/**
	 * Prints whatever data it comes up with or prints nothing if nothing 
	 * is found in the database
	 * @param searchData - whatever the member wants to search in Movies
	 */
	public static void search(String searchData) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement(
					"select m.title, m.rating, m.genre, m.releasedate, concat(a.fname, ' ', a.lname) as ACTOR_OR_ACTRESS from Movies m natural join Actors a natural join ActsIn where ? in (concat(a.fname, ' ', a.lname), a.fname, a.lname, m.title, m.genre, m.releasedate)");
			pStmt.setString(1, searchData);
			ResultSet rs = pStmt.executeQuery();
			if (rs.next()) {
				do {
				// Retrieve by column name
				String title = rs.getString("TITLE");
				String rating = rs.getString("RATING");
				String genre = rs.getString("GENRE");
				String releaseDate = rs.getString("RELEASEDATE");
				String name = rs.getString("ACTOR_OR_ACTRESS");
				// Display values
				System.out.println("Title: " + title + " Rating: " + rating + " Genre: " + genre + " Release Date: "
						+ releaseDate + " Actor/Actress: " + name);
				} while (rs.next());
			} else {
				System.out.println("Sorry, nothing was found!");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try
	}
	/**
	 * View that allows member to see everything except the movie's ID
	 */
	public static void memberMovieView() {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = createConnection();

			String sql = "select * from MemberMovieView";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				// Retrieve by column name
				String title = rs.getString("TITLE");
				String rating = rs.getString("RATING");
				String genre = rs.getString("GENRE");
				String releaseDate = rs.getString("RELEASEDATE");
				String name = rs.getString("ACTOR_OR_ACTRESS");
				// Display values
				System.out.println("Title: " + title + " Rating: " + rating + " Genre: " + genre + " Release Date: "
						+ releaseDate + " Actor/Actress: " + name);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(st);
			close(conn);
		} // end finally try

	}
	/**
	 * Prints out the EmployeesAudit table
	 */
	public static void deletedEmployees() {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = createConnection();

			String sql = "select * from EmployeesAudit";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				// Retrieve by column name
				String id = rs.getString("EMPLOYEE_ID");
				String hireDate = rs.getString("HIRE_DATE");
				String first = rs.getString("FNAME");
				String last = rs.getString("LNAME");
				String jobLocation = rs.getString("JOB_LOCATION");
				String position = rs.getString("POSITION");
				String salary = rs.getString("SALARY");
				Date date = rs.getDate("DATE_DELETED");

				// Display values
				System.out.println("Employee_ID: " + id + " Hire Date: " + hireDate + " First Name: " + first
						+ " Last Name: " + last + " Job Location: " + jobLocation + " Position: " + position
						+ " Salary: " + salary + "Date Deleted: " + date);

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(st);
			close(conn);
		} // end finally try

	}
	/**
	 * Prints out the MembersAudit table
	 */
	public static void deletedMembers() {
		java.sql.Connection conn = null;
		java.sql.Statement st = null;
		try {
			conn = createConnection();

			String sql = "select * from MembersAudit";
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				// Retrieve by column name
				String id = rs.getString("MEMBER_ID");
				String first = rs.getString("FNAME");
				String last = rs.getString("LNAME");
				String phone = rs.getString("PHONE");
				String address = rs.getString("ADDRESS");
				String city = rs.getString("CITY");
				String state = rs.getString("STATE");
				String zip = rs.getString("ZIP");
				Date date = rs.getDate("DATE_DELETED");

				// Display values
				System.out.println("Member_ID: " + id + " First Name: " + first + " Last Name: " + last + " Phone: "
						+ phone + " Address: " + address + " City: " + city + " State: " + state + " Zip: " + zip
						+ " Date Deleted " + date);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(st);
			close(conn);
		} // end finally try

	}
	/**
	 * Adds an actor to an already existing movie
	 * @param m_id - given movie id
	 * @param a_id - given actor id
	 */
	public static void addActorToMovie(String m_id, String a_id) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement("insert into ActsIn(MOVIE_ID, ACTOR_ID) VALUES" + "(?, ?)");
			pStmt.setString(1, m_id);
			pStmt.setString(2, a_id);
			pStmt.executeUpdate();
		} catch (SQLIntegrityConstraintViolationException e) {
		    System.out.println("Duplicate Entry");
		}  catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
			System.out.println("Movie Added");
		} // end finally try
	}
	/**
	 * Prints out recommendations for member
	 * @param memberEmail - given member email
	 */
	public static void recommendations(String memberEmail) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();
			pStmt = conn.prepareStatement("select TITLE from Movies natural join Recommendations where MEMBER_ID in (select MEMBER_ID from Members where EMAIL = ?)");
			pStmt.setString(1, memberEmail);
			ResultSet rs2 = pStmt.executeQuery();
			if(rs2.next()){
				do {
					String title = rs2.getString("TITLE");
					System.out.println("Title: " + title);
				}
			while (rs2.next());
			} else {
				System.out.println("You don't have anything in 'Recommendations'");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try

	}
	/**
	 * Will update employee email address
	 * @param employeeEmail - old employee email
	 * @param newEmployeeEmail - new employee email
	 */
	public static void updateEmployeeEmailAddress(String employeeEmail, String newEmployeeEmail) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();
			int employeeID = 0;

			pStmt = conn.prepareStatement("select EMPLOYEE_ID from EmployeesGroup where EMAIL = ?");
			pStmt.setString(1, employeeEmail);
			ResultSet rs2 = pStmt.executeQuery();
			while (rs2.next()) {
				employeeID = rs2.getInt("EMPLOYEE_ID");
			}
			pStmt = conn.prepareStatement("update EmployeesGroup set EMAIL = ? where EMPLOYEE_ID = ?");
			pStmt.setString(1, newEmployeeEmail);
			pStmt.setInt(2, employeeID);
			pStmt.executeUpdate();
			System.out.println("Here is your new information: ");
			getEmployeeInformation(newEmployeeEmail);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try

	}
	/**
	 * Will update employee password
	 * @param employeeEmail - employee email
	 * @param newEmployeePassword - new employee passowrd
	 */
	public static void updateEmployeePassword(String employeeEmail, String newEmployeePassword) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();
			int employeeID = 0;

			pStmt = conn.prepareStatement("select EMPLOYEE_ID from EmployeesGroup where EMAIL = ?");
			pStmt.setString(1, employeeEmail);
			ResultSet rs2 = pStmt.executeQuery();
			while (rs2.next()) {
				employeeID = rs2.getInt("EMPLOYEE_ID");
			}
			pStmt = conn.prepareStatement("update EmployeesGroup set PASSWORD = ? where EMPLOYEE_ID = ?");
			pStmt.setString(1, newEmployeePassword);
			pStmt.setInt(2, employeeID);
			pStmt.executeUpdate();
			System.out.println("Here is your new information: ");
			getEmployeeInformation(employeeEmail);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try

	}
	/**
	 * Will update member email
	 * @param memberEmail - old member email
	 * @param newMemberEmail - new member email
	 */
	public static void updateMemberEmailAddress(String memberEmail, String newMemberEmail) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();
			int memberID = 0;

			pStmt = conn.prepareStatement("select MEMBER_ID from Members where EMAIL = ?");
			pStmt.setString(1, memberEmail);
			ResultSet rs2 = pStmt.executeQuery();
			while (rs2.next()) {
				memberID = rs2.getInt("MEMBER_ID");
			}
			pStmt = conn.prepareStatement("update Members set EMAIL = ? where MEMBER_ID = ?");
			pStmt.setString(1, newMemberEmail);
			pStmt.setInt(2, memberID);
			pStmt.executeUpdate();
			System.out.println("Here is your new information: ");
			getMemberInformation(newMemberEmail);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try

	}
	/**
	 * Will update member password
	 * @param memberEmail - member email
	 * @param newMemberPassword - new password
	 */
	public static void updateMemberPassword(String memberEmail, String newMemberPassword) {
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();
			int memberID = 0;

			pStmt = conn.prepareStatement("select MEMBER_ID from Members where EMAIL = ?");
			pStmt.setString(1, memberEmail);
			ResultSet rs2 = pStmt.executeQuery();
			while (rs2.next()) {
				memberID = rs2.getInt("MEMBER_ID");
			}
			pStmt = conn.prepareStatement("update Members set PASSWORD = ? where MEMBER_ID = ?");
			pStmt.setString(1, newMemberPassword);
			pStmt.setInt(2, memberID);
			pStmt.executeUpdate();
			System.out.println("Here is your new information: ");
			getMemberInformation(memberEmail);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try
	}
	/**
	 * Deletes an actor
	 * @param actorID - actor id to be deleted
	 */
	public static void deleteActor(String actorID){
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement("delete from Actors where ACTOR_ID = ?");
			pStmt.setString(1, actorID);
			pStmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
			System.out.println("Actor Deleted");
		} // end finally try
	}
	/**
	 * CEO only query that prints out all employees and all their info
	 */
	public static void ceoViewOfEmployees(){
		java.sql.Connection conn = null;
		java.sql.PreparedStatement pStmt = null;
		try {
			conn = createConnection();

			pStmt = conn.prepareStatement(
					"select * from EmployeesGroup");
			ResultSet rs2 = pStmt.executeQuery();
			while (rs2.next()) {
				// Retrieve by column name
				int id = rs2.getInt("EMPLOYEE_ID");
				String email = rs2.getString("EMAIL");
				String password = rs2.getString("PASSWORD");
				String hireDate = rs2.getString("HIRE_DATE");
				String first = rs2.getString("FNAME");
				String last = rs2.getString("LNAME");
				String jobLocation = rs2.getString("JOB_LOCATION");
				String position = rs2.getString("POSITION");
				String salary = rs2.getString("SALARY");

				// Display values
				System.out.println("Employee_ID: " + id);
				System.out.println("Email: " + email);
				System.out.println("Password: " + password);
				System.out.println("Hire Date: " + hireDate);
				System.out.println("First Name: " + first);
				System.out.println("Last Name: " + last);
				System.out.println("Job Location: " + jobLocation);
				System.out.println("Position: " + position);
				System.out.println("Salary: " + salary);
				System.out.println();

			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			close(pStmt);
			close(conn);
		} // end finally try
	}
}
