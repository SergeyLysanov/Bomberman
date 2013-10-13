package gameMechanics;

import org.junit.*;
import base.Address;
import base.AddressService;
import base.GameSocket;
import base.MessageSystem;
import messageSystem.MessageSystemImpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameSessionTest 
{
	@Test
	public void GetResourceData()
	{
		GameSession gameSession = new GameSession();
		int maxUsers = gameSession.GetMaxUsersCount();
		int boomRadius = gameSession.GetBoomRadius();
		int polygon = gameSession.GetPolygon();
		
		assertEquals(1, maxUsers);
		assertEquals(4, boomRadius);
		assertEquals(32, polygon);
	}
	
	@Test
	public void testStartPlay()
	{
		GameSession gameSession = new GameSession();
		
		AddressService addressService = mock(AddressService.class);
		MessageSystem messageSystem = mock(MessageSystemImpl.class);
		when(messageSystem.getAddressService()).thenReturn(addressService);
		when(addressService.getAddress(GameSocket.class)).thenReturn(new Address());
		GameMechanicsImpl mechanics = new GameMechanicsImpl(messageSystem);
		GameMechanicsImpl gameMechanics = spy(mechanics);
		
		gameSession.createUser("user1", 1, gameMechanics);
		gameSession.createUser("user2", 2, gameMechanics);
		
		gameSession.userReady(1, true, gameMechanics);
		gameSession.userReady(2, true, gameMechanics);
		
		gameSession.destroyPlayer(1, 2, gameMechanics); //2 player kill 1 player
		
		boolean isAliveFirst = gameSession.isUserAlive(1);
		boolean isAliveSecond = gameSession.isUserAlive(2);
		
		assertFalse(isAliveFirst);
		assertTrue(isAliveSecond);
		
		//restart session
		gameSession.restartSessions();
		isAliveFirst = gameSession.isUserAlive(1);
		isAliveSecond = gameSession.isUserAlive(2);
		
		assertTrue(isAliveFirst);
		assertTrue(isAliveSecond);
	}

	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(GameSessionTest.class);
	}
}
