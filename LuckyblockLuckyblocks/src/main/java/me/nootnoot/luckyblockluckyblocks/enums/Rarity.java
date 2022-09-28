package me.nootnoot.luckyblockluckyblocks.enums;

import java.util.Locale;

public enum Rarity {
	COMMON,
	UNCOMMON,
	RARE;

	public static Rarity get(String s){
		switch(s.toLowerCase(Locale.ROOT)){
			case "common" -> {return COMMON;}
			case "uncommon" -> {return UNCOMMON;}
			case "rare" -> {return RARE;}
		}
		return null;
	}
}
