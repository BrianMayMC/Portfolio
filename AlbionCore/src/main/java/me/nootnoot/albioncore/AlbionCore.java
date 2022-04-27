package me.nootnoot.albioncore;

import lombok.Getter;
import me.nootnoot.albioncore.BLL.artifactsystem.managers.AbilityManager;
import me.nootnoot.albioncore.BLL.artifactsystem.managers.ArtifactManager;
import me.nootnoot.albioncore.BLL.artifactsystem.managers.BoosterManager;
import me.nootnoot.albioncore.BLL.artifactsystem.menus.listeners.ArtifactHandler;
import me.nootnoot.albioncore.BLL.configsystem.EcolevelsFile;
import me.nootnoot.albioncore.BLL.configsystem.ItemsFile;
import me.nootnoot.albioncore.BLL.configsystem.MessagesFile;
import me.nootnoot.albioncore.BLL.ecosystem.managers.CactusManager;
import me.nootnoot.albioncore.BLL.ecosystem.managers.FarmingManager;
import me.nootnoot.albioncore.BLL.ecosystem.managers.MiningManager;
import me.nootnoot.albioncore.BLL.ecosystem.managers.WoodcuttingManager;
import me.nootnoot.albioncore.BLL.playersystem.managers.ABPlayerManager;
import me.nootnoot.albioncore.BLL.playersystem.playermenusystem.listeners.ClickEvent;
import me.nootnoot.albioncore.BLL.playersystem.playermenusystem.listeners.DeathEvent;
import me.nootnoot.albioncore.BLL.playersystem.playermenusystem.listeners.InventoryMoveEvent;
import me.nootnoot.albioncore.BLL.playersystem.playermenusystem.listeners.JoinEvent;
import me.nootnoot.albioncore.PL.commands.TestArtifactPouch;
import me.nootnoot.albioncore.PL.commands.TestSpawn;
import me.nootnoot.albioncore.PL.commands.TestSpell;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class AlbionCore extends JavaPlugin {
	@Getter
	private static AlbionCore instance;
	@Getter
	private MessagesFile messagesConfig;
	@Getter
	private ItemsFile itemsFile;
	@Getter
	private EcolevelsFile ecolevelsFile;

	@Getter
	private ArtifactManager artifactManager;
	@Getter
	private AbilityManager abilityManager;
	@Getter
	private BoosterManager boosterManager;
	@Getter
	private CactusManager cactusManager;
	@Getter
	private FarmingManager farmingManager;
	@Getter
	private MiningManager miningManager;
	@Getter
	private WoodcuttingManager woodcuttingManager;
	@Getter
	private ABPlayerManager abPlayerManager;

	@Override
	public void onEnable() {
		// Plugin startup logic
		instance = this;
		InitializeFiles();
		InitializeManagers();

		RegisterCommands();
		RegisterListeners();

	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		artifactManager.RemoveAllArtifacts();
	}

	private void InitializeManagers(){
		artifactManager = new ArtifactManager();
		abilityManager = new AbilityManager();
		boosterManager = new BoosterManager();

		cactusManager = new CactusManager();
		farmingManager = new FarmingManager();
		miningManager = new MiningManager();
		woodcuttingManager = new WoodcuttingManager();

		abPlayerManager = new ABPlayerManager();
	}

	private void InitializeFiles(){
		getConfig().options().copyDefaults();
		saveDefaultConfig();

		messagesConfig = new MessagesFile();
		itemsFile = new ItemsFile();
		ecolevelsFile = new EcolevelsFile();
	}

	private void RegisterCommands(){
		getCommand("test").setExecutor(new TestSpawn());
		getCommand("testspell").setExecutor(new TestSpell());
		getCommand("testpouch").setExecutor(new TestArtifactPouch());
	}

	private void RegisterListeners(){
		Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
		Bukkit.getPluginManager().registerEvents(new ArtifactHandler(), this);
		Bukkit.getPluginManager().registerEvents(new InventoryMoveEvent(), this);
		Bukkit.getPluginManager().registerEvents(new ClickEvent(), this);
		Bukkit.getPluginManager().registerEvents(new DeathEvent(), this);
	}

}
