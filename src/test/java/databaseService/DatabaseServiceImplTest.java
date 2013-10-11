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

	private MessageSystem messageSystem;
	private int	Min = 10;
	private int Max = Integer.MAX_VALUE;
	
	public DatabaseServiceImplTest()
	{
		messageSystem = mock(MessageSystemImpl.class);
	}
	
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
	public void testGetUserData()
	{
		DatabaseServiceImpl databaseService = new DatabaseServiceImpl(messageSystem);
		UserSession userSession = new UserSession("test", "sessionId", "pass");
		databaseService.getUserData(userSession, new Address());
		
		assertTrue(userSession.userId.get() > 0);
	}
	
	@Test
	public void testGetNonExistingUserData()
	{
		DatabaseServiceImpl databaseService = new DatabaseServiceImpl(messageSystem);
		UserSession userSession = new UserSession("unknownName", "sessionId", "pass");
		databaseService.getUserData(userSession, new Address());
		
		assertEquals(SessionStatus.eWrongLogin, userSession.eSessionStatus.get());
	}
	
	@Test
	public void testGetUserDataSynchro()
	{
		DatabaseServiceImpl databaseService = new DatabaseServiceImpl(messageSystem);
		UserSession userSession = new UserSession("test", "sessionId", "pass");
		UserDataSet dataSet = databaseService.getUserData(userSession);
		
		assertTrue(dataSet.getid() > 0);
	}
	
	@Test
	public void testRegisterUser()
	{
		Integer userUID = Min + (int)(Math.random() * ((Max - Min) + 1));
		String randomUser = "User" + userUID.toString();
		
		DatabaseServiceImpl database = new DatabaseServiceImpl(new MessageSystemImpl());
		UserSession userSession = new UserSession(randomUser, "sessionId", "pass");
		userSession.email.set("1@mail.ru"); 
		
		database.registerUser(userSession);
		UserDataSet dataSet = database.getUserData(userSession);
		
		assertTrue(dataSet.getid() > 0);
	}

	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(DatabaseServiceImplTest.class);
	}
}