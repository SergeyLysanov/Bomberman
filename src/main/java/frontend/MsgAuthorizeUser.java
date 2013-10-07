package frontend;

import utils.UserSession;
import base.AccountService;
import base.Address;

public class MsgAuthorizeUser extends MessageToAccountService
{
	final private UserSession userSession;
	
	public MsgAuthorizeUser(Address from, Address to, UserSession userSession)
	{
		super(from, to);
		this.userSession = userSession;
	}

	@Override
	public void exec(AccountService accountService, Address from) 
	{
		accountService.authorize(userSession);
	}
}
