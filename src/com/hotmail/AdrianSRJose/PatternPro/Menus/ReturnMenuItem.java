package com.hotmail.AdrianSRJose.PatternPro.Menus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ReturnMenuItem extends StaticMenuItem
{
	private final ItemMenu returnMenu;
	//
	public ReturnMenuItem(ItemMenu returnMenu, String name, Material icon) 
	{
		super(name, new ItemStack(icon != null ? icon : Material.ARROW), new String[]{});
		this.returnMenu = returnMenu;
	}
	
	public ReturnMenuItem(ItemMenu returnMenu, String name, ItemStack icon) 
	{
		super(name, icon, new String[]{});
		this.returnMenu = returnMenu;
	}
	
	public ReturnMenuItem(ItemMenu returnMenu, Material icon) 
	{
		super("�4�l<- Return", new ItemStack(icon != null ? icon : Material.ARROW), new String[]{});
		this.returnMenu = returnMenu;
	}
	
	public ReturnMenuItem(ItemMenu returnMenu, String name) 
	{
		super(name, new ItemStack(Material.ARROW), new String[]{});
		this.returnMenu = returnMenu;
	}
	
	public ReturnMenuItem(ItemMenu returnMenu) 
	{
		super("�4�l<- Return", new ItemStack(Material.ARROW), new String[]{});
		this.returnMenu = returnMenu;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event)
	{
		if (returnMenu != null) 
			returnMenu.open(event.getPlayer());
		else
			event.setWillClose(true);
	}
}
