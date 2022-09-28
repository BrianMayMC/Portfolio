package me.nootnoot.luckyblockoreregen;

import lombok.Getter;
import me.nootnoot.luckyblockoreregen.commands.MainCommand;
import me.nootnoot.luckyblockoreregen.listeners.MineEvent;
import me.nootnoot.luckyblockoreregen.listeners.PlaceEvent;
import me.nootnoot.luckyblockoreregen.managers.RegenBlockManager;
import me.nootnoot.luckyblockoreregen.storage.RegenBlockStorage;
import me.nootnoot.luckyblockoreregen.tasks.RespawnTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class LuckyBlockOreRegen extends JavaPlugin {

	@Getter
	private static LuckyBlockOreRegen instance;
	@Getter
	private RegenBlockManager regenBlockManager;
	@Getter
	private RegenBlockStorage regenBlockStorage;


	@Override
	public void onEnable() {
		// Plugin startup logic
		instance = this;
		SetupFiles();
		SetupManagers();
		SetupListeners();
		SetupCommands();

		try {
			regenBlockStorage.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		long cooldown = getConfig().getLong("respawn-cooldown") * 20L;

		new RespawnTask().runTaskTimer(LuckyBlockOreRegen.getInstance(), cooldown, cooldown);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		regenBlockStorage.save();
		regenBlockManager.RestoreBlocks();
	}

	public void SetupFiles(){
		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}

	public void SetupManagers(){
		regenBlockStorage = new RegenBlockStorage();
		regenBlockManager = new RegenBlockManager();
	}


	public void SetupListeners(){
		getServer().getPluginManager().registerEvents(new PlaceEvent(), this);
		getServer().getPluginManager().registerEvents(new MineEvent(), this);
	}
	public void SetupCommands(){
		getCommand("customblock").setExecutor(new MainCommand());
	}
}
