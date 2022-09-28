package me.nootnoot.luckyblockchestrefill.entities;

import lombok.Getter;

public class RefillChestItem {

	@Getter
	private final SimpleItem item;
	@Getter
	private final String type;
	@Getter
	private final double chance;

	public RefillChestItem(SimpleItem item, String type, double chance) {
		this.item = item;
		this.type = type;
		this.chance = chance;
	}
}
