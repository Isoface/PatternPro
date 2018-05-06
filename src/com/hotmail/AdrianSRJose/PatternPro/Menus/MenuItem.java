package com.hotmail.AdrianSRJose.PatternPro.Menus;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * An Item inside an {@link com.hotmail.AdrianSRJose.AnniPro.itemMenus.MenuItem}.
 */
public class MenuItem
{
	private String displayName;
	private ItemStack icon;
	private List<String> lore;

	public MenuItem(String displayName, ItemStack icon, List<String> lore)
	{
		this.displayName = displayName;
		this.icon = icon;
		this.lore = lore;
	}
	//
	public MenuItem(String displayName, ItemStack icon, String... lore)
	{
		this(displayName, icon, Arrays.asList(lore));
	}
	//
	public MenuItem(String displayName, ItemStack icon)
	{
		this(displayName, icon, new String[]{});
	}

	/**
	 * Gets the display name of the MenuItem.
	 *
	 * @return The display name.
	 */
	public String getDisplayName()
	{
		return displayName;
	}

	/**
	 * Gets the icon of the MenuItem.
	 *
	 * @return The icon.
	 */
	public ItemStack getIcon()
	{
		return icon;
	}
	
	public void setIcon(ItemStack newIcon)
	{
		icon = newIcon;
	}
	
	public void setDisplayName(String name)
	{
		displayName = name;
	}

	/**
	 * Gets the lore of the MenuItem.
	 *
	 * @return The lore.
	 */
	public List<String> getLore()
	{
		return lore;
	}
	
	public String[] getLoreArray()
	{
		String[] tor = new String[lore.size()];
		//
		for (int x = 0; x < tor.length; x++)
			tor[x] = lore.get(x);
		//
		return tor;
	}

	/**
	 * Gets the ItemStack to be shown to the player.
	 *
	 * @param player
	 *            The player.
	 * @return The final icon.
	 */
	public ItemStack getFinalIcon(Player player)
	{
		return setNameAndLore(getIcon().clone(), getDisplayName(), getLore());
	}

	/**
	 * Called when the MenuItem is clicked.
	 *
	 * @param event
	 *            The {@link com.hotmail.AdrianSRJose.AnniPro.itemMenus.ItemClickEvent}.
	 */
	public void onItemClick(ItemClickEvent event)
	{
		// Do nothing by default
	}

	/**
	 * Sets the display name and lore of an ItemStack.
	 *
	 * @param itemStack
	 *            The ItemStack.
	 * @param displayName
	 *            The display name.
	 * @param lore
	 *            The lore.
	 * @return The ItemStack.
	 */
	public static ItemStack setNameAndLore(ItemStack itemStack,
			String displayName, List<String> lore)
	{
		ItemMeta meta = itemStack.getItemMeta();
		if (meta == null) meta = Bukkit.getItemFactory().getItemMeta(itemStack.getType());
		//
		if (meta != null)
		{
			meta.setDisplayName(displayName);
			meta.setLore(lore);
			itemStack.setItemMeta(meta);
		}
		//
		return itemStack;
	}
}