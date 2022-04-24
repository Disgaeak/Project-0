package DataBase;

import RevCustom.BankCustomer;

public class CustomerModel extends BankCustomer
{
	//constructor for setting data
	public CustomerModel(int accNum, String nfirst, String nLast, String uName, String passW, int rNum, double bal, double sBal,
			int jNum, int validy)
	{
		accountNum = accNum;
		firstName = nfirst;
		lastName = nLast;
		userName = uName;
		passWord = passW;
		routNum = rNum;
		balance = bal;
		saveBalance = sBal;
		jointNum = jNum;
		validAccount = validy;
	}
	

	//Empty constructor FTW
	public CustomerModel(String nfirst, String nLast, double bal, int jNum)
	{
		
	}
	
	@Override
	public String toString() {
		return "CustomerModel [accountNumber=" + accountNum + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userName=" + userName + ", password=" + passWord + ", routNumber=" + routNum + ", balance="
				+ balance + ", saveBalance=" + saveBalance + ", jointNumber=" + jointNum + ", validAccount="
				+ validAccount + "]";
	}
	
	
	//Empty constructor FTW
	public CustomerModel()
	{
		
	}
}
