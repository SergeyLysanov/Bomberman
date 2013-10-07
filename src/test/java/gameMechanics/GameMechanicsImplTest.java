package gameMechanics;

import org.junit.*;
import base.Address;
import base.AddressService;
import base.GameSocket;
import base.MessageSystem;
import messageSystem.MessageSystemImpl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameMechanicsImplTest 
{
	private MessageSystem 	messageSystem;
	private AddressService	addressService;
	
	public GameMechanicsImplTest() {
		this.messageSystem = mock(MessageSystemImpl.class);
		this.addressService = mock(AddressService.class);
		
		when(messageSystem.getAddressService()).thenReturn(addressService);
		when(addressService.getAddress(GameSocket.class)).thenReturn(new Address());
	}
	
	@Test
	public void testGameMechanicsImpl()
	{
		GameMechanicsImpl result = new GameMechanicsImpl(messageSystem);
		assertNotNull(result);
	}

	@Test
	public void testAddUser()
	{
		GameMechanicsImpl gameMechanics = new GameMechanicsImpl(messageSystem);
		String userName = "Sergey";
		Integer userId = new Integer(1);
		Address from = new Address();

		int usersCount = gameMechanics.getUserCount();
		gameMechanics.addUser(userName, userId, from);
		int usersCountAfter = gameMechanics.getUserCount();
		
		assertEquals(usersCount+1, usersCountAfter);
	}

	@Test
	public void testDeleteUser()
	{
		GameMechanicsImpl gameMechanics = new GameMechanicsImpl(messageSystem);
		String userName = "Sergey";
		Integer userId = new Integer(1);
		Address from = new Address();

		gameMechanics.addUser(userName, userId, from);
		
		int usersCount = gameMechanics.getUserCount();
		gameMechanics.deleteUser(userId, from);
		int usersCountAfter = gameMechanics.getUserCount();
		
		assertEquals(usersCount-1, usersCountAfter);
	}

	@Test
	public void testDestroyUser()
	{
		GameMechanicsImpl gameMechanics = new GameMechanicsImpl(messageSystem);
		String userName = "Sergey";
		Integer userId = new Integer(1);
		Address from = new Address();

		gameMechanics.addUser(userName, userId, from); //add alive user
		gameMechanics.destroyUser(userId, 2, from);
		int aliveUsers = gameMechanics.getAliveUsers();
		
		assertEquals(0, aliveUsers);
	}

	@Test
	public void testGetAddress()
	{
		GameMechanicsImpl gameMechanics = new GameMechanicsImpl(messageSystem);
		Address result = gameMechanics.getAddress();
		assertNotNull(result);
	}

	@Test
	public void testSendLoseMessage()
	{
		GameMechanicsImpl gameMechanics = new GameMechanicsImpl(messageSystem);
		Integer toUserId = new Integer(1);

		gameMechanics.sendLoseMessage(toUserId);
		
		verify(messageSystem).sendMessage(any(MsgPlayerLose.class));
	}

	@Test
	public void testSendWinMessage()
	{
		GameMechanicsImpl gameMechanics = new GameMechanicsImpl(messageSystem);
		Integer toUserId = new Integer(1);

		gameMechanics.sendWinMessage(toUserId);
		
		verify(messageSystem).sendMessage(any(MsgPlayerWin.class));
	}

	@Test
	public void testStartGame()
	{
		GameMechanicsImpl gameMechanics = new GameMechanicsImpl(messageSystem);

		gameMechanics.startGame();
		
		assertNotNull(gameMechanics.getMap());
		verify(messageSystem).sendMessage(any(MsgStartGame.class));
	}

	@Test
	public void testUserReady()
	{
		GameMechanicsImpl gameMechanics = new GameMechanicsImpl(messageSystem);
		GameMechanicsImpl spyMechanics = spy(gameMechanics);
		
		String userName = "Sergey";
		Integer userId = new Integer(1);
		boolean isReady = true;
		
		spyMechanics.addUser(userName, userId, new Address());
		spyMechanics.userReady(userName, userId, isReady);

		verify(spyMechanics).startGame();
	}

	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(GameMechanicsImplTest.class);
	}
}