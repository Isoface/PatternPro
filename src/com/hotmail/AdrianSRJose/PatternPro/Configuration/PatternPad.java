package com.hotmail.AdrianSRJose.PatternPro.Configuration;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import com.hotmail.AdrianSRJose.PatternPro.Util.Util;

public class PatternPad
{
	private PadColor color;
	private PadColor clickColor;
	private final Map<Integer, PadItem> pads;
	//
	public PatternPad(final ConfigurationSection sc)
	{
		assert sc != null;
		//
		pads = new HashMap<Integer, PadItem>();
		//////////////////////////////////////////////////////////////////
		try
		{
			color = PadColor.valueOf(sc.getString("Color"));
		}
		catch(IllegalArgumentException e)
		{
			color = PadColor.WITHE;
			Util.print(ChatColor.RED + "Invalid PatternPad Color in Config.");
		}
		//////////////////////////////////////////////////////////////////
		try
		{
			clickColor = PadColor.valueOf(sc.getString("ClickColor"));
		}
		catch(IllegalArgumentException e)
		{
			clickColor = PadColor.GREEN;
			Util.print(ChatColor.RED + "Invalid ClickColor in Config.");
		}
		//////////////////////////////////////////////////////////////////
		ConfigurationSection padItems = sc.getConfigurationSection("PadItems");
		if (padItems != null)
		{
			for (String key : padItems.getKeys(false))
			{
				ConfigurationSection aPad = padItems.getConfigurationSection(""+key);
				if (aPad != null)
				{
					PadItem pi = new PadItem(aPad);
					if (pi != null)
						pads.put(pi.getPos(), pi);
				}
			}
		}
	}
	
	public PatternPad(final PadColor color, final PadColor clickColor, final Map<Integer, PadItem> pads)
	{
		this.color = color;
		this.clickColor = clickColor;
		this.pads = pads;
	}
	
	public Map<Integer, PadItem> getPads()
	{
		return pads;
	}
	
	public PadColor getColor()
	{
		return color;
	}
	
	public PadColor getClickColor()
	{
		return clickColor;
	}
}
