package Revature.Project_0;

public class TestClass 
{
	static int balance = 10;
	
	public static int Deposit(int i) 
	{
		try 
		{
			return balance + i;
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
}
