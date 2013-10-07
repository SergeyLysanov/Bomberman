package gameMechanics;

import base.Address;
import base.GameMechanics;

public class MsgDestroyUser extends MsgToGameMechanics
{
	final private Integer userId;
	final private Integer byUserId;

	public MsgDestroyUser(Address from, Address to, Integer userId, Integer byUserId) {
		super(from, to);
		this.userId = userId;
		this.byUserId = byUserId;
	}

	@Override
	public void exec(GameMechanics gameMechanics) 
	{
		gameMechanics.destroyUser(this.userId, this.byUserId, this.getFrom());
		
	}
	
}