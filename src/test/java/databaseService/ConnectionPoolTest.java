package databaseService;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.SQLNestedException;
import org.junit.*;
import static org.junit.Assert.*;


public class ConnectionPoolTest 
{
	private final String database = "bomberman";

	@Test
	public void testSQLiteConnectionPool()
	{
		ConnectionPool result = new ConnectionPool(database);
		assertNotNull(result);
	}
	
	@Test
	public void testGetSQLiteConnection() throws SQLException
	{
		ConnectionPool result = new ConnectionPool(database);
		Connection conn = result.getConnection();
		assertNotNull(conn);
	}

	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(ConnectionPoolTest.class);
	}
}