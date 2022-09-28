package me.nootnoot.jackpotmccoinflip.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class Coinflip {

	private final UUID owner;
	@Setter private UUID player;
	private final double bet;
	@Setter private boolean openOwner = false;
	@Setter private boolean openPlayer = false;

	public Coinflip(UUID owner, double bet){
		this.owner = owner;
		this.bet = bet;
	}

}
