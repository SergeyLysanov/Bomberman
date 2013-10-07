package accountService;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.security.*;

import databaseService.DatabaseServiceImpl;
import databaseService.MsgGetUserData;
import databaseService.MsgRegisterUser;

import utils.SessionStatus;
import utils.TimeHelper;
import utils.UserSession;

import frontend.FrontendImpl;
import frontend.MsgUpdateUserSession;


import base.AccountService;
import base.Address;
import base.DatabaseService;
import base.MessageSystem;
import base.UserDataSet;

public class AccountServiceImpl implements AccountService
{
	private static int TICK_TIME = 100;
	private static String sold = "729138Az";
	private static Logger logger = Logger.getLogger("FileLogger");
	private DatabaseService dataBase;
	private Address accountAddress = new Address();
	private MessageSystem messageSystem;
	
	public AccountServiceImpl(MessageSystem messageSystem, DatabaseService dataBase) 
	{
		logger.info("AccountServiceImpl created");
		this.dataBase = dataBase; 
		this.messageSystem = messageSystem;
		this.messageSystem.addService(this);
	}
	
	@Override
	public void run() 
	{
		while(true)
		{
			messageSystem.execForAbonent(this);
			TimeHelper.Sleep(TICK_TIME);
		}
	}
	
	@Override
	public void authorize(UserSession userSession) // make request to data base
	{
		//UserSession userSession = new UserSession(userName, sessionId, password); 
		//logger.info("get user id by " + userSession.userName);
    	/*Address databaseService = messageSystem.getAddressService().getAddress(
    			DatabaseServiceImpl.class);
    	
		MsgGetUserData msg = new MsgGetUserData(accountAddress, databaseService, userSession);
		messageSystem.sendMessage(msg);*/
		UserDataSet userDataSet = dataBase.getUserData(userSession);
		if(userDataSet == null) //user doesn't exist
		{		
			userSession.userId.set(-1);
			userSession.eSessionStatus.set(SessionStatus.eWrongLogin);
		}
		else
		{
			//check password
			String pass = SHA256Hashing.getHash(userSession.password.get() + sold);
			String realPass= userDataSet.getPassword();
			if(pass.equals(realPass))
			{
				userSession.userId.set(userDataSet.getid());
				userSession.eSessionStatus.set(SessionStatus.eAuthorized);
			}
			else
			{
				userSession.eSessionStatus.set(SessionStatus.eWrongPassword);
			}
		}
	}
	
	@Override
	public void register(UserSession userSession) 
	{
		
		/*UserSession userSession = new UserSession();
		userSession.userName.set(userName);
		userSession.password.set(hash);
		
    	Address databaseService = messageSystem.getAddressService().getAddress(
    			DatabaseServiceImpl.class);
    	
		MsgRegisterUser msg = new MsgRegisterUser(accountAddress, databaseService, userSession);
		messageSystem.sendMessage(msg);*/
		
		
		//Hash pass+sold
		String hash = SHA256Hashing.getHash(userSession.password.get() + sold);
		userSession.password.set(hash);
		
		UserDataSet userDataSet = dataBase.getUserData(userSession);
		if(userDataSet == null) //user doesn't exist
		{	
			dataBase.registerUser(userSession);
			userSession.eSessionStatus.set(SessionStatus.eRegistered);
		}
		else
		{
			userSession.eSessionStatus.set(SessionStatus.eUserExists);
		}
	}
	
	@Override
	public void getUpdateUser(UserSession userSession) //get request from data base
	{
		if(!userSession.eSessionStatus.equals(SessionStatus.eWrongLogin))	//if login is correct
		{
			String pass = userSession.password.get();
			String realPass= userSession.realPassword.get();
			if(pass.equals(realPass)){
				userSession.eSessionStatus.set(SessionStatus.eAuthorized);
			}
			else{
				userSession.eSessionStatus.set(SessionStatus.eWrongPassword);
			}
		}
		
    	Address frontendService = messageSystem.getAddressService().getAddress(
    			FrontendImpl.class);
    	
    	//Update user session on frontend
		MsgUpdateUserSession msg = new MsgUpdateUserSession(accountAddress, frontendService, userSession);
		messageSystem.sendMessage(msg);
	}

	@Override
	public Address getAddress() 
	{
		return accountAddress;
	}


}
