package me.nootnoot.jackpotmcteams.utils;

import lombok.Getter;
import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import org.bukkit.ChatColor;

import java.util.ArrayList;

public class Utils {

	@Getter
	private static String PREFIX = JackpotMCTeams.getInstance().getConfig().getString("team-prefix");

	@Getter
	private static int teamSize = JackpotMCTeams.getInstance().getConfig().getInt("team-size");


	public static void reload(){
		PREFIX = JackpotMCTeams.getInstance().getConfig().getString("team-prefix");
		teamSize = JackpotMCTeams.getInstance().getConfig().getInt("team-size");
	}
	public static String c(String s){
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public static ArrayList<String> cL(ArrayList<String> s){
		ArrayList<String> newList = new ArrayList<>();
		for(String string : s){
			newList.add(Utils.c(string));
		}
		return newList;
	}

	public static int GetPageNumber(int commandparam){
		return switch (commandparam) {
			case 2 -> 10;
			case 3 -> 20;
			case 4 -> 30;
			case 5 -> 40;
			case 6 -> 50;
			case 7 -> 60;
			case 8 -> 70;
			case 9 -> 80;
			case 10 -> 90;
			case 11 -> 100;
			case 12 -> 110;
			case 13 -> 120;
			case 14 -> 130;
			case 15 -> 140;
			case 16 -> 150;
			case 17 -> 160;
			case 18 -> 170;
			case 19 -> 180;
			case 20 -> 190;
			case 21 -> 200;
			case 22 -> 210;
			case 23 -> 220;
			case 24 -> 230;
			case 25 -> 240;
			default -> 1;
		};
	}

}
