package resourceSystem;

import base.Resource;
import base.ResourceSystem;

public class GameResourceFactory 
{
	private static GameResourceFactory instance = null;
	private ResourceSystem resourceSystem = new ResourceSystemImpl();
	
	private GameResourceFactory() {
	}
	
	public static GameResourceFactory instance()
	{
		if(instance == null){
			instance =new GameResourceFactory();
		}
		
		return instance;
	}
	
	public Resource get(String fileName)
	{
		return resourceSystem.getGameResource(fileName);
	}
}
