package DataBase;

import Revature.Project_0.BankAdmin;
import Revature.Project_0.BankEmployee;

public interface EmployeeDAO_I 
{
	//finds an employee or admin based on code
	public BankAdmin searchEmployee(int code);
	
	//creates a new employee or admin in the DB
	public void setEmployee(BankAdmin NC);
	
	//checks if employee or admin is in the DB
	public boolean bIsEmployee(int cm);
	
	//updates employee or admin info
	public void UpdateEmployee(BankEmployee be);
	
	//Removes information from DB
	public void deleteEmployee(int code);
}
