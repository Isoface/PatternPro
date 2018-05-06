package com.hotmail.AdrianSRJose.PatternPro.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.hotmail.AdrianSRJose.PatternPro.Configuration.Config;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.ConfigLoader;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.MenuConfig;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.MenuConfigLoader;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.PCMConfig;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.PCMLoader;
import com.hotmail.AdrianSRJose.PatternPro.Database.BaseData;
import com.hotmail.AdrianSRJose.PatternPro.Database.MySQL;
import com.hotmail.AdrianSRJose.PatternPro.Util.Util;

public class PatternPro extends JavaPlugin// implements PluginMessageListener
{
	private static PatternPro instance;
	private static final String xii = "2";
	private MySQL sql;
	//
	@Override
	public void onEnable()
	{
		// Instance
		instance = this;
		// --- Anti Leak --- //
		if (!load()) {
			return;
		}
		// ---- Principal Configuration ---- //
		Config.load(this);
		//
		YamlConfiguration config = Config.getConfig();
		if (config != null)
			ConfigLoader.load(config);
		// ----------------- //
		////////////////////////
		//this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		//this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
		////////////////////////
		// ---- VerifyAuthMe Bungee
		//AuthMeBungee();
		////////////////////////
		// ----------- Configuration Loader ---------- //
		MenuConfig.load(this);
		//
		PCMConfig.load(this);
		idioma();
		//
		YamlConfiguration pcmConfig = PCMConfig.getConfig();
		if (pcmConfig != null)
			PCMLoader.load(pcmConfig);
		//
		YamlConfiguration menuConfig = MenuConfig.getConfig();
		if (menuConfig != null)
			MenuConfigLoader.load(menuConfig);
		//------------------ My SQL -------------------//
		BaseData base = ConfigLoader.BaseData;
		if (base != null && base.isValidDataBase())
		{
			sql = new MySQL(base);
			if (sql != null)
			{
				Connection c = sql.OpenConnection();
				if (c == null)
				{
					disableNoConnectedToMySQL();
					return;
				}
				else
				{
					sql.loadAllPatterns();
					sql.verifyTables();
				}
				//
			}
		}	
		else 																			
		{
			disableNoConnectedToMySQL();
			return;																		
		}
		//---------------------------------------------//
		//
		if (MenuConfigLoader.Pad == null)
		{
			Util.print(ChatColor.RED + "Error loading the PatternPad. Please check the File 'PatternPro.yml'.");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		// --------------------------------------------//
		// -------- Register ---------- //
		PatternPlayer.RegisterListener(this);
		new Sync(this);
		//
		for (Player all : Bukkit.getOnlinePlayers())
			if (all != null && all.isOnline())
				Sync.log(all);
		//----------------------------------//
		// ----- Commands ------ //
		new PatternCommand(this);
		//new PatternCreatorCommand(this);
		//-----------------------//
		// ---- Print Enable Message ---- //
		Util.print(ChatColor.WHITE + "Enabled!" + ChatColor.GREEN + " | " + ChatColor.WHITE + "Version: " + getDescription().getVersion());
		// ------------------------------ //
	}
	
	@Override
	public void onDisable() 
	{
		if (sql != null) {
			sql.CloseConnection();
		}
	}

	private void disableNoConnectedToMySQL()
	{
		Util.print(ChatColor.RED + "Could not connect to MySQL." + ChatColor.GREEN + " Configure the MySQL");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "in the file " + ChatColor.AQUA + "'PatternPro.yml'");
		Bukkit.getPluginManager().disablePlugin(this);

	}

	public static PatternPro getInstance()
	{
		return instance;
	}

	public MySQL getMySQL()
	{
		return sql;
	}

	//	private void AuthMeBungee()
	//	{
	//		File thisFolder = this.getDataFolder();
	//		if (!thisFolder.exists())
	//			thisFolder.mkdir();
	//		//
	//		String pluginsAddres = thisFolder.getParent();
	//		File plugins = new File(pluginsAddres);
	//		//
	//		File authMeFolder = new File(plugins, "AuthMe");
	//		if (authMeFolder != null && authMeFolder.exists() && authMeFolder.isDirectory())
	//		{
	//			File authMeConfig = new File(authMeFolder, "config.yml");
	//			if (authMeConfig != null && authMeConfig.exists() && authMeConfig.isFile())
	//			{
	//				YamlConfiguration config = YamlConfiguration.loadConfiguration(authMeConfig);
	//				if (config != null)
	//				{
	//					ConfigurationSection hooks = config.getConfigurationSection("Hooks");
	//					if (hooks != null)
	//					{
	//						if (!hooks.getBoolean("bungeecord"))
	//							return;
	//						//
	//						boolean seguir = true;
	//						boolean reloadAuthMe = false;
	//						ArrayList<String> lines = new ArrayList<String>();
	//						//
	//						try
	//						{
	//							InputStream targetStream = new FileInputStream(authMeConfig);
	//							BufferedReader reader = new BufferedReader(new InputStreamReader(targetStream));
	//							String line;
	//							//
	//							//
	//							while ((line = reader.readLine()) != null)
	//							{
	//								String add = null;
	//								//
	//								if (line.contains("bungeecord:"))
	//								{
	//									if (line.contains("true"))
	//									{
	//										add = line.replace("true", "false");
	//										reloadAuthMe = true;
	//									}
	//								}
	//								else
	//									add = line;
	//								//
	//								//
	//								if (add != null)
	//									lines.add(add);
	//							}
	//							//
	//							reader.close();
	//						}
	//						catch(Exception e) { }
	//						//
	//						if (seguir)
	//						{
	//							authMeConfig.setWritable(true);
	//							//
	//							try 
	//							{
	//								BufferedWriter writer = new BufferedWriter(new FileWriter(authMeConfig));
	//								//
	//								for (String tw : lines)
	//								{
	//									writer.write(tw);
	//									writer.newLine();
	//								}
	//								//
	//								writer.close();
	//							} 
	//							catch (IOException ParamException) 
	//							{
	//								ParamException.printStackTrace();
	//							}
	//						}
	//						//
	//						////////////////////////////
	//						if (reloadAuthMe)
	//						{
	//							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "authme reload");
	//						}
	//					}
	//				}
	//			}
	//		}
	//	}

