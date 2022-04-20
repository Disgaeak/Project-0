package Revature.Project_0;

import RevCustom.BankCustomer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankAdmin implements ImpBanking
{
	//for all classes
	public String firstName;
	public String lastName;
	public ArrayList<BankCustomer> customers = new ArrayList<BankCustomer>();
	public Map<Integer, Double> customerSavings = new HashMap<>();
	public Map<Integer, BankCustomer> customerJoints = new HashMap<>();
	public ArrayList<BankCustomer> customerApplications = new ArrayList<BankCustomer>();
	public ArrayList<BankEmployee> employees = new ArrayList<BankEmployee>();
	
	//bank only class
	public int bankCode;
	
	//for this class
	private Scanner myObj = new Scanner(System.in);
	private int scanInt;
	
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
			viewAccount(employees.get(0));
			break;
		case 3:
			viewAccount(customers.get(0));
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
	private void viewAccount(BankCustomer name)
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
		accountMenu();
	}
	
	//check if any applications to open account are available and either accept or deny or pending
	public void checkApplications()
	{
		if(customerApplications.size() != 0)
		{
			viewAccount(customerApplications.get(0));
			System.out.println("Do you wish to Accept account?" + "\n" + "press 0 to deny, 1 to accept, 2 to leave pending");
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
			accountMenu();
		}
		
		accountMenu();
	}
	
	//check if any applications to open account are available and either accept or deny or pending
	public void checkApplications(BankEmployee k)
	{
		if(customerApplications.size() != 0)
		{
			viewAccount(customerApplications.get(0));
			System.out.println("Do you wish to Accept account?" + "\n" + "press 0 to deny, 1 to accept, 2 to leave pending");
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
			k.accountMenu();
		}
		
		k.accountMenu();
	}
	
	//deposits money into a customer account
	public void depositAmount()
	{
		System.out.println("Which customer?");
		//TODO: have it loop through all customers and select manually
		BankCustomer k = customers.get(0);
		
		//input the amount deposited in account
		System.out.println("Please Enter amount you want to deposit");
		double money = myObj.nextDouble();
		
		//checks for negative input to deposit
		if(k.balance < k.balance + money)
		{
			System.out.println("The amount is less than current");
			accountMenu();
		}
		else
		{
			k.balance += money;
			System.out.println("The amount has been added to their account");
			accountMenu();
		}
	}
}
