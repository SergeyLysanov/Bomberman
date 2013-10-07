package base;

import javax.servlet.http.HttpSession;

import utils.UserSession;

public interface Frontend extends Runnable, Abonent 
{
	public void updateUserSession(UserSession newUserSession);
	public String getUserSessionStatus(HttpSession session);
}
