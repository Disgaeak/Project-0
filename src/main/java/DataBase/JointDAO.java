package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import RevCustom.BankCustomer;

public class JointDAO implements JointDAO_I
{
	//get 1 joint customer in the table
	public BankCustomer searchJointCustomers(int jnum)
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//create a statement & result set
			Statement statement = cMan.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM jointaccounts WHERE joint_num = " + jnum);
			
			//create a variable to store DB info
			BankCustomer cust = new BankCustomer();
			if(rs.next())
			{
				cust.firstName = rs.getString("first_name");
				cust.lastName = rs.getString("last_name");
				cust.balance = rs.getDouble("balance");
				cust.jointNum = rs.getInt("joint_num");
				
				return cust;
			}
			
			return null;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public void setJoint(BankCustomer JC)
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//query to be executed
			String query = "INSERT into jointaccounts VALUES (?,?,?,?)";
			
			//creates a prepared statement
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			pstmt.setInt(1, JC.jointNum);
			pstmt.setString(2, JC.firstName);
			pstmt.setString(3, JC.lastName);
			pstmt.setDouble(4, 0.0);
			
			pstmt.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void UpdateJoint(BankCustomer cm) 
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//query to be executed
			String query = "UPDATE jointaccounts SET balance = ? WHERE joint_num = ?";
			
			//creates a prepared statement
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			pstmt.setDouble(1, cm.balance);
			pstmt.setInt(2, cm.jointNum);
			
			pstmt.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteJoint(BankCustomer cm) 
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			String query = "DELETE FROM jointaccounts WHERE joint_num = ?";
			
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			pstmt.setInt(1, cm.jointNum);
			
			pstmt.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
