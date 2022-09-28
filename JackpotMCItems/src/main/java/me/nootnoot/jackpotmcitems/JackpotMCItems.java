package me.nootnoot.jackpotmcitems;

import lombok.Getter;
import me.nootnoot.framework.basesystem.BasePlugin;
import me.nootnoot.framework.registrysystem.Initializer;
import me.nootnoot.jackpotmcitems.commands.BlackMarketCommand;
import me.nootnoot.jackpotmcitems.commands.MainCommand;
import me.nootnoot.jackpotmcitems.config.BlackMarketMenuConfig;
import me.nootnoot.jackpotmcitems.config.MessagesFile;
import me.nootnoot.jackpotmcitems.managers.ItemManager;
import me.nootnoot.jackpotmcitems.interfaces.GlobalCooldown;
import me.nootnoot.jackpotmcitems.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;

public final class JackpotMCItems extends BasePlugin {

	@Getter private static JackpotMCItems instance;
	@Getter private MessagesFile messagesFile;
	@Getter private ItemManager itemManager;
	@Getter private BlackMarketMenuConfig blackMarketMenuConfig;

	@Getter private boolean useRecipes = false;
	@Getter private boolean glow = false;

	@Override
	public void onEnable() {
		super.onEnable();
		instance = this;

		setupFiles();
		setupManagers();
		setupCommands();
		setupListeners();

		new Initializer(this, "me.nootnoot.jackpotmcitems");
	}

	@Override
	public void onDisable() {
		getItemManager().getCustomItems().clear();
		Bukkit.getScheduler().cancelTasks(this);
		GlobalCooldown.getGlobalCooldown().clear();
	}

	public void setupFiles(){
		getConfig().options().copyDefaults();
		saveDefaultConfig();

		messagesFile = new MessagesFile();

		useRecipes = getConfig().getBoolean("use-recipes");
		glow = getConfig().getBoolean("glow");

		blackMarketMenuConfig = new BlackMarketMenuConfig();
	}

	public void setupManagers(){
		itemManager = new ItemManager();
	}

	public void setupCommands(){
		getCommand("customitems").setExecutor(new MainCommand());
		getCommand("blackmarket").setExecutor(new BlackMarketCommand());
	}

	public void setupListeners(){
		getServer().getPluginManager().registerEvents(new ZapStickListener(), this);
		getServer().getPluginManager().registerEvents(new BlacksmithListener(), this);
		getServer().getPluginManager().registerEvents(new StrengthRodListener(), this);
		getServer().getPluginManager().registerEvents(new CritsMultiplierListener(), this);
		getServer().getPluginManager().registerEvents(new ElytraDisablerListener(), this);
		getServer().getPluginManager().registerEvents(new LevitatorListener(), this);
		getServer().getPluginManager().registerEvents(new DeflectorListener(), this);
		getServer().getPluginManager().registerEvents(new RecipeLearnListener(), this);
		getServer().getPluginManager().registerEvents(new CraftListener(), this);
		getServer().getPluginManager().registerEvents(new CloudListener(), this);
	}

	public void reload() throws IOException, InvalidConfigurationException {
		this.reloadConfig();
		this.saveConfig();
		messagesFile.reload();
		itemManager.reload();
	}
}
