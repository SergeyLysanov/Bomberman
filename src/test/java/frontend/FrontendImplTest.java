package frontend;

import javax.servlet.http.HttpSession;
import utils.UserSession;
import base.AccountService;
import base.AddressService;
import base.MessageSystem;
import base.Address;
import messageSystem.MessageSystemImpl;
import utils.SessionStatus;

import org.json.simple.JSONObject;
import org.junit.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class FrontendImplTest 
{
	private MessageSystem 	messageSystem;
	private AddressService	addressService;
	
	public FrontendImplTest()
	{
		this.messageSystem = mock(MessageSystemImpl.class);
		this.addressService = mock(AddressService.class);
		
		when(messageSystem.getAddressService()).thenReturn(addressService);
		when(addressService.getAddress(AccountService.class)).thenReturn(new Address());
	}

	@Test
	public void testFrontendImpl()
	{
		FrontendImpl frontend = new FrontendImpl(messageSystem);
		assertNotNull(frontend);
	}


	@Test
	public void testAuthorizeUser()
	{
		FrontendImpl frontend = new FrontendImpl(messageSystem);
		
		UserSession userSession = new UserSession();
		userSession.userName.set("name");

		frontend.AuthorizeUser(userSession);
		verify(messageSystem).sendMessage(any(MsgAuthorizeUser.class));
	}
	
	@Test
	public void testCreateAndGetUserSession()
	{
		FrontendImpl frontend = new FrontendImpl(messageSystem);
		
		String userName = "name";
		String sessionId = "sessionId";
		String password = "123";
		String email = "email";

		UserSession result = frontend.CreateUserSession(userName, sessionId, password, email);
		UserSession result2 = frontend.GetUserSession(sessionId);
		
		assertNotNull(result);
		assertNotNull(result2);
		assertEquals(result, result2);
	}

	@Test
	public void testParseRegisterUrl()
	{
		FrontendImpl frontend = new FrontendImpl(messageSystem);
		String URL = "/register";
		UserSession userSession = new UserSession();
		userSession.userName.set("name");

		String result = frontend.ParseRequestUrl(URL, userSession);

		verify(messageSystem).sendMessage(any(MsgRegisterUser.class));
		assertEquals("wait registration", result);
	}

	@Test
	public void testParseLoginUrl()
	{
		FrontendImpl frontend = new FrontendImpl(messageSystem);
		String URL = "/login";
		UserSession userSession = new UserSession();
		userSession.userName.set("name");

		String result = frontend.ParseRequestUrl(URL, userSession);

		verify(messageSystem).sendMessage(any(MsgAuthorizeUser.class));
		assertEquals("wait authorization", result);
	}

	@Test
	public void testParseUnknownUrl()
	{
		FrontendImpl fixture = new FrontendImpl(new MessageSystemImpl());
		String URL = "sdf";
		UserSession userSession = new UserSession();

		String result = fixture.ParseRequestUrl(URL, userSession);
		assertEquals("", result);
	}


	@Test
	public void testGetAddress()
	{
		FrontendImpl frontend = new FrontendImpl(messageSystem);
		Address result = frontend.getAddress();
		assertNotNull(result);
	}

	@Test
	public void testGetUserSessionStatusWithNullSession()
	{
		FrontendImpl frontend = new FrontendImpl(messageSystem);
		HttpSession session = null;

		String result = frontend.getUserSessionStatus(session);
		assertEquals("{\"status\": null}", result);
	}
	
	@Test
	public void testGetUserSessionStatus()
	{
		FrontendImpl frontend = new FrontendImpl(messageSystem);
		
		HttpSession session = mock(HttpSession.class);
		when(session.getId()).thenReturn("sessionId");

		UserSession userSession = frontend.CreateUserSession("name", "sessionId", "pass", "email");
		String jsonResult = frontend.getUserSessionStatus(session);

		JSONObject expectedResult = new JSONObject();
		expectedResult.put("userName", userSession.userName.get());
		expectedResult.put("userId", userSession.userId.get());
		expectedResult.put("status", "inProcess");
		  
		assertEquals(expectedResult.toString(), jsonResult);
	}

	@Test
	public void testLogout()
	{
		FrontendImpl frontend = new FrontendImpl(messageSystem);
		String sessionId = "sessionId";
		
		UserSession result = frontend.CreateUserSession("name", sessionId, "pass", "email");
		frontend.logout(sessionId);
		UserSession result2 = frontend.GetUserSession(sessionId);

		assertNull(result2);
	}

	@Test
	public void testUpdateUserSession()
	{
		FrontendImpl frontend = new FrontendImpl(messageSystem);
		String sessionId = "sessionId";
		
		//Create user session
		UserSession userSession = frontend.CreateUserSession("name", sessionId, "pass", "email");
		
		//Updated user session
		UserSession newUserSession = new UserSession("name", sessionId, "pass");
		newUserSession.eSessionStatus.set(SessionStatus.eAuthorized);

		frontend.updateUserSession(newUserSession);
		UserSession result = frontend.GetUserSession(sessionId);
		
		assertEquals(SessionStatus.eAuthorized, result.eSessionStatus.get());
	}


	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(FrontendImplTest.class);
	}
}