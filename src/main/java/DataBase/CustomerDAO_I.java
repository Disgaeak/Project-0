package DataBase;

import java.util.ArrayList;

import RevCustom.BankCustomer;

public interface CustomerDAO_I 
{
	//get all customers
	public ArrayList<BankCustomer> getCustomers();
	
	//searches DB for a customer/employee/joint account
	public BankCustomer searchCustomers(String user);
	public BankCustomer searchCustomers();
	
	//adds to the DB
	public void setCustomer(BankCustomer NC);
	public void setSavings(int ns);
	public void setLog(BankCustomer NC, String mes);
	public void setLog(int bankCode, String mes);
	
	//checks if in the DB
	public boolean bIsCustomer(String cm);
	public boolean bHasSavings(int cm);
	
	//updates information in the DB row
	public void UpdateCustomer(BankCustomer cm);
	
	//removes customer from DB
	public void deleteCustomer(String cm);
	
}
