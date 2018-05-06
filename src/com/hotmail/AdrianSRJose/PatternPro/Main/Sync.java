package com.hotmail.AdrianSRJose.PatternPro.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.hotmail.AdrianSRJose.PatternPro.Configuration.ConfigLoader;
import com.hotmail.AdrianSRJose.PatternPro.Menus.PatternMenu.PatternMenu;

//import fr.xephi.authme.AuthMe;

public class Sync implements Listener 
{
	public static final Map<UUID, Boolean> logs = new HashMap<UUID, Boolean>();
	private static final Map<UUID, Location> joinLoc = new HashMap<UUID, Location>();
	//
	public Sync(JavaPlugin pl)
	{
		Bukkit.getPluginManager().registerEvents(this, pl);
	}

	@EventHandler
	public void onJ(PlayerJoinEvent eve)
	{
		final Player p = eve.getPlayer();
		//
		if (p == null || p.getName() == null)
			return;
		//
		p.setGameMode(GameMode.SURVIVAL);
		//
		if (p.getLocation() != null && p.getLocation().getWorld() != null)
		{
			joinLoc.put(p.getUniqueId(), p.getLocation());
		}
		//
		if (PatternPro.getInstance().getMySQL() == null)
			return;
		//
		if (!PatternPro.getInstance().getMySQL().hasPattern(p, true))
			return;
		//
		if (!PatternPro.getInstance().getMySQL().getUse(p))
			return;
		//
		final PatternPlayer pp = PatternPlayer.getPlayer(p);
		if (pp != null)
		{
			final PatternMenu menu = PatternCommand.getPatternMenu(p);
			//
			menu.setOpenOnMove(true);
			menu.setOnRecover(false);
			menu.setOnTest(false);
			//
			Object t1 = pp.getData("key-task-no-close-inventory-key");
			if (t1 != null && t1 instanceof Integer)
			{
				Integer id = (Integer)t1;
				Bukkit.getScheduler().cancelTask(id.intValue());
			}
			//
			pp.setData("key-last-connection-key", System.currentTimeMillis());
			//
			Bukkit.getScheduler().runTaskLater(PatternPro.getInstance(), new Runnable()
			{
				@Override
				public void run() 
				{
					if (p != null && p.isOnline())
					{
						p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 9000000, 10));
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 9000000, 10));
						//
						menu.Open();
					}
				}
			}, 10L);
			//
			//
			pp.setData("key-task-no-close-inventory-key", Integer.valueOf(Bukkit.getScheduler().runTaskTimer(PatternPro.getInstance(), new Runnable()
			{
				@Override
				public void run() 
				{
					final long lastC = (long)pp.getData("key-last-connection-key");
					//
					if (p != null && p.isOnline())
					{
						if (!isLoged(p))
						{
							if (ConfigLoader.LogTimeout > (long)0 && !menu.isOnRecover())
							{
								long sa = (System.currentTimeMillis() - lastC);
								long s = (sa / 1000);
								//
								if (s >= ConfigLoader.LogTimeout)
								{
									p.kickPlayer(Idioma.TIME_OUT_MESSGE.toString());
									//
									Object t1 = pp.getData("key-task-no-close-inventory-key");
									if (t1 != null && t1 instanceof Integer)
										Bukkit.getScheduler().cancelTask(((Integer)t1).intValue());
								}
							}
						}
						else
						{
							Object t1 = pp.getData("key-task-no-close-inventory-key");
							if (t1 != null && t1 instanceof Integer)
								Bukkit.getScheduler().cancelTask(((Integer)t1).intValue());
						}
					}
					else
					{
						Object t1 = pp.getData("key-task-no-close-inventory-key");
						if (t1 != null && t1 instanceof Integer)
							Bukkit.getScheduler().cancelTask(((Integer)t1).intValue());
					}
				}
			}, 20L, 0L).getTaskId()));
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMove(PlayerMoveEvent eve)
	{
		final Player p = eve.getPlayer();
		//
		if (p == null || p.getName() == null)
			return;
		//
		if (PatternPro.getInstance().getMySQL() == null) {
			return;
		}
		//
		if (!PatternPro.getInstance().getMySQL().hasPattern(p, true) || !PatternPro.getInstance().getMySQL().getUse(p))
			return;
		//
		final PatternPlayer pp = PatternPlayer.getPlayer(p);
		if (pp != null)
		{
			final PatternMenu menu = PatternCommand.getPatternMenu(p);
			//
			if (!isLoged(p))
			{
				Location loc = joinLoc.get(p.getUniqueId());
				if (loc != null && loc.getWorld() != null)
				{
					Location plock = p.getLocation();
					final Location to = new Location(plock.getWorld(), plock.getX(), plock.getY(), plock.getZ(), loc.getYaw(), loc.getPitch());
					//
					if (p.isOnGround())
						p.teleport(to);
				}
				//
				if (menu.openOnMove())
					menu.Open();
			}
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent eve)
	{
		logs.remove(eve.getPlayer().getUniqueId());
		//
		final Player p = eve.getPlayer();
		final PatternPlayer pp = PatternPlayer.getPlayer(p);
		if (pp != null)
		{
			Object t1 = pp.getData("key-task-no-close-inventory-key");
			if (t1 != null && t1 instanceof Integer)
				Bukkit.getScheduler().cancelTask(((Integer)t1).intValue());
		}
	}

	private boolean isLoged(final Player p)
	{
		if (p == null || p.getUniqueId() == null)
			return false;
		//
		boolean tor = true;
		//
		if (!logs.containsKey(p.getUniqueId()) || logs.get(p.getUniqueId()) == null)
			return false;
		//
		return tor;
	}

	public static void log(final Player p)
	{
		logs.put(p.getUniqueId(), Boolean.TRUE);
	}
}