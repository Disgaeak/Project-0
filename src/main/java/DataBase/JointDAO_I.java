package DataBase;

import RevCustom.BankCustomer;

public interface JointDAO_I 
{
	public BankCustomer searchJointCustomers(int jnum);
	
	public void setJoint(BankCustomer JC);
	
	public void UpdateJoint(BankCustomer cm);
	
	public void deleteJoint(BankCustomer cm);
}
