package me.nootnoot.luckyblockluckyblocks.entities;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;

public class LuckyblockItem {

	@Getter
	private final ItemStack item;
	@Getter
	private final double chance;

	public LuckyblockItem(ItemStack item, double chance) {
		this.item = item;
		this.chance = chance;
	}
}
