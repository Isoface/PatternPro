package com.hotmail.AdrianSRJose.PatternPro.Configuration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.hotmail.AdrianSRJose.PatternPro.Util.Util;

public class Config {
	private static YamlConfiguration mainConfig = null;
	private static File mainConfigFile = null;

	//
	public static void load(Plugin p) {
		mainConfigFile = new File(p.getDataFolder().getAbsolutePath());
		if (!mainConfigFile.exists() || !mainConfigFile.isDirectory())
			mainConfigFile.mkdir();
		mainConfigFile = new File(p.getDataFolder().getAbsolutePath(), "PatternPro.yml");
		try {
			if (!mainConfigFile.exists())
				mainConfigFile.createNewFile();
			mainConfig = YamlConfiguration.loadConfiguration(mainConfigFile);
			//
			int save = 0;
			//
			// save += sds(mainConfig, "", "");
			//
			// save += sds(mainConfig, "Auth-Server", false);
			save += sds(mainConfig, "Log-Timeout", 30);
			save += sds(mainConfig, "Max-Attemps", 3);
			////////////////////////////////////////////
			// save += Util.createSectionIfNoExitsInt(mainConfig, "Bungeecord");
			// ConfigurationSection bungee =
			//////////////////////////////////////////// mainConfig.getConfigurationSection("Bungeecord");
			// save += sds(bungee, "Use", false);
			// save += sds(bungee, "sendPlayerTo", "");
			////////////////////////////////////////////
			save += sds(mainConfig, "Titles.On", true);
			save += sds(mainConfig, "Titles.Succes", true);
			save += sds(mainConfig, "Titles.Created", true);
			///////////////////////////////////////////////////////////////////////////////////
			save += Util.createSectionIfNoExitsInt(mainConfig, "Database");
			ConfigurationSection database = mainConfig.getConfigurationSection("Database");
			save += sds(database, "Host", "");
			save += sds(database, "Database", "");
			save += sds(database, "Username", "");
			save += sds(database, "Password", "");
			save += sds(database, "Port", 3306);
			//
			if (save > 0)
				saveConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * [ 0] [ 1] [ 2] [ 3] [ 4] [ 5] [ 6] [ 7] [8]
	 * 
	 * [ 9] [10] [11] [12] [13] [14] [15] [16] [17]
	 * 
	 * [18] [19] [20] [21] [22] [23] [24] [25] [26]
	 * 
	 * [27] [28] [29] [30] [31] [32] [33] [34] [35]
	 * 
	 * [36] [37] [38] [39] [40] [41] [42] [43] [44]
	 * 
	 * [45] [46] [47] [48] [49] [50] [51] [52] [53]
	 */

	public static int sds(ConfigurationSection section, String path, Object str) {
		if (section != null) {
			if (!section.isSet(path)) {
				if (str != null) {
					if (str instanceof String)
						section.set(path, (String) str);
					else
						section.set(path, str);

					return 1;
				}
			}
		}
		return 0;
	}

	public static YamlConfiguration getConfig() {
		return mainConfig;
	}

	public static void saveConfig() {
		if (mainConfig != null) {
			try {
				mainConfig.save(mainConfigFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
