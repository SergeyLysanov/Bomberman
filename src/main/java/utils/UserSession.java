package utils;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;


public class UserSession 
{
	public     	AtomicReference<String> sessionId = new AtomicReference<String>();
	public     	AtomicReference<String> userName = new AtomicReference<String>();
	public     	AtomicReference<String> password = new AtomicReference<String>();
	public     	AtomicReference<String> realPassword = new AtomicReference<String>();
	public     	AtomicReference<String> email = new AtomicReference<String>() ;
	public		AtomicLong				userId = new AtomicLong(-1);
	public  	AtomicReference<SessionStatus>	eSessionStatus = new AtomicReference<SessionStatus>(SessionStatus.eInProcess);

	public UserSession(){};
	public UserSession(String userName, Integer userId)
	{
		this.userName.set(userName);
		this.userId.set(userId);
	}
	
	public UserSession(String userName, String sessionId, String password)
	{
		this.userName.set(userName);
		this.sessionId.set(sessionId);
		this.password.set(password);
	}
}
