package me.nootnoot.jackpotmcitems.config;

import me.nootnoot.framework.utils.FileCreator;
import me.nootnoot.jackpotmcitems.JackpotMCItems;

public class BlackMarketMenuConfig extends FileCreator {
	public BlackMarketMenuConfig() {
		super("/menus", "blackmarketmenu", JackpotMCItems.getInstance());
	}
}
