package Revature.Project_0;

import java.util.Scanner;
import RevCustom.BankCustomer;

public class BankEmployee extends BankAdmin
{
	//for this class
	private Scanner myObj = new Scanner(System.in);
	private int scanInt;
	private BankAdmin boss;
	
	public void accountMenu()
	{
		boss = ProjectDriver.boss;
		//account menu for the employee
		System.out.println("please select an option:" + "\n" + "1- View account" + "\n" + "2- View customer account" + "\n" + "3- View customer Savings"
				+ "\n" + "4- View Customer Joint Account" + "\n" + "5- View Applications" + "\n" + "6- log off");
		scanInt = myObj.nextInt();
		
		switch(scanInt)
		{
		case 1:
			viewAccount();
			break;
		case 2:
			
			break;
		case 3:
			
			break;
		case 4:
			break;
		case 5:
			boss.checkApplications(this);
			break;
		case 6:
			System.out.println("now logging off");
			ProjectDriver.LoginMenu();
			break;
			default:
		}
	}
	
	public void viewAccount() 
	{
		System.out.println("First name: " + firstName + "\n" + "Last Name: " + lastName);
		accountMenu();
	}
	
	public void viewAccount(BankCustomer n)
	{
		System.out.println("First name: " + n.firstName + " " + n.lastName + "\n" + "Username: " + n.userName + "\n" 
				+ "Account #: " + n.accountNum + " " + n.routNum + "\n" + "Balance: $" + n.balance + "\n" + "Joint #: " + n.jointNum);
		accountMenu();
	}
}