	private void idioma()
	{
		File lang = new File(getDataFolder(), "PatternLanguage.yml");
		//
		if (!lang.exists())
		{
			try
			{
				getDataFolder().mkdir();
				lang.createNewFile();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				Util.print(ChatColor.RED+"Couldn't create language file. Disabling...");
				Bukkit.getPluginManager().disablePlugin(this);
			}
		}
		//
		YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
		conf.options().header(
				"This is the language config file. "
						+ "\r\nWill be replaced with a number when needed: {#}"
						+ "\r\nWill be replaced with a word when needed: {W}"
						+ "\r\nLine Separator: {S}"
						+ "\r\nNormal MC color codes are supported.");
		//
		for (Idioma item : Idioma.values())
		{
			if (!conf.isSet(item.getPath()) || conf.getString(item.getPath()) == null)
			{
				conf.set(item.getPath(), item.getDefault());
			}
		}
		//
		Idioma.setFile(conf);
		//
		try
		{
			conf.save(lang);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private boolean load()
	{
		try 
		{
			URLConnection url = new URL("https://www.spigotmc.org/").openConnection();
			url.connect();
		} 
		catch (Throwable x) 
		{
			disableNoInternet();
			return false;
		}
		//
		try
		{
			if (Integer.valueOf(xii) < Integer.valueOf(externalTXT()))
			{
				Util.print(ChatColor.RED+"This is an old version. Please download the latest version. Disabling...");
				Bukkit.getPluginManager().disablePlugin(this);
				return false;
			}
		} 
		catch (Throwable e)
		{
			Util.print(ChatColor.RED+"An error occurred on attempt to Enable. Disabling...");
			Bukkit.getPluginManager().disablePlugin(this);
			return false;
		}
		//
		return true;
	}

	private String externalTXT()
	{
		String x = null;
		//
		try 
		{
			URL url = new URL("https://raw.githubusercontent.com/Isoface/PluginVersions/master/PatternPro.txt");
			//
			URLConnection connection = null;
			//
			if (isMineshafterPresent())
				connection = url.openConnection(Proxy.NO_PROXY);
			else
				connection = url.openConnection();
			//
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			//
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			ArrayList<String> lines = new ArrayList<String>();
			//
			//
			while ((line = reader.readLine()) != null)
			{
				lines.add(line);
			}
			//
			x = lines.get(0);
			//
			reader.close();
		}
		catch (Exception ParamException) {}
		//
		return x;
	}

	private void disableNoInternet() 
	{
		Util.print(ChatColor.RED + "You don't have a valid internet connection, please connect to the internet for the plugin to work!");
		Bukkit.getPluginManager().disablePlugin(this);
	}

	private boolean isMineshafterPresent()
	{
		try 
		{
			Class.forName("mineshafter.MineServer");
			return true;
		} 
		catch (Exception e) 
		{
			return false;
		}
	}

	//	@Override
	//    public void onPluginMessageReceived(String channel, Player player, byte[] data) 
	//	{
	//        /*ByteArrayDataInput in = ByteStreams.newDataInput(data);
	//        String subchannel = in.readUTF();
	//        if (!"AuthMe".equals(subchannel)) {
	//            return;
	//        }
	//
	//        String type = in.readUTF();
	//        String name = in.readUTF();
	//        switch (type) {
	//            case MessageType.UNREGISTER:
	//                dataSource.invalidateCache(name);
	//                break;
	//            case MessageType.REFRESH_PASSWORD:
	//            case MessageType.REFRESH_QUITLOC:
	//            case MessageType.REFRESH_EMAIL:
	//            case MessageType.REFRESH:
	//                dataSource.refreshCache(name);
	//                break;
	//            case MessageType.BUNGEE_LOGIN:
	//                handleBungeeLogin(name);
	//                break;
	//            default:
	//                ConsoleLogger.debug("Received unsupported bungeecord message type! ({0})", type);
	//        }*/
	//    }
}
