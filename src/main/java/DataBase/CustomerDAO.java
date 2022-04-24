package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class CustomerDAO implements CustomerDAO_I
{
	//get all customers in the table
	public ArrayList<CustomerModel> getCustomers()
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//create a statement & result set
			Statement statement = cMan.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Customers");
			
			//create a list to store all of them
			ArrayList<CustomerModel> cust = new ArrayList<CustomerModel>();
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
				
				cust.add(new CustomerModel(accountNumber, firstName, lastName, userName, password, routNumber, balance, saveBalance, jointNumber, validAccount));
			}
			
			return cust;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}	
	
	//get one customer in the table
	public CustomerModel searchCustomers(String user)
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
			CustomerModel cust = new CustomerModel();
			while(rs.next())
			{
				if(rs.getString("user_name").equals(user))
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
					
					cust = new CustomerModel(accountNumber, firstName, lastName, userName, password, routNumber, balance, saveBalance, jointNumber, validAccount);
					return cust;
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
	
	//get 1 joint customer in the table
	public CustomerModel searchJointCustomers(int jnum)
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//create a statement & result set
			Statement statement = cMan.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM jointaccounts WHERE joint_num = " + jnum);
			
			//create a list to store all of them
			CustomerModel cust = new CustomerModel();
			while(rs.next())
			{
				if(rs.getInt("joint_num") == jnum)
				{
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					double balance = rs.getDouble("balance");
					int jointNumber = rs.getInt("joint_num");
					
					cust = new CustomerModel(firstName, lastName, balance, jointNumber);
					return cust;
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
	
	//adds a customer to the DB
	public void setCustomer(CustomerModel NC)
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
	
	public void setJoint(CustomerModel JC)
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//query to be executed
			String query = "INSERT into jointaccounts (joint_num, first_name, last_name, balance)" + " values (?,?,?,?)";
			
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

	public boolean bIsCustomer(String cm) 
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			String uName;
			
			//create a statement & result set
			String query = "SELECT * FROM customers";
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				uName = rs.getString("user_name");
				if(uName.equals(cm))
					return true;
			}
			
			return false;
		}
		catch(SQLException e) {}
		return false;
	}

	@Override
	public CustomerModel searchCustomers(int app) 
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			
			//create a statement & result set
			Statement statement = cMan.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM Customers");
			
			//create a list to store all of them
			CustomerModel cust = new CustomerModel();
			while(rs.next())
			{
				if(rs.getInt("valid_aacount") == app)
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
					
					cust = new CustomerModel(accountNumber, firstName, lastName, userName, password, routNumber, balance, saveBalance, jointNumber, validAccount);
					return cust;
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

	@Override
	public void UpdateCustomer(CustomerModel cm) 
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

	@Override
	public boolean bHasSavings(int cm) 
	{
		try
		{
			Connection cMan = ConnectManager.getConnect();
			int uName;
			
			//create a statement & result set
			String query = "SELECT * FROM savings";
			PreparedStatement pstmt = cMan.prepareStatement(query);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next())
			{
				uName = rs.getInt("account");
				if(uName == cm)
					return true;
			}
			
			return false;
		}
		catch(SQLException e) {}
		return false;
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

	@Override
	public void UpdateJoint(CustomerModel cm) 
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
	
	//adds a log to the DB from a customer
	public void setLog(CustomerModel NC, String mes)
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
}
