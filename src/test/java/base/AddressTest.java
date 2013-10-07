package base;

import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.*;
import static org.junit.Assert.*;


public class AddressTest 
{
	@Test
	public void testAddress()
	{
		Address result = new Address();
		assertNotNull(result);
	}


	@Test
	public void testHashCode()
	{
		//Test increment
		Address address = new Address();
		int result = address.hashCode();
		
		Address address2 = new Address();
		int result2 = address2.hashCode();

		assertEquals(result+1, result2);
	}


	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(AddressTest.class);
	}
}