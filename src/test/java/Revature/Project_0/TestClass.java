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
			e.printStackTrace();
		}
		return i;
	}

	public static int withdraw(int i) 
	{
		try 
		{
			if(i > balance)
			{
				return 0;
			}
			return balance - i;
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return i;
	}
	
}
