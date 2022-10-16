package jdbc.demo;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

public class App {
	static Connection con;

	public static void main(String[] args) throws SQLException {
		databaseConnection();
		ReadDataFromDatabase();

	}

	// List of Drivers Method
	private static void listDrivers() {
		Enumeration<Driver> driverList = DriverManager.getDrivers();
		while (driverList.hasMoreElements()) {
			Driver driverClass = (Driver) driverList.nextElement();
			System.out.println("   " + driverClass.getClass().getName());

		}
	}

	// Loading Drivers And Creating Database Connection
	private static void databaseConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service";
		String userName = "Sidhodhan";
		String password = "root";

		con = DriverManager.getConnection(jdbcURL, userName, password);
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loadded");

		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		}
		listDrivers();
		// Connecting to DataBase
		try {
			System.out.println("Connecting to database:" + jdbcURL);

			System.out.println("Connection is successful!!!!!!" + con);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Method For reading Data from data base
	private static void ReadDataFromDatabase() {
		try {
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery("Select * from employee_payroll_p");
			System.out.println("Data From Employee Payroll table.");
			while (result.next()) {
				System.out.println("ID: " + result.getInt("id"));
				System.out.println("Name: " + result.getString("Name"));
				System.out.println("Gender: " + result.getString("gender"));
				System.out.println("Starting Date: " + result.getString("start") + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
