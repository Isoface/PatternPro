package com.hotmail.AdrianSRJose.PatternPro.Main.Title;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.hotmail.AdrianSRJose.PatternPro.Main.VersionUtils;

public class TitleHandler 
{
	private static TitleHandler instance;
	public static TitleHandler getInstance()
	{
		if(instance == null)
        {
            instance = new TitleHandler();
        }
		return instance;
	}
	
	private TitleAPI titleApi;
	
	private TitleHandler()
    {
        try
        {
            String version = getVersion();
            String name = "com.hotmail.AdrianSRJose.PatternPro.Main.Title.versions."+version+".title";
            Class<?> cl = Class.forName(name);
            Class<? extends TitleAPI> titleApi = cl.asSubclass(TitleAPI.class);
            TitleAPI manager = titleApi.newInstance();
            this.titleApi = manager;
        }
        catch (Throwable e) 
        {
        	Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE+"Sorry, Titles not Supported for this spigot version");
        }
    }
	
	public static String getVersion()
    {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf(".") + 1);
    }
	
	public void SendTitleToPlayer(Player p, String t, String s, int fadeIn, int stay, int fadeOut)
	{
		if (p != null && t != null && s != null)
		{
			titleApi.sendTitleToPlayer(p, t, s, fadeIn, stay, fadeOut);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void sendTitle(final Player p, String t, String s, int i1, int i2, int i3)
	{
		if (t == null && s == null)
			throw new NullPointerException("Invalid titles");
		
		s = s == null ? "" : s;
		t = t == null ? "" : t;
		
		if (VersionUtils.isNewSpigotVersion())
			p.sendTitle(t , s);
		else
			TitleHandler.getInstance().SendTitleToPlayer(p, t, s, i1, i2, i3);
	}
	
	public static void sendTitle(final Player p, String t, String s)
	{
		sendTitle(p, t, s, 6, 26, 6);
	}
	
	public TitleAPI getTitle()
	{
		return titleApi;
	}
}
