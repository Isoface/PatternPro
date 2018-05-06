package com.hotmail.AdrianSRJose.PatternPro.Configuration;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;

import com.hotmail.AdrianSRJose.PatternPro.Util.Util;

public class RecoverPasswordItem
{
	private final String name;
	private PadColor color;
	private final Integer slot;
	//
	public RecoverPasswordItem(final ConfigurationSection sc) 
	{
		if (sc.isString("Name"))
		{
			name = Util.wc(sc.getString("Name"));
		}
		else
		{
			name = null;
			Util.print(ChatColor.RED + "Invalid RecoverPatternItem Name in Config.");
		}
		/////////////////////////////////////////////////////
		try
		{
			color = PadColor.valueOf(sc.getString("Color"));
		}
		catch(IllegalArgumentException e)
		{
			color = PadColor.WITHE;
			Util.print(ChatColor.RED + "Invalid RecoverPatternItem Color in Config.");
		}
		/////////////////////////////////////////////////////
		if (sc.isInt("Slot"))
		{
			slot = Integer.valueOf(sc.getInt("Slot"));
		}
		else
		{
			slot = null;
			Util.print(ChatColor.RED + "Invalid RecoverPatternItem Slot in Config.");
		}
	}
	
	public RecoverPasswordItem(final String name, final PadColor color, final Integer slot)
	{
		this.name = name != null ? Util.wc(name) : name;
		this.color = color;
		this.slot = slot;
	}
	
	public String getName()
	{
		return name;
	}
	
	public PadColor getColor()
	{
		return color;
	}
	
	public Integer getSlot()
	{
		return slot;
	}
	
	public int save(final ConfigurationSection sc)
	{
		int save = 0;
		//
		if (sc != null)
		{
			if (name != null)
				save += Util.setDefaultIfNotSet(sc, "Name", (name.replace("§", "&").replace("\\xa7", "&")));
			//////////////////////////////////////////////
			if (color != null)
				save += Util.setDefaultIfNotSet(sc, "Color", color.toString());
			//////////////////////////////////////////////
			if (slot != null)
				save += Util.setDefaultIfNotSet(sc, "Slot", slot.intValue());
		}
		//
		return save;
	}
}
