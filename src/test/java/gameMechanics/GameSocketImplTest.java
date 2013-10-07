package gameMechanics;

import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.Request;
import base.MessageSystem;
import gameMechanics.GameMap.GameMap;
import messageSystem.MessageSystemImpl;
import org.eclipse.jetty.websocket.WebSocket;
import utils.UserGameSession;
import base.Address;
import org.junit.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GameSocketImplTest 
{
	private MessageSystem messageSystem;
	
	public GameSocketImplTest() 
	{
		messageSystem = mock(MessageSystemImpl.class);
	}

	@Test
	public void testGameSocketImpl()
	{
		GameSocketImpl result = new GameSocketImpl(messageSystem);
		assertNotNull(result);
	}

	@Test
	public void testGetAddress()
	{
		GameSocketImpl gameSocket = new GameSocketImpl(messageSystem);
		Address result = gameSocket.getAddress();
		assertNotNull(result);
	}
	
	@Test
	public void testPlayMessage()
	{
		GameSocketImpl gameSocket = new GameSocketImpl(messageSystem);
		String message = "{\"type\":\"play\",\"from\":{\"userName\":\"test\",\"userId\":\"22\"},\"value\":null}";
		gameSocket.parseMessage(message);
		
		verify(messageSystem).sendMessage(any(MsgAddUser.class));
	}
	
	@Test
	public void testDestroyMessage()
	{
		GameSocketImpl gameSocket = new GameSocketImpl(messageSystem);
		String message = "{\"type\":\"destroy\",\"from\":{\"userName\":\"test\",\"userId\":\"22\"},\"value\":{\"byUser\":\"22\"}}";
		gameSocket.parseMessage(message);
		
		verify(messageSystem).sendMessage(any(MsgDestroyUser.class));
	}

	@Test
	public void testReadyMessage()
	{
		GameSocketImpl gameSocket = new GameSocketImpl(messageSystem);
		String message = "{\"type\":\"ready\",\"from\":{\"userName\":\"test\",\"userId\":\"22\"},\"value\":{\"isReady\":true}}";
		gameSocket.parseMessage(message);
		
		verify(messageSystem).sendMessage(any(MsgUserReady.class));
	}
	
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(GameSocketImplTest.class);
	}
}