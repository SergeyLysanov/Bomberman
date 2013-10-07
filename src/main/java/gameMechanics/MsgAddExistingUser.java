package gameMechanics;

import base.Address;
import base.GameSocket;
import utils.UserGameSession;


public class MsgAddExistingUser extends MsgToGameSocket
{
	final private UserGameSession userSession;
	final private Integer forUserId;

	public MsgAddExistingUser(Address from, Address to, Integer forUserId, UserGameSession userSession) {
		super(from, to);
		this.userSession = userSession;
		this.forUserId = forUserId;
	}

	@Override
	public void exec(GameSocket gameSocket) 
	{
		gameSocket.addExistingUser(this.forUserId, this.userSession);
	}
	
}