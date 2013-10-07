package frontend;

import utils.UserSession;
import base.AccountService;
import base.Address;

public class MsgRegisterUser extends MessageToAccountService
{
	final private UserSession userSession;
	
	public MsgRegisterUser(Address from, Address to, UserSession userSession)
	{
		super(from, to);
		this.userSession = userSession;
	}

	@Override
	public void exec(AccountService accountService, Address from) 
	{
		accountService.register(userSession);
	}
}