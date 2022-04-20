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
		System.out.println("please select an option:" + "\n" + "1- View account" + "\n" + "2- View customer account" + "\n" + "3- Check applications"
				+ "\n" + "4- log off");
		scanInt = myObj.nextInt();
		
		switch(scanInt)
		{
		case 1:
			viewAccount();
			break;
		case 2:
			viewAccount(customers.get(0));
			break;
		case 3:
			boss.checkApplications(this);
			break;
		case 4:
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
		System.out.println("First name: " + n.firstName + "\n" + "Last Name: " + n.lastName);
		accountMenu();
	}
}
