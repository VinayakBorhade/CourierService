package db.src.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Order {
	static Connection myConn;
	static PreparedStatement myStmt;
	
	private int orderid,price,userid;
	private String source,destination,pickupTime,deliveryType,details;
	private boolean isDeliverd,isAssigned,isPaid;
	
	private int fragileCount,durableCount,otherCount;
	
	public Order(Connection myConn) {
		this.myConn=myConn;
	}
	
	public Order(String source,String destination,String deliveryType,String details, Connection myConn) {
		this(myConn);setPrice(0);
		//System.out.println("isDelivered: "+this.isDeliverd); 
		//this.isDeliverd=false;this.setIsAssigned(false);this.setIsPaid(false);
		this.setSource(source);this.setDestination(destination);
		this.setDeliveryType(deliveryType);this.setDetails(details);
		
		/* query to count the 3 types of objects */
		setFragileCount(0);setDurableCount(1);setOtherCount(0);
		
	}
	
	private void setIsDeliverd(boolean b) {
		// TODO Auto-generated method stub
		this.isDeliverd=b;
	}

	public boolean insertOrder() {
		try {
			myStmt = myConn.prepareStatement("insert into orders (destination,source,deliveryType,details)" 
					+ "values (?,?,?,?)" );
			
			myStmt.setString(1, this.getDestination());
			myStmt.setString(2, this.getSource());
			myStmt.setString(3, this.getDeliveryType());
			myStmt.setString(4, this.getDetails());
			myStmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/*public Order(String email,String password) {
		/*convert password string to hash pass here 
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
	}*/

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public String getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(String deliveryType) {
		this.deliveryType = deliveryType;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public int getDurableCount() {
		return durableCount;
	}

	public void setDurableCount(int durableCount) {
		this.durableCount = durableCount;
	}

	public int getFragileCount() {
		return fragileCount;
	}

	public void setFragileCount(int fragileCount) {
		this.fragileCount = fragileCount;
	}

	public int getOtherCount() {
		return otherCount;
	}

	public void setOtherCount(int otherCount) {
		this.otherCount = otherCount;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public Boolean getIsPaid() {
		return isPaid;
	}

	public void setIsPaid(Boolean isPaid) {
		this.isPaid = isPaid;
	}

	public Boolean getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(Boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	public Boolean getIsDeliverd() {
		return isDeliverd;
	}


	
}
