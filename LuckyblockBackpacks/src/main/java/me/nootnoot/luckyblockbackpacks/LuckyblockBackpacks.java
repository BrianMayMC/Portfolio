package me.nootnoot.luckyblockbackpacks;

import lombok.Getter;
import me.nootnoot.luckyblockbackpacks.commands.BackpackCommand;
import me.nootnoot.luckyblockbackpacks.listeners.*;
import me.nootnoot.luckyblockbackpacks.managers.BackpackManager;
import me.nootnoot.luckyblockbackpacks.storage.BackpackStorage;
import org.bukkit.plugin.java.JavaPlugin;

public final class LuckyblockBackpacks extends JavaPlugin {

	@Getter
	private static LuckyblockBackpacks instance;
	@Getter
	private BackpackManager backpackManager;

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
	}

	private void setupCommands(){
		getCommand("backpack").setExecutor(new BackpackCommand());
	}

	private void setupListeners(){
		getServer().getPluginManager().registerEvents(new BackpackClose(), this);
		getServer().getPluginManager().registerEvents(new BackpackDrop(), this);
		getServer().getPluginManager().registerEvents(new BackpackClick(), this);
		getServer().getPluginManager().registerEvents(new FirstJoin(), this);
		getServer().getPluginManager().registerEvents(new ShulkerListener(), this);
		getServer().getPluginManager().registerEvents(new BackpackPlace(), this);
		getServer().getPluginManager().registerEvents(new BackpackDie(), this);
		getServer().getPluginManager().registerEvents(new BackpackRespawn(), this);
	}

	private void setupManagers(){
		backpackManager = new BackpackManager(new BackpackStorage());
	}

	private void setupFiles(){
		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}

	public boolean isCombatAThing(){
		return getServer().getPluginManager().getPlugin("JackpotMCCombat") != null;
	}
}
