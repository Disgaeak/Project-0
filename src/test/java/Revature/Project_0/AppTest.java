package Revature.Project_0;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class AppTest 
{
   TestClass tc = new TestClass();
	
    @Test
    public void addition()
    {
    	int x = TestClass.Deposit(30);
    	
    	assertTrue(x > 30);
    }
}
