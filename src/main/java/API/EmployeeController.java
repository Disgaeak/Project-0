package API;

import DataBase.EmployeeDAO;
import Revature.Project_0.BankAdmin;
import Revature.Project_0.BankEmployee;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class EmployeeController 
{
	static EmployeeDAO dao;
	
	public EmployeeController(Javalin app)
	{
		dao = new EmployeeDAO();
		
		app.get("/employee/{code}", getHandle); //select a user
		app.post("/employee", postHandle); //create a new user
		app.put("/employee/{username}", putHandle); //update user info
		app.delete("/employee/gone/{code}", deleteHandle); //delete a user
	}
	
	public static Handler getHandle = ctx ->{
		
		String co = ctx.pathParam("code");
		
		//searches for employee or admin in database
		BankAdmin ba = dao.searchEmployee(Integer.parseInt(co));
		
		if(ba != null)
		{
			//found the employee
			ctx.status(200);
			ctx.result("found Employee");
			ctx.json(ba);
		}
		else
		{
			//not found
			ctx.status(404);
		}
	};
	
	public static Handler postHandle = ctx ->{
		
		BankAdmin ba = ctx.bodyAsClass(BankAdmin.class);
		
		//creates a new employee in the database
		dao.setEmployee(ba);
	};
	
	public static Handler putHandle = ctx ->{
		BankAdmin ba = ctx.bodyAsClass(BankAdmin.class);
		
		//updates employee information
		dao.UpdateEmployee((BankEmployee)ba);
	};
	
	public static Handler deleteHandle = ctx ->{
		String co = ctx.pathParam("code");
		
		//removes employee from database
		ctx.result("found delete");
		dao.deleteEmployee(Integer.parseInt(co));
	};
}
