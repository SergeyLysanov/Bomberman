package accountService;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import messageSystem.AddressServiceImpl;
import messageSystem.MessageSystemImpl;
import base.Address;
import base.AddressService;
import base.DatabaseService;
import base.Msg;
import databaseService.DatabaseServiceImpl;
import databaseService.UserDataSetImpl;
import frontend.FrontendImpl;
import utils.UserSession;
import base.MessageSystem;
import utils.SessionStatus;
import org.junit.*;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AccountServiceImplTest 
{
	private MessageSystem 	messageSystem;
	private DatabaseService	database;
	private AddressService  addressService;

	@Test
	public void testAccountServiceImpl()
	{
		AccountServiceImpl result = new AccountServiceImpl(messageSystem, database);
		assertNotNull(result);
	}

	@Test
	public void testAuthorizeNonExistingUser()
	{
		AccountServiceImpl accountService = new AccountServiceImpl(messageSystem, database);
		UserSession userSession = new UserSession();
		
		UserDataSetImpl fakeDataSet = null;
		when(database.getUserData(userSession)).thenReturn(fakeDataSet);
		
		accountService.authorize(userSession);
		
		assertEquals(-1, userSession.userId.get());
		assertEquals(SessionStatus.eWrongLogin, userSession.eSessionStatus.get());
	}

	@Test
	public void testAuthorizeWithCorrectPass()
	{
		AccountServiceImpl accountService = new AccountServiceImpl(messageSystem, database);
		UserSession userSession = new UserSession("Sergey", "1", "12345");
		
		UserDataSetImpl fakeDataSet = new UserDataSetImpl("Sergey", "85697f1db65123015e7dac37b33709da2eb676f0be35e153404458d990e975b1");
		when(database.getUserData(userSession)).thenReturn(fakeDataSet);
		
		accountService.authorize(userSession);
		
		assertEquals(SessionStatus.eAuthorized, userSession.eSessionStatus.get());
	}

	@Test
	public void testAuthorizeWithUncorrectPass()
	{
		AccountServiceImpl accountService = new AccountServiceImpl(messageSystem, database);
		UserSession userSession = new UserSession("Sergey", "1", "12345");

		UserDataSetImpl fakeDataSet = new UserDataSetImpl("Sergey", "NonCorrectPass");
		when(database.getUserData(userSession)).thenReturn(fakeDataSet);
		
		accountService.authorize(userSession);
		assertEquals(SessionStatus.eWrongPassword, userSession.eSessionStatus.get());
	}

	@Test
	public void testGetAddress()
	{
		
		AccountServiceImpl fixture = new AccountServiceImpl(messageSystem, database);
		Address result = fixture.getAddress();
		assertNotNull(result);
	}

	@Test
	public void testGetUpdateUserWithWrongLogin()
	{
		AccountServiceImpl accountService = new AccountServiceImpl(messageSystem, database);
		UserSession userSession = new UserSession("Sergey", "1", "12345");
		userSession.realPassword.set("12345");
		userSession.eSessionStatus.set(SessionStatus.eWrongLogin);

		when(messageSystem.getAddressService()).thenReturn(addressService);
		when(addressService.getAddress(FrontendImpl.class)).thenReturn(new Address());
		
		accountService.getUpdateUser(userSession);
	}

	@Test
	public void testGetUpdateUserWithCorrectPass()
	{
		AccountServiceImpl accountService = new AccountServiceImpl(messageSystem, database);
		UserSession userSession = new UserSession("Sergey", "1", "12345");
		userSession.realPassword.set("12345");
		userSession.eSessionStatus.set(SessionStatus.eRegistered);

		when(messageSystem.getAddressService()).thenReturn(addressService);
		when(addressService.getAddress(FrontendImpl.class)).thenReturn(new Address());
		accountService.getUpdateUser(userSession);
		
		assertEquals(SessionStatus.eAuthorized, userSession.eSessionStatus.get());
	}

	@Test
	public void testGetUpdateUserWithUncorrectPass()
	{	
		AccountServiceImpl accountService = new AccountServiceImpl(messageSystem, database);
		UserSession userSession = new UserSession("Sergey", "1", "12345");
		userSession.realPassword.set("123456");
		userSession.eSessionStatus.set(SessionStatus.eRegistered);

		when(messageSystem.getAddressService()).thenReturn(addressService);
		when(addressService.getAddress(FrontendImpl.class)).thenReturn(new Address());
		accountService.getUpdateUser(userSession);
		
		assertEquals(SessionStatus.eWrongPassword, userSession.eSessionStatus.get());
	}

	@Test
	public void testRegisterNewUser()
	{
		AccountServiceImpl accountService = new AccountServiceImpl(messageSystem, database);
		UserSession userSession = new UserSession("Sergey", "Session", "password");
		
		UserDataSetImpl fakeDataSet = null;
		when(database.getUserData(userSession)).thenReturn(fakeDataSet);

		accountService.register(userSession);
		
		assertEquals(SessionStatus.eRegistered, userSession.eSessionStatus.get());
	}

	@Test
	public void testRegisterOldUser()
	{
		AccountServiceImpl accountService = new AccountServiceImpl(messageSystem, database);
		UserSession userSession = new UserSession("Sergey", "Session", "password");
		
		UserDataSetImpl fakeDataSet = new UserDataSetImpl("Sergey", "password");
		when(database.getUserData(userSession)).thenReturn(fakeDataSet);

		accountService.register(userSession);
		
		assertEquals(SessionStatus.eUserExists, userSession.eSessionStatus.get());
	}

	@Before
	public void setUp()
	{
		this.messageSystem = mock(MessageSystem.class);
		this.database = mock(DatabaseService.class);
		this.addressService = mock(AddressServiceImpl.class);
	}

	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(AccountServiceImplTest.class);
	}
}