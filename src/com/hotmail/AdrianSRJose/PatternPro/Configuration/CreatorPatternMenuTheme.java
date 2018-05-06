package com.hotmail.AdrianSRJose.PatternPro.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.hotmail.AdrianSRJose.PatternPro.Menus.ItemMenu.Size;
import com.hotmail.AdrianSRJose.PatternPro.Util.Util;

public class CreatorPatternMenuTheme extends MenuTheme {
	private final static List<ItemConfig> items = new ArrayList<ItemConfig>();

	//
	public CreatorPatternMenuTheme() {
		super(items, Size.SIX_LINE.getSize(), "CreatorPatternMenuTheme", Util.wc("&4&lPattern Creator"));
	}

	//
	@Override
	public List<ItemConfig> getItems() {
		List<ItemConfig> items = new ArrayList<ItemConfig>();
		//
		Integer[] slots = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 5,
				24, 25, 28, 29, 33, 34, 37, 38, 42, 43, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53 };
		ItemConfig[] its = new ItemConfig[54];
		for (int x = 0; x < its.length; x++)
			if (Arrays.asList(slots).contains(Integer.valueOf(x)))
				its[x] = new ItemConfig(x, ".", new ItemStack(Material.STAINED_GLASS_PANE, 1),
						(setBlack(x) ? PadColor.CYAN.shortValue() : PadColor.BLUE.shortValue()),
						Arrays.asList(new String[] {}));
		//
		for (int x = 0; x < its.length; x++) {
			ItemConfig ii = its[x];
			if (ii != null)
				items.add(ii);
		}
		//
		return items;
	}

	//
	private static boolean setBlack(int i) {
		boolean tor = false;
		//
		switch (i) {
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
}
