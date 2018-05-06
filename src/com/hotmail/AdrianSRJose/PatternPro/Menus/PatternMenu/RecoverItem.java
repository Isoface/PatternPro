package com.hotmail.AdrianSRJose.PatternPro.Menus.PatternMenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import com.hotmail.AdrianSRJose.PatternPro.Configuration.MenuConfigLoader;
import com.hotmail.AdrianSRJose.PatternPro.Configuration.PadColor;
import com.hotmail.AdrianSRJose.PatternPro.Main.Idioma;
import com.hotmail.AdrianSRJose.PatternPro.Main.PatternCommand;
import com.hotmail.AdrianSRJose.PatternPro.Main.PatternPlayer;
import com.hotmail.AdrianSRJose.PatternPro.Main.PatternPro;
import com.hotmail.AdrianSRJose.PatternPro.Main.Sync;
import com.hotmail.AdrianSRJose.PatternPro.Menus.ItemClickEvent;
import com.hotmail.AdrianSRJose.PatternPro.Menus.MenuItem;

public class RecoverItem extends MenuItem
{
	private int attemps = 0;
	private final Player owner;
	public RecoverItem(final String displayName, final PadColor color, final Player owner) 
	{
		super(""+displayName, new ItemStack(Material.STAINED_GLASS_PANE, 1, color.shortValue()));
		//
		this.owner = owner;
	}
	
	@Override
	public void onItemClick(ItemClickEvent event)
	{
		final Player p = event.getPlayer();
		//
		if (p == null || p.getUniqueId() == null)
			return;
		//
		if (PatternPro.getInstance().getMySQL().getPassword(p, true) == null)
		{
			p.sendMessage(""+Idioma.YOU_DONT_HAVE_A_SECURITY_PIN.toString());
			return;
		}
		//
		final PatternPlayer pp = PatternPlayer.getPlayer(p);
		if (pp != null && p != null && owner != null && owner.getUniqueId().equals(p.getUniqueId()))
		{
			final PatternMenu menu = PatternCommand.getPatternMenu(p);
			//
			if (menu.isOnTest())
				return;
			//
			menu.setOpenOnMove(false);
			menu.setOnRecover(true);
			//
			SingleQuestionPrompt.newPrompt(p, ""+Idioma.RECOVER_ENTER_YOUR_SECURITY_PIN.toString(), new AcceptAnswer()
			{
				@Override
				public boolean onAnswer(String input) 
				{
					if (input != null && !input.isEmpty())
					{
						if (attemps >= MenuConfigLoader.MaxRecoverAttemps)
						{
							attemps = 0;
							p.kickPlayer(""+Idioma.RECOVER_TOO_ATTEMPS_KICKMESSAGE.toString());
							return true;
						}
						else
						{
							String pass = input.trim();
							if (pass.startsWith("/"))
								pass = pass.substring(1);
							//
							if (PatternPro.getInstance().getMySQL().checkRecoverPassword(p, pass))
							{
								p.sendMessage(""+Idioma.RECOVER_CORRECT_PIN.toString());
								pp.setData("key-puted-patternt-key", null);
								//
								Sync.log(p);
								//
								p.removePotionEffect(PotionEffectType.BLINDNESS);
								p.removePotionEffect(PotionEffectType.SLOW);
								p.removePotionEffect(PotionEffectType.JUMP);
								//
								SetPatternMenu sp = PatternCommand.getPatternMenuCreator(p);
								sp.Open();
								//
								return true;
							}
							else
							{
								p.sendMessage(""+Idioma.RECOVER_INCORRECT_PIN.toString());
								attemps += 1;
							}
						}
					}
					//
					return false;
				}
			});
			//
			event.setWillClose(true);
		}
	}
}
