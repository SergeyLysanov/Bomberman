package databaseService;

import base.DatabaseService;
import base.Abonent;
import base.Address;
import base.Msg;

public abstract class MessageToDatabaseService extends Msg  
{
	public MessageToDatabaseService(Address from, Address to)
	{
		super(from, to);
	}
	
	public void exec(Abonent abonent)
	{
		if(abonent instanceof DatabaseService)
			exec((DatabaseService)abonent, this.getFrom());
	}
	
	public abstract void exec(DatabaseService databaseService, Address from);
	
	
}
