package me.nootnoot.luckyblockluckyblocks.utils;

import me.nootnoot.luckyblockluckyblocks.LuckyblockLuckyblocks;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;

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

	public static NamespacedKey key = new NamespacedKey(LuckyblockLuckyblocks.getInstance(), "luckyblock");
}
