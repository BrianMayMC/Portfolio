package me.nootnoot.jackpotmccombat;

import lombok.Getter;
import me.nootnoot.jackpotmccombat.commands.GraceDisableCommand;
import me.nootnoot.jackpotmccombat.listeners.*;
import me.nootnoot.jackpotmccombat.listeners.blocked.BlockItemsListener;
import me.nootnoot.jackpotmccombat.listeners.blocked.HeightLimitListener;
import me.nootnoot.jackpotmccombat.listeners.blocked.ListContainer;
import me.nootnoot.jackpotmccombat.listeners.grace.GracePickupListener;
import me.nootnoot.jackpotmccombat.listeners.messages.MessageListener;
import me.nootnoot.jackpotmccombat.managers.CombatPlayerManager;
import me.nootnoot.jackpotmccombat.tasks.CombatTask;
import me.nootnoot.jackpotmccombat.tasks.GraceTask;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class JackpotMCCombat extends JavaPlugin{

	@Getter private CombatPlayerManager combatPlayerManager;
	@Getter private static JackpotMCCombat instance;

	@Getter private ListContainer listContainer;

	@Getter private boolean grace;
	@Getter private boolean pearl;
	@Getter private boolean blocked;

	@Getter private Economy econ;

	@Override
	public void onEnable() {
		// Plugin startup logic
		instance = this;

		setupFiles();
		setupManagers();
		setupCommands();

		if (!setupEconomy() ) {
			Bukkit.getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		grace = getConfig().getBoolean("grace-enabled");
		pearl = getConfig().getBoolean("pearl-cooldown-enabled");
		blocked = getConfig().getBoolean("luckyblock-blocked-stuff");
		new CombatTask().runTaskTimer(this, 20L, 20L);
		if(grace) {
			new GraceTask().runTaskTimer(this, 20L, 20L);
		}
		setupListeners();

		listContainer = new ListContainer();
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		Bukkit.getScheduler().cancelTasks(this);
		combatPlayerManager.getCombatPlayers().clear();
		combatPlayerManager.getPlayersOnGrace().clear();
		combatPlayerManager.getStoredBlockChanges().clear();
		combatPlayerManager = null;

		listContainer.getTotemPlayers().clear();
		listContainer.getArrowPlayers().clear();
	}



	public void setupFiles(){
		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}

	public void setupManagers(){
		combatPlayerManager = new CombatPlayerManager();
	}

	public void setupListeners(){
		getServer().getPluginManager().registerEvents(new CombatTagListener(), this);
		getServer().getPluginManager().registerEvents(new CommandBlockerListener(), this);
		getServer().getPluginManager().registerEvents(new ElytraListener(), this);
		getServer().getPluginManager().registerEvents(new EnderpearlListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerKillListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), this);
		getServer().getPluginManager().registerEvents(new PotionSplashListener(), this);
		getServer().getPluginManager().registerEvents(new RiptideListener(), this);
		getServer().getPluginManager().registerEvents(new ServerCrashListener(), this);
		getServer().getPluginManager().registerEvents(new PvpWallListener(), this);
		getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
		getServer().getPluginManager().registerEvents(new MessageListener(), this);
		getServer().getPluginManager().registerEvents(new GracePickupListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);

		if(blocked) {
			getServer().getPluginManager().registerEvents(new BlockItemsListener(), this);
			getServer().getPluginManager().registerEvents(new HeightLimitListener(), this);
		}
	}

	public void setupCommands(){
		getCommand("pvp").setExecutor(new GraceDisableCommand());
	}

	public boolean checkNootTeams(){
		return getServer().getPluginManager().getPlugin("JackpotMCTeams") != null;
	}

	public boolean checkFaction(){
		return getServer().getPluginManager().getPlugin("LuckyblockFactions") != null;
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
