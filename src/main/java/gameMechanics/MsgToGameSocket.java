package gameMechanics;

import base.Msg;
import base.Abonent;
import base.Address;
import base.GameSocket;

public abstract class MsgToGameSocket extends Msg
{
	public MsgToGameSocket(Address from, Address to)
	{
		super(from, to);
	}

	public void exec(Abonent abonent)
	{
		if(abonent instanceof GameSocket)
		{
			exec((GameSocket)abonent);
		}
	}
	
	public abstract void exec(GameSocket gameSocket);
}
