package com.hotmail.AdrianSRJose.PatternPro.Main;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import com.hotmail.AdrianSRJose.PatternPro.Util.Util;

public enum Idioma 
{
	//{#} will be replaced with the a number
	//{W} will be replaced with a word when needed
	//{S} will be the line separator
	// Modelo : (Aqui El Nombre)("", ChatColor.GOLD+(ChatColor.BOLD+"")),
	
	PLACE_PATTERN_JOIN_MESSAGE("Place-Pattern-Message", ChatColor.GREEN.toString()+ChatColor.BOLD+"Place your pattern."),
	ICORRECT_PATTERN("Incorrect-Pattern-Message", ChatColor.RED.toString()+ChatColor.BOLD+"Incorrect Pattern!"),
	PATTERN_SUCCES("Pattern-Succes-Message", ChatColor.GREEN.toString()+ChatColor.BOLD+"Pattern Succes!"),
	TOO_ATTEPS("Too-Attemps", ChatColor.RED + ChatColor.BOLD.toString() + "Exceeded the number of permitted attempts."),
	//
	//
	PC_FEW_SELECTED_POINTS("Very-few-selected-points-Message", ChatColor.RED + ChatColor.BOLD.toString() + "The pattern has to be more than 4 points!"),
	PC_PATTERN_CREATED("Pattert-Created-Message", ChatColor.GREEN + ChatColor.BOLD.toString() + "Pattern Created!"),
	//
	//
	YOU_DONT_HAVE_A_SECURITY_PIN("You-dont-have-a-security-PIN-Message", ChatColor.RED + ChatColor.BOLD.toString() + "You need a Security PIN to use this button."),
	//
	CREATING_PIN_ENTER_YOUR_SECURITY_PIN("Enter-a-security-PIN", ChatColor.GREEN + "Enter your new Security " + ChatColor.GOLD + "PIN."),
	CREATING_PIN_INVALID("This-is-an-invalid-Security-PIN", ChatColor.GREEN + ChatColor.BOLD.toString() + ChatColor.RED + ChatColor.BOLD.toString() + "This is an invalid PIN, try again."),
	CREATING_PIN_SHORT_PIN("This-is-a-very-short-PIN", ChatColor.RED + ChatColor.BOLD.toString() + "This is a very short PIN. Your PIN must be between 5 and 40 characters, try again."),
	CREATING_PIN_LONG_PIN("This-is-a-very-long-PIN", ChatColor.RED + ChatColor.BOLD.toString() + "This is a very long PIN. Your PIN must be between 5 and 40 characters, try again."),
	CREATING_PIN_SUCESS("Your-security-PIN-is", ChatColor.GREEN + ChatColor.BOLD.toString() + "Your new security PIN is "+ChatColor.GOLD+ChatColor.BOLD.toString() + "{W}."),
	//
	//
	PLAYER_DISABLE_MESSAGE("Player-Disable-his-Pattern-Message", ChatColor.RED + ChatColor.BOLD.toString() + "Your Pattern has been disabled!"),
	PLAYER_ENABLE_MESSAGE("Player-Enable-his-Pattern-Message", ChatColor.GREEN + ChatColor.BOLD.toString() + "Your Pattern has been enabled!"),
	//
	//
	RECOVER_ENTER_YOUR_SECURITY_PIN("PatternRecover-Enter-Security-PIN-Message", ChatColor.YELLOW + ChatColor.BOLD.toString() + "Enter your Security PIN."),
	RECOVER_CORRECT_PIN("PatternRecover-Correct-Users-PIN-Message", ChatColor.GREEN + ChatColor.BOLD.toString() +"Correct PIN, enter your new " + ChatColor.GOLD + ChatColor.BOLD.toString() + "Pattern"),
	RECOVER_INCORRECT_PIN("PatternRecover-Incorrect-PIN-Message", ChatColor.RED + ChatColor.BOLD.toString() + "Incorrect PIN"),
	RECOVER_TOO_ATTEMPS_KICKMESSAGE("PatternRecover-Too-Attems-KickMessage", ChatColor.RED+ChatColor.BOLD.toString()+"Exceeded the number of permitted attempts."),
	//
	//
	TIME_OUT_MESSGE("TimeOut-Message", ChatColor.RED+ChatColor.BOLD.toString()+"Login timeout exceeded, you have been kicked from the server, please try again!");
	
 
    private String path;
    private String def;
    private static YamlConfiguration LANG;
 
	/**
	 * Lang enum constructor.
	 * 
	 * @param path
	 *            The string path.
	 * @param start
	 *            The default string.
	 */
	Idioma(String path, String start)
	{
		this.path = path;
		def = start;
	}

	/**
	 * Set the {@code YamlConfiguration} to use.
	 * 
	 * @param config
	 *            The config to set.
	 */
	public static void setFile(YamlConfiguration config)
	{
		LANG = config;
	}

	@Override
	public String toString()
	{
		final String gg = LANG.getString(path, def);
		return Util.wc(gg != null ? gg : "" + def);
	}
	
	/*public String toShortenString(int characters)
	{
		final String string = toString();
		//
		if(string.length() <= characters)
			return string;
		//
		return string.substring(0, characters);
	}*/
	
	public String toStringReplaceAll(String oldValue, String newValue)
	{
		return toString().replaceAll(oldValue, newValue);
	}
	
	public String toStringReplacement(String word)
	{
		return replaceWord(toString(), word);
	}
	
	public String toStringReplacement(int number)
	{
		return replaceNumber(toString(),number);
	}
	
	public String toStringReplacement(int number, String word)
	{
		return replaceWord(replaceNumber(toString(),number),word);
	}
	
	private String replaceWord(String string, String word)
	{
		return string.replace("{W}", word);
	}
	
	private String replaceNumber(String string, int number)
	{
		return string.replace("{#}", ""+number);
	}
	
	public String[] toStringArray()
	{
		String s = toString();
		//
		if(s.contains("{S}"))
			return s.split("{S}");
		else return new String[] {s};
	}
	
	public String[] toStringArray(int number)
	{
		String s = this.toStringReplacement(number);
		if(s.contains("{S}"))
			return s.split("{S}");
		else return new String[] {s};
	}
	
	public String[] toStringArray(int number, String word)
	{
		String s = this.toStringReplacement(number, word);
		if(s.contains("{S}"))
			return s.split("{S}");
		else return new String[] {s};
	}
	
	public String[] toStringArray(String word)
	{
		String s = this.toStringReplacement(word);
		if(s.contains("{S}"))
			return s.split("{S}");
		else return new String[] {s};
	}

	/**
	 * Get the default patch value.
	 * 
	 * @return The default patch value.
	 */
	public String getDefault()
	{
		return def == null ? def : def.replaceAll("\\xa7", "&");
	}

	/**
	 * Get the String path.
	 * 
	 * @return The String patch.
	 */
	public String getPath()
	{
		return path;
	}

}
