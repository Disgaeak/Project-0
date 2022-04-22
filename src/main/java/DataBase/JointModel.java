package DataBase;

import RevCustom.BankCustomer;

public class JointModel extends BankCustomer
{
	
	public JointModel(int jNum, String first, String last, double balance)
	{
		firstName = first;
		lastName = last;
		this.balance = balance;
		jointNum = jNum;
	}

	@Override
	public String toString() {
		return "JointModel [firstName=" + firstName + ", lastName=" + lastName + ", balance=" + balance
				+ ", jointNumber=" + jointNum + "]";
	}
	
	public JointModel()
	{
		
	}
}
