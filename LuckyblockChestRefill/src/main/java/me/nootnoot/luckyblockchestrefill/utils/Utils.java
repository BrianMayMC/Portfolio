package me.nootnoot.luckyblockchestrefill.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static String c(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public static List<String> cL(List<String> strings){
		List<String> newList = new ArrayList<>();
		for(String s : strings){
			newList.add(c(s));
		}
		return newList;
	}
}
