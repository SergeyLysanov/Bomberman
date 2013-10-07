package base;

import gameMechanics.GameMap.GameMap;
import gameMechanics.GameSession;
import gameMechanics.UserCommand;
import utils.UserGameSession;

public interface GameSocket extends Runnable, Abonent 
{
	public void addExistingUser(Integer forUserId, UserGameSession existingUserSession);
	public void deleteUserFromClient(Integer userId);
	public void startGame(GameSession gameSession, GameMap map);
	public void playerWin(Integer winnerId);
	public void playerLose(Integer loserId);
	public UserCommand parseMessage(String message);
	public void setupGameMechanicsAddress(Address address);
}
