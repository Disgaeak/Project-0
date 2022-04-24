package Revature.Project_0;

import RevCustom.BankCustomer;
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
	private ProjectDriver pd;
	private String input;
	private CustomerModel c;
	private double scandoub;
	
	@Override
	public void accountMenu() 
	{
		//account menu for the admin
		System.out.println("please select an option:" + "\n" + "1- View account" + "   " + "2- View employee account"+ "\n" + "3- View customer account" + 
		"   " + "4- Check applications" + "\n" + "5- Change Customer Account" + "   " + "6- Log off");
		scanInt = myObj.nextInt();
		
		switch(scanInt)
		{
		case 1:
			viewAccount();
			break;
		case 2:
			viewAccount(pd.bE);
			break;
		case 3:
			System.out.println("Enter the username of the customer");
			input = myObj.next();
			
			c = cdao.searchCustomers(input);
			if(c != null)
				viewAccount(c);
			else
				System.out.println("this account doesn't exist.");
			
			break;
		case 4:
			checkApplications();
			break;
		case 5:
			System.out.println("Enter the username of the customer");
			input = myObj.next();
			
			c = cdao.searchCustomers(input);
			if(c != null)
				AlterAccount(c);
			else
				System.out.println("this account doesn't exist.");
			
			break;
		case 6:
			System.out.println("now logging off");
			ProjectDriver.LoginMenu();
			break;
			default:
		}
		
		accountMenu();
	}
	
	//Shared methods /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void viewAccount(BankCustomer name)
	{
		System.out.println("First name: " + name.firstName + " " + name.lastName + "\n" + "Username: " + name.userName + "\n" 
				+ "Account #: " + name.accountNum + " " + name.routNum + "\n" + "Balance: $" + name.balance + "\n" + "Joint #: " + name.jointNum);
	}
	
	@Override
	public void viewAccount() 
	{
		System.out.println("Name: " + firstName + " " + lastName + "\n" + "Bank Code: " + bankCode);
	}
	
	public void viewSavings(BankCustomer n)
	{
		System.out.println("First name: " + n.firstName + " " + n.lastName + "\n" + "Username: " + n.userName + "\n" 
				+ "Savings Balance: $" + n.saveBalance);
	}
	
	public void viewJoint(BankCustomer n)
	{
		System.out.println("First name: " + n.firstName + " " + n.lastName + "\n" + "Balance: $" + n.balance + "\n" 
				+ "Joint: #" + n.jointNum);
	}
	
	//check if any applications to open account are available and either accept or deny or pending
	protected void checkApplications()
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
				cdao.setLog(bankCode, "has denied " + bc.userName + " account");
				checkApplications();
				break;
			case 1:
				bc.validAccount = 1;
				cdao.UpdateCustomer((CustomerModel)bc);
				cdao.setLog(bankCode, "has accepted " + bc.userName + " account");
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
		}
	}
	//End of Shared methods /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Admin only methods //////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void viewAccount(BankEmployee name)
	{
		System.out.println("Name: " + name.firstName + " " + name.lastName + "\n" + "Bank Code: " + name.bankCode);
	}
	
	private void AlterAccount(BankCustomer cust)
	{
		// ability for admin to make changes on a customer's account
		System.out.println("What would you like to change?:" + "\n" + "1- Name" + "   " + "2- Username / password"+ "\n" + "3- Balance" + 
				"   " + "4- Savings Balance" + "\n" + "5- Joint balance" + "   " + "6- Validation" + "\n" + "7- Return to menu");
				scanInt = myObj.nextInt();
				
		switch(scanInt)
		{
		case 1:
			System.out.println("First name is: "  + cust.firstName + "\n" + "Enter new first name: ");
			input = myObj.next();
			cust.firstName = input;
			
			System.out.println("Last name is: "  + cust.lastName + "\n" + "Enter new last name: ");
			input = myObj.next();
			cust.lastName = input;
			
			cdao.UpdateCustomer((CustomerModel)cust);
			cdao.setLog(bankCode, "has updated the name of  " + cust.userName);
			break;
		case 2:
			System.out.println("Username is: "  + cust.userName + "\n" + "Enter new username: ");
			input = myObj.next();
			cust.firstName = input;
			
			System.out.println("Password is: "  + cust.passWord + "\n" + "Enter new password: ");
			input = myObj.next();
			cust.lastName = input;
			
			cdao.UpdateCustomer((CustomerModel)cust);
			cdao.setLog(bankCode, "has updated the login info of " + cust.userName);
			break;
		case 3:
			System.out.println("Current balance is: $"  + cust.balance + "\n" + "Enter new balance: ");
			scandoub = myObj.nextDouble();
			cust.balance = scandoub;
			
			cdao.UpdateCustomer((CustomerModel)cust);
			cdao.setLog(bankCode, "has updated " + cust.userName + " checking balance to $" + cust.balance);
			break;
		case 4:
			System.out.println("Current Savings is: $"  + cust.balance + "\n" + "Enter new balance: ");
			scandoub = myObj.nextDouble();
			cust.saveBalance = scandoub;
			
			cdao.UpdateCustomer((CustomerModel)cust);
			cdao.setLog(bankCode, "has updated " + cust.userName + " savings balance to $" + cust.balance);
			break;
		case 5:
			bc = cdao.searchJointCustomers(cust.jointNum);
			
			System.out.println("Current joint balance is: $"  + bc.balance + "\n" + "Enter new balance: ");
			scandoub = myObj.nextDouble();
			bc.balance = scandoub;
			
			cdao.UpdateJoint((CustomerModel)bc);
			cdao.setLog(bankCode, "has updated " + cust.userName + " joint balance to $" + cust.balance);
			break;
		case 6:
			System.out.println("Valid: 0 = Deny, 1 = valid, 2 = pending" + "\n"
					+ "Current Validation is: "  + cust.validAccount + "\n" + "Enter new validation: ");
			scanInt = myObj.nextInt();
			cust.validAccount = scanInt;
			
			cdao.UpdateCustomer((CustomerModel)cust);
			switch(scanInt)
			{
			case 0:
				cdao.setLog(bankCode, "has updated the validation of " + cust.userName + " to denied");
				break;
			case 1:
				cdao.setLog(bankCode, "has updated the validation of " + cust.userName + " to accepted");
				break;
			case 2:
				cdao.setLog(bankCode, "has updated the validation of " + cust.userName + " to pending");
				break;
				default:
					accountMenu();
			}
			break;
		case 7:
			accountMenu();
			break;
			default:
				accountMenu();
		}
		
		AlterAccount(cust);
	}
}
