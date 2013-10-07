package gameMechanics.GameMap;

import base.MapBuilder;

public class RandomMapBuilder extends MapBuilder{

	
//	public RandomMapBuilder(int width, int height) {
//		this.width = width;
//		this.height = height;
//	}
	@Override
	public void BuildGrasses() {
		for(int i = 1; i < map.width - 1; ++i) //x-axis
			for(int j = 1; j < map.height - 1; ++j) //y-axis
			{
				map.addGrass(i, j);
			}
	}
	
	@Override
	public void BuildBushes() {
		//Create bushed along x-axis
		for(int i = 0; i < map.width; i++) {
			 map.addBush(i, 0);
			 map.addBush(i, map.height-1);
			 
		}
		//Create bushed along y-axis
		for(int i = 1; i < map.height - 1; i++) {
			 map.addBush(0, i);
			 map.addBush(map.width-1, i);
		}
		
		for(int i = 2; i < map.width-1; i += 2) //x-axis
		{
			for(int j = 2; j < map.height-1; j += 2) //y-axis
		  	{
				map.addBush(i, j);
		  	}
		}
	}

	@Override
	public void AddUser(int userId, int posX, int posY) {
		map.addPlayer(posX, posY, userId);
	}

	@Override
	public void BuildBoxes() {
		//random pattern Min + (int)(Math.random() * ((Max - Min) + 1));
		  for(int i = 3; i < map.width - 1; i += 2) //x-axis
			  	for(int j = 3; j < map.height - 1; j += 2) //y-axis
					if (getRandomNumber(1,7) < 6)
					{
						String bonus = null;
						if ( 7 < getRandomNumber(0,9))//create bonus
						{ // random bonus set [0,9]
						}
						map.addBox(i, j, bonus);
					}
			
	}
	
	private int getRandomNumber(int min, int max)
	{
		return min + (int)(Math.random() * ((max) + 1));
	}

}
