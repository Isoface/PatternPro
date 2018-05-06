package com.hotmail.AdrianSRJose.PatternPro.Menus.PatternMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import com.hotmail.AdrianSRJose.PatternPro.Configuration.ConfigLoader;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.CreatorPad;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.CreatorPatternMenuTheme;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.ItemConfig;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.MenuTheme;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.PCMLoader;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.PadItem;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.PatternPad;
import com.hotmail.AdrianSRJose.PatternPro.Database.MySQL;
import com.hotmail.AdrianSRJose.PatternPro.Main.Idioma;
import com.hotmail.AdrianSRJose.PatternPro.Main.PatternPlayer;
import com.hotmail.AdrianSRJose.PatternPro.Main.PatternPro;
import com.hotmail.AdrianSRJose.PatternPro.Main.Title.TitleHandler;
import com.hotmail.AdrianSRJose.PatternPro.Menus.ItemClickEvent;
import com.hotmail.AdrianSRJose.PatternPro.Menus.ItemMenu;
import com.hotmail.AdrianSRJose.PatternPro.Menus.ItemMenu.Size;
import com.hotmail.AdrianSRJose.PatternPro.Menus.MenuItem;
import com.hotmail.AdrianSRJose.PatternPro.Util.Util;

public class SetPatternMenu
{
	private final UUID ownerID;
	private final PatternPlayer pp;
	private ItemMenu menu;
	private String title = "&4&lPattern Creator";
	private Map<Integer, PadItem> pads = new HashMap<Integer, PadItem>();
	private Map<Integer, PadMenuItem> padsMenuItems = new HashMap<Integer, PadMenuItem>();
	//
	/**
	 * [0] [1] [2]
	 * 
	 * [3] [4] [5]
	 * 
	 * [6] [7] [8]
	 */
	//
	public SetPatternMenu(final Player owner) 
	{
		if (owner != null)
			this.ownerID = owner.getUniqueId();
		else
			ownerID = null;
		//
		this.menu = new ItemMenu(Util.wc("&4&lPattern Creator"), Size.fit(PCMLoader.MenuItems.size() > PCMLoader.considerar ? PCMLoader.MenuItems.size() : PCMLoader.considerar));
		pp = PatternPlayer.getPlayer(owner);
	}
	
