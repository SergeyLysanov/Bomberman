package databaseService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.DelegatingPreparedStatement;

import base.TResultHandler;
import base.UserDAO;

public class UserDAOImpl implements UserDAO
{
	private Connection connection;
	
	public UserDAOImpl(Connection connection){
		this.connection = connection;
	}

	@Override
	public UserDataSetImpl get(long id) throws SQLException 
	{
		TExecutor execT = new TExecutor();
		String query = "SELECT idUser, userName, password, email  FROM User WHERE idUser = ?";
		DelegatingPreparedStatement stmt= (DelegatingPreparedStatement) this.connection.prepareStatement(query);
		stmt.setLong(1, id);
		UserDataSetImpl user = execT.execQuery(connection, 
				stmt, 
				new TResultHandler<UserDataSetImpl>(){
					public UserDataSetImpl handle(ResultSet result) throws SQLException{
						if(result.next())
						{
							UserDataSetImpl user = new UserDataSetImpl(
								result.getLong("idUser"), 
								result.getString("userName"), 
								result.getString("password"),
								result.getString("email"));
							return user;
						}
						else{
							return null;
						}
					}
		});
		return user;
	}

	@Override
	public UserDataSetImpl getByName(String name) throws SQLException {
		TExecutor execT = new TExecutor();
		String query = "SELECT idUser, userName, password, email FROM User WHERE userName = ?";
		DelegatingPreparedStatement stmt= (DelegatingPreparedStatement) this.connection.prepareStatement(query);
		stmt.setString(1, name);
		UserDataSetImpl user = execT.execQuery(connection, 
				stmt, 
				new TResultHandler<UserDataSetImpl>(){
					public UserDataSetImpl handle(ResultSet result) throws SQLException{
						if(result.next())
						{
							UserDataSetImpl user = new UserDataSetImpl(
								result.getLong("idUser"), 
								result.getString("userName"), 
								result.getString("password"),
								result.getString("email"));
						
							return user;
						}
						else{
							return null;
						}
					}
		});
		
		return user;
	}

	@Override
	public void add(UserDataSetImpl dataSet) throws SQLException {
		// TODO Auto-generated method stub
		String update = "INSERT INTO User(userName, password, email) VALUES(?,?,?)";
		
		DelegatingPreparedStatement stmt = (DelegatingPreparedStatement) connection.prepareStatement(update);
		stmt.setString(1, dataSet.getName());
		stmt.setString(2, dataSet.getPassword());
		stmt.setString(3, dataSet.getEmail());
		
		TExecutor execT = new TExecutor();
		execT.execUpdate(connection, stmt);
	}

	@Override
	public void delete(long id) throws SQLException {
		// TODO Auto-generated method stub
		
	}
}
