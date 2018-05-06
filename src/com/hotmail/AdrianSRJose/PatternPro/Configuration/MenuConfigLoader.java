package com.hotmail.AdrianSRJose.PatternPro.Configuration;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.hotmail.AdrianSRJose.PatternPro.Menus.ItemMenu.Size;
import com.hotmail.AdrianSRJose.PatternPro.Util.Util;

import net.md_5.bungee.api.ChatColor;

public class MenuConfigLoader 
{
	public static List<ItemConfig> MenuItems = new ArrayList<ItemConfig>();
	public static int considerar = Size.FIVE_LINE.getSize();
	public static final List<MenuTheme> LoadedThemes = new ArrayList<MenuTheme>();
	public static String defaultMenuTheme = null;
	public static PatternPad Pad = null;
	// ------ Recover
	public static RecoverPasswordItem recoverItem = null;
	public static int MaxRecoverAttemps = 3;
	//public static String RecoverMenuName = "&e&lRetrieve my Pattern";
	
	public static void load(final ConfigurationSection sc)
	{
		if (sc != null)
		{
			ConfigurationSection menu = sc.getConfigurationSection("PatternMenu");
			if (menu != null)
			{
				ConfigurationSection pad = menu.getConfigurationSection("PatternPad");
				if (pad != null)
				{
					PatternPad gg = new PatternPad(pad);
					if (gg != null)
						Pad = gg;
				}
				//
				ConfigurationSection recSc = menu.getConfigurationSection("RecoverPattern");
				if (recSc != null)
				{
					recoverItem = new RecoverPasswordItem(recSc);
					if (recoverItem == null)
						Util.print(ChatColor.RED + "Invalid RecoverPatternItem in Config.");
					//
					if (recSc.isInt("Max-User-Password-Attemps"))
						MaxRecoverAttemps = recSc.getInt("Max-User-Password-Attemps");
					MaxRecoverAttemps = MaxRecoverAttemps < 1 ? 1 : MaxRecoverAttemps;
					//if (recSc.isString("MenuTitle"))
					//	RecoverMenuName = Util.wc(recSc.getString("MenuTitle"));
				}
				//
				if (menu.isString("DefaultTheme"))
					defaultMenuTheme = menu.getString("DefaultTheme");
				
				//Bukkit.getConsoleSender().sendMessage("In Config >>>>>>>>>>>>> " + menu.getString("DefaultTheme"));
				//
				ConfigurationSection themesSection = menu.getConfigurationSection("Themes");
				if (themesSection != null)
				{
					for (String key : themesSection.getKeys(false))
					{
						ConfigurationSection ts = themesSection.getConfigurationSection(""+key);
						if (ts != null)
						{
							MenuTheme theme = new MenuTheme(ts);
							if (theme != null)
								LoadedThemes.add(theme);
						}
					}
				}
			}
			// ------ Print loaded Theems
//			for (MenuTheme amt : LoadedThemes)
//			{
//				if (amt != null)
//					Util.print(ChatColor.GREEN + "A Menu Theme Has Been Loaded >>>> " + ChatColor.YELLOW + amt.getSCName());
//				//
//				for (ItemConfig g : amt.getItems())
//				{
//					if (g.getSlot() != null && g.getSlot().intValue() > considerar)
//						considerar = g.getSlot();
//				}
//			}
		}
	}

}
