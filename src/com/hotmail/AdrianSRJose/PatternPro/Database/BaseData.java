package com.hotmail.AdrianSRJose.PatternPro.Database;

import org.bukkit.Bukkit;

public class BaseData 
{
	private String host = null;
	private String database = null;
	private String user = null;
	private String password = null;
	private int port = 3306;
	
	public BaseData(String host, String database, String user, String password, int port)
	{
		this.host = host;
		this.database = database;
		this.user = user;
		this.password = password;
		this.port = port;
	}
	
	public String getHost()
	{
		return host;
	}
	
	public String getDatabase()
	{
		return database;
	}
	
	public String getUser()
	{
		return user;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public int getPort()
	{
		return port;
	}
	
	public boolean isValidDataBase() 
	{
		return host != null && database != null && user != null;
	}
	
	public void printData()
	{
		if (host != null && database != null && user != null && password != null)
		{
			Bukkit.getConsoleSender().sendMessage("§aHost: "+host);
			Bukkit.getConsoleSender().sendMessage("§aDatabase: "+database);
			Bukkit.getConsoleSender().sendMessage("§aUser: "+user);
			Bukkit.getConsoleSender().sendMessage("§aPassword: "+password);
			Bukkit.getConsoleSender().sendMessage("§aPort: "+port);
		}
	}
}
