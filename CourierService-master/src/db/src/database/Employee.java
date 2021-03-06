package db.src.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Employee {
	static Connection myConn;
	static PreparedStatement myStmt;
	
	private int employeeid;
	private String employeename,phone,address,password;
	private Boolean available;
	
	public Employee(Connection myConn) {
		this.myConn=myConn;
	}
	
	public Employee(String employeename,String password, String phone,String address,Boolean available, Connection myConn) {
		this(myConn);
		this.setEmployeename(employeename);this.setPhone(phone);this.setAddress(address);this.setPassword(password);
		this.available=available;
	}
	
	public Boolean insertEmployee() {
		try {
			myStmt = myConn.prepareStatement("insert into employee (empolyeename,phone,address,available)" 
					+ "values (?,?,?,?)" );
			
			myStmt.setString(1, this.getEmployeename());
			myStmt.setString(2, this.getPhone());
			myStmt.setString(3, this.getAddress());
			myStmt.setBoolean(4, this.getAvailable());
			myStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public int getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmployeename() {
		return employeename;
	}

	public void setEmployeename(String employeename) {
		this.employeename = employeename;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
