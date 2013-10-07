package base;

import utils.UserSession;

public interface DatabaseService extends Runnable, Abonent 
{
	void getUserData(UserSession userSession, Address from);
	UserDataSet getUserData(UserSession userSession);
	void registerUser(UserSession userSession);
}
