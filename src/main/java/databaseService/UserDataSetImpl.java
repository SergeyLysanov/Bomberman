package databaseService;

import base.UserDataSet;

public class UserDataSetImpl implements UserDataSet{
	private long id;
	private String name;
	private String password;
	private String email;
	
	public UserDataSetImpl(long id, String name, String password, String email){
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
	}
	
	public UserDataSetImpl(String name, String password)
	{
		this.id = -1; 
		this.name = name; 
		this.password = password;
	}
	
	public String getName(){
		return name;
	}
	
	public long getid(){
		return id;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getEmail(){
		return email;
	}

}
