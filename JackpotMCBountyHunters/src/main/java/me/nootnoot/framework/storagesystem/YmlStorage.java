package me.nootnoot.framework.storagesystem;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class YmlStorage {


	public static void saveInventory(Player p, int kitAmount, JavaPlugin instance, String path, String fileNameWithoutExtension) {
		try {
			File f = new File(instance.getDataFolder().getAbsolutePath() + path, fileNameWithoutExtension + ".yml");
			FileConfiguration c = YamlConfiguration.loadConfiguration(f);
			c.set("inventory." + kitAmount + ".armor", p.getInventory().getArmorContents());
			c.set("inventory." + kitAmount + ".content", p.getInventory().getContents());
			c.save(f);
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

}
