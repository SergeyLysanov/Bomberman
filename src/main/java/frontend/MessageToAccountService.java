package frontend;
import base.AccountService;
import base.Abonent;
import base.Address;
import base.Msg;

public abstract class MessageToAccountService extends Msg  
{
	public MessageToAccountService(Address from, Address to)
	{
		super(from, to);
	}
	
	public void exec(Abonent abonent)
	{
		if(abonent instanceof AccountService)
			exec((AccountService)abonent, this.getFrom());
	}
	
	public abstract void exec(AccountService accountService, Address from);
	
	
}
