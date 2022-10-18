package jdbc.demo;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

public class App {
	static Connection con;

	public static void main(String[] args) throws SQLException {
		// Database Connection
		databaseConnection();

		// Read Data Method For Read data from database
		readDataFromDatabase();
		// Update Basic pay(salery) using PreparedStatement
		updateBasicPay();
		// Read Data By Name Method
		readDataByNameUsingPreparedStatement();
		// Method To read data of particular date range
		readDataByStartPerticularStartDateRange();
		con.close();

	}

	private static void readDataByStartPerticularStartDateRange() {
		try {

			PreparedStatement prstm = (PreparedStatement) con.prepareStatement(
					"SELECT * FROM employee_payroll_p WHERE start BETWEEN CAST(? AS DATE) AND DATE(NOW());");
			prstm.setString(1, "2022-01-01");
			System.out
					.println("Retrieve all employees who have joined in a particular date range(2022-01-01 to today)");
			ResultSet result = prstm.executeQuery();
			while (result.next()) {
				System.out.print("ID: " + result.getInt("id") + " ");
				System.out.print("Name: " + result.getString("Name") + " ");
				System.out.print("Gender: " + result.getString("gender") + " ");
				System.out.print("Salary: " + result.getString("basic_pay") + " ");
				System.out.print("Starting Date: " + result.getString("start") + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

//Method For Read data By Name Using PreparedStatement
	private static void readDataByNameUsingPreparedStatement() {

		try {

			PreparedStatement prstm = (PreparedStatement) con
					.prepareStatement("Select * from employee_payroll_p WHERE Name=?");
			prstm.setString(1, "Terisa");
			System.out.println("Data From Employee Payroll table.");
			ResultSet result = prstm.executeQuery("Select * from employee_payroll_p");
			while (result.next()) {
				System.out.println("ID: " + result.getInt("id"));
				System.out.println("Name: " + result.getString("Name"));
				System.out.println("Gender: " + result.getString("gender"));
				System.out.println("salary: " + result.getString("basic_pay"));
				System.out.println("Starting Date: " + result.getString("start") + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//Method For Update Basic Pay using PreparedStatement
	private static void updateBasicPay() {
		try {

			PreparedStatement prstm = (PreparedStatement) con
					.prepareStatement("UPDATE employee_payroll_p set basic_pay = ? where Name =?");
			prstm.setDouble(1, 5000000.00);
			prstm.setString(2, "Terisa");
			Integer recordUpdated = prstm.executeUpdate();
			System.out.println("Updatated Table" + recordUpdated);
			readDataFromDatabase();

		} catch (SQLException e) {
			e.printStackTrace();
		}

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
	private static void readDataFromDatabase() {
		try {
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery("Select * from employee_payroll_p");
			System.out.println("Data From Employee Payroll table.");
			while (result.next()) {
				System.out.println("ID: " + result.getInt("id"));
				System.out.println("Name: " + result.getString("Name"));
				System.out.println("Gender: " + result.getString("gender"));
				System.out.println("Salary: " + result.getString("basic_pay"));
				System.out.println("Starting Date: " + result.getString("start") + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
