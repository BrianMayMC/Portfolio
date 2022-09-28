package me.nootnoot.luckyblockfactions;


import lombok.Getter;
import me.nootnoot.framework.basesystem.BasePlugin;
import me.nootnoot.framework.registrysystem.Initializer;
import me.nootnoot.luckyblockfactions.commands.FactionCommand;
import me.nootnoot.luckyblockfactions.entities.Faction;
import me.nootnoot.luckyblockfactions.entities.FactionClaim;
import me.nootnoot.luckyblockfactions.files.UpgradeMenuConfig;
import me.nootnoot.luckyblockfactions.managers.FactionManager;
import me.nootnoot.luckyblockfactions.placeholders.Placeholders;
import me.nootnoot.luckyblockfactions.storage.FactionStorage;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitRunnable;
import org.dynmap.DynmapAPI;
import org.dynmap.markers.MarkerSet;

import java.text.DecimalFormat;

public final class LuckyblockFactions extends BasePlugin {

	@Getter private static LuckyblockFactions instance;

	@Getter private FactionManager factionManager;
	@Getter private FactionStorage factionStorage;

	@Getter private UpgradeMenuConfig upgradeMenuConfig;

	@Getter private Economy econ;

	@Getter private DynmapAPI api;
	@Getter private MarkerSet marker;

	@Override
	public void onEnable() {
		instance = this;
		super.onEnable();
		new Initializer(this, "me.nootnoot.luckyblockfactions");

		if (!setupEconomy()) {
			Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		api = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("dynmap");
		if(api.getMarkerAPI().getMarkerSet("factions") == null){
			marker = api.getMarkerAPI().createMarkerSet("factions", "Factions", api.getMarkerAPI().getMarkerIcons(), false);
		}else{
			marker = api.getMarkerAPI().getMarkerSet("factions");
		}

		setupStorage();
		setupFiles();
		setupManagers();
		setupCommands();

		new Placeholders().register();

		new BukkitRunnable(){
			@Override
			public void run() {
				for(Faction f : factionManager.getFactions()) {
					if (LuckyblockFactions.getInstance().getFactionManager().getFrozenFactions().containsKey(f)) return;
					if (f.getPlayersOnline() != 0) {
						if (f.getPower() < f.getMaxPower()) {
							double increase = LuckyblockFactions.getInstance().getConfig().getDouble("power-regen-amount");
							f.setPower(Math.min(Double.parseDouble(new DecimalFormat("#.#").format(f.getPower() + increase)), f.getMaxPower()));
						}
					}
				}
			}
		}.runTaskTimer(LuckyblockFactions.getInstance(), LuckyblockFactions.getInstance().getConfig().getLong("power-regen-time") * 100L,
				LuckyblockFactions.getInstance().getConfig().getLong("power-regen-time") * 100L);
		TextComponent a = new TextComponent();
	}

	@Override
	public void onDisable(){
		for(Faction f : factionManager.getFactions()){
			for(FactionClaim fc : f.getAreas()){
				if(fc.getMarker() != null){
					fc.getMarker().deleteMarker();
				}
			}
		}
		factionStorage.save();
		factionManager.clear();
	}

	public void setupFiles(){
		upgradeMenuConfig = new UpgradeMenuConfig();
	}

	public void setupManagers(){
		factionManager = new FactionManager(factionStorage);
	}

	public void setupCommands(){
		getCommand("faction").setExecutor(new FactionCommand());
	}

	public void setupStorage(){
		factionStorage = new FactionStorage();
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
