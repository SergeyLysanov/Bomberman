package gameMechanics.GameMap;

import java.util.logging.Logger;

import org.json.simple.JSONArray;
 // $codepro.audit.disable unnecessaryImport

public class GameMap {
	private static Logger logger = Logger.getLogger("FileLogger");
	public Integer width;
	public Integer height;
	MapObject[][] objects;

	public GameMap(int height, int width)
	{
		this.width = width;
		this.height = height;
		objects = new MapObject[width][height];
	}
	public void addBush(int x, int y)
	{
		MapObject bush = new MapObject("bush", null);
		addElement(bush, x,  y);
	}

	public void addBox(int x, int y, String bonus)
	{
		MapObject box = new MapObject("box", bonus);
		addElement(box, x,  y);
	}

	public void addPlayer(int x, int y, Integer userId)
	{
		//MapObject player = new MapObject("player", userId.toString());
		//addElement(player, x,  y);
		//replace box near player
		for(int i = x-1; i <= x+1; ++i)
			for(int j=y-1; j <= y+1; ++j)
			{
				MapObject  obj = getObject(i, j);
				if(obj != null)
					if(obj.type.equals("box"))
						addGrass(i, j);
			}
		
	}

	public void  addGrass(int x, int y)
	{
		MapObject grass = new MapObject("grass", null);
		addElement(grass, x,  y);
	}

	public MapObject getObject(int x, int y)
	{

		try{
			return 	objects[x][y];
		}
		catch(java.lang.ArrayIndexOutOfBoundsException e){
			return null;
		}
	}

	public JSONArray toJSON()
	{
		JSONArray rows = new JSONArray();
		for(int i = 0; i < width; ++i)
		{
			JSONArray col = new JSONArray();
			for(int j = 0; j < height; ++j)
			{
				col.add(objects[i][j].toJSON());
			}
			rows.add(col);
		}
		return rows;
	}

	private void addElement(MapObject obj, int x, int y)
	{
		try{
			objects[x][y] = obj;
		}
		catch(java.lang.ArrayIndexOutOfBoundsException e){
			System.out.println("ERROR x" + x);
			System.out.println("ERROR y" + y);
			logger.severe(e.toString() + "x: " + x + " y: " + y );
		}

	}
}
