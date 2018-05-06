package com.hotmail.AdrianSRJose.PatternPro.Main.Title.versions.v1_8_R1;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.hotmail.AdrianSRJose.PatternPro.Main.Title.TitleAPI;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PlayerConnection;

public class title implements TitleAPI
{
	@Override
	public void sendTitleToPlayer(Player paramPlayer, String paramTitle, String paramSubtitle, int paramFadeIn, int paramStay, int paramFadeOut) 
	{
		PlayerConnection plcn = ((CraftPlayer)paramPlayer).getHandle().playerConnection;

		PacketPlayOutTitle ppot = new PacketPlayOutTitle(EnumTitleAction.TIMES, null ,paramFadeIn, paramStay, paramFadeOut);
		plcn.sendPacket(ppot);
		
		String t = paramTitle;
		String s = paramSubtitle;
		
		IChatBaseComponent title = ChatSerializer.a("{\"text\": \"" + t + "\"}");
		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, title);
		plcn.sendPacket(titlePacket);
		
		IChatBaseComponent subtitle = ChatSerializer.a("{\"text\": \"" + s + "\"}");
		PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, subtitle);
		plcn.sendPacket(subtitlePacket);
	}
}
