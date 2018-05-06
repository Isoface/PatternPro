package com.hotmail.AdrianSRJose.PatternPro.Main;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.hotmail.AdrianSRJose.PatternPro.Database.MySQL;
import com.hotmail.AdrianSRJose.PatternPro.Menus.PatternMenu.PatternMenu;
import com.hotmail.AdrianSRJose.PatternPro.Menus.PatternMenu.SetPatternMenu;

public class PatternCommand implements CommandExecutor 
{
	private static final Map<UUID, PatternMenu> menus = new HashMap<UUID, PatternMenu>();
	private static final Map<UUID, SetPatternMenu> setMenus = new HashMap<UUID, SetPatternMenu>();
	//
	public PatternCommand(JavaPlugin pl) 
	{
		pl.getCommand("Pattern").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if (sender instanceof Player)
		{
			Player p = (Player)sender;
			//
			if (args.length > 0)
			{
				if (args[0].equalsIgnoreCase("Manager"))
				{
					if (p.hasPermission("Pattern.Manager.Open") || p.isOp())
					{
						SetPatternMenu men = getPatternMenuCreator(p);
						men.Open();
						men.Open();
						//
						return true;
					}
					else
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
				}
				else if (args[0].equalsIgnoreCase("on") || args[0].equalsIgnoreCase("off"))
				{
					if (p.hasPermission("Pattern.On.Off") || p.isOp())
					{
						MySQL sql = PatternPro.getInstance().getMySQL();
						//
						if (args[0].equalsIgnoreCase("on"))
						{
							//Enable Pattern to the Sender
							sql.setUse(p, true);
							p.sendMessage(""+Idioma.PLAYER_ENABLE_MESSAGE.toString());
						}
						else
						{
							//Disable Pattern to the Sender
							sql.setUse(p, false);
							p.sendMessage(""+Idioma.PLAYER_DISABLE_MESSAGE.toString());
						}
						//
						return true;
					}
					else
						sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
				}
				else if (args[0].equalsIgnoreCase("test"))
				{
					if (p.hasPermission("Pattern.TestMenu.Open") || p.isOp())
					{
						PatternMenu menu = PatternCommand.getPatternMenu(p);
						menu.setOnTest(true);
						menu.setOpenOnMove(false);
						menu.setOnRecover(false);
						menu.Open();
					}
					//
					return true;
				}
				else if (args[0].equalsIgnoreCase("help"))
				{
					p.sendMessage(ChatColor.GREEN + "Pattern Commands:");// + ChatColor.WHITE + ChatColor.BOLD.toString() + "v"+PatternPro.getInstance().getDescription().getVersion());
					//
					p.sendMessage(ChatColor.GOLD + "- /Pattern manager " + ChatColor.GREEN + "= Opens the Pattern creator or editor Menu.");
					p.sendMessage(ChatColor.GOLD + "- /Pattern on/off " + ChatColor.GREEN + "= Enable or disable the Pattern.");
					p.sendMessage(ChatColor.GOLD + "- /Pattern test " + ChatColor.GREEN + "= Opens the Pattern test Menu.");
					return true;
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "Error in command syntax. Check command: /Pattern help");
					return true;
				}
			}
			else
				p.performCommand("Pattern help");
		}
		else
			sender.sendMessage(ChatColor.RED + "You must be a Player to use This Command.");
		//
		return true;
	}
	
	public static PatternMenu getPatternMenu(final Player p)
	{
		if (p == null || p.getUniqueId() == null)
			return null;
		//
		if (!PatternPro.getInstance().getMySQL().hasPattern(p, true))
			return null;
		//
		if (!menus.containsKey(p.getUniqueId()) || menus.get(p.getUniqueId()) == null)
			menus.put(p.getUniqueId(), new PatternMenu(p, PatternPro.getInstance().getMySQL().getPattern(p, true)));
		//
		return menus.get(p.getUniqueId());
	}
	
	public static SetPatternMenu getPatternMenuCreator(final Player p)
	{
		if (p == null || p.getUniqueId() == null)
			return null;
		//
		if (!setMenus.containsKey(p.getUniqueId()) || setMenus.get(p.getUniqueId()) == null)
			setMenus.put(p.getUniqueId(), new SetPatternMenu(p));
		//
		return setMenus.get(p.getUniqueId());
	}
}
