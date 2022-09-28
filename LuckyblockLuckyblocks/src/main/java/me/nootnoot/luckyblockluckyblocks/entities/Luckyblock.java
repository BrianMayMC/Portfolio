package me.nootnoot.luckyblockluckyblocks.entities;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.luckyblockluckyblocks.enums.Rarity;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

public class Luckyblock {

	@Getter@Setter
	private Location location;
	@Getter@Setter
	private Entity hologram;
	@Getter@Setter
	private Rarity rarity;

	public Luckyblock(Rarity rarity, Location location) {
		this.rarity = rarity;
		this.location = location;
	}
}
