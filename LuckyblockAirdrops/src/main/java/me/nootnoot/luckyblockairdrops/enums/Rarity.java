package me.nootnoot.luckyblockairdrops.enums;

import me.nootnoot.luckyblockairdrops.LuckyblockAirdrops;

import java.util.concurrent.ThreadLocalRandom;

public enum Rarity {
	COMMON,
	UNCOMMON,
	RARE;

	public String getName(Rarity rarity){
		return switch (rarity) {
			case COMMON -> LuckyblockAirdrops.getInstance().getConfig().getString("airdrops.name.common");
			case UNCOMMON -> LuckyblockAirdrops.getInstance().getConfig().getString("airdrops.name.uncommon");
			case RARE -> LuckyblockAirdrops.getInstance().getConfig().getString("airdrops.name.rare");
		};
	}


}
