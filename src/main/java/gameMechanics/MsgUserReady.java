package gameMechanics;

import base.Address;
import base.GameMechanics;

public class MsgUserReady extends MsgToGameMechanics
{
	final private String userName;
	final private Integer userId;
	final private boolean isReady;

	public MsgUserReady(Address from, Address to, String userName, Integer userId, boolean isReady) {
		super(from, to);
		this.userName = userName;
		this.userId = userId;
		this.isReady = isReady;
		
	}

	@Override
	public void exec(GameMechanics gameMechanics) 
	{
		gameMechanics.userReady(this.userName, this.userId, this.isReady);
		
	}
	
}