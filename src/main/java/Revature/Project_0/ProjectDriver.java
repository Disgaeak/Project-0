package Revature.Project_0;

import RevCustom.BankCustomer;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import DataBase.CustomerDAO;

public class ProjectDriver 
{
	private static BankCustomer customer = new BankCustomer();
	public static BankEmployee bE = new BankEmployee();
	public static BankAdmin boss = new BankAdmin();
	private static String userName;
	private static Scanner myObj = new Scanner(System.in); 
	private static CustomerDAO cdao = new CustomerDAO();
	
	public static Logger demo = LogManager.getLogger(ProjectDriver.class);
	
	public static void main(String[] args) 
	{	
		
		//Hire 1 employee
		bE.firstName = "Robert";
		bE.lastName = "Dover";
		bE.bankCode = 1234;
		
		//create 1 Admin
		boss.firstName = "John";
		boss.lastName = "Wilhelm";
		boss.bankCode = 5647;
		
		/*Examples of how to use logger in this package
		 * demo.trace("We've just greeted the user!");
		 * demo.debug("We've just greeted the user!");
		 * demo.info("We've just greeted the user!");
		 * demo.warn("We've just greeted the user!");
		 * demo.error("We've just greeted the user!");
		 * demo.fatal("We've just greeted the user!");
		 */
		
		LoginMenu();
	}
	
	public static void LoginMenu()
	{
		String op1 = "open";
		
		//Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		System.out.println("- Exception-al Bank Login Menu -" + "\n" + "Please Enter username or" + "\n" + "Type 'open' to open a new account.");
		userName = myObj.nextLine();  // Read user input
		
		if(userName.equalsIgnoreCase(op1))
		{
			customer.openAccount();
		}
		else if(cdao.bIsCustomer(userName))
		{
			//gets customer with username
			BankCustomer sc = cdao.searchCustomers(userName);
			
			switch(sc.validAccount)
			{
			case 0:
				System.out.println("Your account has been canceled");
				LoginMenu();
				break;
			case 1:
				System.out.println("please enter password.");
				userName = myObj.nextLine();
				
				if(sc.passWord.equals(userName))
					sc.accountMenu();
				else
				{
					System.out.println("The password is incorrect.");
					LoginMenu();
				}
				break;
			case 2:
				System.out.println("Your account is still pending");
				LoginMenu();
				break;
			}
		}
		else
		{
			if(userName.equalsIgnoreCase(""))
			{
				/*this is to prevent an exception
				* for some reason when leaving admin login to login menu, it doesnt wait for user input
				* therefore, username will equal ""
				*/
				LoginMenu();
			}
			else
			{
				int hold = Integer.parseInt(userName);
				
				//checks if employee or boss. if neither end program
				if(hold == 1234)
				{
					bE.accountMenu();
				}
				else if(hold == 5647)
				{
					boss.accountMenu();
				}
				else
					System.out.println("No username found.");
			}
		}
	}
}
