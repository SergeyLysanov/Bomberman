package utils;

public class UserGameSession 
{
	public	String 			sessionId = new String(); 
	public  String 			userName = new String();
	public  int				killedByUserId = 0;
	public	int				userId = -1;
	public  int				playerNum = 0;
	public  int				boomRadius = 1;
	public  int 			speed = 2;
	public  int				x;
	public  int				y;
	public  Boolean			isAlive = true;
	public  Boolean			isReady = false;

	public UserGameSession(){};
	public UserGameSession(String userName, int userId)
	{
		this.userName = userName;
		this.userId = userId;
	}
}
