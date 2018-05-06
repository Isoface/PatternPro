package com.hotmail.AdrianSRJose.PatternPro.Configuration;

import org.bukkit.configuration.ConfigurationSection;

import com.hotmail.AdrianSRJose.PatternPro.Database.BaseData;

public class ConfigLoader 
{
	//public static boolean isLoginServer = true;
	//
	public static BaseData BaseData = null;
	public static long LogTimeout = 30;
	public static int MaxAttemps = 3;
	//
	public static boolean UseTitles = true;
	public static boolean UseSuccesTitle = true;
	public static boolean UseCreatedTitle = true;
	//
	//public static boolean UseBungeecord = false;
	//public static String serverNameToSend = "";
	//
	public static void load(ConfigurationSection sc)
	{
		if (sc != null)
		{
			//isLoginServer = sc.getBoolean("Auth-Server");
			//
			if (sc.isLong("Log-Timeout"))
				LogTimeout = sc.getLong("Log-Timeout");
			//
			if (sc.isInt("Max-Attemps"))
				MaxAttemps = sc.getInt("Max-Attemps");
			//
//			ConfigurationSection bungee = sc.getConfigurationSection("Bungeecord");
//			if (bungee != null)
//			{
//				UseBungeecord = bungee.getBoolean("Use");
//				serverNameToSend = bungee.getString("sendPlayerTo");
//			}
			//
			ConfigurationSection titles = sc.getConfigurationSection("Titles");
			if (titles != null)
			{
				if (titles.isBoolean("On"))
					UseTitles = titles.getBoolean("On");
				//
				if (titles.isBoolean("Succes"))
					UseSuccesTitle = titles.getBoolean("Succes");
				//
				if (titles.isBoolean("Created"))
					UseCreatedTitle = titles.getBoolean("Created");
			}
			
			//
			ConfigurationSection data = sc.getConfigurationSection("Database");
			if (data != null)
			{
				String host = null;
				String database = null;
				String user = null;
				String password = null;
				int port = 3306;
				if (data.isString("Host"))
					host = data.getString("Host").replaceAll("'", "");
				//
				if (data.isString("Database"))
					database = data.getString("Database").replaceAll("'", "");
				//
				if (data.isString("Username"))
					user = data.getString("Username").replaceAll("'", "");
				//
				if (data.isString("Password"))
					password = data.getString("Password").replaceAll("'", "");
				//
				if (data.isInt("Port"))
					port = data.getInt("Port");
				//
				//
				if (!host.equalsIgnoreCase("") && !database.equalsIgnoreCase("") && !user.equalsIgnoreCase(""))
					BaseData = new BaseData(host, database, user, password, port);
			}
			///////////////////////////////////////////////////////////////////
		}
	}

}
