package me.nootnoot.luckyblockairdrops.entities;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.luckyblockairdrops.enums.Rarity;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.dynmap.markers.AreaMarker;

import java.util.List;

public class Airdrop {

	@Getter
	private final Rarity rarity;
	@Getter@Setter
	private Location location;
	@Getter
	private final List<ItemStack> items;
	@Getter@Setter
	private Entity armorstand;
	@Getter@Setter
	private AreaMarker marker;

	public Airdrop(Rarity rarity, Location location, List<ItemStack> items, Entity armorstand) {
		this.rarity = rarity;
		this.location = location;
		this.items = items;
		this.armorstand = armorstand;
	}
}
