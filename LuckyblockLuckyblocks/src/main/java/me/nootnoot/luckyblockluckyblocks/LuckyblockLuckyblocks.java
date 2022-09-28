package me.nootnoot.luckyblockluckyblocks;

import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import me.nootnoot.luckyblockluckyblocks.commands.LuckyblockCommand;
import me.nootnoot.luckyblockluckyblocks.entities.Luckyblock;
import me.nootnoot.luckyblockluckyblocks.listeners.LuckyblockMine;
import me.nootnoot.luckyblockluckyblocks.managers.LuckyblockManager;
import me.nootnoot.luckyblockluckyblocks.storage.LuckyblockStorage;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;


public final class LuckyblockLuckyblocks extends JavaPlugin {

	@Getter
	private static LuckyblockLuckyblocks instance;

	@Getter
	private LuckyblockManager manager;

	@Override
	public void onEnable() {
		// Plugin startup logic
		instance = this;
		setupFiles();
		setupManagers();
		setupCommands();
		setupListeners();
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		for(Luckyblock l : getManager().getLuckyblocks()){
			l.getHologram().remove();
			l.getLocation().getBlock().setType(Material.AIR);
		}
	}

	private void setupCommands(){
		getCommand("luckyblock").setExecutor(new LuckyblockCommand());
	}

	private void setupListeners(){
		getServer().getPluginManager().registerEvents(new LuckyblockMine(), this);
	}

	private void setupManagers(){
		manager = new LuckyblockManager(new LuckyblockStorage());
	}

	private void setupFiles(){
		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}
}
