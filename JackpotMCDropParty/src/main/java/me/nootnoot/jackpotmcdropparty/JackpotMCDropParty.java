package me.nootnoot.jackpotmcdropparty;

import lombok.Getter;
import me.nootnoot.jackpotmcdropparty.commands.SpawnCommand;
import me.nootnoot.jackpotmcdropparty.listeners.ItemAddToMenuListener;
import me.nootnoot.jackpotmcdropparty.listeners.ItemsRemoveFromMenuListener;
import me.nootnoot.jackpotmcdropparty.managers.PartyItemManager;
import me.nootnoot.jackpotmcdropparty.storage.MenuStorage;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class JackpotMCDropParty extends JavaPlugin {

	@Getter private static JackpotMCDropParty instance;
	@Getter private PartyItemManager partyItemManager;
	@Getter private MenuStorage menuStorage;

	@Override
	public void onEnable() {
		// Plugin startup logic
		instance = this;

		setupFiles();
		setupCommands();
		setupManagers();
		setupListeners();
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		for(Location location : getPartyItemManager().getPlacedDropParties()){
			location.getBlock().setType(Material.AIR);
		}
		menuStorage.save();
	}

	public void setupFiles(){
		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}

	public void setupCommands(){
		getCommand("dropparty").setExecutor(new SpawnCommand());
	}

	public void setupManagers(){
		menuStorage = new MenuStorage();
		partyItemManager = new PartyItemManager();
	}
	public void setupListeners(){
		getServer().getPluginManager().registerEvents(new ItemAddToMenuListener(), this);
		getServer().getPluginManager().registerEvents(new ItemsRemoveFromMenuListener(), this);
	}

	public void reload(){
		reloadConfig();
		saveConfig();
	}
}
