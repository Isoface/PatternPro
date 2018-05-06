package com.hotmail.AdrianSRJose.PatternPro.Configuration;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.hotmail.AdrianSRJose.PatternPro.Util.Util;

public class MenuTheme 
{
	private final List<ItemConfig> items;
	private int menuSize = 0;
	private String SCThemeName;
	private String ThemeName;
	//
	public MenuTheme(final ConfigurationSection themeSection)
	{
		items = new ArrayList<ItemConfig>();
		//
		if (themeSection != null)
		{
			SCThemeName = themeSection.getName();
			ThemeName = Util.wc(themeSection.getString("Name"));
			//
			ConfigurationSection itemsSection = themeSection.getConfigurationSection("Items");
			if (itemsSection != null)
			{
				for (String key : itemsSection.getKeys(false))
				{
					ConfigurationSection is = itemsSection.getConfigurationSection(""+key);
					if (is != null)
					{
						ItemConfig ItemLoaded = new ItemConfig(is);
						if (ItemLoaded != null)
						{
							items.add(ItemLoaded);
							//
							menuSize++;
							//
							if (ItemLoaded.getSlot() != null && ItemLoaded.getSlot().intValue() > menuSize)
								menuSize = ItemLoaded.getSlot();
						}
					}
				}
			}
		}
	}
	
	public MenuTheme(final List<ItemConfig> items, final int MenuSize, final String SCThemeName, final String realThemeName)
	{
		this.items = items;
		this.menuSize = MenuSize;
		this.SCThemeName = SCThemeName;
		this.ThemeName = realThemeName;
	}
	
	public int getThemeMenuSize()
	{
		return menuSize;
	}
	
	public List<ItemConfig> getItems()
	{
		return items;
	}
	
	public String getSCName()
	{
		return SCThemeName;
	}
	
	public String getName()
	{
		return ThemeName;
	}
}
