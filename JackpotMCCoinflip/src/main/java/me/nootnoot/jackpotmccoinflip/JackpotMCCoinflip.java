package me.nootnoot.jackpotmccoinflip;

import lombok.Getter;
import me.nootnoot.framework.basesystem.BasePlugin;
import me.nootnoot.framework.registrysystem.Initializer;
import me.nootnoot.jackpotmccoinflip.commands.CoinflipCommand;
import me.nootnoot.jackpotmccoinflip.files.MainMenuConfig;
import me.nootnoot.jackpotmccoinflip.managers.CoinflipManager;
import me.nootnoot.jackpotmccoinflip.storage.CoinflipStorage;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class JackpotMCCoinflip extends BasePlugin {

	@Getter private static JackpotMCCoinflip instance;
	private CoinflipStorage coinflipStorage;
	@Getter private MainMenuConfig mainMenuConfig;
	@Getter private CoinflipManager coinflipManager;
	@Getter private Economy econ;
	@Override
	public void onEnable() {
		// Plugin startup logic
		super.onEnable();
		instance = this;

		mainMenuConfig = new MainMenuConfig();
		coinflipStorage = new CoinflipStorage();
		coinflipManager = new CoinflipManager(coinflipStorage);
		if (!setupEconomy() ) {
			Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
		}

		getCommand("coinflip").setExecutor(new CoinflipCommand());
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		coinflipStorage.save();
	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return true;
	}
}
