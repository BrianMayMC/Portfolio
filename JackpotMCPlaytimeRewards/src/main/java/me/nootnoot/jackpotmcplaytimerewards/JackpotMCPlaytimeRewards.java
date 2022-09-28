package me.nootnoot.jackpotmcplaytimerewards;

import lombok.Getter;
import lombok.Setter;
import me.nootnoot.framework.basesystem.BasePlugin;
import me.nootnoot.framework.registrysystem.Initializer;
import me.nootnoot.jackpotmcplaytimerewards.commands.PlaytimeRewardsCommand;
import me.nootnoot.jackpotmcplaytimerewards.files.LevelConfig;
import me.nootnoot.jackpotmcplaytimerewards.files.LevelMenuConfig;
import me.nootnoot.jackpotmcplaytimerewards.managers.LevelManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public final class JackpotMCPlaytimeRewards extends BasePlugin {

	@Getter private static JackpotMCPlaytimeRewards instance;
	@Getter private LevelManager levelManager;
	@Getter private LevelConfig levelConfig;
	@Getter private LevelMenuConfig levelMenuConfig;

	@Getter @Setter private int page = 1;

	@Override
	public void onEnable() {
		// Plugin startup logic
		super.onEnable();
		instance = this;

		levelConfig = new LevelConfig();
		levelMenuConfig = new LevelMenuConfig();


		levelManager = new LevelManager();

		for(Player p : Bukkit.getOnlinePlayers()){
			levelManager.getPlaytimePlayers().add(p);
		}

		new Initializer(this, "me.nootnoot.jackpotmcplaytimerewards");
		getCommand("playtimerewards").setExecutor(new PlaytimeRewardsCommand());
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
		levelManager.getLevels().clear();
		levelManager.getPlaytimePlayers().clear();
	}
}
