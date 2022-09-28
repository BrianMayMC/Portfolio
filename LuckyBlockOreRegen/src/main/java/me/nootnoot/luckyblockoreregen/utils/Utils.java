package me.nootnoot.luckyblockoreregen.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static String c(String s){
		return ChatColor.translateAlternateColorCodes('&', getHex(s));
	}

	private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

	public static String getHex(String msg) {
		Matcher matcher = pattern.matcher(msg);
		while (matcher.find()) {
			String color = msg.substring(matcher.start(), matcher.end());
			msg = msg.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
			matcher = pattern.matcher(msg);
		}
		return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg);
	}

	public static void cL(Player p, List<String> s){
		for(String string : s){
			p.sendMessage(c(string));
		}
	}

}
