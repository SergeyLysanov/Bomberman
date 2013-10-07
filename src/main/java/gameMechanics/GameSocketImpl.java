package gameMechanics;

import java.io.IOException;
 // $codepro.audit.disable unnecessaryImport
 // $codepro.audit.disable unnecessaryImport
 // $codepro.audit.disable unnecessaryImport
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;

import org.json.simple.parser.JSONParser;

 // $codepro.audit.disable unnecessaryImport
import gameMechanics.GameMap.GameMap;

import utils.TimeHelper;
 // $codepro.audit.disable unnecessaryImport
import utils.UserGameSession;


 // $codepro.audit.disable unnecessaryImport
import base.Address;
import base.GameSocket;
import base.MessageSystem;

public class GameSocketImpl extends WebSocketServlet implements GameSocket
{
	private static Logger logger = Logger.getLogger("FileLogger");
	private static final long serialVersionUID = 466045608072654615L;
	private final ConcurrentHashMap<Integer, GameWebSocket> userIdToSocket = new ConcurrentHashMap<Integer, GameWebSocket>();
	private JSONParser 	parser = new JSONParser();
	private Address gameSocketAddress = new Address();
	private Address	gameMechanicsAddress;
	private MessageSystem messageSystem;
	
	public GameSocketImpl(MessageSystem messageSystem)
	{
		this.messageSystem = messageSystem;
		this.messageSystem.addService(this);
	}
	
	@Override
	public WebSocket doWebSocketConnect(HttpServletRequest arg0, String arg1) 
	{
		//System.out.println("GameWebSocket::doWebSocketConnect");
		logger.info("GameWebSocket::doWebSocketConnect");
		return new GameWebSocket();
	}
	
	@Override
	public void run() 
	{
		while(true)
		{
			messageSystem.execForAbonent(this);
			TimeHelper.Sleep(15);
		}
	}

	@Override
	public Address getAddress() {
		return gameSocketAddress;
	}
	
	@Override
	public void addExistingUser(Integer forUserId, UserGameSession existingUserSession) {
		logger.log(Level.INFO, "add existing users for userId"  + forUserId);
		GameWebSocket ws  = userIdToSocket.get(forUserId);
		String message = UserCommand.CreateJSONPlayCommand(existingUserSession.userId,
				existingUserSession.userName);
		
		ws.SendMessage(message);
	}
	
	@Override
	public void deleteUserFromClient(Integer userId) {
		String message = UserCommand.CreateJSONDeleteCommand(userId);
		
		for (GameWebSocket ws : userIdToSocket.values()) 
		{
			ws.SendMessage(message);
		}	
	}
	
	@Override
	public void startGame(GameSession gameSession, GameMap map)
	{
		String message = UserCommand.CreateJSONStartGame(gameSession, map);
		
		for (GameWebSocket ws : userIdToSocket.values()) {
			ws.SendMessage(message);
		}	
	}
	
	@Override
	public void playerWin(Integer winnerId) {
		logger.log(Level.INFO, "Player win "  + winnerId);
		String message = UserCommand.CreateJSONWinCommand(winnerId);
		userIdToSocket.get(winnerId).SendMessage(message);
	}
	
	@Override
	public void playerLose(Integer loserId) {
		logger.log(Level.INFO, "Player lose: "  + loserId);
		String message = UserCommand.CreateJSONLoseCommand(loserId);
		userIdToSocket.get(loserId).SendMessage(message);
	}
	
	@Override
	public UserCommand parseMessage(String message)
	{
		//Parse message
		UserCommand command = new UserCommand(message, parser);
		if(command.type.equals("play"))
		{
			//add new user to GameSession
			logger.log(Level.INFO, "Player " + command.fromUserName + " play");
	    	MsgAddUser msg = new MsgAddUser(gameSocketAddress, gameMechanicsAddress, command.fromUserName, command.fromUserId);
	    	messageSystem.sendMessage(msg);
		}
		else if(command.type.equals("destroy"))
		{
			//destroy player in GameSession
			Integer byUserId = Integer.parseInt(command.value.get("byUser").toString());
			logger.log(Level.INFO, "Player " + command.fromUserName + " destroyed by userId: " + byUserId);
	    	MsgDestroyUser msg = new MsgDestroyUser(gameSocketAddress, gameMechanicsAddress, command.fromUserId, byUserId);
	    	messageSystem.sendMessage(msg);
		}
		else if(command.type.equals("ready"))
		{
	    	boolean isReady =  Boolean.parseBoolean(command.value.get("isReady").toString());
	    	logger.log(Level.INFO, "Player " + command.fromUserName + " ready " + isReady);
	    	MsgUserReady msg = new MsgUserReady(gameSocketAddress, gameMechanicsAddress, command.fromUserName, command.fromUserId, isReady);
	    	messageSystem.sendMessage(msg);
		}
		
		return command;
	}
	
	@Override
	public void setupGameMechanicsAddress(Address address)
	{
		this.gameMechanicsAddress = address;
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	//Game web socket class
	private class GameWebSocket implements WebSocket.OnTextMessage
	{
		private Connection  connection;
		private Integer 	userId;
	
		@Override
		public void onClose(int closeCode, String message) 
		{
			//System.out.println("GameWebSocket::onClose");
			logger.info("GameWebSocket::onClose");
			if(userIdToSocket.containsKey(this.userId))
			{
				userIdToSocket.remove(this.userId);
	
		    	MsgDeleteUser msg = new MsgDeleteUser(gameSocketAddress, gameMechanicsAddress, this.userId);
		    	messageSystem.sendMessage(msg);
			}
		}

		@Override
		public void onOpen(Connection arg0) 
		{
			this.connection = arg0;
			//System.out.println("GameWebSocket::onOpen user open ");
			logger.info("GameWebSocket::onOpen user open ");
		}

		@Override
		public void onMessage(String message) 
		{
			UserCommand command = parseMessage(message);
			if(command.type.equals("play"))
			{
				//Subscribe on event
				userIdToSocket.put(command.fromUserId, this);
				this.userId = command.fromUserId;
			}
			
			//Send message to all users except current
			for (GameWebSocket ws : userIdToSocket.values()) {
				if(!(ws.equals(this)))
	        	{
		            ws.SendMessage(message);
	        	}
			}
		}
		
		public void SendMessage(String message)
		{
            try{
               connection.sendMessage(message);
            }
            catch (IOException e){
                e.printStackTrace();
                logger.log(Level.SEVERE, "SendMessage error");
            }
		}
	}
}
