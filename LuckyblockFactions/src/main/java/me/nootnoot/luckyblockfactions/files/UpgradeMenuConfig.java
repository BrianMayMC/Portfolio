package me.nootnoot.luckyblockfactions.files;

import me.nootnoot.framework.utils.FileCreator;
import me.nootnoot.luckyblockfactions.LuckyblockFactions;

public class UpgradeMenuConfig extends FileCreator {
	public UpgradeMenuConfig() {
		super("/menus", "upgrademenu", LuckyblockFactions.getInstance());
	}
}
