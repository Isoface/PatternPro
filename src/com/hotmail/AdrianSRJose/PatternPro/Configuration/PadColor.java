package com.hotmail.AdrianSRJose.PatternPro.Configuration;

public enum PadColor
{
	WITHE((short)0),
	ORANGE((short)1),
	MAGENTA((short)2), 
	BLUE((short)11), 
	YELLOW((short)4), 
	GREEN((short)5),
	BLACK((short)15), 
	RED((short)14), 
	PINK((short)6),
	CYAN((short)9);
	
	private final short data;
	PadColor(short data)
	{
		this.data = data;
	}
	
	public short shortValue()
	{
		return data;
	}
}
