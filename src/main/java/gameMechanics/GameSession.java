package gameMechanics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 // $codepro.audit.disable unnecessaryImport
 // $codepro.audit.disable unnecessaryImport
import javax.swing.Timer;

import base.GameMechanics;

import resourceSystem.GameResource;
import resourceSystem.GameResourceFactory;

import utils.Pair;
import utils.TimeHelper;
import utils.UserGameSession;

public class GameSession 
{
	//public Set<Integer> users = new CopyOnWriteArraySet<Integer>();
	private Map<Integer, UserGameSession> userIdToSession = new HashMap<Integer, UserGameSession>();
	List<Pair<Integer, Integer>> positions = new ArrayList<Pair<Integer,Integer>>();
	private final GameResource gameResource = (GameResource) GameResourceFactory.instance().get("GameResource");
	private boolean isTimerRunning = false;

	public GameSession()
	{
		InitializePositions();
	}
	
	public void createUser(String userName, Integer userId, GameMechanics gameMechanics)
	{
		UserGameSession userSession = new UserGameSession(userName, userId);
		
		userIdToSession.put(userSession.userId, userSession);
	}
	
	public void userReady(Integer userId, boolean isReady, GameMechanics gameMechanics)
	{
		UserGameSession userGameSession = userIdToSession.get(userId);
		userGameSession.isReady = isReady;
		
		if(isTimeToStartGame())//it is time to start game!
		{
			gameMechanics.startGame();	
		}
	}
	
	public boolean isTimeToStartGame()
	{
		
		int readyCount = 0;
		
		for(Integer id : userIdToSession.keySet())
		{
			UserGameSession userSession = userIdToSession.get(id);
			if(userSession.isReady == true)
				++readyCount;
		}
		
		return (readyCount == userIdToSession.size());
	}
	
	public void setupPlayersToStart()
	{
		int playerNum = 0;
		for(Integer id : userIdToSession.keySet())
		{
			UserGameSession userSession = userIdToSession.get(id);
			Pair<Integer, Integer> pos = positions.get(playerNum);
			userSession.x = (int) pos.getFirst();
			userSession.y = (int) pos.getSecond();
			userSession.isAlive = true;
			userSession.isReady = false;
			userSession.boomRadius = gameResource.boomRadius;
			userSession.speed = gameResource.speed;
			userSession.playerNum = playerNum;
			playerNum = playerNum + 1;
		}
	}
	
	public Map<Integer, UserGameSession> getUsers()
	{
		return userIdToSession;
	}
	
	public void deleteUser(Integer userId)
	{
		userIdToSession.get(userId).isAlive = true;
		userIdToSession.remove(userId);
	}
	
	public int getUsersCount()
	{
		return userIdToSession.size();
	}
	
	public Boolean isUserAlive(Integer userId)
	{
		return userIdToSession.get(userId).isAlive;
	}
	
	public void destroyPlayer(Integer userId, Integer byUserId, final GameMechanics gameMechanics)
	{
		userIdToSession.get(userId).isAlive = false;
		userIdToSession.get(userId).killedByUserId = byUserId;
		
		gameMechanics.sendLoseMessage(userId);
		
		if(getALivePlayers() <= 1) //end round
		{
			//Check draw game 
			if(!isTimerRunning)
			{
				ActionListener al = new ActionListener() 
				{
					@Override
					public void actionPerformed(ActionEvent e) 
					{
						for(Integer id : userIdToSession.keySet())
						{
							if(isUserAlive(id)){
								gameMechanics.sendWinMessage(id);
							}
						}
						
						//Restart round
						TimeHelper.Sleep(1000);
						gameMechanics.startGame();
						isTimerRunning = false;
					}
				};
	
				Timer t = new Timer(1000,al); // Timer(TimeInMilliSeconds, ActionListener) 1000ms = 1s 
				t.setRepeats(false);
				isTimerRunning = true;
				t.start();
			}
		}
	}
	
	public void restartSessions()
	{
		for (UserGameSession session : userIdToSession.values()){
			session.isAlive = true;
		}
		//alivePlayersCount = userIdToSession.size();
	}
	
	public Integer getALivePlayers()
	{
		Integer alivePlayers = 0;
		for(Integer id : userIdToSession.keySet())
		{
			if(userIdToSession.get(id).isAlive)
				alivePlayers += 1;
		}
		return alivePlayers;
	}
	
	
	private void InitializePositions()
	{
		//players 4 players pos
		Pair<Integer,Integer> pair1=new Pair<>(7,7);
		Pair<Integer,Integer> pair2=new Pair<>(15,7);
		Pair<Integer,Integer> pair3=new Pair<>(1,1);
		Pair<Integer,Integer> pair4=new Pair<>(7,1);
		positions.add(pair1);
		positions.add(pair2);
		positions.add(pair3);
		positions.add(pair4);
	}
	
	/*////////////////////////////////////////////////////////////////////////////////////
	//Resource getters*/
	public int GetMapWidth(){
		return gameResource.mapWidth;
	}
	
	public int GetMapHeight(){
		return gameResource.mapHeight;
	}
	
	public int GetMaxUsersCount(){
		return gameResource.countPlayers;
	}
	
	public int GetBoomRadius(){
		return gameResource.boomRadius;
	}
	
	public int GetPolygon(){
		return gameResource.polygon;
	}
	
}
