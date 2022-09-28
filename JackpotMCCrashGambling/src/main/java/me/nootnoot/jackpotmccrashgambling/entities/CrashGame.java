package me.nootnoot.jackpotmccrashgambling.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class CrashGame {

	private final UUID player;
	@Setter private double tempIncrease = 1;
	@Setter private double bet;
	@Setter private Result result;
	@Setter private double chosenIncrease = 0;
	@Setter private boolean pulledOut = false;

	public CrashGame(UUID player, double bet){
		this.player = player;
		this.bet = bet;
	}


}
