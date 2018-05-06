package com.hotmail.AdrianSRJose.PatternPro.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.hotmail.AdrianSRJose.PatternPro.Util.Util;

public class PCMConfig
{
	private static YamlConfiguration mainConfig = null;
	private static File mainConfigFile = null;

	//
	public static void load(Plugin p)
	{
		mainConfigFile = new File(p.getDataFolder().getAbsolutePath());
		if(!mainConfigFile.exists() || !mainConfigFile.isDirectory())
			mainConfigFile.mkdir();
		mainConfigFile = new File(p.getDataFolder().getAbsolutePath(), "PatternCreatorMenu.yml");
		try
		{
			if(!mainConfigFile.exists())
				mainConfigFile.createNewFile();
			mainConfig = YamlConfiguration.loadConfiguration(mainConfigFile);
			//
			int save = 0;
			//
			save += Util.createSectionIfNoExitsInt(mainConfig, "PatternCreatorMenu");
			ConfigurationSection menu = mainConfig.getConfigurationSection("PatternCreatorMenu");
			//
			//
			save += Util.createSectionIfNoExitsInt(menu, "PatternCreatorPad");
			ConfigurationSection pad = Util.createSectionIfNoExits(menu, "PatternCreatorPad");
			save += sds(pad, "Color", PadColor.WITHE.toString());
			save += sds(pad, "ClickColor", PadColor.GREEN.toString());
			//
			//
			save += Util.createSectionIfNoExitsInt(pad, "PadItems");
			ConfigurationSection padItems = Util.createSectionIfNoExits(pad, "PadItems");
			Integer[] padSlots = new Integer[]{21, 22, 23, 30, 31, 32, 39, 40, 41};
			for (int i = 0; i < padSlots.length; i++)
			{
				PadItem newPad = new PadItem(i, padSlots[i]);
				save += newPad.save(Util.createSectionIfNoExits(padItems, ""+i));
			}
			//
			save += sds(menu, "DefaultTheme", "CreatorPatternMenuTheme");
			//
			save += Util.createSectionIfNoExitsInt(menu, "Themes");
			ConfigurationSection themes = menu.getConfigurationSection("Themes");
			int setItems = Util.createSectionIfNoExitsInt(themes, "CreatorPatternMenuTheme");
			if (setItems > 0)
			{
				save += 1;
				//
				ConfigurationSection NormalTheme = themes.getConfigurationSection("CreatorPatternMenuTheme");
				//
				save += sds(NormalTheme, "Name", "&4&lPattern Creator");
				/////////////////////////////////////////////////////////////////////////////////////////////
				/////////////////////////////////////////////////////////////////////////////////////////////
				ConfigurationSection NormalThemeItems = Util.createSectionIfNoExits(NormalTheme, "Items");
				//
				Integer[] slots = new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,5,24,25,28,29,33,34,37,38,42,43,26,27,35,36,44,45,46,47,48,49,50,51,52,53};
				ItemConfig[] its = new ItemConfig[54];
				for (int x = 0; x < its.length ; x++)
					if (Arrays.asList(slots).contains(Integer.valueOf(x)))
						its[x] = new ItemConfig(x, ".", new ItemStack(Material.STAINED_GLASS_PANE, 1), (setBlack(x) ? PadColor.CYAN.shortValue() : PadColor.BLUE.shortValue()), Arrays.asList(new String[]{}));
				//
				for (int x = 0; x < its.length; x++)
				{
					ItemConfig i = its[x];
					if (i != null)
					{
						i.save(NormalThemeItems.createSection(""+x));
					}
				}
			}
			///////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////
			//
			if(save > 0)
				saveConfig();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static boolean setBlack(int i)
	{
		boolean tor = false;
		//
		switch(i)
		{
			case 1:
			case 9:
			case 3:
			case 11:
			case 19:
			case 27:
			case 5:
			case 13:
			case 29:
			case 37:
			case 45:
			case 7:
			case 15:
			case 47:
			case 17:
			case 25:
			case 33:
			case 35:
			case 43:
			case 51:
			case 53:
				tor = true;
		}
		//
		return tor;
	}
	
	/* [ 0] [ 1] [ 2] [ 3] [ 4] [ 5] [ 6] [ 7]  [8]
	 * 
	 * [ 9] [10] [11] [12] [13] [14] [15] [16] [17]
	 * 
	 * [18] [19] [20] [21] [22] [23] [24] [25] [26]
	 * 
	 * [27] [28] [29] [30] [31] [32] [33] [34] [35]
	 * 
	 * [36] [37] [38] [39] [40] [41] [42] [43] [44]
	 * 
	 * [45] [46] [47] [48] [49] [50] [51] [52] [53]
	 */
	
	public static int sds(ConfigurationSection section, String path, Object str)
	{
		if(section != null)
		{
			if(!section.isSet(path))
			{
				if (str != null)
				{
					if (str instanceof String)
						section.set(path, (String)str);
					else
						section.set(path, str);
					
					return 1;
				}
			}
		}
		return 0;
	}
	
	public static YamlConfiguration getConfig()
	{
		return mainConfig;
	}
	
	public static void saveConfig()
	{
		if(mainConfig != null)
		{
			try
			{
				mainConfig.save(mainConfigFile);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
