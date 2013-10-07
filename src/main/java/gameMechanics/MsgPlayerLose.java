package gameMechanics;

 // $codepro.audit.disable unnecessaryImport

 // $codepro.audit.disable unnecessaryImport
import base.Address;
import base.GameSocket;

public class MsgPlayerLose extends MsgToGameSocket
{
	private Integer loserId;
	
	public MsgPlayerLose(Address from, Address to, Integer loserId) {
		super(from, to);
		this.loserId = loserId;
	}

	@Override
	public void exec(GameSocket gameSocket) 
	{
		gameSocket.playerLose(this.loserId);
	}
	
}