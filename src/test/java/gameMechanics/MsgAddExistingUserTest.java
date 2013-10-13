package gameMechanics;

import org.junit.*;

import accountService.MsgUpdateUserData;
import accountService.MsgUpdateUserDataTest;
import base.Address;
import utils.UserGameSession;
import utils.UserSession;
import base.AccountService;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class MsgAddExistingUserTest 
{

	@Test
	public void testMsgAddExistingUser()
	{
		Address from = new Address();
		Address to = new Address();
		UserGameSession gameSession = new UserGameSession("name", 1);
		gameSession.boomRadius = 1;
		gameSession.isAlive = false;
		gameSession.isReady = false;
		gameSession.killedByUserId = 1;
		gameSession.playerNum = 1;
		gameSession.sessionId = "sessionId";
		gameSession.speed = 3;

		MsgAddExistingUser result = new MsgAddExistingUser(from, to, 1,  gameSession);
		assertNotNull(result);
	}
	
	@Test
	public void testExec()
	{
		GameSocketImpl gameSocket = mock(GameSocketImpl.class);
		
		Address from = new Address();
		Address to = new Address();
		UserGameSession gameSession = new UserGameSession("name", 1);

		MsgAddExistingUser msg = new MsgAddExistingUser(from, to, 1,  gameSession);
		msg.exec(gameSocket);
		
		verify(gameSocket).addExistingUser(1, gameSession);
	}
	
	
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(MsgAddExistingUserTest.class);
	}
}
