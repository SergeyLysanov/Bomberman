package databaseService;

import java.sql.Connection;
import java.sql.SQLException;

import java.util.logging.Logger;

import utils.LoggerFactory;
import utils.SessionStatus;
import utils.TimeHelper;
import utils.UserSession;
import accountService.MsgUpdateUserData;
import base.Address;
import base.DatabaseService;
import base.MessageSystem;
import base.UserDAO;
import base.UserDataSet;

public class DatabaseServiceImpl implements DatabaseService
{
	private static Logger logger = LoggerFactory.getLogger("DatabaseLogger", "./log/database-log.txt");
	private static ConnectionPool connectionPool = new ConnectionPool("bomberman");
	private Address databaseAddress = new Address();
	private MessageSystem messageSystem;
	
	public DatabaseServiceImpl(MessageSystem messageSystem) {
		logger.info("Database created");
		this.messageSystem = messageSystem;
		this.messageSystem.addService(this);
		
	}
	@Override
	public void run() {
		while(true)
		{
			messageSystem.execForAbonent(this);
			TimeHelper.Sleep(100);
		}
	}

	@Override
	public Address getAddress() {
		return databaseAddress;
	}

	@Override
	public void getUserData(UserSession userSession, Address from) //asynchro call
	{	
		
		try 
		{
			Connection connection = connectionPool.getConnection();
			UserDAO userDAO = new UserDAOImpl(connection);
			UserDataSetImpl userDataSet = userDAO.getByName(userSession.userName.get());
			if(userDataSet != null)
			{
				userSession.userId.set(userDataSet.getid());
				userSession.realPassword.set(userDataSet.getPassword());
			}
			else
			{
				userSession.eSessionStatus.set(SessionStatus.eWrongLogin);
				userSession.userId.set(-1);
			}
			
			logger.info("user id: " + userSession.userId + " user pass: " + userSession.realPassword);
			MsgUpdateUserData msg = new MsgUpdateUserData(databaseAddress, from, userSession);
			messageSystem.sendMessage(msg);
		} 
		catch (SQLException e) {
			logger.severe(e.toString());
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void registerUser(UserSession userSession) {
		//Create new userDataSet
		UserDataSetImpl userDataSet = new UserDataSetImpl(-1, 
				userSession.userName.get(), 
				userSession.password.get(), 
				userSession.email.get());
		
		
		try {
			Connection connection = connectionPool.getConnection();
			UserDAO userDAO = new UserDAOImpl(connection);
			userDAO.add(userDataSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.severe(e.toString());
			e.printStackTrace();
		}
	}
	
	@Override
	public UserDataSet getUserData(UserSession userSession) //synchro call
	{	
		UserDataSetImpl userDataSet = null;
		try 
		{
			Connection connection = connectionPool.getConnection();
			UserDAO userDAO = new UserDAOImpl(connection);
			userDataSet = userDAO.getByName(userSession.userName.get());
		} 
		catch (SQLException e) {
			logger.severe(e.toString());
			e.printStackTrace();
		}
		
		return userDataSet;
	}
	
}
