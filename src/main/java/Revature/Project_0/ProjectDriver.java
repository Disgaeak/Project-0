package Revature.Project_0;

import RevCustom.BankCustomer;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import API.CustomerController;
import API.EmployeeController;
import DataBase.CustomerDAO;
import DataBase.EmployeeDAO;

public class ProjectDriver 
{
	//can be used by others
	public static BankEmployee bE;
	public static BankAdmin boss = new BankAdmin();
	public static Logger demo = LogManager.getLogger(ProjectDriver.class);
	
	//this class only
	private static BankCustomer customer = new BankCustomer();
	private static String userName;
	private static Scanner myObj = new Scanner(System.in); 
	private static CustomerDAO cdao = new CustomerDAO();
	private static EmployeeDAO edao = new EmployeeDAO();
	
	public static void main(String[] args) 
	{	
		/*Examples of how to use logger in this package
		 * demo.trace("We've just greeted the user!");
		 * demo.debug("We've just greeted the user!");
		 * demo.info("We've just greeted the user!");
		 * demo.warn("We've just greeted the user!");
		 * demo.error("We've just greeted the user!");
		 * demo.fatal("We've just greeted the user!");
		 */
		
		//LoginMenu();
		
		//creates a javalin object and starts listening on port 7070
		Javalin app = Javalin.create().start(7070);
		
		//verb = get, from user, which will result in hello on page
		//sets up a listener for GET requests to the user path, when detected returns "Hello"
		//in body of response
		EmployeeController eCon = new EmployeeController(app);
		CustomerController cCon = new CustomerController(app);
		//you can store a lambda in a handler variable
		//app.get("user", userHandle);
	}
	
	public static void LoginMenu()
	{
		String op1 = "open";
		
		//Scanner myObj = new Scanner(System.in);  // Create a Scanner object
		System.out.println("- Exception-al Bank Login Menu -" + "\n" + "Please Enter username or" + "\n" + "Type 'open' to open a new account.");
		userName = myObj.nextLine();  // Read user input
		
		//checks who is logging in, first is they typed 'open'
		if(userName.equalsIgnoreCase(op1))
		{
			customer.openAccount();
		}
		else if(cdao.bIsCustomer(userName))
		{
			//if not then check if it's a customers username.
			
			//gets customer with username
			BankCustomer sc = cdao.searchCustomers(userName);
			
			//checks if the account is valid
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
				System.out.println("Your account is pending");
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
				//if not then it must be an employee. Now checks if code is employee or admin
				
				int c = Integer.parseInt(userName);
				
				//have either employee or admin login and call respective acccount menu
				if(edao.bIsEmployee(c))
				{
					//is employee aka lv equals 0
					bE = boss.convertBA(edao.searchEmployee(c));
					bE.accountMenu();
				}
				else
				{
					//is an Admin
					boss = edao.searchEmployee(c);
					
					if(boss != null)
						boss.accountMenu();
					else
						System.out.println("No one was found."); LoginMenu();
				}
			}
		}
	}
}
