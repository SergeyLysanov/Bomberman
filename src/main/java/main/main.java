package main;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import databaseService.DatabaseServiceImpl;

import frontend.FrontendImpl;
import gameMechanics.GameMechanicsImpl;
import gameMechanics.GameSocketImpl;
import messageSystem.MessageSystemImpl;
import accountService.AccountServiceImpl;
import base.*;


public class main
{
	private static Server server;
	private static ServletContextHandler handler;
	private static Logger logger = Logger.getLogger("FileLogger");
	
	public static void main(String[] args) throws Exception
	{   
		setupLogger();
		setupServer();
        setupThreads();
        
		server.start();
		server.join();
	}
	
	static private void daemonize() throws Exception
    {
        System.in.close();
        System.out.close();
    }
	
	static public void setupThreads()
	{
	    //Start initialize threads
	    MessageSystem messageSystem = new MessageSystemImpl();
		
		Frontend frontend = new FrontendImpl(messageSystem);
		Thread frontendThread = new Thread(frontend);	
		
		DatabaseService database = new DatabaseServiceImpl(messageSystem);
		Thread databaseThread = new Thread(database);
		
		AccountService accountService = new AccountServiceImpl(messageSystem, database);
		Thread accountServiceThread = new Thread(accountService);

		AccountService accountService2 = new AccountServiceImpl(messageSystem, database);
		Thread accountServiceThread2 = new Thread(accountService2);
		
		GameSocket gameSocket = new GameSocketImpl(messageSystem);
		Thread gameSocketThread = new Thread(gameSocket);
		GameMechanics gameMechanics = new GameMechanicsImpl(messageSystem);
		Thread gameMechanicsThread = new Thread(gameMechanics);
		gameSocket.setupGameMechanicsAddress(gameMechanics.getAddress());
		
		ServletHolder webSocketHolder = new ServletHolder((GameSocketImpl)gameSocket);
		handler.addServlet(webSocketHolder, "/play/");
		
		ServletHolder holder = new ServletHolder((FrontendImpl)frontend);
		handler.addServlet(holder, "/");
		
		databaseThread.start();
		accountServiceThread.start();
		accountServiceThread2.start();
		frontendThread.start();
		gameMechanicsThread.start();
		gameSocketThread.start();
	}
	
	static public void setupLogger() throws SecurityException, IOException
	{
		try
        {
            daemonize();
        }
        catch (Throwable e)
        {
            System.err.println("Startup failed. " + e.getMessage());
            return;
        }
		
		FileHandler logFile = new FileHandler("server_log.txt");
		logFile.setFormatter(new SimpleFormatter());
	    logger.addHandler(logFile);
	    logger.setUseParentHandlers(false);
	    logger.info("Start server");
	}
	
	static public void setupServer()
	{
		server = new Server();
		
        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setHost("127.0.0.1");
        connector.setPort(9999);
        server.setConnectors(new Connector[]{ connector});
		
		handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		handler.setContextPath("/");
        server.setHandler(handler);
	}

}
