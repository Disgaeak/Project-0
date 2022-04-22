package RevCustom;

import Revature.Project_0.BankAdmin;
import Revature.Project_0.ImpBanking;
import Revature.Project_0.ProjectDriver;

import java.util.ArrayList;
import java.util.Scanner;

import DataBase.CustomerDAO;
import DataBase.CustomerModel;

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
	public double saveBalance;
	public ArrayList<String> JointName = new ArrayList<String>();
	public int jointNum;
	
	//used for banks
	public int validAccount = 2;
	public BankAdmin admin;
	
	//for method inputs
	private Scanner myObj = new Scanner(System.in);
	private String input;
	private double scandoub;
	private int scanInt;
	private static CustomerDAO cdao = new CustomerDAO();
	private BankCustomer hold;
	
	@Override
	public void accountMenu() 
	{
		admin = ProjectDriver.boss;
		
		//account menu for the customer who logged in(not for admin or employee)
		System.out.println("please select an option:" + "\n" + "1- View account" + "\n" + "2- Deposit amount" + "\n" + "3- Withdraw amount"
				+ "\n" + "4- Savings" + "\n" + "5- Joint account" + "\n" + "6- log off");
		scanInt = myObj.nextInt();
		
		switch(scanInt)
		{
		case 1:
			viewAccount();
			break;
		case 2:
			depositAmount(false);
			break;
		case 3:
			withdrawAmt(false);
			break;
		case 4:
			openSavings();
			break;
		case 5:
			openJoint();
			break;
		case 6:
			System.out.println("now logging off");
			ProjectDriver.LoginMenu();
			break;
			default:
		}
	}

	@Override
	public void viewAccount() 
	{
		//account has been accepted
		System.out.println("First name: " + firstName + "\n" + "Last Name: " + lastName + "\n" + "username: " + userName + 
				"\n" + "Account Number: " + accountNum + "\n" + "Routing Number: " + routNum + "\n" + "Balance: $" + balance);
		accountMenu();
	}
	
	public void openAccount()
	{
		//assigns values
		admin = ProjectDriver.boss;
		
		//sets up information for the new account
		BankCustomer nCustomer = new BankCustomer();
		System.out.println("Please Enter First name");
		input = myObj.nextLine();  // Read user input
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
		
		//makes a unique account number
		nCustomer.accountNum = RNG();
		while(cdao.getCustomers().contains(nCustomer.accountNum))
			nCustomer.accountNum = RNG();
		
		//makes unique routing number
		nCustomer.routNum = RNG();
		while(cdao.getCustomers().contains(nCustomer.routNum))
			nCustomer.routNum = RNG();
		
		//makes a unique joint number
		nCustomer.jointNum = RNG();
		while(cdao.getCustomers().contains(nCustomer.jointNum))
			nCustomer.routNum = RNG();
		
		//acc#, firstname, lastname, username, password, rout#, balance, savebalance, joint#, validation
		cdao.setCustomer(new CustomerModel(nCustomer.accountNum, nCustomer.firstName, nCustomer.lastName, nCustomer.userName, nCustomer.passWord,
				nCustomer.routNum, 0.0, 0.0, nCustomer.jointNum, 2));
		
		ProjectDriver.LoginMenu();
	}
	
	public int RNG()
	{
		int n;
		int min = 1000000;
		int max = 9999999;
		
		//creates a unique account and routing number for the customer
		n = (int) Math.floor(Math.random()*(max-min+1)+min);
		
		return n;
	}
	
	//deposits money into their account
	public void depositAmount(boolean bSavings)
	{
		System.out.println("Please Enter amount you want to deposit");
		scandoub = myObj.nextDouble();
		
		//checks if money goes to savings or checking
		if(bSavings)
		{	
			//takes money from checkings and deposit to savings
			if(balance > scandoub && scandoub > 0)
			{
				saveBalance += scandoub;
				balance -= scandoub;
				
				//updates information by casting customer to customer model
				cdao.UpdateCustomer((CustomerModel)this);
				System.out.println("The amount has been added to your account.");
				openSavings();
			}
			else
			{
				//customer is putting more than they have
				System.out.println("You don't have enough to deposit that amount.");
				openSavings();
			}
		}
		else
		{
			//checks if customer deposits a negative amount
			if(scandoub < 0.0)
				scandoub = Math.abs(scandoub);
			
			balance += scandoub;
			
			//updates information by casting customer to customer model
			cdao.UpdateCustomer((CustomerModel)this);
			System.out.println("The amount has been added to your account");
			accountMenu();
		}
	}
	
	public void openSavings()
	{
		if(cdao.bHasSavings(accountNum))
		{
			//savings menu for the customer
			System.out.println("please select an option:" + "\n" + "1- View Balance" + "\n" + "2- Deposit into Savings" + "\n" 
					+ "3- Withdraw from Savings" + "\n" + "4- Return");
			scanInt = myObj.nextInt();
			
			switch(scanInt)
			{
			case 1:
				//views current balance
				System.out.println("Balance: $" + saveBalance);
				openSavings();
				break;
			case 2:
				//deposits money into savings
				depositAmount(true);
				break;
			case 3:
				//withdraw money from savings
				withdrawAmt(true);
				break;
			case 4:
				accountMenu();
				break;
				default:
					accountMenu();
			}
		}
		else
		{
			//you dont have a savings
			System.out.println("You Don't have a savings account. Would you like to make one? Yes/No");
			input = myObj.next();
			
			if(input.equalsIgnoreCase("Yes"))
			{
				cdao.setSavings(accountNum);
				System.out.println("Savings account has been created.");
				accountMenu();
			}
			else
				accountMenu();
		}
	}
	
	private void openJoint() 
	{
		BankCustomer jc = cdao.searchJointCustomers(jointNum);
		
		if(jc != null)
		{
			//menu for joint account
			System.out.println("Joint Account menu: " + "\n" + "1- View Balance" + "\n" + "2- Deposit" 
					+ "\n" + "3- Withdraw" + "\n" + "4- Log off");
			scanInt = myObj.nextInt();
			
			switch(scanInt)
			{
			case 1:
				System.out.println("Main Owner: " + jc.firstName + " " + jc.lastName
						+ "\n" + "Balance: $" + jc.balance);
				openJoint();
				break;
			case 2:
				System.out.println("Please Enter the Amount you wish to deposit.");
				scandoub = myObj.nextDouble();
				
				if(balance > scandoub && scandoub > 0)
				{
					balance -= scandoub;
					jc.balance += scandoub;
					cdao.UpdateJoint((CustomerModel) jc);
					cdao.UpdateCustomer((CustomerModel)this);
					System.out.println("The amount has been deposited.");
					openJoint();
				}
				else
				{
					System.out.println("The amount is incorrect.");
					openJoint();
				}
				break;
			case 3:
				System.out.println("Please Enter the Amount you wish to withdraw.");
				scandoub = myObj.nextDouble();
				
				if(jc.balance > scandoub && scandoub > 0)
				{
					balance += scandoub;
					jc.balance -= scandoub;
					cdao.UpdateJoint((CustomerModel)jc);
					cdao.UpdateCustomer((CustomerModel)this);
					System.out.println("The amount has been withdrawn.");
					openJoint();
				}
				else
				{
					System.out.println("The amount is incorrect.");
					openJoint();
				}
				break;
			case 4:
				accountMenu();
				break;
				default:
					accountMenu();
			}
		}
		else
		{
			System.out.println("Please Enter the username to add to the joint account.");
			input = myObj.next();
			
			hold = cdao.searchCustomers(input);
			if(hold != null)
			{
				hold.jointNum = this.jointNum;
				cdao.setJoint((CustomerModel)this);
				cdao.UpdateCustomer((CustomerModel)hold);
				openJoint();
			}
			else
			{
				System.out.println("That username does't exist.");
				openJoint();
			}
		}
		
	}
	
	//withdraws money from either checking or savings
	public void withdrawAmt(boolean bSavings)
	{
		System.out.println("Please Enter amount you want to withdraw.");
		scandoub = myObj.nextDouble();
		
		//checks if money goes to savings or checking
		if(bSavings)
		{
			//takes money from savings and deposit to checkings
			if(saveBalance > scandoub && scandoub > 0)
			{
				saveBalance -= scandoub;
				balance += scandoub;
				//updates information by casting customer to customer model
				cdao.UpdateCustomer((CustomerModel)this);
				System.out.println("The amount has been added to your account.");
				openSavings();
			}
			else
			{
				//customer is putting more than they have
				System.out.println("You don't have sufficient funds.");
				openSavings();
			}
		}
		else
		{
			if(scandoub > balance || scandoub < 0)
			{
				System.out.println("You don't have sufficient funds.");
				accountMenu();
			}
			else
			{
				balance -= scandoub;
				//updates information by casting customer to customer model
				cdao.UpdateCustomer((CustomerModel)this);
				System.out.println("The amount has been taken from your account");
				accountMenu();
			}
		}
	}
}
