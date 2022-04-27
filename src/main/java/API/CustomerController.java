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
		
		BankCustomer cm = dao.searchCustomers(co);
		
		if(cm != null)
		{
			ctx.status(200);
			ctx.json(cm);
		}
		else
		{
			ctx.status(404);
		}
	};
	
	public static Handler postHandle = ctx ->{
		
		BankCustomer cm = ctx.bodyAsClass(BankCustomer.class);
		
		dao.setCustomer(cm);
	};
	
	public static Handler putHandle = ctx ->{
		BankCustomer cm = ctx.bodyAsClass(BankCustomer.class);
		
		dao.UpdateCustomer(cm);
	};
	
	public static Handler deleteHandle = ctx ->{
		String co = ctx.pathParam("username");
		
		dao.deleteCustomer(co);
		ctx.result("found delete");
		
	};
}
