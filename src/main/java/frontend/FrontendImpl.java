package frontend;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//import org.eclipse.jetty.websocket.WebSocketFactory.Acceptor;

import utils.LoggerFactory;
import utils.SessionStatus;
import utils.TimeHelper;
import utils.UserSession;

import accountService.AccountServiceImpl;
import base.*;

public class FrontendImpl extends HttpServlet implements Frontend
{
	private static Logger logger = LoggerFactory.getLogger("FrontendLogger", "./log/frontend-log.txt");
	private static int TICK_TIME = 100;
	private Map<String, UserSession> sessionIdToSession = new HashMap<String, UserSession>();
	private Address frontendAddress = new Address();
	private PageGenerator pageGenerator = new PageGenerator();
	private MessageSystem messageSystem;
	
	public FrontendImpl(MessageSystem messageSystem)
	{ 
		logger.info("Frontend created");
		this.messageSystem = messageSystem;
		this.messageSystem.addService(this);
	}
	
	public void run()
	{
		while(true)
		{
			long startTime = System.currentTimeMillis();
			messageSystem.execForAbonent(this);
			long deltaTime = System.currentTimeMillis() - startTime;
			if(deltaTime/TICK_TIME < 1)
				TimeHelper.Sleep((int)(TICK_TIME - deltaTime));
		}
	}
	
	protected void doGet(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException 
	{
		logger.info("doGet " + req.toString());
	    PrintWriter pw = new PrintWriter(resp.getOutputStream());
	    
	    String requestUri = req.getRequestURI();
	    HttpSession session = req.getSession(false);
	    
		if(requestUri.equals("/"))
		{
			if (session != null) 
			{
				String sessionId = session.getId();
				UserSession userSession = sessionIdToSession.get(sessionId);
				resp.setContentType("text/html; charset=UTF-8");
				pw.println(pageGenerator.getMainPage(userSession));
			}
			else //new user
			{
				//resp.sendRedirect("/login");
				logger.info("Get new user");
				resp.setContentType("text/html; charset=UTF-8");
				pw.println(pageGenerator.getMainPage(null));
			}
		}
		else if(requestUri.equals("/register/status.json") ||
				requestUri.equals("/login/status.json"))
		{
			resp.setContentType("application/json; charset=UTF-8");
			String json = getUserSessionStatus(session);
			pw.println(json);
		}
		else if(requestUri.equals("/logout"))
		{
			//delete session if exists
			if (session != null) 
			{
				String sessionId = session.getId();
				logout(sessionId);
				session.invalidate();
			}
			
			resp.sendRedirect("/");
		}
		else
		{
			resp.sendRedirect("/");
		}
		
	    pw.flush();
	}
	
	protected void doPost(HttpServletRequest req, 
			HttpServletResponse resp) throws ServletException, IOException 
	{
		logger.info("Post request: " + req.toString());
		
		HttpSession session = req.getSession(true);
	    PrintWriter pw = new PrintWriter(resp.getOutputStream());
	    
	    //parse request
	    String userName = req.getParameter("name");
	    String password = req.getParameter("password");
    	String email = req.getParameter("email");
	    String sessionId = session.getId();
	    
	    UserSession userSession = null;
	    if (session.isNew()) //Create new session
	    {	    
	        //Create new session
	    	session.setAttribute("created", new Date());
	    	userSession = CreateUserSession(userName, sessionId, password, email);
	    }
	    else
	    {
	    	userSession = GetUserSession(sessionId);
	    	userSession.userName.set(userName);
	    	userSession.password.set(password);
	    	userSession.email.set(email);
	    }
	    
	    logger.info("User : " + userName + password);
	    String response = ParseRequestUrl(req.getRequestURI(), userSession);
	    
    	pw.println(response);
	    pw.flush();	
	}
	
	@Override
	public void updateUserSession(UserSession newUserSession) {
		logger.info("user session: " + newUserSession);
		//update session
		UserSession userSession = sessionIdToSession.get(newUserSession.sessionId.get());
		userSession.eSessionStatus = newUserSession.eSessionStatus;
		userSession.userId = newUserSession.userId;
	}

	@Override
	public String getUserSessionStatus(HttpSession session) 
	{
		String JSONUserSession = "{\"status\": null}";
		if (session != null) 
		{
			String sessionId = session.getId();
			UserSession userSession = sessionIdToSession.get(sessionId);
			JSONUserSession = pageGenerator.parseUserSession(userSession);
		}
		return JSONUserSession;
	}

	@Override
	public Address getAddress() {
		return frontendAddress;
	}
	
	public String ParseRequestUrl(String URL, UserSession userSession)
	{
		String resp = "";
	    if(URL.equals("/login"))
	    {
	    	AuthorizeUser(userSession);
	    	resp = "wait authorization";
	    }
	    else if(URL.equals("/register"))
	    {
	    	RegisterUser(userSession);
	    	resp = "wait registration";
	    }
	    
	    return resp;
	}
	
	public void AuthorizeUser(UserSession userSession)
	{
    	Address accountService = messageSystem.getAddressService().getAddress(AccountServiceImpl.class);
    	MsgAuthorizeUser msg = new MsgAuthorizeUser(frontendAddress, accountService, userSession);
    	logger.info("Send authorization request to accountService for: " + userSession.userName.get());
    	messageSystem.sendMessage(msg);
	}
	
	public void RegisterUser(UserSession userSession)
	{
		Address accountService = messageSystem.getAddressService().getAddress(AccountServiceImpl.class);
    	MsgRegisterUser msg = new MsgRegisterUser(frontendAddress, accountService, userSession);
    	logger.info("Send registration request to accountService for: " + userSession.userName );
    	messageSystem.sendMessage(msg);
	}
	
	public UserSession CreateUserSession(String userName, String sessionId, String  password, String email)
	{
    	UserSession userSession = new UserSession(userName, sessionId, password);
    	userSession.email.set(email);
    	sessionIdToSession.put(sessionId, userSession);
    	
    	return userSession;
	}
	
	public UserSession GetUserSession(String sessionId)
	{
		return sessionIdToSession.get(sessionId);
	}
	
	public void logout(String sessionId)
	{
		sessionIdToSession.remove(sessionId);
	}
	
	
}