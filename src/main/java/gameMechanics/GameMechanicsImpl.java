package gameMechanics;

import gameMechanics.GameMap.MapDirector;
import gameMechanics.GameMap.RandomMapBuilder;

 // $codepro.audit.disable unnecessaryImport
import java.util.Map;
 // $codepro.audit.disable unnecessaryImport
 // $codepro.audit.disable unnecessaryImport
 // $codepro.audit.disable unnecessaryImport
import java.util.logging.Logger;

 // $codepro.audit.disable unnecessaryImport

 // $codepro.audit.disable unnecessaryImport
 // $codepro.audit.disable unnecessaryImport

import utils.TimeHelper;
import utils.UserGameSession;
import base.Address;
import base.GameMechanics;
import base.MessageSystem;

import gameMechanics.GameMap.GameMap;

public class GameMechanicsImpl implements GameMechanics
{
	private static Logger 	logger = Logger.getLogger("FileLogger");
	private Address 		gameMechanicsAddress = new Address();
	private Address			gameSocketAddress;
	private MessageSystem 	messageSystem;
	
	private GameSession 	gameSession = new GameSession();
	private GameMap 		map;
	
	public GameMechanicsImpl(MessageSystem messageSystem)
	{
		logger.info("GameMechanics created");
		this.messageSystem = messageSystem;
		this.messageSystem.addService(this);
		this.gameSocketAddress = messageSystem.getAddressService().getAddress(GameSocketImpl.class);
	}
	
	@Override
	public void run() {
		while(true)
		{
			messageSystem.execForAbonent(this);
			TimeHelper.Sleep(30);
		}
	}

	@Override
	public Address getAddress() {
		return gameMechanicsAddress;
	}
	
	@Override
	public void addUser(String userName, Integer userId, Address from) 
	{
		//When user connected
		logger.info("GameMechanicsImpl::addNewUser " + userName);
		//System.out.println("GameMechanicsImpl::addNewUser " + userName);
		
		//send users data to new user
		Map<Integer, UserGameSession> userIdToSession = gameSession.getUsers();
		for (UserGameSession session : userIdToSession.values()) 
		{
	    	MsgAddExistingUser msg = new MsgAddExistingUser(gameMechanicsAddress, 
	    			gameSocketAddress, userId, session); //push users
	    	
	    	messageSystem.sendMessage(msg);
		}
		
		//Create new user
		gameSession.createUser(userName, userId, this);
	}
	
	@Override
	public void userReady(String userName, Integer userId, boolean isReady) 
	{
		logger.info("User ready " + userName);
		gameSession.userReady(userId, isReady, this);
	}
	
	@Override
	public void startGame()
	{
		gameSession.setupPlayersToStart();
		//Create map
		
		MapDirector director = new MapDirector();
		RandomMapBuilder mapBuilder = new RandomMapBuilder();
		director.setMapBuilder(mapBuilder);
		director.constructMap(gameSession);
		
		this.map = director.getMap();
		logger.info("Map created");
		
		//send start game message
		logger.info("Start game message");
    	MsgStartGame msg = new MsgStartGame(gameMechanicsAddress, gameSocketAddress, gameSession, map);
    	
    	messageSystem.sendMessage(msg);
	}
	
	@Override
	public void deleteUser(Integer userId, Address from) //Когда пользователь вышел
	{
		//System.out.println("GameMechanicsImpl::deleteUser " + userId);
		logger.info("deleteUser " + userId);
		gameSession.deleteUser(userId);
		
		//send message to delete user
    	MsgDeleteUserFromClient msg = new MsgDeleteUserFromClient(gameMechanicsAddress, 
    			gameSocketAddress, userId); 
    	
    	messageSystem.sendMessage(msg);
	}

	@Override
	public void destroyUser(Integer userId, Integer byUserId, Address from) //Когда пользователь умер
	{
		//если одновременно два умирают, игра запускается два раза.
		logger.info("destroyUser " + userId + "byUser " + byUserId);
		gameSession.destroyPlayer(userId, byUserId, this);
	}

	@Override
	public void sendWinMessage(Integer toUserId) 
	{
		//Create win message
		logger.info("Send win message " + toUserId);
    	MsgPlayerWin msg = new MsgPlayerWin(gameMechanicsAddress, 
    			gameSocketAddress, toUserId); 
    	
    	messageSystem.sendMessage(msg);
	}

	@Override
	public void sendLoseMessage(Integer toUserId) 
	{
		//Create lose message
		logger.info("Send lose message " + toUserId);
    	MsgPlayerLose msg = new MsgPlayerLose(gameMechanicsAddress, 
    			gameSocketAddress, toUserId); 
    	
    	messageSystem.sendMessage(msg);
	}
	
	@Override
	public int getUserCount()
	{
		return gameSession.getUsersCount();
	}
	
	public int getAliveUsers()
	{
		return gameSession.getALivePlayers();
	}
	
	public GameMap getMap()
	{
		return map;
	}
}
