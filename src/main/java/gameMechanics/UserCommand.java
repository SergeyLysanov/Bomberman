package gameMechanics;

import gameMechanics.GameMap.GameMap;

import java.util.Map;
 // $codepro.audit.disable unnecessaryImport
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import utils.UserGameSession;

public class UserCommand 
{
	private static Logger logger = Logger.getLogger("FileLogger");
	public String 		type;
	public JSONObject 		value;
	public Integer		fromUserId;
	public String  		fromUserName;

	public UserCommand(String jsonObj, JSONParser parser) 
	{
		try
		{
			Object obj = parser.parse(jsonObj);

			JSONObject jsonObject = (JSONObject) obj;

			this.type = (String) jsonObject.get("type");

			JSONObject from = (JSONObject) jsonObject.get("from");
			this.value = (JSONObject) jsonObject.get("value");
			this.fromUserId = Integer.parseInt((String)from.get("userId"));
			this.fromUserName = (String) from.get("userName");


			//System.out.println("GameWebSocket::onMessage commandType: " + commandType);
		}
		catch(ParseException e)
		{
			logger.severe("GameWebSocket::onMessage PARSE ERROR " + e);
			//System.out.println("GameWebSocket::onMessage PARSE ERROR" + e);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////
	//Factory methods
	@SuppressWarnings("unchecked")
	public static String CreateJSONPlayCommand(Integer fromUserId, String fromUserName)
	{
		JSONObject obj = new JSONObject();
		JSONObject from = new JSONObject();
		obj.put("type", "play");
		obj.put("value", null);
		obj.put("from", from);

		from.put("userName", fromUserName);
		from.put("userId", fromUserId);

		return obj.toJSONString();
	}
	@SuppressWarnings("unchecked")
	public static String CreateJSONDeleteCommand(Integer fromUserId)
	{
		JSONObject obj = new JSONObject();
		JSONObject from = new JSONObject();
		obj.put("type", "delete");
		obj.put("value", null);
		obj.put("from", from);

		from.put("userName", "");
		from.put("userId", fromUserId);

		return obj.toJSONString();
	}
	@SuppressWarnings("unchecked")
	public static String CreateJSONStartGame(GameSession gameSession, GameMap map)
	{
		Map<Integer, UserGameSession> userIdToSession = gameSession.getUsers();
		
		JSONObject obj = new JSONObject();
		JSONObject from = new JSONObject();
		JSONObject value = new JSONObject(); 
		JSONArray users = new JSONArray();
		obj.put("type", "start_game");

		for (UserGameSession userSession : userIdToSession.values()) //Put all users
		{
			JSONObject userJSON = new JSONObject();
			userJSON.put("userName", userSession.userName);
			userJSON.put("userId", userSession.userId);
			userJSON.put("x", userSession.x);
			userJSON.put("y", userSession.y);
			userJSON.put("radius", userSession.boomRadius);
			userJSON.put("speed", userSession.speed);

			users.add(userJSON);
		}	

		obj.put("value", value);
		obj.put("from", from);
		value.put("users", users);
		value.put("polygon", gameSession.GetPolygon());
		value.put("map", map.toJSON());
		value.put("map_width", map.width);
		value.put("map_height", map.height);

		from.put("userName", "Server");
		from.put("userId", null);

		return obj.toJSONString();
	}

	@SuppressWarnings("unchecked")
	public static String CreateJSONWinCommand(Integer winnerId)
	{
		JSONObject obj = new JSONObject();
		JSONObject from = new JSONObject();
		obj.put("type", "win");
		obj.put("value", null);
		obj.put("from", from);

		from.put("userName", "");
		from.put("userId", winnerId);

		return obj.toJSONString();
	}

	@SuppressWarnings("unchecked")
	public static String CreateJSONLoseCommand(Integer loserId)
	{
		JSONObject obj = new JSONObject();
		JSONObject from = new JSONObject();
		obj.put("type", "lose");
		obj.put("value", null);
		obj.put("from", from);

		from.put("userName", "");
		from.put("userId", loserId);

		return obj.toJSONString();
	}
}
