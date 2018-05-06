package com.hotmail.AdrianSRJose.PatternPro.Menus;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * A {@link com.hotmail.AdrianSRJose.AnniPro.itemMenus.StaticMenuItem} that closes the
 * {@link com.hotmail.AdrianSRJose.AnniPro.itemMenus.ItemMenu}.
 */
public class CloseMenuItem extends StaticMenuItem
{

	public CloseMenuItem()
	{
		super(ChatColor.RED + "Close", new ItemStack(Material.RECORD_4));
	}

	@Override
	public void onItemClick(ItemClickEvent event)
	{
		event.setWillClose(true);
	}
}