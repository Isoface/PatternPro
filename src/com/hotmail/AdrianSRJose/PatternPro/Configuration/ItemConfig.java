package com.hotmail.AdrianSRJose.PatternPro.Configuration;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import com.hotmail.AdrianSRJose.PatternPro.Menus.ItemClickEvent;
import com.hotmail.AdrianSRJose.PatternPro.Menus.ItemClickHandler;
import com.hotmail.AdrianSRJose.PatternPro.Menus.ItemMenu;
import com.hotmail.AdrianSRJose.PatternPro.Menus.MenuItem;
import com.hotmail.AdrianSRJose.PatternPro.Util.Util;

public class ItemConfig
{
	private final Integer slot;
	private final String name;
	private final ItemStack type;
	private final Short data;
	private final List<String> lore;
	private ItemClickHandler clickHandler;
	
	@SuppressWarnings("deprecation")
	public ItemConfig(final ConfigurationSection sc)
	{
		assert sc != null;
		//
		slot = Integer.valueOf(sc.getInt("Slot"));
		name = sc.getString("Name") != null ? ChatColor.translateAlternateColorCodes('&', sc.getString("Name")) : null;
		data = (short)sc.getInt("TypeData");
		type = new ItemStack(Material.getMaterial(sc.getInt("TypeID")), 1, (data != null ? data.shortValue() : (short)0));
		//
		lore = new ArrayList<String>();
		if (sc.getStringList("Lore") != null)
		{
			for (String g : sc.getStringList("Lore"))
				lore.add(g);
		}
	}
	
	public ItemConfig(Integer slot, String name, ItemStack type, Short data, List<String> lore)
	{
		this.slot = slot;
		this.name = name;
		this.lore = lore;
		this.data = data;
		if (type != null)
			this.type = new ItemStack(type.getType(), 1, (data != null ? data.shortValue() : (short)0));
		else
			this.type = type;
	}
	
	public ItemConfig(Integer slot, String name, ItemStack type, Short data, List<String> lore, ItemClickHandler clickHandler)
	{
		this(slot,name,type,data,lore);
		this.clickHandler = clickHandler;
	}
	
	public Integer getSlot()
	{
		return slot;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ItemStack getType()
	{
		return type;
	}
	
	public void setInMenu(final ItemMenu menu)
	{
		if (menu != null)
		{
			final MenuItem item = new MenuItem(name, type, lore);
			if (item != null && slot != null)
			{
				if (this.clickHandler != null)
					menu.setItem(slot.intValue(), new CustomItem(name, type, lore, clickHandler));
				else
					menu.setItem(slot.intValue(), item);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	public int save(final ConfigurationSection sc)
	{
		int save = 0;
		//
		if (slot != null)
			save += Util.setDefaultIfNotSet(sc, "Slot", slot.intValue());
		//
		if (name != null)
			save += Util.setDefaultIfNotSet(sc, "Name", name);
		//
		if (type != null && type.getType() != null)
			save += Util.setDefaultIfNotSet(sc, "TypeID", type.getTypeId());
		//
		if (data != null)
			save += Util.setDefaultIfNotSet(sc, "TypeData", data);
		//
		if (this.lore != null)
			save += Util.setDefaultIfNotSet(sc, "Lore", lore);
		//
		return save;
	}
	
	private class CustomItem extends MenuItem
	{
		private final ItemClickHandler handler;
		//
		public CustomItem(String displayName, ItemStack icon, List<String> lore, ItemClickHandler handler)
		{
			super(displayName, icon, lore);
			this.handler = handler;
		}
		
		@Override
		public void onItemClick(ItemClickEvent event)
		{
			handler.onItemClick(event);
		}
	}
}
