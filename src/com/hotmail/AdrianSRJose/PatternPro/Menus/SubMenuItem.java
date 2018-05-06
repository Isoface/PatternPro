package com.hotmail.AdrianSRJose.PatternPro.Menus;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.hotmail.AdrianSRJose.PatternPro.Main.PatternPro;

/**
 * A {@link ninja.amp.ampmenus.items.MenuItem}
 * {@link ninja.amp.ampmenus.menus.ItemMenu}.
 */
public class SubMenuItem extends MenuItem
{
	private final ItemMenu menu;

	public SubMenuItem(String displayName, ItemMenu menu, ItemStack icon, String... lore)
	{
		super(displayName, icon, lore);
		this.menu = menu;
	}

	@Override
	public void onItemClick(ItemClickEvent event)
	{
		event.setWillClose(true);
		final UUID ID = event.getPlayer().getUniqueId();
		Bukkit.getScheduler().scheduleSyncDelayedTask(PatternPro.getInstance(), new Runnable()
		{
			@Override
			public void run()
			{
				Player p = Bukkit.getPlayer(ID);
				if (p != null && menu != null)
				{
					menu.open(p);
				}
			}
		}, 3);
	}
}