package db.src.database;

import java.sql.*;

public class Users {
	static Connection myConn;
	static PreparedStatement myStmt;
	
	private int userid;
	private String username,email,password,createdAt,sourceAddress,phone;
	
	public Users(Connection myConn) {
		this.myConn=myConn;
	}
	
	public Users(String username,String email,String password,String sourceAddress,String phone,Connection myConn) {
		this(myConn);
		this.username=username;this.email=email;this.password=password;this.sourceAddress=sourceAddress;
		this.phone=phone;
	}
		
	public boolean insertUser() {
		try {
			
			myStmt = myConn.prepareStatement("insert into users (username,email,password,sourceaddress,phone) values (?,?,?,?,?)" );

			myStmt.setString(1, this.getUsername());
			myStmt.setString(2, this.getEmail());
			myStmt.setString(3, this.getPassword());
			myStmt.setString(4, this.getSourceAddress());
			myStmt.setString(5, this.getPhone());
			myStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public Users(String email,String password) {
		/*convert password string to hash pass here */
		ResultSet myRs;
		try {
			String query="select * from users where username = ? and password = ?";
			myStmt=myConn.prepareStatement(query);
			myStmt.setString(1, email);
			myStmt.setString(2, password);
			myRs = myStmt.executeQuery();
			while(myRs.next() ) {
				this.userid=(int)myRs.getLong("userid");
				this.username=myRs.getString("username");
				this.email=myRs.getString("email");
				this.password=myRs.getString("password");
				this.sourceAddress=myRs.getString("sourceaddress");
				this.phone=myRs.getString("phone") ;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSourceAddress() {
		return sourceAddress;
	}

	public void setSourceAddress(String sourceAddress) {
		this.sourceAddress = sourceAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	
}
