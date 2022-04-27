package DataBase;

import Revature.Project_0.BankAdmin;
import Revature.Project_0.BankEmployee;

public interface EmployeeDAO_I 
{
	public BankAdmin searchEmployee(int code);
	public void setEmployee(BankAdmin NC);
	public boolean bIsEmployee(int cm);
	public void UpdateEmployee(BankEmployee be);
	
	//Removes information from DB
	public void deleteEmployee(int code);
}
