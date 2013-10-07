package gameMechanics;

import base.Address;
import base.GameMechanics;
 // $codepro.audit.disable unnecessaryImport


public class MsgAddUser extends MsgToGameMechanics
{
	final private String userName;
	final private Integer userId;

	public MsgAddUser(Address from, Address to, String userName, Integer userId) {
		super(from, to);
		this.userName = userName;
		this.userId = userId;
	}

	@Override
	public void exec(GameMechanics gameMechanics) 
	{
		gameMechanics.addUser(this.userName, this.userId, this.getFrom());
		
	}
	
}
