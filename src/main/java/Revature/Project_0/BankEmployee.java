package Revature.Project_0;

import java.util.Scanner;

import DataBase.CustomerDAO;
import RevCustom.BankCustomer;

public class BankEmployee extends BankAdmin
{
	//for this class
	private Scanner myObj = new Scanner(System.in);
	private int scanInt;
	private String input;
	private CustomerDAO cdao = new CustomerDAO();
	private BankCustomer c;
	
	public void accountMenu()
	{
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
			System.out.println("Enter the username of the customer");
			input = myObj.next();
			
			c = cdao.searchCustomers(input);
			if(c != null)
				viewAccount(c);
			else
				System.out.println("this account doesn't exist.");
			
			break;
		case 3:
			System.out.println("Enter the username of the customer");
			input = myObj.next();
			
			c = cdao.searchCustomers(input);
			if(c != null)
				viewSavings(c);
			else
				System.out.println("this account doesn't exist.");
			
			break;
		case 4:
			System.out.println("Enter the username of the customer");
			input = myObj.next();
			
			c = cdao.searchCustomers(input);
			if(c != null)
			{
				scanInt = c.jointNum;
				c = cdao.searchJointCustomers(scanInt);
				if(c != null)
					viewJoint(c);
				else
					System.out.println("this Joint account doesn't exist.");
			}
			else
				System.out.println("this account doesn't exist.");
			
			break;
		case 5:
			checkApplications();
			break;
		case 6:
			System.out.println("now logging off");
			ProjectDriver.LoginMenu();
			break;
			default:
		}
		
		accountMenu();
	}
}
