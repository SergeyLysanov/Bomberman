package gameMechanics;

import base.Msg;
import base.Abonent;
import base.Address;
import base.GameMechanics;

public abstract class MsgToGameMechanics extends Msg
{
	public MsgToGameMechanics(Address from, Address to)
	{
		super(from, to);
	}

	public void exec(Abonent abonent)
	{
		if(abonent instanceof GameMechanics)
		{
			exec((GameMechanics)abonent);
		}
	}
	
	public abstract void exec(GameMechanics gameMechanics);
}
