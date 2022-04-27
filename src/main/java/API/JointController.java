package API;

import DataBase.CustomerDAO;
import DataBase.JointDAO;
import RevCustom.BankCustomer;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class JointController 
{
	static JointDAO dao;
	
	public JointController(Javalin app)
	{
		dao = new JointDAO();
		
		app.get("/joint/{code}", getHandle); //select a user
		app.post("/joint", postHandle); //create a new user
		app.put("/joint/{username}", putHandle); //update user info
		app.delete("/joint/gone/{username}", deleteHandle); //delete a user
	}
	
	public static Handler getHandle = ctx ->{
		
		String co = ctx.pathParam("code");
		
		BankCustomer cm = dao.searchJointCustomers(Integer.parseInt(co));
		
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
		
		dao.setJoint(cm);
	};
	
	public static Handler putHandle = ctx ->{
		BankCustomer cm = ctx.bodyAsClass(BankCustomer.class);
		
		dao.UpdateJoint(cm);
	};
	
	public static Handler deleteHandle = ctx ->{
		BankCustomer cm = ctx.bodyAsClass(BankCustomer.class);
		
		dao.deleteJoint(cm);
	};
}
