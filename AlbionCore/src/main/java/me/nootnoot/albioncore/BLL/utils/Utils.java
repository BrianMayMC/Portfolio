package me.nootnoot.albioncore.BLL.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;

public class Utils {

	public static String c(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public static ArrayList<String> list(ArrayList<String> list){
		ArrayList<String> newList = new ArrayList<>();
		for(String string : list){
			newList.add(ChatColor.translateAlternateColorCodes('&', string));
		}
		return newList;
	}
}
