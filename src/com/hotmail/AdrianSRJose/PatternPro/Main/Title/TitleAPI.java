package com.hotmail.AdrianSRJose.PatternPro.Main.Title;

import org.bukkit.entity.Player;

public abstract interface TitleAPI 
{
	public abstract void sendTitleToPlayer(Player paramPlayer, String paramTitle, String paramSubtitle, int paramFadeIn, int paramStay, int paramFadeOut);
}
