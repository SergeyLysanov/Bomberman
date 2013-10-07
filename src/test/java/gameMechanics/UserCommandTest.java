package gameMechanics;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.JSONParser;
import gameMechanics.GameMap.GameMap;
import org.junit.*;

import utils.UserGameSession;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class UserCommandTest {

	@Test
	public void testUserCommand()
	{
		String jsonObj = "";
		JSONParser parser = new JSONParser();
		UserCommand result = new UserCommand(jsonObj, parser);
		assertNotNull(result);
	}
	
	@Test
	public void testCreateJSONDeleteCommand()
	{
		Integer fromUserId = new Integer(1);

		String result = UserCommand.CreateJSONDeleteCommand(fromUserId);
		assertEquals("{\"value\":null,\"from\":{\"userId\":1,\"userName\":\"\"},\"type\":\"delete\"}", result);
	}

	@Test
	public void testCreateJSONLoseCommand()
	{
		Integer loserId = new Integer(1);
		String result = UserCommand.CreateJSONLoseCommand(loserId);

		assertEquals("{\"value\":null,\"from\":{\"userId\":1,\"userName\":\"\"},\"type\":\"lose\"}", result);
	}

	@Test
	public void testCreateJSONPlayCommand()
	{
		Integer fromUserId = new Integer(1);
		String fromUserName = "";
		String result = UserCommand.CreateJSONPlayCommand(fromUserId, fromUserName);
		assertEquals("{\"value\":null,\"from\":{\"userId\":1,\"userName\":\"\"},\"type\":\"play\"}", result);
	}

	@Test
	public void testCreateJSONStartGame()
	{
		Map<Integer, UserGameSession> userIdToSession = new HashMap<Integer, UserGameSession>();
		userIdToSession.put(1, new UserGameSession("name", 1));
		userIdToSession.put(2, new UserGameSession("name", 2));
		
		GameSession gameSession = mock(GameSession.class);	
		when(gameSession.getUsers()).thenReturn(userIdToSession);
		when(gameSession.GetPolygon()).thenReturn(10);
		
		GameMap map = mock(GameMap.class);
		
		String result = UserCommand.CreateJSONStartGame(gameSession, map);
		
		assertEquals("{\"value\":{\"polygon\":10,\"users\":[{\"speed\":2,\"userId\":1,\"userName\":\"name\",\"radius\":1,\"y\":0,\"x\":0},{\"speed\":2,\"userId\":2,\"userName\":\"name\",\"radius\":1,\"y\":0,\"x\":0}],\"map_width\":null,\"map\":null,\"map_height\":null},\"from\":{\"userId\":null,\"userName\":\"Server\"},\"type\":\"start_game\"}", result);
	}

	@Test
	public void testCreateJSONWinCommand()
	{
		Integer winnerId = new Integer(1);
		String result = UserCommand.CreateJSONWinCommand(winnerId);
		assertEquals("{\"value\":null,\"from\":{\"userId\":1,\"userName\":\"\"},\"type\":\"win\"}", result);
	}

	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(UserCommandTest.class);
	}
}