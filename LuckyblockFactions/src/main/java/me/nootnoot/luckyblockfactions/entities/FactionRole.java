package me.nootnoot.luckyblockfactions.entities;

import java.util.Locale;

public enum FactionRole {
	MEMBER,
	OFFICER,
	CO_LEADER,
	LEADER;

	public static FactionRole get(String name){
		switch(name.toLowerCase(Locale.ROOT)){
			case "member" -> {return MEMBER;}
			case "officer" -> {return OFFICER;}
			case "coleader" -> {return CO_LEADER;}
			default -> {return LEADER;}
		}
	}

	public static String getName(FactionRole role){
		switch(role){
			case MEMBER -> {return "Member";}
			case OFFICER -> {return "Officer";}
			case CO_LEADER -> {return "Co-Leader";}
			default -> {return "Leader";}
		}
	}
}
