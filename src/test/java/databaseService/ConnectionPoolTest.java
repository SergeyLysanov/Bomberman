package databaseService;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.SQLNestedException;
import org.junit.*;
import static org.junit.Assert.*;


public class ConnectionPoolTest {

	@Test
	public void testConnectionPool()
	{

		ConnectionPool result = new ConnectionPool();

		assertNotNull(result);
	}


	@Test
	public void testGetConnection() throws SQLException
	{
		ConnectionPool fixture = new ConnectionPool();
		Connection result = fixture.getConnection();
		assertNotNull(result);
	}

	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(ConnectionPoolTest.class);
	}
}