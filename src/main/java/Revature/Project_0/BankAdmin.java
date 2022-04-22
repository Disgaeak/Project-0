package Revature.Project_0;

import RevCustom.BankCustomer;
import java.util.ArrayList;
import java.util.Scanner;

import DataBase.CustomerDAO;
import DataBase.CustomerModel;

public class BankAdmin implements ImpBanking
{
	//for all classes
	public String firstName;
	public String lastName;
	
	//bank only class
	public int bankCode;
	
	//for this class
	private Scanner myObj = new Scanner(System.in);
	private int scanInt;
	private CustomerDAO cdao = new CustomerDAO();
	private BankCustomer bc;
	
	@Override
	public void accountMenu() 
	{
		//account menu for the admin
		System.out.println("please select an option:" + "\n" + "1- View account" + "\n" + "2- View employee account"+ "\n" + "3- View customer account" + 
		"\n" + "4- Check applications" + "\n" + "5- deposit money into customer account" + "\n" + "6- Log off");
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
			checkApplications();
			break;
		case 5:
			depositAmount();
			break;
		case 6:
			System.out.println("now logging off");
			ProjectDriver.LoginMenu();
			break;
			default:
		}
	}
	
	//Admin only method to view a specific account
	public void viewAccount(BankCustomer name)
	{
		System.out.println("First name: " + name.firstName + " " + name.lastName + "\n" + "Username: " + name.userName + "\n" 
				+ "Account #: " + name.accountNum + " " + name.routNum + "\n" + "Balance: $" + name.balance + "\n" + "Joint #: " + name.jointNum);
		accountMenu();
	}
	
	private void viewAccount(BankEmployee name)
	{
		System.out.println("First name: " + name.firstName + "\n" + "Last Name: " + name.lastName);
		accountMenu();
	}
	
	@Override
	public void viewAccount() 
	{
		System.out.println("First name: " + firstName + "\n" + "Last Name: " + lastName);
		accountMenu();
	}
	
	//check if any applications to open account are available and either accept or deny or pending
	private void checkApplications()
	{
		bc = cdao.searchCustomers(2);
		
		if(bc != null)
		{
			viewAccount(bc);
			System.out.println("Do you wish to Accept account?" + "\n" + "press 0 to deny, 1 to accept");
			int in = myObj.nextInt();
			
			switch(in) 
			{
			case 0:
				bc.validAccount = 0;
				cdao.UpdateCustomer((CustomerModel)bc);
				checkApplications();
				break;
			case 1:
				bc.validAccount = 1;
				cdao.UpdateCustomer((CustomerModel)bc);
				checkApplications();
				break;
			default:
				System.out.println("Not a valid answer. Now logging off");
				ProjectDriver.LoginMenu();
			}
		}
		else
		{
			System.out.println("There are no pending applications.");
			accountMenu();
		}
	}
	
	//check if any applications to open account are available and either accept or deny or pending
	public void checkApplications(BankEmployee k)
	{
		bc = cdao.searchCustomers(2);
		
		if(bc != null)
		{
			viewAccount(bc);
			System.out.println("Do you wish to Accept account?" + "\n" + "press 0 to deny, 1 to accept");
			int in = myObj.nextInt();
			
			switch(in) 
			{
			case 0:
				bc.validAccount = 0;
				checkApplications();
				break;
			case 1:
				bc.validAccount = 1;
				checkApplications();
				break;
			default:
				System.out.println("Not a valid answer. Now logging off");
				ProjectDriver.LoginMenu();
			}
		}
		else
		{
			System.out.println("There are no pending applications.");
			k.accountMenu();
		}
	}
	
	//deposits money into a customer account
	public void depositAmount()
	{
		System.out.println("Which customer?");
		//TODO: have it loop through all customers and select manually
		
		//input the amount deposited in account
		System.out.println("Please Enter amount you want to deposit");
		double money = myObj.nextDouble();
		
		
	}
}
