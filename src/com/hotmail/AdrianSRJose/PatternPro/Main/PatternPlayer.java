package com.hotmail.AdrianSRJose.PatternPro.Main;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public final class PatternPlayer
{
	private static final Map<UUID, PatternPlayer> players = new ConcurrentHashMap<UUID, PatternPlayer>();

	public static PatternPlayer getPlayer(UUID id)
	{
		if (id != null && players.get(id) == null)
			PlayerLoader.loadPlayer(id);
		//
		return id != null ? players.get(id) : null;
	}
	
	public static PatternPlayer getPlayer(final Player p)
	{
		if (p != null && p.getUniqueId() != null)
			return getPlayer(p.getUniqueId());
		//
		return null;
	}
	
	public static Collection<PatternPlayer> getPlayers()
	{
		return players.values();
	}
	
	public static void RegisterListener(final Plugin p)
	{
		players.clear();
		PlayerLoader l = new PlayerLoader();
		Bukkit.getPluginManager().registerEvents(l, p);
	}
	
	private static class PlayerLoader implements Listener
	{
		public PlayerLoader()
		{
			//If any players are online when this is called (hint: /reload), we load their PatternPlayer
			for(Player p : Bukkit.getOnlinePlayers())
			{
				loadPlayer(p);
			}
		}
		
		@EventHandler(priority = EventPriority.LOWEST)
		public void removePutedPassword(PlayerJoinEvent event)
		{
			removePutedPassword(event.getPlayer());
		}
		
		@EventHandler(priority = EventPriority.MONITOR)
		public void removePutedPassword(PlayerQuitEvent event)
		{
			removePutedPassword(event.getPlayer());
		}

		@EventHandler(priority = EventPriority.MONITOR)
		public void removePutedPassword(PlayerKickEvent event)
		{
			removePutedPassword(event.getPlayer());
		}
		
		private void removePutedPassword(final Player p)
		{
			if (exists(p))
			{
				final PatternPlayer pp = PatternPlayer.getPlayer(p);
				pp.setData("key-puted-patternt-key", null);
			}
		}
		
		private boolean exists(final Player p)
		{
			return players.containsKey(p.getUniqueId());
		}
		
		private static void loadPlayer(final Player p)
		{
			final PatternPlayer player = new PatternPlayer(p.getUniqueId(),p.getName());
			players.put(p.getUniqueId(), player);
		}
		
		private static void loadPlayer(final UUID id)
		{
			if (id != null)
			{
				final Player p = Bukkit.getPlayer(id);
				if (p != null)
				{
					final PatternPlayer player = new PatternPlayer(p.getUniqueId(), p.getName());
					players.put(p.getUniqueId(), player);
				}
			}
		}	
	}
	
	private final UUID id;
	private final String name;
	
	private Map<Object,Object> data;
	
	private PatternPlayer(UUID ID, String Name)
	{
		id = ID;
		name = Name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public UUID getID()
	{
		return id;
	}
	
	public Object getData(Object key)
	{
		if(data == null)
			return null;
		return data.get(key);
	}
	
	public void setData(Object key, Object value)
	{
		if(data == null)
			data = new HashMap<Object,Object>();
		//
		data.put(key, value);
	}

	
	public Player getPlayer()
	{
		return Bukkit.getPlayer(id);
	}
	
	public boolean isOnline()
	{
		return getPlayer() != null && getPlayer().isOnline();
	}
	
	public Map<Object,Object> getData()
	{
		return data;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof PatternPlayer)
		{
			PatternPlayer p = (PatternPlayer)obj;
			return id.equals(p.id);
		}
		else return false;
	}
}