	private void setItems()
	{
		MenuTheme theme = null;
		String menuThemeName = PCMLoader.defaultMenuTheme;
		//
		if (menuThemeName != null)
		{
			for (MenuTheme amt : PCMLoader.LoadedThemes)
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
		if (theme == null)
			theme = new CreatorPatternMenuTheme();
		//
		if (theme.getThemeMenuSize() != menu.getSize().getSize())
		{
			menu = new ItemMenu(Util.wc(""+theme.getName()), getMinimalSize(Size.fit(theme.getThemeMenuSize())));
			title = Util.wc(""+theme.getName());
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
		PatternPad pad = new CreatorPad();
		this.pads = pad.getPads();
		//
		for (PadItem ii : pads.values())
		{
			PadMenuItem e = new PadMenuItem(pp, ii);
			if (e != null)
			{
				menu.setItem(ii.getSlot(), e);
				padsMenuItems.put(ii.getSlot(), e);
			}
		}
	}
	
	private boolean isValidPattern(final PatternPlayer ap)
	{
		boolean tor = false;
		//
		if (ap != null)
		{
			Object data = ap.getData("key-new-patter-key");
			if (data != null && data instanceof String)
			{
				String cp = (String)data;
				if (cp != null)
				{
					if (cp.startsWith("_"))
						cp = cp.substring(1);
					//
					String[] array = cp.split("_");
					String realPass = "";
					//
					for (String sg : array)
					{
						if (sg != null)
						{
							realPass += " " + sg.replace(" ", "").trim();
						}
					}
					//
					if (realPass.trim().length() >= 7)
					{
						tor = true;
					}
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
		if (Bukkit.getPlayer(ownerID) != null)
		{
			menu.clearAllItems();
			setItems();
			menu.open(Bukkit.getPlayer(ownerID));
			menu.update(Bukkit.getPlayer(ownerID));
			//
			if (pp != null)
			{
				pp.setData("key-new-patter-key", null);
				pp.setData("key-task-new-patter-key-creating....", null);
			}
			//
			return;
		}
	}
	
	public void Update()
	{
		menu.clearAllItems();
		setItems();
	}
	
	public class PadMenuItem extends MenuItem
	{
		private final PatternPlayer pp;
		private final PadItem item;
		private final PadMenuItem instance;
		//
		public PadMenuItem(PatternPlayer pp, PadItem item)
		{
			super(".", new ItemStack(Material.STAINED_GLASS_PANE, 1, new CreatorPad().getColor().shortValue()));
			this.pp = pp;
			this.item = item;
			instance = this;
		}

		@Override
		public void onItemClick(ItemClickEvent event)
		{
			final Player p = event.getPlayer();
			final PatternPlayer ap = PatternPlayer.getPlayer(p);
			//
			if (pp != null && pp.equals(ap))
			{
				Object data = pp.getData("key-new-patter-key");
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
				//
				pp.setData("key-new-patter-key", dataString + "_" + item.getPos());
				//
				//
				Object taskData = pp.getData("key-task-new-patter-key");
				if (taskData != null && taskData instanceof Integer)
				{
					Integer g = (Integer)taskData;
					if (g != null)
						Bukkit.getScheduler().cancelTask(g.intValue());
				}
				
				Object taskData2 = pp.getData("key-task-new-patter-key-creating....");
				if (taskData2 != null && taskData2 instanceof Integer)
				{
					Integer g = (Integer)taskData2;
					if (g != null)
						Bukkit.getScheduler().cancelTask(g.intValue());
				}
				
				instance.setIcon(new ItemStack(Material.STAINED_GLASS_PANE, 1, new CreatorPad().getClickColor().shortValue()));
				menu.update(p);
				
				String set = dataString + "_" + item.getPos();
				pp.setData("key-task-new-patter-key-creating....", Integer.valueOf(Bukkit.getScheduler().runTaskLater(PatternPro.getInstance(), new Runnable()
				{
					@Override
					public void run() 
					{
						if (pp != null)
						{
							if (isValidPattern(pp))
							{
								event.setWillClose(true);
								//
								MySQL sql = PatternPro.getInstance().getMySQL();
								sql.setPattern(pp.getPlayer(), set);
								sql.setUse(p, true);
								//
								pp.getPlayer().sendMessage(Idioma.PC_PATTERN_CREATED.toString());
								//
								if (ConfigLoader.UseTitles && ConfigLoader.UseCreatedTitle)
								{
									TitleHandler.sendTitle(p, Idioma.PC_PATTERN_CREATED.toString(), "");
								}
								//
								//
								pp.setData("key-new-patter-key", null);
								pp.setData("key-task-new-patter-key-creating....", null);
								//
								if (pp.getPlayer() != null)
								{
									menu.update(Bukkit.getPlayer(pp.getID()));
									//
									final InventoryView coi = pp.getPlayer().getOpenInventory();
									if (coi != null)
									{
										if (coi.getTopInventory() != null)
										{
											if (ChatColor.stripColor(menu.getName()).contains(ChatColor.stripColor(coi.getTitle())))
											{
												coi.close();
												pp.getPlayer().closeInventory();
												//
												SingleQuestionPrompt.newPrompt(p, ""+Idioma.CREATING_PIN_ENTER_YOUR_SECURITY_PIN.toString(), new AcceptAnswer()
												{
													@Override
													public boolean onAnswer(String input) 
													{
														if (input != null) 
														{
															String i = input.trim();
															if (i.startsWith("/"))
																i = i.substring(1);
															//
															if (i.length() > 5)
															{
																if (i.length() <= 40)
																{
																	p.sendMessage(""+Idioma.CREATING_PIN_SUCESS.toStringReplacement(i));
																	PatternPro.getInstance().getMySQL().setPassword(p, i);
																	return true;
																}
																else
																	p.sendMessage(""+Idioma.CREATING_PIN_LONG_PIN.toString());
															}
															else
																p.sendMessage(""+Idioma.CREATING_PIN_SHORT_PIN.toString());
														}
														else
															p.sendMessage(""+Idioma.CREATING_PIN_INVALID.toString());
														//
														return false;
													}
												});
											}
										}
									}
								}
								//
								Update();
							}
							else
							{
								pp.setData("key-task-new-patter-key", Integer.valueOf(Bukkit.getScheduler().runTaskLater(PatternPro.getInstance(), new Runnable()
								{
									@Override
									public void run() 
									{
										if (pp != null)
										{
											Player p = pp.getPlayer();
											if (p != null && p.isOnline())
											{
												pp.setData("key-new-patter-key", null);
												p.sendMessage(Idioma.PC_FEW_SELECTED_POINTS.toString());
												Open();
												menu.update(p);
											}
										}
									}
								}, 18L).getTaskId()));
							}
						}
					}
				}, 18L).getTaskId()));
			}
		}
	}
	
}
