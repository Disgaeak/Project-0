package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import Revature.Project_0.BankAdmin;
import Revature.Project_0.BankEmployee;

public class EmployeeDAO implements EmployeeDAO_I
{

	public BankAdmin searchEmployee(int code)
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			String query = "SELECT * FROM Bankemployees WHERE code = ?";
			//create a statement & result set
			PreparedStatement pstmt = cMan.prepareStatement(query);
			pstmt.setInt(1, code);
			
			ResultSet rs = pstmt.executeQuery();
			//create a list to store all of them
			BankAdmin ae = new BankAdmin();
			while(rs.next())
			{
				if(rs.getInt("code") == code)
				{
					int bCode = rs.getInt("code");
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					LocalDate start = LocalDate.parse(rs.getString("start_date"));
					double pay = rs.getDouble("hour_pay");
					int validAccount = rs.getInt("lv");
					
					ae = new BankAdmin(bCode, firstName, lastName, start, pay, validAccount);
					return ae;
				}
			}
			
			return null;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	//adds an employee to the DB
	public void setEmployee(BankAdmin NC)
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//query to be executed
			String query = "INSERT into Bankemployees (code, first_name, last_name, start_date, hour_pay, lv)" + " values (?,?,?,?,?,?)";
			
			//creates a prepared statement
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			pstmt.setInt(1, NC.bankCode);
			pstmt.setString(2, NC.firstName);
			pstmt.setString(3, NC.lastName);
			pstmt.setString(4, NC.startDay.toString());
			pstmt.setDouble(5, NC.hrPay);
			pstmt.setInt(6, NC.lv);
			
			pstmt.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public boolean bIsEmployee(int cm) 
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			int lv;
			
			//create a statement & result set
			String query = "SELECT * FROM Bankemployees WHERE code = ?";
			PreparedStatement pstmt = cMan.prepareStatement(query);
			pstmt.setInt(1, cm);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				lv = rs.getInt("lv");
				if(lv == 0)
					return true;
			}
			
			return false;
		}
		catch(SQLException e) 
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void UpdateEmployee(BankEmployee be) 
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//query to be executed
			String query = "UPDATE bankemployees SET first_name = ?, last_name = ?, hour_pay = ?, lv = ? WHERE code = ?";
			
			//creates a prepared statement
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			pstmt.setString(1, be.firstName);
			pstmt.setString(2, be.lastName);
			pstmt.setDouble(3, be.hrPay);
			pstmt.setInt(4, be.lv);
			pstmt.setInt(5, be.bankCode);
			
			pstmt.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void deleteEmployee(int code) 
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//query to be executed
			String query = "DELETE from bankemployees WHERE code = ?";
			
			//creates a prepared statement
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			pstmt.setInt(1, code);
			
			pstmt.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}
