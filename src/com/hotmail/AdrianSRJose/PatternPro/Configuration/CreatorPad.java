package com.hotmail.AdrianSRJose.PatternPro.Configuration;

import java.util.HashMap;
import java.util.Map;

public class CreatorPad extends PatternPad {
	private final static Map<Integer, PadItem> pads = new HashMap<Integer, PadItem>();

	//
	public CreatorPad() {
		super(PadColor.WITHE, PadColor.GREEN, pads);
		setPadItems();
	}

	private void setPadItems() {
		for (int x = 0; x < 9; x++) {
			PadItem p = new PadItem(x, getSlotFormPos(x));
			pads.put(getSlotFormPos(x), p);
		}
	}

	private Integer getSlotFormPos(int x) {
		Integer g = null;
		//
		switch (x) {
		case 0:
			g = Integer.valueOf(21);
			break;
		case 1:
			g = Integer.valueOf(22);
			break;
		case 2:
			g = Integer.valueOf(23);
			break;
		case 3:
			g = Integer.valueOf(30);
			break;
		case 4:
			g = Integer.valueOf(31);
			break;
		case 5:
			g = Integer.valueOf(32);
			break;
		case 6:
			g = Integer.valueOf(39);
			break;
		case 7:
			g = Integer.valueOf(40);
			break;
		case 8:
			g = Integer.valueOf(41);
			break;
		}
		//
		return g;
	}

}
