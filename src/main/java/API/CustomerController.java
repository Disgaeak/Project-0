package API;

import DataBase.CustomerDAO;
import RevCustom.BankCustomer;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class CustomerController 
{
	static CustomerDAO dao;
	
	public CustomerController(Javalin app)
	{
		dao = new CustomerDAO();
		
		app.get("/customer/{username}", getHandle); //select a user
		app.post("/customer", postHandle); //create a new user
		app.put("/customer/{username}", putHandle); //update user info
		app.delete("/customer/gone/{username}", deleteHandle); //delete a user
	}
	
	public static Handler getHandle = ctx ->{
		
		String co = ctx.pathParam("username");
		
		//selects the customer from the database
		BankCustomer cm = dao.searchCustomers(co);
		
		if(cm != null)
		{
			//confirms the customer is found
			ctx.result("found customer");
			ctx.json(cm);
		}
		else
		{
			//customer not found
			ctx.status(404);
		}
	};
	
	public static Handler postHandle = ctx ->{
		
		BankCustomer cm = ctx.bodyAsClass(BankCustomer.class);
		
		//creates a new customer in the database
		dao.setCustomer(cm);
		ctx.result("Created a new customer account.");
	};
	
	public static Handler putHandle = ctx ->{
		BankCustomer cm = ctx.bodyAsClass(BankCustomer.class);
		
		if(cm != null)
		{
			//updates customer info in the database
			dao.UpdateCustomer(cm);
			ctx.result("updated customer info");
		}
		else
			ctx.result("could not update");
	};
	
	public static Handler deleteHandle = ctx ->{
		String co = ctx.pathParam("username");
		
		//removes a customer from the database
		if(dao.bIsCustomer(co))
		{
			dao.deleteCustomer(co);
			ctx.result("Deleted");
		}
		else
			ctx.result("could not find customer");
		
	};
}
