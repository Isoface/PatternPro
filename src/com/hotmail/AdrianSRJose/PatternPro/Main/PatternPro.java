package com.hotmail.AdrianSRJose.PatternPro.Main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

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
	private MySQL sql;
	//
	@Override
	public void onEnable()
	{
		// Instance
		instance = this;
		// ---- Principal Configuration ---- //
		Config.load(this);
		//
		YamlConfiguration config = Config.getConfig();
		if (config != null) {
			ConfigLoader.load(config);
		}

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
}
