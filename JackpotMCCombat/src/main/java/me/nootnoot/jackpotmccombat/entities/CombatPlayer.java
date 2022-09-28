package me.nootnoot.jackpotmccombat.entities;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public class CombatPlayer {

	@Getter
	private final Player p;
	@Getter@Setter
	private long cooldown;

	public CombatPlayer(Player p, long cooldown) {
		this.p = p;
		this.cooldown = cooldown;
	}
}
