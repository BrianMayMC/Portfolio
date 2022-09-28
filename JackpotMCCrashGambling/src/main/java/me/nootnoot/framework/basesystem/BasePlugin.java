package me.nootnoot.framework.basesystem;

import lombok.Getter;
import me.nootnoot.framework.menusystem.MenuListener;
import me.nootnoot.framework.menusystem.MenuManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BasePlugin extends JavaPlugin {

	@Getter
	private MenuManager menuManager;

	@Override
	public void onEnable(){
		menuManager = new MenuManager();
		getServer().getPluginManager().registerEvents(new MenuListener(menuManager), this);

		getConfig().options().copyDefaults();
		saveDefaultConfig();
	}


}
