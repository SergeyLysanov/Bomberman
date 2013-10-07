package base;
import gameMechanics.GameMap.GameMap;

public abstract class MapBuilder 
{
	protected GameMap map;
	
	public GameMap getMap(){return map;}
	public void createNewMap(int width, int height)
	{
		map = new GameMap(height, width);
	}
	
	public abstract void BuildBushes();
	public abstract void BuildGrasses();
	public abstract void AddUser(int userId, int posX, int posY);
	public abstract void BuildBoxes();
}
