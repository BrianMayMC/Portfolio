package me.nootnoot.jackpotmccrashgambling;

import lombok.Getter;
import me.nootnoot.framework.basesystem.BasePlugin;
import me.nootnoot.jackpotmccrashgambling.commands.CrashCommand;
import me.nootnoot.jackpotmccrashgambling.files.MainMenuConfig;
import me.nootnoot.jackpotmccrashgambling.managers.CrashManager;
import me.nootnoot.jackpotmccrashgambling.storage.CrashStorage;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class JackpotMCCrashGambling extends BasePlugin {

	@Getter
	private static JackpotMCCrashGambling instance;

	@Getter private MainMenuConfig mainMenuConfig;

	@Getter
	private CrashManager crashManager;
	private CrashStorage crashStorage;

	@Getter
	private Economy econ;

	@Override
	public void onEnable() {
		// Plugin startup logic
		super.onEnable();
		instance = this;
		if (!setupEconomy() ) {
			Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		mainMenuConfig = new MainMenuConfig();
		crashStorage = new CrashStorage();
		crashManager = new CrashManager(crashStorage);
		getCommand("crashgamble").setExecutor(new CrashCommand());
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		crashStorage.saveGames();
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
		return econ != null;
	}
}
