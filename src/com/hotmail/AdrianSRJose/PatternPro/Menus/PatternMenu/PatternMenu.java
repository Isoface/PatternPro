package com.hotmail.AdrianSRJose.PatternPro.Menus.PatternMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.hotmail.AdrianSRJose.PatternPro.Configuration.ConfigLoader;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.DefaultMenuTheme;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.ItemConfig;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.MenuConfigLoader;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.MenuTheme;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.PadItem;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.PatternPad;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.RecoverPasswordItem;
import com.hotmail.AdrianSRJose.PatternPro.Main.Idioma;
import com.hotmail.AdrianSRJose.PatternPro.Main.PatternPlayer;
import com.hotmail.AdrianSRJose.PatternPro.Main.PatternPro;
import com.hotmail.AdrianSRJose.PatternPro.Main.Sync;
import com.hotmail.AdrianSRJose.PatternPro.Main.Title.TitleHandler;
import com.hotmail.AdrianSRJose.PatternPro.Menus.ItemClickEvent;
import com.hotmail.AdrianSRJose.PatternPro.Menus.ItemMenu;
import com.hotmail.AdrianSRJose.PatternPro.Menus.ItemMenu.Size;
import com.hotmail.AdrianSRJose.PatternPro.Menus.MenuItem;
import com.hotmail.AdrianSRJose.PatternPro.Util.Util;
import com.lambdaworks.crypto.SCryptUtil;

//import fr.xephi.authme.AuthMe;

public class PatternMenu
{
	private final UUID owner;
	private final PatternPlayer pp;
	private ItemMenu menu;
	private String title = "PatternMenu Title Here";
	private Map<Integer, PadItem> pads = new HashMap<Integer, PadItem>();
	private Map<Integer, PadMenuItem> padsMenuItems = new HashMap<Integer, PadMenuItem>();
	private boolean openOnMove = true;
	private boolean isOnRecover = false;
	private boolean isOnTest = false;
	//
	private int Attemps = 0;
	//
	private /*final*/ String pass;
	
	/**
	 * [0] [1] [2]
	 * 
	 * [3] [4] [5]
	 * 
	 * [6] [7] [8]
	 */
	//
	public PatternMenu(final Player owner, final String pass) 
	{
		this.owner = owner.getUniqueId();
		this.menu = new ItemMenu("PatternMenu Title Here", Size.fit(MenuConfigLoader.MenuItems.size() > MenuConfigLoader.considerar ? MenuConfigLoader.MenuItems.size() : MenuConfigLoader.considerar));
		pp = PatternPlayer.getPlayer(owner);
		this.pass = pass;
	}
	
