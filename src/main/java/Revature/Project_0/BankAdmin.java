package Revature.Project_0;

import RevCustom.BankCustomer;

import java.time.LocalDate;
import java.util.Scanner;

import DataBase.CustomerDAO;
import DataBase.EmployeeDAO;
import DataBase.JointDAO;

public class BankAdmin implements ImpBanking
{
	//for all classes
	public String firstName;
	public String lastName;
	public int bankCode;
	public double hrPay;
	public int lv;
	public LocalDate startDay;
	
	//for this class
	private Scanner myObj = new Scanner(System.in);
	private int scanInt;
	private CustomerDAO cdao = new CustomerDAO();
	private EmployeeDAO edao = new EmployeeDAO();
	private JointDAO jdao = new JointDAO();
	private BankCustomer bc;
	private BankEmployee be;
	private String input;
	private BankCustomer c;
	private double scandoub;
	
	//constructor used for creating an admin/employee
	public BankAdmin(int code, String firstName2, String lastName2, LocalDate start, double pay, int validAccount) 
	{
		bankCode = code;
		firstName = firstName2;
		lastName = lastName2;
		startDay = start;
		hrPay = pay;
		lv = validAccount;
	}

	public BankAdmin() 
	{
		
	}

	@Override
	public void accountMenu() 
	{
		//account menu for the admin
		System.out.println("please select an option:" + "\n" + "1- View account" + "   " + "2- View employee Menu"+ "\n" + "3- Hire employee" + "   " +
		"4- View customer account" + "\n" + "5- Check applications" + "   " + "6- Change Customer Account" + "\n" + "7- Log off");
		scanInt = myObj.nextInt();
		
		switch(scanInt)
		{
		case 1:
			viewAccount(); //view self account
			break;
		case 2:
			AlterAccount(); //switches to a menu to change employee info
			break;
		case 3:
			HireEmployee(); //creates a new employee for DB
			break;
		case 4:
			System.out.println("Enter the username of the customer");
			input = myObj.next();
			
			//searches for customer
			c = cdao.searchCustomers(input);
			if(c != null)
				viewAccount(c); //found the customer
			else
				System.out.println("this account doesn't exist."); //didn't found the customer
			
			break;
		case 5:
			//check if there are pending customer applications
			checkApplications();
			break;
		case 6:
			System.out.println("Enter the username of the customer");
			input = myObj.next();
			
			//searches for customer to change the account
			c = cdao.searchCustomers(input);
			if(c != null)
				AlterAccount(c);
			else
				System.out.println("this account doesn't exist.");
			
			break;
		case 7:
			//logs off to go back to login menu
			System.out.println("now logging off");
			ProjectDriver.LoginMenu();
			break;
			default:
		}
		
		//used when finished to loop back unless logging off
		accountMenu();
	}
	
	//Shared methods /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void viewAccount(BankCustomer name)
	{
		//views a customer main info
		System.out.println("First name: " + name.firstName + " " + name.lastName + "\n" + "Username: " + name.userName + "\n" 
				+ "Account #: " + name.accountNum + " " + name.routNum + "\n" + "Balance: $" + name.balance + "\n" + "Joint #: " + name.jointNum);
	}
	
	@Override
	public void viewAccount() 
	{
		//views self info
		System.out.println("Name: " + firstName + " " + lastName + "\n" + "Bank Code: " + bankCode + "\n" + "Start date: " + startDay + "\n" 
				+ "Hourly wage: $" + hrPay + "\n" + "Rank: " + lv);
	}
	
	public void viewSavings(BankCustomer n)
	{
		//views customer savings account(if applicable)
		System.out.println("First name: " + n.firstName + " " + n.lastName + "\n" + "Username: " + n.userName + "\n" 
				+ "Savings Balance: $" + n.saveBalance);
	}
	
	public void viewJoint(BankCustomer n)
	{
		//views customer joint account (if applicable)
		System.out.println("First name: " + n.firstName + " " + n.lastName + "\n" + "Balance: $" + n.balance + "\n" 
				+ "Joint: #" + n.jointNum);
	}
	
