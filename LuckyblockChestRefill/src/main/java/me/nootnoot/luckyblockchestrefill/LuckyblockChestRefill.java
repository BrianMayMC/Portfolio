package me.nootnoot.luckyblockchestrefill;

import lombok.Getter;
import me.nootnoot.luckyblockchestrefill.commands.RefillChestItemsCommand;
import me.nootnoot.luckyblockchestrefill.listeners.BlockPlace;
import me.nootnoot.luckyblockchestrefill.manager.ChestManager;
import me.nootnoot.luckyblockchestrefill.storage.ChestStorage;
import me.nootnoot.luckyblockchestrefill.tasks.RespawnTask;
import org.bukkit.plugin.java.JavaPlugin;

public final class LuckyblockChestRefill extends JavaPlugin {

	@Getter
	private static LuckyblockChestRefill instance;
	@Getter
	private ChestManager chestManager;

	private ChestStorage chestStorage;


	@Override
	public void onEnable() {
		// Plugin startup logic
		instance = this;
		chestStorage = new ChestStorage();
		setupFiles();
		setupManagers();
		setupCommands();
		setupListeners();

		new RespawnTask().runTaskTimer(this, getConfig().getInt("respawn-time") * 20L * 60L, getConfig().getInt("respawn-time") * 20L * 60L);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		chestStorage.saveChests();
		chestStorage.saveItems();
	}

	private void setupCommands(){
		getCommand("refillchest").setExecutor(new RefillChestItemsCommand());
	}

	private void setupListeners(){
		getServer().getPluginManager().registerEvents(new BlockPlace(), this);
	}

	private void setupManagers(){
		chestManager = new ChestManager(chestStorage);
	}

	private void setupFiles(){
		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}
}