	private void setItems()
	{
		MenuTheme theme = null;
		String menuThemeName = MenuConfigLoader.defaultMenuTheme;
		//
		if (menuThemeName != null)
		{
			for (MenuTheme amt : MenuConfigLoader.LoadedThemes)
			{
				if (amt != null && amt.getSCName() != null && menuThemeName.equalsIgnoreCase(amt.getSCName()))
					theme = amt;
			}
			//
			if (theme == null)
				Util.print(ChatColor.RED + "Could not find the Theme "+ ChatColor.YELLOW + "'"+menuThemeName+"'");
		}
		else
			Util.print(ChatColor.RED + "Invalid Default Theme in config");
		//
		//
		//
		if (theme == null)
			theme = new DefaultMenuTheme();
		//
		if (theme.getThemeMenuSize() != menu.getSize().getSize())
		{
			menu = new ItemMenu(""+theme.getName(), getMinimalSize(Size.fit(theme.getThemeMenuSize())));
			this.title = ""+theme.getName();
		}
		//
		for (ItemConfig i : theme.getItems())
		{
			if (i != null && i.getSlot() != null)
			{
				int slot = i.getSlot().intValue();
				//
				if (slot > 53 || slot < 0)
				{
					Util.print(ChatColor.RED + "The slot has to be between 0 and 53. The item " + i.getName() != null ? "'"+i.getName()+"'" : "'Invalid Item Name'" + " has an invalid slot.");
					continue;
				}
				//
				i.setInMenu(menu);
			}
		}
		//////////////////////////////////////////////////////////////////////////////////////////////
		PatternPad pad = MenuConfigLoader.Pad;
		if (pad != null)
		{
			this.pads = pad.getPads();
			//
			for (PadItem ii : pads.values())
			{
				PadMenuItem e = new PadMenuItem(pp, ii);
				if (e != null)
				{
					if (!(ii.getSlot() > menu.getSize().getSize()))
					{
						menu.setItem(ii.getSlot(), e);
						padsMenuItems.put(ii.getSlot(), e);
					}
					else
						Util.print(ChatColor.RED + "Could not place a PadItem because the slot in config is greater than the size of the menu.");
				}
			}
		}
		/////////////////////////////////////////////////////////////////////////////////////////////
		RecoverPasswordItem recover = MenuConfigLoader.recoverItem;
		if (recover != null && recover.getSlot() != null && recover.getName() != null)
		{
			final RecoverItem recItem = new RecoverItem(recover.getName(), recover.getColor(), Bukkit.getPlayer(owner));
			if (recover.getSlot() > menu.getSize().getSize())
			{
				Util.print(ChatColor.RED + "The slot of the RecoverItem is greater than the size of the menu.");
			}
			else
			{
				menu.setItem(recover.getSlot(), recItem);
			}
		}
	}

	
	private boolean isSucces(final PatternPlayer ap)
	{
		boolean tor = false;
		//
		if (ap != null)
		{
			Object data = ap.getData("key-puted-patternt-key");
			if (data != null && data instanceof String)
			{
				String cp = (String)data;
				if (cp != null)
				{
					if (SCryptUtil.racifirev(cp, pass))
						tor = true;
				}
			}
		}
		//
		return tor;
	}
	
