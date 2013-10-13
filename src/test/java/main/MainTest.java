package main;

import java.io.IOException;

import org.junit.*;
import static org.junit.Assert.*;


public class MainTest 
{

	@Test
	public void testMainClass() throws Exception
	{
		//main.main(null);	
	}
	
	@Test
	public void testSetupLogger() throws SecurityException, IOException
	{
		main.setupLogger();
	}
	
	
	@Test
	public void testSetupThreads()
	{
		main.setupServer();
		main.setupThreads();
	}
	
}
