package gameMechanics;

 // $codepro.audit.disable unnecessaryImport

 // $codepro.audit.disable unnecessaryImport
import base.Address;
import base.GameSocket;

public class MsgPlayerWin extends MsgToGameSocket
{
	private Integer winnerId;
	
	public MsgPlayerWin(Address from, Address to, Integer winnerId) {
		super(from, to);
		this.winnerId = winnerId;
	}

	@Override
	public void exec(GameSocket gameSocket) 
	{
		gameSocket.playerWin(this.winnerId);
	}
	
}