package utils;

public class TimeHelper 
{
	private static int tickTime = 200;
	
	public static void Sleep(int ms)
	{
		try {			
			Thread.sleep(ms);
		} 
		catch (InterruptedException e) 
		{
			System.err.println(e);
		}
	}
}
