package Revature.Project_0;

import RevCustom.BankCustomer;
import java.util.ArrayList;
import java.util.Scanner;

public class BankAdmin implements ImpBanking
{
	//for all classes
	public String firstName;
	public String lastName;
	public ArrayList<BankCustomer> customers = new ArrayList<BankCustomer>();
	public ArrayList<BankCustomer> customerApplications = new ArrayList<BankCustomer>();
	public ArrayList<BankEmployee> employees = new ArrayList<BankEmployee>();
	
	//bank only class
	public int bankCode;
	
	//Admin only method to view a specific account
	public void viewAccount(BankCustomer name)
	{
		System.out.println("First name: " + name.firstName + "\n" + "Last Name: " + name.lastName);
	}
	
	private void viewAccount(BankEmployee name)
	{
		System.out.println("First name: " + name.firstName + "\n" + "Last Name: " + name.lastName);
	}
	
	@Override
	public void viewAccount() 
	{
		System.out.println("First name: " + firstName + "\n" + "Last Name: " + lastName);
	}
	
	//check if any applications to open account are available and either accept or deny or pending
	public void checkApplications()
	{
		if(customerApplications.size() != 0)
		{
			viewAccount(customerApplications.get(0));
			System.out.println("Do you wish to Accept account?" + "\n" + "press 0 to deny, 1 to accept, 2 to leave pending");
			Scanner myObj = new Scanner(System.in);  // Create a Scanner object
			int in = myObj.nextInt();
			switch(in) 
			{
			case 0:
				customerApplications.get(0).validAccount = 0;
				customers.add(customerApplications.get(0));
				customerApplications.remove(0);
				break;
			case 1:
				customerApplications.get(0).validAccount = 1;
				customers.add(customerApplications.get(0));
				customerApplications.remove(0);
				break;
			case 2:
				customerApplications.get(0).validAccount = 2;
				break;
			default:
				System.out.println("Not a valid answer. Now logging off");
				ProjectDriver.LoginMenu();
			}
		}
		else
		{
			System.out.println("There are no pending applications.");
			//TODO: change to valid path instead of logging out
			ProjectDriver.LoginMenu();
		}
		
		System.out.println("Now Logging off.");
		//TODO: change to valid path instead of logging out
		ProjectDriver.LoginMenu();
	}
}
