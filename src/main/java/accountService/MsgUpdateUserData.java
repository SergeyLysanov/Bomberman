package accountService;

import utils.UserSession;
import base.AccountService;
import base.Address;
import frontend.MessageToAccountService;

public class MsgUpdateUserData extends MessageToAccountService
{
	final private UserSession userSession;
	
	public MsgUpdateUserData(Address from, Address to, UserSession userSession)
	{
		super(from, to);
		this.userSession = userSession;
	}

	@Override
	public void exec(AccountService accountService, Address from) 
	{
		accountService.getUpdateUser(userSession);
	}
}
