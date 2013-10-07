package accountService;

import org.junit.*;
import base.Address;
import utils.UserSession;
import base.AccountService;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class MsgUpdateUserDataTest {

	@Test
	public void testMsgUpdateUserData()
	{
		Address from = new Address();
		Address to = new Address();
		UserSession userSession = new UserSession();

		MsgUpdateUserData result = new MsgUpdateUserData(from, to, userSession);

		assertNotNull(result);
	}

	@Test
	public void testExec()
	{
		UserSession userSession = new UserSession();
		MsgUpdateUserData msg = new MsgUpdateUserData(new Address(), new Address(), userSession);	
		AccountService mockedAccountService = mock(AccountService.class);

		msg.exec(mockedAccountService, new Address());
		verify(mockedAccountService).getUpdateUser(userSession);
	}

	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(MsgUpdateUserDataTest.class);
	}
}