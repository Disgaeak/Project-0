package Revature.Project_0;

import RevCustom.BankCustomer;
import java.util.Scanner;

public class ProjectDriver 
{
	private static BankCustomer customer = new BankCustomer();
	private static BankEmployee bE = new BankEmployee();
	public static BankAdmin boss = new BankAdmin();
	private static BankCustomer temp;
	private static String userName;
	private static Scanner myObj = new Scanner(System.in); 
	
	public static void main(String[] args) 
	{
		//Hire 1 employee
		bE.firstName = "Robert";
		bE.lastName = "Dover";
		bE.bankCode = 1234;
		
		//create 1 Admin
		boss.firstName = "John";
		boss.lastName = "Wilhelm";
		boss.bankCode = 5647;
		
		LoginMenu();
	}
	
	public static void LoginMenu()
	{
		String op1 = "open";
		
		//Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		System.out.println("- Exception-al Bank Login Menu -" + "\n" + "Please Enter username or" + "\n" + "Type 'open' to open a new account.");
		String userName = myObj.nextLine();  // Read user input
		
		if(userName.equalsIgnoreCase(op1))
		{
			customer.openAccount();
		}
		else if(checkCustomer(userName))
		{
			//checks if the input username is a customer (accepted or denied)
			for(int i = 0;i < boss.customers.size();i++)
			{
				if(userName.equals(boss.customers.get(i).userName))
				{
					//when found username ask for password
					temp = boss.customers.get(i);
					System.out.println("please enter password.");
					userName = myObj.nextLine();
					
					//checks if password is correct
					if(userName.equals(temp.passWord))
						temp.viewAccount();
					else
					{
						System.out.println("The password is incorrect.");
						break;
					}
				}
			}
		}
		else if(checkPending(userName))
		{
			//if application is still pending find customer and let them know
			for(int i = 0;i < boss.customerApplications.size();i++)
			{
				if(userName.equals(boss.customerApplications.get(i).userName))
				{
					//when found username ask for password
					temp = boss.customerApplications.get(i);
					System.out.println("please enter password.");
					userName = myObj.nextLine();
					
					//checks if password is correct
					if(userName.equals(temp.passWord))
						temp.viewAccount();
					else
					{
						System.out.println("The password is incorrect.");
						break;
					}
				}
			}
		}
		else
		{
			int hold = Integer.parseInt(userName);
			
			//checks if employee or boss. if neither end program
			if(hold == 1234)
			{
				bE.checkApplications();
			}
			else if(hold == 5647)
			{
				boss.checkApplications();
			}
			else
				System.out.println("No username found.");
		}
	}
	
	public static boolean checkCustomer(String val)
	{
		boolean hasFound = false;
		
		//checks if the input username is a customer
		if(boss.customers.size() != 0)
		{
			for(int i = 0;i < boss.customers.size();i++)
			{
				if(val.equals(boss.customers.get(i).userName))
				{
					hasFound = true;
					break;
				}
			}
		}
			
		return hasFound;
	}
	
	public static boolean checkPending(String val)
	{
		boolean hasFound = false;
		
		//checks if the input username is still pending
		if(boss.customerApplications.size() != 0)
		{
			for(int i = 0;i < boss.customerApplications.size();i++)
			{
				if(val.equals(boss.customerApplications.get(i).userName))
				{
					hasFound = true;
					break;
				}	
			}
		}
		
		return hasFound;
	}
}
