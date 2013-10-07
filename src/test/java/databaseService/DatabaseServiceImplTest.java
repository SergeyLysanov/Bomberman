package databaseService;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import base.MessageSystem;
import base.UserDataSet;
import messageSystem.MessageSystemImpl;
import base.Address;
import utils.UserSession;
import utils.SessionStatus;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DatabaseServiceImplTest 
{

	MessageSystem messageSystem;
	
	@Test
	public void testDatabaseServiceImpl()
	{
		DatabaseServiceImpl result = new DatabaseServiceImpl(messageSystem);
		assertNotNull(result);
	}

	@Test
	public void testGetAddress()
	{
		DatabaseServiceImpl databaseService = new DatabaseServiceImpl(messageSystem);
		Address result = databaseService.getAddress();

		assertNotNull(result);
	}


	@Test
	public void testRegisterUser()
	{
		DatabaseServiceImpl fixture = new DatabaseServiceImpl(new MessageSystemImpl());
		UserSession userSession = new UserSession();
		userSession.userName = new AtomicReference();
		userSession.email = new AtomicReference();
		userSession.password = new AtomicReference();
	}


	@Before
	public void setUp()
	{
		messageSystem = mock(MessageSystemImpl.class);
	}


	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(DatabaseServiceImplTest.class);
	}
}