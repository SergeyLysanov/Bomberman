package databaseService;

import org.junit.*;

import base.UserDataSet;
import static org.junit.Assert.*;

public class UserDataSetImplTest 
{
	@Test
	public void testUserDataSetImpl()
	{
		long id = 1;
		String name = "name";
		String pass = "pass";
		String email = "email";
		UserDataSet userDataSet = new UserDataSetImpl(1, name, pass, email);
		
		assertEquals(name, userDataSet.getName());
		assertEquals(pass, userDataSet.getPassword());
		assertEquals(id, userDataSet.getid());
		assertEquals(email, userDataSet.getEmail());
	}
	
	
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(UserDataSetImplTest.class);
	}
}
