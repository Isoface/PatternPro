package com.hotmail.AdrianSRJose.PatternPro.Main.Title.versions.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.hotmail.AdrianSRJose.PatternPro.Main.Title.TitleAPI;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class title implements TitleAPI
{
	@Override
	public void sendTitleToPlayer(Player paramPlayer, String paramTitle, String paramSubtitle, int paramFadeIn, int paramStay, int paramFadeOut) 
	{
		PlayerConnection plcn = ((CraftPlayer)paramPlayer).getHandle().playerConnection;

		PacketPlayOutTitle ppot = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null ,paramFadeIn, paramStay, paramFadeOut);
		plcn.sendPacket(ppot);
		
		String t = paramTitle;
		String s = paramSubtitle;
		
		IChatBaseComponent title = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + t + "\"}");
		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, title);
		plcn.sendPacket(titlePacket);
		
		IChatBaseComponent subtitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + s + "\"}");
		PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitle);
		plcn.sendPacket(subtitlePacket);
	}
}
