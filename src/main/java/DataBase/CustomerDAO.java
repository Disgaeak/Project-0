package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;
import java.util.ArrayList;

import RevCustom.BankCustomer;

public class CustomerDAO implements CustomerDAO_I
{
	//get all customers in the table
	public ArrayList<BankCustomer> getCustomers()
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//create a statement & result set
			Statement statement = cMan.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Customers");
			
			//create a list to store all of them
			ArrayList<BankCustomer> cust = new ArrayList<BankCustomer>();
			while(rs.next())
			{
				int accountNumber = rs.getInt("account_num");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String userName = rs.getString("user_name");
				String password = rs.getString("user_password");
				int routNumber = rs.getInt("rout_num");
				double balance = rs.getDouble("balance");
				double saveBalance = rs.getDouble("save_balance");
				int jointNumber = rs.getInt("joint_num");
				int validAccount = rs.getInt("valid_aacount");
				
				cust.add(new BankCustomer(accountNumber, firstName, lastName, userName, password, routNumber, balance, saveBalance, jointNumber, validAccount));
			}
			
			return cust;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}	
	
	//-----------------------------------------------------------Searchers-----------------------------------------------------------------
	//get one customer in the table
	public BankCustomer searchCustomers(String user)
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			String query = "SELECT * FROM Customers WHERE user_name = ?";
			//create a statement & result set
			PreparedStatement pstmt = cMan.prepareStatement(query);
			pstmt.setString(1, user);
			
			ResultSet rs = pstmt.executeQuery();
			//create a list to store all of them
			BankCustomer cust = new BankCustomer();
			if(rs.next())
			{
				int accountNumber = rs.getInt("account_num");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String userName = rs.getString("user_name");
				String password = rs.getString("user_password");
				int routNumber = rs.getInt("rout_num");
				double balance = rs.getDouble("balance");
				double saveBalance = rs.getDouble("save_balance");
				int jointNumber = rs.getInt("joint_num");
				int validAccount = rs.getInt("valid_aacount");
				
				cust = new BankCustomer(accountNumber, firstName, lastName, userName, password, routNumber, balance, saveBalance, jointNumber, validAccount);
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
	
	@Override
	public BankCustomer searchCustomers() 
	{
		//used by admin to search for customer applications who are pending
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//create a statement & result set
			Statement statement = cMan.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Customers WHERE valid_account = 2");
			
			//create a list to store all of them
			BankCustomer cust = new BankCustomer();
			if(rs.next())
			{
				int accountNumber = rs.getInt("account_num");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String userName = rs.getString("user_name");
				String password = rs.getString("user_password");
				int routNumber = rs.getInt("rout_num");
				double balance = rs.getDouble("balance");
				double saveBalance = rs.getDouble("save_balance");
				int jointNumber = rs.getInt("joint_num");
				int validAccount = rs.getInt("valid_aacount");
				
				cust = new BankCustomer(accountNumber, firstName, lastName, userName, password, routNumber, balance, saveBalance, jointNumber, validAccount);
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
	
	//------------------------------------------------End of Searches ------------------------------------------------------------------------------------
	
	//------------------------------------------------Setters -------------------------------------------------------------------------------------
	//adds a customer to the DB
	public void setCustomer(BankCustomer NC)
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//query to be executed
			String query = "INSERT into Customers (account_num, first_name, last_name, user_name, user_password, rout_num, balance, save_balance,"
					+ " joint_num, valid_aacount)" + " values (?,?,?,?,?,?,?,?,?,?)";
			
			//creates a prepared statement
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			pstmt.setInt(1, NC.accountNum);
			pstmt.setString(2, NC.firstName);
			pstmt.setString(3, NC.lastName);
			pstmt.setString(4, NC.userName);
			pstmt.setString(5, NC.passWord);
			pstmt.setInt(6, NC.routNum);
			pstmt.setDouble(7, NC.balance);
			pstmt.setDouble(8, NC.saveBalance);
			pstmt.setInt(9, NC.jointNum);
			pstmt.setInt(10, NC.validAccount);
			
			pstmt.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void setSavings(int ns) 
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//query to be executed
			String query = "INSERT into savings (account, balance)" + " values (?,?)";
			
			//creates a prepared statement
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			pstmt.setInt(1, ns);
			pstmt.setDouble(2, 0);
			
			pstmt.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}

	//adds a log to the DB from a customer
	public void setLog(BankCustomer NC, String mes)
	{
		try
		{
			LocalDate ld = LocalDate.now();
			Connection cMan = ConnectManager.getConnect();
			
			//query to be executed
			String query = "INSERT into BankLogs (user_name, description) VALUES (?, ?)";
			
			//creates a prepared statement
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			pstmt.setString(1, NC.userName);
			pstmt.setString(2, ld.toString() + " " + mes);
			
			pstmt.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	//adds a log to the DB from an employee or admin
	public void setLog(int bankCode, String mes)
	{
		try
		{
			LocalDate ld = LocalDate.now();
			Connection cMan = ConnectManager.getConnect();
			
			//query to be executed
			String query = "INSERT into BankLogs (bank_code, description) VALUES (?, ?)";
			
			//creates a prepared statement
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			pstmt.setInt(1, bankCode);
			pstmt.setString(2, ld.toString() + " " + mes);
			
			pstmt.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	//----------------------------------------------------------------End of Setters-------------------------------------------------------------
	
	//----------------------------------------------------------------booleans (checkers)-------------------------------------------------------
	public boolean bIsCustomer(String cm) 
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();

			//create a statement & result set
			String query = "SELECT * FROM customers WHERE user_name = ?";
			PreparedStatement pstmt = cMan.prepareStatement(query);
			pstmt.setString(1, cm);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				return true;
			}
			
			return false;
		}
		catch(SQLException e) {}
		return false;
	}
	
	@Override
	public boolean bHasSavings(int cm) 
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
		
			//create a statement & result set
			String query = "SELECT * FROM savings WHERE account = ?";
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			pstmt.setInt(1, cm);
			
			ResultSet rs = pstmt.executeQuery();
			if(rs.next())
			{
				return true;
			}
			
			return false;
		}
		catch(SQLException e) {}
		return false;
	}

	//-----------------------------------------------------------End of checkers--------------------------------------------------------
	
	
	//----------------------------------------------------------- Updaters --------------------------------------------------------
	@Override
	public void UpdateCustomer(BankCustomer cm) 
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//query to be executed
			String query = "UPDATE customers SET first_name = ?, last_name = ?, user_name = ?, user_password = ?, "
					+ "balance = ?, save_balance = ?,joint_num = ?, valid_aacount = ? WHERE account_num = ?";
			
			//creates a prepared statement
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			pstmt.setString(1, cm.firstName);
			pstmt.setString(2, cm.lastName);
			pstmt.setString(3, cm.userName);
			pstmt.setString(4, cm.passWord);
			pstmt.setDouble(5, cm.balance);
			pstmt.setDouble(6, cm.saveBalance);
			pstmt.setInt(7, cm.jointNum);
			pstmt.setInt(8, cm.validAccount);
			pstmt.setInt(9, cm.accountNum);
			
			pstmt.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	//--------------------------------------------------End of updaters-----------------------------------------------------------------------
	
	@Override
	public void deleteCustomer(String cm) 
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//query to be executed
			String query = "DELETE from customers WHERE user_name = ?";
			
			//creates a prepared statement
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			pstmt.setString(1, cm);
			
			pstmt.execute();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
	}
}
