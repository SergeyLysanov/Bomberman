package databaseService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
 // $codepro.audit.disable unnecessaryImport
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.dbcp.DelegatingPreparedStatement;

import base.TResultHandler;

public class TExecutor 
{
	private static Logger logger = Logger.getLogger("FileLogger");
	public <T> T execQuery(Connection connection,
			DelegatingPreparedStatement stmt,//String query,
			TResultHandler<T> handler) throws SQLException
	{
		//Statement stmt= connection.createStatement();
		//stmt.execute(query); 
		ResultSet result = stmt.executeQuery();
		T value = handler.handle(result);
		result.close();
		stmt.close();
		
		return value;
	}
	
	public void execUpdate(Connection connection, DelegatingPreparedStatement stmt)
	{
		try
		{
			stmt.executeUpdate();
			stmt.close();
		}
		catch (SQLException e)
		{
			logger.severe(e.toString());
			e.printStackTrace();
		}
	}
}
