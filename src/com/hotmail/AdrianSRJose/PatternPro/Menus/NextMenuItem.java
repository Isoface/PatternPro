package com.hotmail.AdrianSRJose.PatternPro.Menus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class NextMenuItem extends StaticMenuItem
{
	private final ItemMenu nextMenu;
	//
	public NextMenuItem(ItemMenu nextMenu, String name, Material icon) 
	{
		super(name, new ItemStack(icon != null ? icon : Material.ARROW), new String[]{});
		this.nextMenu = nextMenu;
	}
	
	public NextMenuItem(ItemMenu nextMenu, Material icon) 
	{
		super("§a§lNext ->", new ItemStack(icon != null ? icon : Material.ARROW), new String[]{});
		this.nextMenu = nextMenu;
	}
	
	public NextMenuItem(ItemMenu nextMenu, String name) 
	{
		super(name, new ItemStack(Material.ARROW), new String[]{});
		this.nextMenu = nextMenu;
	}
	
	public NextMenuItem(ItemMenu nextMenu) 
	{
		super("§a§lNext ->", new ItemStack(Material.ARROW), new String[]{});
		this.nextMenu = nextMenu;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event)
	{
		if (nextMenu != null) 
			nextMenu.open(event.getPlayer());
		else
			event.setWillClose(true);
	}
}
