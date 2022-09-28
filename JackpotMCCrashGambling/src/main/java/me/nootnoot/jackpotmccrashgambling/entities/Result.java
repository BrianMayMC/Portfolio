package me.nootnoot.jackpotmccrashgambling.entities;

public enum Result {
	WINNER,
	LOSER;

	public static String getNiceName(Result result){
		if(result == WINNER){
			return "&aWon";
		}else{
			return "&cLost";
		}
	}
}
