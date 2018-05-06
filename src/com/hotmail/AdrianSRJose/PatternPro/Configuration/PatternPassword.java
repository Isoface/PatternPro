package com.hotmail.AdrianSRJose.PatternPro.Configuration;

import java.util.UUID;

public class PatternPassword 
{
	private final UUID ownerID;
	private final Integer[] pass;
	//
	public PatternPassword(final UUID owner, final Integer[] pass) 
	{
		this.ownerID = owner;
		this.pass = pass;
	}
	
	public Integer[] getPassword()
	{
		return pass;
	}
	
	public UUID getOwnerID()
	{
		return ownerID;
	}

}
