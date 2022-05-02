package Revature.Project_0;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AppTest 
{
   TestClass tc = new TestClass();
	
    @Test
    public void addition()
    {
    	int x = TestClass.Deposit(30);
    	
    	assertTrue(x > 30);
    }
    
    @Test
    public void subtract()
    {
    	int x = TestClass.withdraw(13);
    	
    	assertTrue(x == 0);
    }
}
