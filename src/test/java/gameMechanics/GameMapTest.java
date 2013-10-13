package gameMechanics;

import gameMechanics.GameMap.GameMap;
import gameMechanics.GameMap.MapDirector;
import gameMechanics.GameMap.RandomMapBuilder;

import org.json.simple.JSONArray;
import org.junit.*;

import static org.junit.Assert.*;

public class GameMapTest 
{
	@Test 
	public void testMapToJSON()
	{
		GameSession gameSession = new GameSession();
		
		MapDirector director = new MapDirector();
		RandomMapBuilder mapBuilder = new RandomMapBuilder();
		director.setMapBuilder(mapBuilder);
		director.constructMap(gameSession);
		
		GameMap map = director.getMap();
		JSONArray result = map.toJSON();
		
		assertNotNull(result);
	}


	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(GameMapTest.class);
	}
}
