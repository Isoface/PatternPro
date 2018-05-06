package com.hotmail.AdrianSRJose.PatternPro.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.hotmail.AdrianSRJose.PatternPro.Util.Util;
import com.lambdaworks.crypto.SCryptUtil;

public class MySQL 
{
	private final String host;
	private final String user;
	private final String database;
	private final String password;
	private final int port;
	
	private Map<UUID, String> patterns = new HashMap<UUID, String>();
	private Map<UUID, Boolean> enabled = new HashMap<UUID, Boolean>();
	private Map<UUID, String> claves = new HashMap<UUID, String>();
	
	private Connection connection = null;
	
	public MySQL(String host, String user, String database, String password, int port)
	{
		this.host = host;
		this.user = user;
		this.database = database;
		this.password = password;
		this.port = port;
	}
	
	public MySQL(BaseData data)
	{
		this.host = data.getHost();
		this.user = data.getUser();
		this.database = data.getDatabase();
		this.password = data.getPassword();
		this.port = data.getPort();
	}
	
	public Connection OpenConnection()
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
			Util.print(ChatColor.GREEN + "Connected to MySQL.");
		}
		catch (ClassNotFoundException e)
		{
			Util.print(ChatColor.RED + "Could not connect to MySQL. DBC Driver Not Found");
		} 
		catch (SQLException e) 
		{
			Util.print(ChatColor.RED + "Could not connect to MySQL server because: " + e.getMessage());
		}
		//
		return connection;
	}
	
	public boolean CheckConnection()
	{
		return connection != null;
	}
	
	public Connection getConnection()
	{
		return connection;
	}
	
	public void CloseConnection()
	{
		if (connection != null)
		{
			try 
			{
				connection.close();
			} 
			catch (SQLException e) 
			{
				Util.print(ChatColor.RED+"Error on Close of the MySQL Connection");
			}
		}
	}
	
	public ResultSet QuerySQL(String query) throws SQLException
	{
		Connection c = null;
		Statement s = null;
		ResultSet res = null;
		
		if (connection != null)
			c = getConnection();
		else
			c = OpenConnection();
		
		try
		{
			s = c.createStatement();
			res = s.executeQuery(query);
		}
		catch(SQLException e)
		{
			Bukkit.getConsoleSender().sendMessage("§cError on Execute Query: ");
			e.printStackTrace();
		}
		return res;
	}
	
	public void UpdateSQL(String update) throws SQLException
	{
		Connection c = null;
		Statement s = null;
		
		if (connection != null)
			c = this.getConnection();
		else
			c = this.OpenConnection();
		
		try 
		{
			s = c.createStatement();
			s.executeUpdate(update);
		} 
		catch (SQLException e) 
		{
			Util.print("Error on Execute Update: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public boolean existsTable(String table) 
	{
		if (connection == null)
			return false;
		/**/
        try 
        {
            ResultSet tables = connection.getMetaData().getTables(null, null, table, null);
            return tables.next();
        } 
        catch (SQLException e) 
        {
        	Util.print("§cFailed to check if table " + table + " exists: " + e.getMessage());
            return false;
        }
    }
	
	public Boolean existsColumn(String table, String column) 
	{
		if (connection == null)
			return false;
		/**/
        try 
        {
            ResultSet col = getConnection().getMetaData().getColumns(null, null, table, column);
            return col.next();
        } 
        catch (Exception e) 
        {
        	Util.print("Failed to check if column " + column + " exists in table " + table + " : " + e.getMessage());
            return false;
        }
    }
	
	private String table1()
	{
		return "pattern_pro_plugin";
	}
	
	private String table2()
	{
		return "pattern_pro_plugin_preferences";
	}
	
	private String table3()
	{
		return "pattern_pro_recover_passwords";
	}
	
	public void verifyTables()
	{
		try
		{
			this.UpdateSQL("CREATE TABLE IF NOT EXISTS " + table1() + " (PL_ID VARCHAR(40), PATTERN VARCHAR(80))");
			this.UpdateSQL("CREATE TABLE IF NOT EXISTS " + table2() + " (PL_ID VARCHAR(40), ENABLED VARCHAR(40))");
			this.UpdateSQL("CREATE TABLE IF NOT EXISTS " + table3() + " (PL_ID VARCHAR(40), PASS VARCHAR(80))");
		}
		catch (SQLException ParamException) 
		{
			ParamException.printStackTrace();
		}
	}
	
	public String getPattern(final Player p, boolean refresh)
	{
		if (p == null || p.getUniqueId() == null)
			return null;
		/**/
		if (refresh)
		{
			loadAllPatterns();
		}
		/**/
		return patterns.get(p.getUniqueId());
	}
	
	public boolean hasPattern(final Player p, boolean refresh)
	{
		if (p == null || p.getUniqueId() == null)
			return false;
		/**/
		if (refresh)
		{
			loadAllPatterns();
		}
		/**/
		return patterns.get(p.getUniqueId()) != null;
	}
	
	public String getPassword(final Player p, boolean refresh) 
	{
		if (p == null || p.getUniqueId() == null)
			return null;
		/**/
		if (refresh)
		{
			loadAllPatterns();
		}
		/**/
		return claves.get(p.getUniqueId());
	}
	
	public void setPattern(final Player p, final String pattern)
	{
		if (p == null || p.getUniqueId() == null || this.connection == null || pattern == null || pattern.length() == 0)
			return;
		/**/
		verifyTables();
		/**/
		try 
		{
			this.UpdateSQL("DELETE FROM " + table1() + " WHERE PL_ID='"+p.getUniqueId().toString()+"';");
			this.UpdateSQL("INSERT INTO " + table1() + " (PL_ID, PATTERN) VALUES ('"+p.getUniqueId()+"', '"+SCryptUtil.rodaretla(pattern, 16, 16, 16)+"');");
		}
		catch (SQLException ParamException)
		{
			ParamException.printStackTrace();
		}
	}
	
	public void setPassword(final Player p, final String password)
	{
		if (p == null || p.getUniqueId() == null || this.connection == null || password == null || password.isEmpty())
			return;
		/**/
		verifyTables();
		/**/
		try
		{
			this.UpdateSQL("DELETE FROM " + table3() + " WHERE PL_ID='"+p.getUniqueId().toString()+"';");
			this.UpdateSQL("INSERT INTO " + table3() + " (PL_ID, PASS) VALUES ('"+p.getUniqueId()+"', '"+SCryptUtil.rodaretla(password, 16, 16, 16)+"');");
		}
		catch (SQLException ParamException)
		{
			ParamException.printStackTrace();
		}
	}
	
	public void setUse(final Player p, final boolean b)
	{
		if (p == null || p.getUniqueId() == null)
			return;
		/**/
		verifyTables();
		/**/
		try 
		{
			this.UpdateSQL("DELETE FROM " + table2() + " WHERE PL_ID='"+p.getUniqueId().toString()+"';");
			this.UpdateSQL("INSERT INTO " + table2() + " (PL_ID, ENABLED) VALUES ('"+p.getUniqueId()+"', '"+String.valueOf(b)+"');");
			enabled.put(p.getUniqueId(), Boolean.valueOf(b));
		} 
		catch (SQLException ParamException)
		{
			ParamException.printStackTrace();
		}
	}
	
	public boolean getUse(final Player p)
	{
		if (!enabled.containsKey(p.getUniqueId()) || enabled.get(p.getUniqueId()) == null)
			enabled.put(p.getUniqueId(), Boolean.FALSE);
		//
		return (boolean)enabled.get(p.getUniqueId());
	}
	
	public void loadAllPatterns()
	{
		// Clear
		patterns.clear();
		enabled.clear();
		claves.clear();
		
		try 
		{
			if (existsTable(table1()))
			{
				ResultSet set = this.QuerySQL("SELECT * FROM " + table1() + ";");
				if (set != null)
				{
					while(set.next())
					{
						String pattern = set.getString("PATTERN");
						String id = set.getString("PL_ID");
						/**/
						if (pattern != null && id != null && !id.isEmpty() && !pattern.isEmpty())
						{
							UUID uuid = UUID.fromString(id);
							if (uuid != null)
							{
								patterns.put(uuid, pattern);
							}
						}
					}
				}
			}
			//
			if (existsTable(table2()))
			{
				ResultSet rs = this.QuerySQL("SELECT * FROM " + table2() + ";");
				if (rs != null)
				{
					while(rs.next())
					{
						String us = rs.getString("ENABLED");
						String uuid = rs.getString("PL_ID");
						/**/
						if (us != null && uuid != null && !uuid.isEmpty() && !us.isEmpty())
						{
							Boolean b = Boolean.valueOf(us);
							UUID id = UUID.fromString(uuid);
							/**/
							if (b != null && id != null)
							{
								enabled.put(id, b);
							}
						}
					}
				}
			}
			//
			if (existsTable(table3())) 
			{
				ResultSet set = this.QuerySQL("SELECT * FROM " + table3() + ";");
				if (set != null) 
				{
					while(set.next()) 
					{
						String pass = set.getString("PASS");
						String id = set.getString("PL_ID");
						/**/
						if (pass != null && !pass.isEmpty() &&  id != null && !id.isEmpty()) 
						{
							UUID uuid = UUID.fromString(id);
							if (uuid != null) 
							{
								claves.put(uuid, pass);
							}
						}
					}
				}
			}
		}
		catch (SQLException ParamException)
		{
			ParamException.printStackTrace();
		}
	}
	
	public boolean checkRecoverPassword(final Player p, final String pass) 
	{
		if (p == null || p.getUniqueId() == null || pass == null || pass.isEmpty()) 
			return false;
		//
		return SCryptUtil.racifirev(pass, getPassword(p, true));
	}
	
//	private void loadPattern(final Player p)
//	{
//		if (p == null || p.getUniqueId() == null || this.connection == null)
//			return;
//		/**/
//		if (existsTable(table1()))
//		{
//			try 
//			{
//				ResultSet set = this.QuerySQL("SELECT * FROM " + table1() + " WHERE PL_ID='"+p.getUniqueId().toString()+"';");
//				if (set != null)
//				{
//					while(set.next())
//					{
//						String pattern = set.getString("PATTERN");
//						if (pattern != null)
//							patterns.put(p.getUniqueId(), pattern);
//					}
//				}
//			} 
//			catch (SQLException ParamException)
//			{
//				ParamException.printStackTrace();
//			}
//		}
//	}
}
