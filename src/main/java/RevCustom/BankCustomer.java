package RevCustom;

import Revature.Project_0.BankAdmin;
import Revature.Project_0.ImpBanking;
import Revature.Project_0.ProjectDriver;

import java.util.ArrayList;
import java.util.Map;
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
	private boolean s = false;
	
	@Override
	public void accountMenu() 
	{
		admin = ProjectDriver.boss;
		
		//account menu for the customer who logged in(not for admin or employee)
		System.out.println("please select an option:" + "\n" + "1- View account" + "\n" + "2- Deposit amount" + "\n" + "3- Withdraw amount"
				+ "\n" + "4- Savings" + "\n" + "5- View/ create Joint account" + "\n" + "6- log off");
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
		//check info on account
		switch(validAccount)
		{
		case 0:
			//account application has been denied
			System.out.println("Your application has been denied");
			break;
		case 1:
			//account has been accepted
			System.out.println("First name: " + firstName + "\n" + "Last Name: " + lastName + "\n" + "username: " + userName + 
					"\n" + "Account Number: " + accountNum + "\n" + "Routing Number: " + routNum + "\n" + "Balance: $" + balance);
			accountMenu();
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
		while(admin.customerSavings.containsKey(nCustomer.accountNum))
			nCustomer.accountNum = RNG();
		
		//checks if account and routing number are different
		nCustomer.routNum = RNG();
		while(nCustomer.accountNum == nCustomer.routNum)
			nCustomer.routNum = RNG();
		
		admin.customerApplications.add(nCustomer);
		ProjectDriver.LoginMenu();
	}
	
	private int RNG()
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
				admin.customerSavings.put(accountNum, saveBalance);
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
			System.out.println("The amount has been added to your account");
			accountMenu();
		}
	}
	
	public void openSavings()
	{
		Map<Integer, Double> h = admin.customerSavings;
		
		if(h.containsKey(accountNum))
		{
			//savings menu for the customer
			System.out.println("please select an option:" + "\n" + "1- View Balance/ open account" + "\n" + "2- Deposit into Savings" + "\n" 
					+ "3- Withdraw from Savings" + "\n" + "4- Return");
			scanInt = myObj.nextInt();
			
			switch(scanInt)
			{
			case 1:
				//views current balance, if they dont have a savings, then create one
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
			System.out.println("You have added a savings account");
			admin.customerSavings.put(accountNum, 0.0);
			openSavings();
		}
	}
	
	private void openJoint() 
	{
		//checks if user has a joint account
		if(admin.customerJoints.containsKey(jointNum))
		{
			BankCustomer c = admin.customerJoints.get(jointNum);
			
			//menu for joint account
			System.out.println("Joint Account menu: " + "\n" + "1- View Balance" + "\n" + "2- View Members" + "\n" + "3- Deposit" 
					+ "\n" + "4-Withdraw" + "\n" + "5- Log off");
			
			scanInt = myObj.nextInt();
			
			switch(scanInt)
			{
			case 1:
				//view balance
				System.out.println(c.balance);
				break;
			case 2:
				//get all members linked in this joint account
				for(int i = 0;i < c.JointName.size();i++)
				{
					System.out.println(c.JointName.get(i) + "\n");
				}
				break;
			case 3:
				//deposit money from the signed in customer balance to the joint balance
				
				System.out.println("Enter the amount you want to deposit");
				scandoub = myObj.nextDouble();
				
				if(scandoub > balance || scandoub < 0)
				{
					System.out.println("You don't have sufficient funds.");
					openJoint();
				}
				else
				{
					balance -= scandoub;
					c.balance += scandoub;
					openJoint();
				}
				break;
			case 4:
				//withdraw money from joint balance into the signed in customer balance
				
				System.out.println("Enter the amount you want to withdraw");
				scandoub = myObj.nextDouble();
				
				if(scandoub > c.balance || scandoub < 0)
				{
					System.out.println("You don't have sufficient funds.");
					openJoint();
				}
				else
				{
					balance += scandoub;
					c.balance -= scandoub;
					openJoint();
				}
				break;
			case 5:
				accountMenu();
				break;
			default:
				openJoint();
			}
		}
		else
		{
			System.out.println("You do not have a joint account. Please fill in the information to create a joint account.");
			
			//sets up information for the new account
			BankCustomer nCustomer = new BankCustomer();
			nCustomer.firstName = firstName;
			nCustomer.lastName = lastName;
			
			//makes a unique joint account number
			nCustomer.jointNum = RNG();
			while(admin.customerJoints.containsKey(nCustomer.jointNum))
				nCustomer.jointNum = RNG();			
			
			do
			{
				System.out.println("Please enter the account Number of the person/s that will join");
				scanInt = myObj.nextInt();
				
				for(int i = 0;i < admin.customers.size();i++)
				{
					if(scanInt == admin.customers.get(i).accountNum)
					{
						nCustomer.JointName.add(admin.customers.get(i).firstName + " " + admin.customers.get(i).lastName);
						break;
					}
					else
					{
						System.out.println("The account number is incorrect");
						accountMenu();
					}
				}
				
				System.out.println("Would you like to add more?" + "\n" + "Yes/No");
				input = myObj.nextLine();
				
				if(input.equalsIgnoreCase("Yes"))
					s  = true;
				else
					s = false;
			}while(s);
			
			//adds the customer to joint list
			admin.customerJoints.put(nCustomer.jointNum, nCustomer);
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
				admin.customerSavings.put(accountNum, saveBalance);
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
				System.out.println("The amount has been taken from your account");
				accountMenu();
			}
		}
	}
}
