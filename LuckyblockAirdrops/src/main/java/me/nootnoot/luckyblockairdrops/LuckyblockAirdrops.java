package me.nootnoot.luckyblockairdrops;

import lombok.Getter;
import me.nootnoot.luckyblockairdrops.commands.AirdropCommand;
import me.nootnoot.luckyblockairdrops.commands.AirdropItemsCommand;
import me.nootnoot.luckyblockairdrops.entities.Airdrop;
import me.nootnoot.luckyblockairdrops.listeners.AirdropListeners;
import me.nootnoot.luckyblockairdrops.managers.AirdropManager;
import me.nootnoot.luckyblockairdrops.menus.CommonMenu;
import me.nootnoot.luckyblockairdrops.menus.RareMenu;
import me.nootnoot.luckyblockairdrops.menus.UncommonMenu;
import me.nootnoot.luckyblockairdrops.menus.menusystem.MenuManager;
import me.nootnoot.luckyblockairdrops.storage.ItemStorage;
import me.nootnoot.luckyblockairdrops.tasks.RemoveTask;
import me.nootnoot.luckyblockairdrops.tasks.SpawnTask;
import me.nootnoot.luckyblockairdrops.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.AreaMarker;
import org.dynmap.markers.MarkerSet;

public final class LuckyblockAirdrops extends JavaPlugin {

	@Getter
	private static LuckyblockAirdrops instance;
	@Getter
	private MenuManager menuManager;
	@Getter
	private AirdropManager airdropManager;
	@Getter
	private DynmapAPI api;
	@Getter
	private MarkerSet marker;

	@Override
	public void onEnable() {
		// Plugin startup logic
		instance = this;
		api = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
		if(api.getMarkerAPI().getMarkerSet("airdrops") == null){
			marker = api.getMarkerAPI().createMarkerSet("airdrops", "Airdrops", api.getMarkerAPI().getMarkerIcons(), false);
		}else{
			marker = api.getMarkerAPI().getMarkerSet("airdrops");
		}
		setupFiles();
		setupManagers();
		setupCommands();
		setupListeners();

		new BukkitRunnable(){
			@Override
			public void run(){
				getServer().broadcastMessage(Utils.c("&6&l(!)&e Airdrops have now spawned around the earth. Go to https://maps.luckyblock.gg to view their location!"));
				(new SpawnTask(getConfig().getInt("amount"))).run();
			}
		}.runTaskTimer(this, getConfig().getLong("spawn-time") * 20L * 60L, getConfig().getLong("spawn-time") * 20L * 60L);
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		for(AreaMarker marker : airdropManager.getMarkers()){
			marker.deleteMarker();
		}
		for(Airdrop a : airdropManager.getAirdrops()){
			new RemoveTask(a).run();
		}
		airdropManager.getMarkers().clear();
		airdropManager.getAirdrops().clear();
	}

	private void setupFiles(){
		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}

	private void setupManagers(){
		menuManager = new MenuManager();
		airdropManager = new AirdropManager(new ItemStorage());
	}

	private void setupCommands(){
		getCommand("airdrop").setExecutor(new AirdropCommand());
		getCommand("airdropitems").setExecutor(new AirdropItemsCommand());
	}

	private void setupListeners(){
		getServer().getPluginManager().registerEvents(new CommonMenu(), this);
		getServer().getPluginManager().registerEvents(new UncommonMenu(), this);
		getServer().getPluginManager().registerEvents(new RareMenu(), this);
		getServer().getPluginManager().registerEvents(new AirdropListeners(), this);
	}
}
