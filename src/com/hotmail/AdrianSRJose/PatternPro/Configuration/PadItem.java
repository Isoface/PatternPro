package com.hotmail.AdrianSRJose.PatternPro.Configuration;

import org.bukkit.configuration.ConfigurationSection;

import com.hotmail.AdrianSRJose.PatternPro.Util.Util;

public class PadItem
{
	private final Integer pos;
	private final Integer slot;
	//
	public PadItem(final ConfigurationSection sc)
	{
		pos = Integer.valueOf(sc.getInt("Pos"));
		slot = Integer.valueOf(sc.getInt("Slot"));
	}
	
	public PadItem(Integer pos, Integer slot)
	{
		this.pos = pos;
		this.slot = slot;
	}
	
	public Integer getPos()
	{
		return pos;
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
			if (pos != null)
				save += Util.setDefaultIfNotSet(sc, "Pos", pos.intValue());
			//
			if (slot != null)
				save += Util.setDefaultIfNotSet(sc, "Slot", slot.intValue());
		}
		//
		return save;
	}
}

