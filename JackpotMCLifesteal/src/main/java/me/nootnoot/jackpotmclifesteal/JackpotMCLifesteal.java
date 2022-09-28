package me.nootnoot.jackpotmclifesteal;

import jdk.jfr.Enabled;
import lombok.Getter;
import me.nootnoot.framework.basesystem.BasePlugin;
import me.nootnoot.framework.registrysystem.Initializer;
import me.nootnoot.jackpotmclifesteal.commands.HeartsCommand;
import me.nootnoot.jackpotmclifesteal.placeholders.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class JackpotMCLifesteal extends BasePlugin {

	@Getter
	private static JackpotMCLifesteal instance;

	@Getter private final List<Player> playersBanning = new ArrayList<>();

	@Override
	public void onEnable() {
		// Plugin startup logic
		super.onEnable();
		instance = this;

		setupFiles();
		setupCommands();
		new Initializer(this, "me.nootnoot.jackpotmclifesteal");
		new Placeholders().register();


	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		playersBanning.clear();
	}

	public void setupFiles(){
		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}

	public void setupCommands(){
		getCommand("hearts").setExecutor(new HeartsCommand());
	}
}
