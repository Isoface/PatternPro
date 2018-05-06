package com.hotmail.AdrianSRJose.PatternPro.Configuration;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.hotmail.AdrianSRJose.PatternPro.Menus.ItemMenu.Size;

public class PCMLoader
{
	public static List<ItemConfig> MenuItems = new ArrayList<ItemConfig>();
	public static int considerar = Size.FIVE_LINE.getSize();
	public static final List<MenuTheme> LoadedThemes = new ArrayList<MenuTheme>();
	public static String defaultMenuTheme = null;
	public static PatternPad Pad = null;
	
	public static void load(final ConfigurationSection sc)
	{
		if (sc != null)
		{
			ConfigurationSection menu = sc.getConfigurationSection("PatternCreatorMenu");
			if (menu != null)
			{
				ConfigurationSection pad = menu.getConfigurationSection("PatternCreatorPad");
				if (pad != null)
				{
					PatternPad gg = new PatternPad(pad);
					if (gg != null)
						Pad = gg;
				}
				//
				if (menu.isString("DefaultTheme"))
					defaultMenuTheme = menu.getString("DefaultTheme");
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
		}
	}

}
