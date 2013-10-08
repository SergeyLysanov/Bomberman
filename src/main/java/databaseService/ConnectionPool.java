package databaseService;

import java.sql.SQLException;
import java.sql.Connection;
import java.util.logging.Logger;

import org.apache.commons.dbcp.BasicDataSource;

import utils.LoggerFactory;

public class ConnectionPool 
{
	private static Logger logger = LoggerFactory.getLogger("DatabaseLogger", "./log/database-log.txt");
    private static BasicDataSource bds;

    public ConnectionPool()
    {
    	bds = new BasicDataSource();
    	bds.setDriverClassName("com.mysql.jdbc.Driver");
    	bds.setUrl("jdbc:mysql://localhost:3306/bomberman");
    	bds.setUsername("bomberman");
    	bds.setPassword("12345");
    	bds.setMaxActive(50);
    }

    public Connection getConnection() throws SQLException
    {
        return bds.getConnection();
    }  
}
