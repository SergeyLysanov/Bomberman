package gameMechanics;

import base.Address;
import base.GameMechanics;

public class MsgDeleteUser extends MsgToGameMechanics
{
	final private Integer userId;

	public MsgDeleteUser(Address from, Address to, Integer userId) {
		super(from, to);
		this.userId = userId;
	}

	@Override
	public void exec(GameMechanics gameMechanics) 
	{
		gameMechanics.deleteUser(this.userId, this.getFrom());
		
	}
	
}