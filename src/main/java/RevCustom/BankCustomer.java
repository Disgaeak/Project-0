package RevCustom;

import Revature.Project_0.BankAdmin;
import Revature.Project_0.ImpBanking;
import Revature.Project_0.ProjectDriver;

import java.util.Scanner;

public class BankCustomer implements ImpBanking
{
	//customer info
	public String firstName;
	public String lastName;
	public String userName;
	public String passWord;
	public int accountNum;
	public int routNum;
	public double balance;
	
	//used for banks
	public int validAccount = 2;
	public BankAdmin admin;
	
	@Override
	public void viewAccount() 
	{
		//check info on account
		switch(validAccount)
		{
		case 0:
			//account application has been denied
			System.out.println("Your application has been denied");
			break;
		case 1:
			//account has been accepted
			System.out.println("First name: " + firstName + "\n" + "Last Name: " + lastName + "\n" + "username: " + userName);
			break;
		case 2:
			//account is still pending
			System.out.println("Your account application is still pending. Please wait.");
			break;
		default:
			
		}
	}
	
	public void openAccount()
	{
		admin = ProjectDriver.boss;
		
		BankCustomer nCustomer = new BankCustomer();
		System.out.println("Please Enter First name");
		Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		String input = myObj.nextLine();  // Read user input
		nCustomer.firstName = input;
		System.out.println("Please enter last name");
		input = myObj.nextLine();
		nCustomer.lastName = input;
		System.out.println("Please enter a username");
		input = myObj.nextLine();
		nCustomer.userName = input;
		System.out.println("Please enter a password");
		input = myObj.nextLine();
		nCustomer.passWord = input;
		
		admin.customerApplications.add(nCustomer);
		ProjectDriver.LoginMenu();
	}
}