	//check if any applications to open account are available and either accept or deny or pending
	protected void checkApplications()
	{
		bc = cdao.searchCustomers();
		
		if(bc != null)
		{
			viewAccount(bc);
			System.out.println("Do you wish to Accept account?" + "\n" + "press 0 to deny, 1 to accept");
			int in = myObj.nextInt();
			
			switch(in) 
			{
			case 0:
				//account is denied or canceled
				bc.validAccount = 0;
				cdao.UpdateCustomer(bc);
				ProjectDriver.demo.info(bankCode +  " has denied " + bc.userName + "'s account");
				cdao.setLog(bankCode, "has denied " + bc.userName + " account");
				checkApplications();
				break;
			case 1:
				//account becomes valid and active
				bc.validAccount = 1;
				cdao.UpdateCustomer(bc);
				ProjectDriver.demo.info(bankCode +  " has accepted " + bc.userName + "'s account");
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
			//auto returns menu if no applications available
			System.out.println("There are no pending applications.");
		}
	}
	
	public BankEmployee convertBA(BankAdmin test)
	{
		//manually used to convert bank admin to bank employee to prevent casting
		BankEmployee e = new BankEmployee();
		e.firstName = test.firstName;
		e.lastName = test.lastName;
		e.bankCode = test.bankCode;
		e.hrPay = test.hrPay;
		e.startDay = test.startDay;
		e.lv = 0;
		
		return e;
	}
	//End of Shared methods /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//Admin only methods //////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void viewAccount(BankEmployee name)
	{
		//views account of employees
		System.out.println("Name: " + name.firstName + " " + name.lastName + "\n" + "Bank Code: " + name.bankCode + "  Rank: " + name.lv 
				+ "\n" + "Hourly wage is $" + name.hrPay + "\n" + "Start date: " + name.startDay);
	}
	
	private void AlterAccount()
	{
		// ability for admin to make changes on an employee's account
		System.out.println("What would you like to do?:" + "\n" + "1- View Employee Account" + "   " + "2- Change employee's name"+ "\n" + "3- Change employee's pay" + 
				"   " + "4- Change employee's rank" + "\n" + "5- Fire employee" + "   " + "6- Return to menu");
				scanInt = myObj.nextInt();
				
		switch(scanInt)
		{
		case 1:
			System.out.println("Enter the code for Employee: ");
			scanInt = myObj.nextInt();
			
			//checks if code is valid
			if(edao.bIsEmployee(scanInt))
				be = convertBA(edao.searchEmployee(scanInt));
			else
				System.out.println("This employee is invalid");
			
			viewAccount(be);
			break;
		case 2:
			//changes name of employee in the DB
			System.out.println("Enter the code of the employee: ");
			scanInt = myObj.nextInt();
			be = convertBA(edao.searchEmployee(scanInt));
			
			//changes first name
			System.out.println("Current name is: " + be.firstName + " " + be.lastName + "\n" + "Enter new first name for employee: ");
			input = myObj.next();
			be.firstName = input;
			
			//changes last name
			System.out.println("Enter new last name for employee: ");
			input = myObj.next();
			be.lastName = input;
			
			//updates and logs it in the DB
			edao.UpdateEmployee(be);
			ProjectDriver.demo.info(bankCode +  " has updated " + be.bankCode + "'s name");
			cdao.setLog(bankCode, " has updated " + be.bankCode + "'s name.");
			System.out.println("Successfully updated information");
			break;
		case 3:
			System.out.println("Enter the code of the employee: ");
			scanInt = myObj.nextInt();
			be = convertBA(edao.searchEmployee(scanInt));
			
			//changes the hourly pay of the employee
			if(be != null)
			{
				System.out.println("Current hourly wage is: $" + be.hrPay + "\n" + "Enter new hourly wage for employee: ");
				scandoub = myObj.nextDouble();
				be.hrPay = scandoub;
				
				//updates the information to the DB
				edao.UpdateEmployee(be);
				ProjectDriver.demo.info(bankCode + " has updated " + be.bankCode + "'s hourly wage.");
				cdao.setLog(bankCode, " has updated " + be.bankCode + "'s hourly wage.");
				System.out.println("Successfully updated information");
			}
			else
				System.out.println("Invalid code"); 
			
			break;
		case 4:
			System.out.println("Enter the code of the employee: ");
			scanInt = myObj.nextInt();
			be = convertBA(edao.searchEmployee(scanInt));
			
			//changes rank of the employee
			if(be != null)
			{
				System.out.println("Current rank is: " + be.lv + "\n" + "Enter new rank for employee: ");
				scanInt = myObj.nextInt();
				be.lv = scanInt;
				
				edao.UpdateEmployee(be);
				ProjectDriver.demo.info(bankCode + " has updated " + be.bankCode + "'s rank.");
				cdao.setLog(bankCode, " has updated " + be.bankCode + "'s rank.");
				System.out.println("Successfully updated information");
			}
			else
				System.out.println("Invalid code");
			
			break;
		case 5:
			System.out.println("Enter the code of the employee: ");
			scanInt = myObj.nextInt();
			
			if(edao.bIsEmployee(scanInt))
			{
				System.out.println("Are you sure you want to fire " + scanInt + " and remove records?" + "\n" + "Type Yes/No");
				input = myObj.next();
				
				if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y"))
				{
					edao.deleteEmployee(scanInt);
					ProjectDriver.demo.info(bankCode + " has fired " + scanInt);
					cdao.setLog(bankCode, " has fired " + scanInt);
				}
				else
					AlterAccount();
			}
			else
			{
				System.out.println("Employee doesn't exist");
			}
			
			break;
		case 6:
			accountMenu();
			break;
			default:
				accountMenu();
		}
		
		AlterAccount();
	}
	
	private void AlterAccount(BankCustomer cust)
	{
		// ability for admin to make changes on a customer's account
		System.out.println("What would you like to change?:" + "\n" + "1- Name" + "   " + "2- Username / password"+ "\n" + "3- Balance" + 
				"   " + "4- Savings Balance" + "\n" + "5- Joint balance" + "   " + "6- Wire transfer" + "\n" + "7- Validation" + "   " + "8- Return to Menu");
				scanInt = myObj.nextInt();
				
		switch(scanInt)
		{
		case 1:
			//changes first name of the customer
			System.out.println("First name is: "  + cust.firstName + "\n" + "Enter new first name: ");
			input = myObj.next();
			cust.firstName = input;
			
			//changes last name of the customer
			System.out.println("Last name is: "  + cust.lastName + "\n" + "Enter new last name: ");
			input = myObj.next();
			cust.lastName = input;
			
			//update changes to DB and logs it
			cdao.UpdateCustomer(cust);
			cdao.setLog(bankCode, "has updated the name of  " + cust.userName);
			break;
		case 2:
			//changes username of the customer
			System.out.println("Username is: "  + cust.userName + "\n" + "Enter new username: ");
			input = myObj.next();
			cust.firstName = input;
			
			//changes password of the customer
			System.out.println("Password is: "  + cust.passWord + "\n" + "Enter new password: ");
			input = myObj.next();
			cust.lastName = input;
			
			//updates changes to DB and logs it
			cdao.UpdateCustomer(cust);
			cdao.setLog(bankCode, "has updated the login info of " + cust.userName);
			break;
		case 3:
			//changes customer checking balance
			System.out.println("Current balance is: $"  + cust.balance + "\n" + "Enter new balance: ");
			scandoub = myObj.nextDouble();
			cust.balance = scandoub;
			
			//updates to DB and log
			cdao.UpdateCustomer(cust);
			cdao.setLog(bankCode, "has updated " + cust.userName + " checking balance to $" + cust.balance);
			break;
		case 4:
			//changes customer savings balance
			System.out.println("Current Savings is: $"  + cust.balance + "\n" + "Enter new balance: ");
			scandoub = myObj.nextDouble();
			cust.saveBalance = scandoub;
			
			//updates to the DB and logs it
			cdao.UpdateCustomer(cust);
			cdao.setLog(bankCode, "has updated " + cust.userName + " savings balance to $" + cust.balance);
			break;
		case 5:
			bc = jdao.searchJointCustomers(cust.jointNum);
			
			if(bc != null)
			{
				//if customer has a joint account, changes the balance
				System.out.println("Current joint balance is: $"  + bc.balance + "\n" + "Enter new balance: ");
				scandoub = myObj.nextDouble();
				bc.balance = scandoub;
				
				jdao.UpdateJoint(bc);
				cdao.setLog(bankCode, "has updated " + cust.userName + " joint balance to $" + cust.balance);
			}
			else
			{
				//customer doesn't have a joint account
				System.out.println("This customer doesn't have a joint account");
			}
			break;
		case 6:
			System.out.println("Please Enter the username from whom to take the money: ");
			input = myObj.next();
			
			System.out.println("Please enter the username to give the money to: ");
			String inTwo = myObj.next();
			
			c = cdao.searchCustomers(input);
			BankCustomer cTwo = cdao.searchCustomers(inTwo);
			
			if(c != null && cTwo != null)
			{
				System.out.println("Enter the amount you wish to transfer: ");
				scandoub = myObj.nextDouble();
				
				wireTransfer(c, cTwo, scandoub);
			}
			else
			{
				System.out.println("Error: could not find customer.");
			}
			
			break;
		case 7:
			//changes the validation of the customer account
			System.out.println("Valid: 0 = Deny, 1 = valid, 2 = pending" + "\n"
					+ "Current Validation is: "  + cust.validAccount + "\n" + "Enter new validation: ");
			scanInt = myObj.nextInt();
			cust.validAccount = scanInt;
			
			cdao.UpdateCustomer(cust);
			switch(scanInt)
			{
			case 0:
				//account is denied/ canceled
				cdao.setLog(bankCode, "has updated the validation of " + cust.userName + " to denied");
				break;
			case 1:
				//account is accepted
				cdao.setLog(bankCode, "has updated the validation of " + cust.userName + " to accepted");
				break;
			case 2:
				//account is pending/ under investigation
				cdao.setLog(bankCode, "has updated the validation of " + cust.userName + " to pending");
				break;
				default:
					accountMenu();
			}
			break;
		case 8:
			accountMenu();
			break;
			default:
				accountMenu();
		}
		
		AlterAccount(cust);
	}
	
	private void HireEmployee()
	{
		BankAdmin hold = new BankAdmin();
		
		//creates a unique bank code for the new employee
		int c = RNG();
		while(edao.searchEmployee(c) != null)
			c = RNG();
		
		hold.bankCode = c;
		
		//employees name
		System.out.println("Enter employee's first name: ");
		input = myObj.next();
		hold.firstName = input;
		
		System.out.println("Enter employee's last name: ");
		input = myObj.next();
		hold.lastName = input;
		
		//the starting date of the employee/admin
		hold.startDay = LocalDate.now();
		
		//sets the hourly wage of employee/admin
		System.out.println("Enter employee's hourly pay: ");
		scandoub = myObj.nextDouble();
		hold.hrPay = scandoub;
		
		//sets rank
		System.out.println("Enter Rank: 0- employee or 1- Admin");
		scanInt = myObj.nextInt();
		hold.lv = scanInt;
		
		//adds to the DB and logs it
		edao.setEmployee(hold);
		cdao.setLog(bankCode, " has hired " + hold.bankCode + "!");
		ProjectDriver.demo.info(bankCode +  " has hired " + hold.bankCode + "!");
		System.out.println("Employee " + hold.bankCode + " has been hired!");
	}
	
	private int RNG()
	{
		//used for generating a random number for the bank code
		int n;
		int min = 1000;
		int max = 9999;
		
		n = (int) Math.floor(Math.random()*(max-min+1)+min);
		
		return n;
	}
	
	private void wireTransfer(BankCustomer A, BankCustomer B, double amt)
	{
		//transfers money from A to B
		if(amt < A.balance && amt > 0)
		{
			A.balance -= amt;
			B.balance += amt;
			
			cdao.UpdateCustomer(B);
			cdao.UpdateCustomer(A);
			cdao.setLog(bankCode, " has transfered $" + amt + " from " + A.userName + " to " + B.userName);
			ProjectDriver.demo.info(bankCode + " has transfered $" + amt + " from " + A.userName + " to " + B.userName);
			System.out.println("Success ");
		}
		else
			System.out.println("Insufficient funds to transfer");
	}
}
