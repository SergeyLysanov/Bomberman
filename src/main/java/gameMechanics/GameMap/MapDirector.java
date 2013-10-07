package gameMechanics.GameMap;

import java.util.Map;

import utils.UserGameSession;
import gameMechanics.GameSession;
import base.MapBuilder;

public class MapDirector 
{
	private MapBuilder mapBuilder;
	
	public void setMapBuilder(MapBuilder mapBuilder)
	{
		this.mapBuilder = mapBuilder;
	}
	
	public GameMap getMap (){
		return this.mapBuilder.getMap();
	}
	
	public void constructMap(GameSession gameSession)
	{
		mapBuilder.createNewMap(gameSession.GetMapWidth(), gameSession.GetMapHeight());
		mapBuilder.BuildGrasses();
		mapBuilder.BuildBushes();
		mapBuilder.BuildBoxes();
		
		Map<Integer, UserGameSession> userIdToSession = gameSession.getUsers();
		for(Integer id : userIdToSession.keySet())
		{
			UserGameSession userSession = userIdToSession.get(id);
			mapBuilder.AddUser(userSession.userId, userSession.x, userSession.y);
		}
	}
}
