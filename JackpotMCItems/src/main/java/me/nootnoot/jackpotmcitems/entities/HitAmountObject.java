package me.nootnoot.jackpotmcitems.entities;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public class HitAmountObject {

	@Getter
	private final Player target;
	@Getter@Setter
	private int hits;

	public HitAmountObject(Player target) {
		this.target = target;
		hits = 0;
	}
}
