package com.hotmail.AdrianSRJose.PatternPro.Main;

import org.bukkit.Bukkit;

public class VersionUtils
{
    public static String getVersion()
    {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf(".") + 1);
    }
    
    public static boolean isNewSpigotVersion()
	{
    	String ver = getVersion();
    	if (ver.contains("v1_7") || ver.contains("v1_8"))
    		return false;
    	return true;
    	
    	/*for (int x = 1; x <= 3; x++)
    	{
    		String ver = getVersion().replace("v1_", "").replace("_R"+x, "");
        	int v = Integer.parseInt(ver);
        	if (v > 8)
        		return true;
    	}*/
	}
    
    public static boolean is1_7()
    {
    	if (getVersion().contains("v1_7"))
    		return true;
    	return false;
    }
    
    public static boolean is1_8()
    {
    	if (getVersion().contains("v1_8"))
    		return true;
    	return false;
    }
    
    public static boolean is1_9()
    {
    	if (getVersion().contains("v1_9"))
    		return true;
    	return false;
    }
    
    public static boolean is1_10()
    {
    	if (getVersion().contains("v1_10"))
    		return true;
    	return false;
    }
    
    public static boolean is1_11()
    {
    	if (getVersion().contains("v1_11"))
    		return true;
    	return false;
    }
    
    public static boolean is1_12()
    {
    	if (getVersion().contains("v1_12"))
    		return true;
    	return false;
    }
    
    public int getIntVersion()
    {
    	String ver = getVersion();
    	for (int x = 0; x <= 4; x++)
    		ver = ver.substring(3).replace("_R"+x, "");
    	return Integer.parseInt(ver);
    }
    
    /*public enum McVersion
    {
    	v1_7_R3,
    	v1_7_R4,
    	v1_8_R1,
    	v1_8_R2,
    	v1_8_R3,
    	v1_9_R1,
    	v1_9_R2,
    	v1_10_R1,
    	v1_11_R1,
    	v1_12_R1,
    }*/
}
