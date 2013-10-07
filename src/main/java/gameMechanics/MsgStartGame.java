package gameMechanics;

import gameMechanics.GameMap.GameMap;

 // $codepro.audit.disable unnecessaryImport

 // $codepro.audit.disable unnecessaryImport
import base.Address;
import base.GameSocket;

public class MsgStartGame extends MsgToGameSocket
{
	private GameSession gameSession;
	private GameMap map;
	
	public MsgStartGame(Address from, Address to, GameSession gameSession, GameMap map) {
		super(from, to);
		this.gameSession = gameSession;
		this.map = map;
	}

	@Override
	public void exec(GameSocket gameSocket) 
	{
		gameSocket.startGame(gameSession, map);
	}
	
}