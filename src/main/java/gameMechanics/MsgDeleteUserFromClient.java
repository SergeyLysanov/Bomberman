package gameMechanics;

import base.Address;
import base.GameSocket;


public class MsgDeleteUserFromClient extends MsgToGameSocket
{
	final private Integer userId;

	public MsgDeleteUserFromClient(Address from, Address to, Integer userId) {
		super(from, to);
		this.userId = userId;
	}

	@Override
	public void exec(GameSocket gameSocket) 
	{
		gameSocket.deleteUserFromClient(this.userId);
	}
	
}