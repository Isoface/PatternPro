package com.hotmail.AdrianSRJose.PatternPro.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

public class Util
{
	public static void print(String mess)
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN+"[PatternPro] " + mess);
	}
	
	public static String shortenString(String string, int characters)
	{
		if(string.length() <= characters)
			return string;
		//
		return string.substring(0, characters);
	}
	
	public static String wc(String g)
	{
		return g == null ? "null String" : ChatColor.translateAlternateColorCodes('&', g);
	}
	
	public static String remC(String g)
	{
		return g == null ? "null string" : ChatColor.stripColor(g);
	}
	
	public static ConfigurationSection createSectionIfNoExits(ConfigurationSection father, String newSectionName)
	{
		return father.isConfigurationSection(newSectionName) ? father.getConfigurationSection(newSectionName) : father.createSection(newSectionName);
	}
	
	public static int createSectionIfNoExitsInt(ConfigurationSection father, String newSectionName)
	{
		if (!father.isConfigurationSection(newSectionName) || father.getConfigurationSection(newSectionName) == null)
		{
			father.createSection(newSectionName);
			return 1;
		}
		//
		return 0;
	}
	
//	public static int setUpdatedIfNotEqual(ConfigurationSection section, String path, Object update)
//	{
//		if(section != null && path != null)
//		{
//			if(!section.isSet(path))
//			{
//				section.set(path, update);
//				return 1;
//			}
//			else
//			{
//				if (!update.equals(section.get(path)))
//				{
//					section.set(path, update);
//					return 1;
//				}
//			}
//		}
//		//
//		return 0;
//	}
	
	public static int setDefaultIfNotSet(ConfigurationSection section, String path, Object str)
	{
		if(section != null)
		{
			if(!section.isSet(path))
			{
				if (str != null)
				{
					if (str instanceof String)
						section.set(path, (String)str);
					else
						section.set(path, str);
					
					return 1;
				}
			}
		}
		return 0;
	}

}
