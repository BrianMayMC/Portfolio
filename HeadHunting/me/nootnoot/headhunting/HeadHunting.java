package me.nootnoot.headhunting;

import lombok.Getter;
import me.nootnoot.headhunting.commands.BoosterCommand;
import me.nootnoot.headhunting.commands.placeSign;
import me.nootnoot.headhunting.commands.rankup;
import me.nootnoot.headhunting.commands.souls;
import me.nootnoot.headhunting.data.PlayerSQLLiteHandler;
import me.nootnoot.headhunting.listeners.*;
import me.nootnoot.headhunting.managers.BoosterManager;
import me.nootnoot.headhunting.managers.HHPlayerManager;
import me.nootnoot.headhunting.managers.HeadManager;
import me.nootnoot.headhunting.managers.LevelManager;
import me.nootnoot.headhunting.papi.PlaceholderAPIHook;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class HeadHunting extends JavaPlugin {

	@Getter
	private static HeadHunting instance;

	@Getter
	private Economy econ;

	@Getter
	private HeadManager headManager;
	@Getter
	private LevelManager levelManager;



	@Override
	public void onEnable() {
		// Plugin startup logic
		instance = this;
		getConfig().options().copyDefaults();
		saveDefaultConfig();


		if (!setupEconomy()) {
			Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		PlayerSQLLiteHandler.getInstance().setupDatabase();

		headManager = new HeadManager();
		levelManager = new LevelManager();

		getServer().getPluginManager().registerEvents(HHPlayerManager.getInstance(), this);

		getServer().getPluginManager().registerEvents(new KillEntityEvent(), this);
		getServer().getPluginManager().registerEvents(new SellHeadEvent(), this);
		getServer().getPluginManager().registerEvents(new JoinEvent(), this);
		getServer().getPluginManager().registerEvents(new SignClick(), this);
		getServer().getPluginManager().registerEvents(new EntitySoulEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerKillEvent(), this);
		getServer().getPluginManager().registerEvents(new PlayerHeadEvent(), this);
		getServer().getPluginManager().registerEvents(new BoosterClaimEvent(), this);

		getCommand("rankup").setExecutor(new rankup());
		getCommand("placesign").setExecutor(new placeSign());
		getCommand("soul").setExecutor(new souls());
		getCommand("boosters").setExecutor(new BoosterCommand());


		if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new PlaceholderAPIHook().register();

			getLogger().info(" - Successfully hooked into PlaceholderAPI!");
		}

	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		headManager.getHeads().clear();
		levelManager.getLevels().clear();
		HHPlayerManager.getInstance().getPlayers().clear();
		BoosterManager.GetInstance().getBoosters().clear();
		BoosterManager.GetInstance().getActiveboosters().clear();
		Bukkit.getScheduler().cancelAllTasks();
	}


	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) return false;

		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) return false;

		econ = rsp.getProvider();
		return true;
	}



}