	private Size getMinimalSize(Size s)
	{
		if (s == null)
			return Size.THREE_LINE;
		//
		switch(s)
		{
			case FIVE_LINE:
				return Size.FIVE_LINE;
			case FOUR_LINE:
				return Size.FOUR_LINE;
			case ONE_LINE:
				return Size.THREE_LINE;
			case SIX_LINE:
				return Size.SIX_LINE;
			case THREE_LINE:
				return Size.THREE_LINE;
			case TWO_LINE:
				return Size.THREE_LINE;
			default:
				return Size.THREE_LINE;
		}
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public void Open()
	{
		Player p = Bukkit.getPlayer(owner);
		if (p != null)
		{
			menu.clearAllItems();
			setItems();
			menu.open(p);
			//
			if (pp != null)
				pp.setData("key-puted-patternt-key", null);
			//
			this.pass = PatternPro.getInstance().getMySQL().getPattern(p, true);
		}
	}

	public class PadMenuItem extends MenuItem
	{
		private final PatternPlayer pp;
		private final PadItem item;
		private final PadMenuItem instance;
		//
		public PadMenuItem(PatternPlayer pp, PadItem item)
		{
			super(".", new ItemStack(Material.STAINED_GLASS_PANE, 1, MenuConfigLoader.Pad.getColor().shortValue()));
			this.pp = pp;
			this.item = item;
			instance = this;
		}

		//@SuppressWarnings("deprecation")
		@Override
		public void onItemClick(ItemClickEvent event)
		{
			final Player p = event.getPlayer();
			final PatternPlayer ap = PatternPlayer.getPlayer(p);
			//
			if (pp != null && pp.equals(ap))
			{
				Object data = pp.getData("key-puted-patternt-key");
				String dataString = "";
				if (data != null && data instanceof String)
				{
					dataString = (String)data;
					if (dataString != null && dataString.contains(""+item.getPos()))
					{
						return;
					}
				}
				//
				pp.setData("key-puted-patternt-key", dataString + "_" + item.getPos());
				//
				//
				Object taskData = pp.getData("key-task-pattern-key");
				if (taskData != null && taskData instanceof Integer)
				{
					Integer g = (Integer)taskData;
					if (g != null)
						Bukkit.getScheduler().cancelTask(g.intValue());
				}
				
				instance.setIcon(new ItemStack(Material.STAINED_GLASS_PANE, 1, MenuConfigLoader.Pad.getClickColor().shortValue()));
				menu.update(p);
				
				if (isSucces(pp))
				{
					event.setWillClose(true);
					ap.setData("key-puted-patternt-key", null);
					//
					setOpenOnMove(false);
					setOnTest(true);
					setAttemps(0);
					setOnRecover(false);
					//
					pp.getPlayer().sendMessage(Idioma.PATTERN_SUCCES.toString());
					//
					if (ConfigLoader.UseTitles && ConfigLoader.UseSuccesTitle)
					{
						TitleHandler.sendTitle(p, Idioma.PATTERN_SUCCES.toString(), "");
					}
					//
					Sync.log(p);
					//
					Object t1 = pp.getData("key-task-no-close-inventory-key");
					if (t1 != null && t1 instanceof Integer)
						Bukkit.getScheduler().cancelTask(((Integer)t1).intValue());
					//
					p.removePotionEffect(PotionEffectType.BLINDNESS);
					p.removePotionEffect(PotionEffectType.SLOW);
					p.removePotionEffect(PotionEffectType.JUMP);
				}
				else
				{
					pp.setData("key-task-pattern-key", Integer.valueOf(Bukkit.getScheduler().runTaskLater(PatternPro.getInstance(), new Runnable()
					{
						@Override
						public void run() 
						{
							if (pp != null)
							{
								Player p = pp.getPlayer();
								if (p != null && p.isOnline())
								{
									pp.setData("key-puted-patternt-key", null);
									p.sendMessage(Idioma.ICORRECT_PATTERN.toString());
									Open();
									menu.update(p);
									//
									if (!isOnTest)
									{
										if (Attemps >= ConfigLoader.MaxAttemps)
										{
											//AuthMe.getApi().forceLogout(p);
											p.kickPlayer(Idioma.TOO_ATTEPS.toString());
											//
											Attemps = 0;
										}
										else
											Attemps ++;
									}
									else
										Attemps = 0;
								}
							}
						}
					}, 20L).getTaskId()));
				}
			}
		}
	}
	
//	private void bungeeSender(final Player p, String to)
//	{
//		Bukkit.getScheduler().scheduleSyncDelayedTask(PatternPro.getInstance(), new Runnable()
//		{
//			@Override
//			public void run() 
//			{
//				sendBungeecordMessage("ConnectOther", p.getName(), to);
//			}
//		}, 20L);
//	}
//	
//    private void sendBungeecordMessage(String... data) 
//    {
//        ByteArrayDataOutput out = ByteStreams.newDataOutput();
//        for (String element : data)
//        {
//            out.writeUTF(element);
//        }
//        sendBungeeMessage(out.toByteArray());
//    }
//    
//    private void sendBungeeMessage(byte[] bytes) 
//    {
//        Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
//        if (player != null)
//        {
//            player.sendPluginMessage(PatternPro.getInstance(), "BungeeCord", bytes);
//        }
//    }
	
	public boolean openOnMove()
	{
		return this.openOnMove;
	}
	
	public boolean isOnRecover()
	{
		return isOnRecover;
	}
	
	public boolean isOnTest()
	{
		return isOnTest;
	}
	
	public void setOpenOnMove(boolean b)
	{
		openOnMove = b;
	}
	
	public void setOnRecover(boolean b)
	{
		isOnRecover = b;
	}
	
	public void setOnTest(boolean b)
	{
		isOnTest = b;
	}
	
	public void setAttemps(int c)
	{
		Attemps = c;
	}
}
