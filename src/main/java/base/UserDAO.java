package base;

import java.sql.SQLException;

import databaseService.UserDataSetImpl;

public interface UserDAO {
	UserDataSetImpl get(long id) throws SQLException;
	UserDataSetImpl getByName(String name) throws SQLException;
	void add(UserDataSetImpl dataSet) throws SQLException;
	void delete(long id) throws SQLException;
}
