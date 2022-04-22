package DataBase;

import java.util.ArrayList;

public interface CustomerDAO_I 
{
	//get all customers
	public ArrayList<CustomerModel> getCustomers();
	
	//get 1 customer in the table
	public CustomerModel searchCustomers(String user);
	
	//get 1 customer in the table
	public CustomerModel searchCustomers(int app);
	
	//get 1 customer in the table
	public CustomerModel searchJointCustomers(int jnum);
	
	//adds a customer to the DB
	public void setCustomer(CustomerModel NC);
	
	public void setJoint(CustomerModel JC);
	
	public boolean bIsCustomer(String cm);
	public boolean bHasSavings(int cm);
	public void setSavings(int ns);
	
	public void UpdateCustomer(CustomerModel cm);
	public void UpdateJoint(CustomerModel cm);
}
