package RevCustom;

import Revature.Project_0.ImpBanking;
import Revature.Project_0.ProjectDriver;

import java.util.ArrayList;
import java.util.Scanner;

import DataBase.CustomerDAO;
import DataBase.JointDAO;

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
	
	//for method inputs
	private Scanner myObj = new Scanner(System.in);
	private String input;
	private double scandoub;
	private int scanInt;
	private static CustomerDAO cdao = new CustomerDAO();
	private static JointDAO jdao = new JointDAO();
	private BankCustomer hold;
	
	public BankCustomer(int accNum, String nfirst, String nLast, String uName, String passW, int rNum, double bal, double sBal,
			int jNum, int validy)
	{
		accountNum = accNum;
		firstName = nfirst;
		lastName = nLast;
		userName = uName;
		passWord = passW;
		routNum = rNum;
		balance = bal;
		saveBalance = sBal;
		jointNum = jNum;
		validAccount = validy;
	}
	
	public BankCustomer(String nfirst, String nLast, double bal, int jNum)
	{
		//this constructor is used for joint account info
	}
	
	public BankCustomer()
	{
		//blank constructor just in case
	}
	
	public String toString() {
		return "accountNumber=" + accountNum + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userName=" + userName + ", password=" + passWord + ", routNumber=" + routNum + ", balance="
				+ balance + ", saveBalance=" + saveBalance + ", jointNumber=" + jointNum + ", validAccount="
				+ validAccount ;
	}
	
	@Override
	public void accountMenu() 
	{
		//account menu for the customer who logged in
		System.out.println("please select an option:" + "\n" + "1- View account" + "  " + "2- Deposit amount" + "\n" + "3- Withdraw amount"
				+ "  " + "4- Savings" + "\n" + "5- Joint account" + "  " + "6- log off");
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
		//view self info
		System.out.println("First name: " + firstName + "\n" + "Last Name: " + lastName + "\n" + "username: " + userName + 
				"\n" + "Account Number: " + accountNum + "\n" + "Routing Number: " + routNum + "\n" + "Balance: $" + balance);
		accountMenu();
	}
	
	public void openAccount()
	{	
		//sets up information for the new account
		BankCustomer nCustomer = new BankCustomer();
		
		//user enters first name
		System.out.println("Please Enter First name");
		input = myObj.nextLine();  
		nCustomer.firstName = input;
		
		//user enters last name
		System.out.println("Please enter last name");
		input = myObj.nextLine();
		nCustomer.lastName = input;
		
		//user enters a username, it's checked if already exists
		System.out.println("Please enter a username");
		input = myObj.nextLine();
		while(cdao.bIsCustomer(input))
		{
			System.out.println("That username is taken: ");
			input = myObj.next();
		}
		nCustomer.userName = input;
		
		//user enters a password
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
		
		//this puts it in pending application
		nCustomer.validAccount = 2;
		
		//adds customer to the database
		cdao.setCustomer(nCustomer);
		
		//logs information
		ProjectDriver.demo.info(nCustomer.userName +  " has created new account.");
		cdao.setLog(nCustomer, " has created new account.");
		ProjectDriver.LoginMenu();
	}
	
	public int RNG()
	{
		int n;
		int min = 1000000;
		int max = 9999999;
		
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
				
				//updates information and logs it
				cdao.UpdateCustomer(this);
				ProjectDriver.demo.info(userName +  " has deposited $" + scandoub + " into savings");
				cdao.setLog(this, " has deposited $" + scandoub + " into savings");
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
			
			//updates information and logs it
			cdao.UpdateCustomer(this);
			ProjectDriver.demo.info(userName +  " has deposited $" + scandoub + " into checkings");
			cdao.setLog(this, " has deposited $" + scandoub + " into checkings");
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
				//customer makes a new savings account and logs it
				cdao.setSavings(accountNum);
				ProjectDriver.demo.info(userName +  " has created new savings account.");
				cdao.setLog(this, " has created a new savings account");
				System.out.println("Savings account has been created.");
				accountMenu();
			}
			else
				accountMenu(); //customer doesn't want a savings account
		}
	}
	
	private void openJoint() 
	{
		BankCustomer jc = jdao.searchJointCustomers(jointNum);
		
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
					jdao.UpdateJoint(jc);
					cdao.UpdateCustomer(this);
					System.out.println("The amount has been deposited.");
					ProjectDriver.demo.info(userName +  " has deposited $" + scandoub + " into joint account");
					cdao.setLog(this, " has deposited $" + scandoub + " into joint account");
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
					jdao.UpdateJoint(jc);
					cdao.UpdateCustomer(this);
					System.out.println("The amount has been withdrawn.");
					ProjectDriver.demo.info(userName +  " has withdrawn $" + scandoub + " from joint account");
					cdao.setLog(this, " has withdrawn $" + scandoub + " from joint account");
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
				jdao.setJoint(this);
				cdao.UpdateCustomer(hold);
				
				ProjectDriver.demo.info(userName +  " has created a joint account with " + hold.userName);
				cdao.setLog(this, " has created a joint account with " + hold.userName);
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
				cdao.UpdateCustomer(this);
				ProjectDriver.demo.info(userName +  " has withdrawn $" + scandoub + " from savings");
				cdao.setLog(this, " has withdrawn $" + scandoub + " from savings");
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
				//updates information of customer
				cdao.UpdateCustomer(this);
				ProjectDriver.demo.info(userName +  " has withdrawn $" + scandoub + "  from checkings");
				cdao.setLog(this, " has withdrawn $" + scandoub + " from checkings");
				System.out.println("The amount has been taken from your account");
				accountMenu();
			}
		}
	}
}
