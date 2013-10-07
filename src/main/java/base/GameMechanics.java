package base;

//import utils.UserSession;

public interface GameMechanics extends Runnable, Abonent 
{
	public void addUser(String userName, Integer userId, Address from);
	public void userReady(String userName, Integer userId, boolean isReady);
	public int  getUserCount();
	public void deleteUser(Integer userId, Address from);
	public void destroyUser(Integer userId, Integer byUserId, Address from);
	public void startGame();
	public void sendWinMessage(Integer toUserId);
	public void sendLoseMessage(Integer toUserId);
}
