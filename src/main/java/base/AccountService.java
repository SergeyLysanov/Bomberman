package base;

import utils.UserSession;

public interface AccountService extends Runnable, Abonent
{
	public void getUpdateUser(UserSession userSession);
	public void authorize(UserSession userSession);
	public void register(UserSession userSession);
}
