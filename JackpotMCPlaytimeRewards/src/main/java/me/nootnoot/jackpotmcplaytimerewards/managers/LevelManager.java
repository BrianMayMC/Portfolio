package me.nootnoot.jackpotmcplaytimerewards.managers;

import lombok.Getter;
import me.nootnoot.framework.utils.Utils;
import me.nootnoot.jackpotmcplaytimerewards.JackpotMCPlaytimeRewards;
import me.nootnoot.jackpotmcplaytimerewards.entities.Level;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class LevelManager {

	@Getter private final List<Level> levels = new ArrayList<>();

	@Getter private final List<Player> playtimePlayers = new ArrayList<>();

	public LevelManager() {
		load();
		new BukkitRunnable(){
			@Override
			public void run(){
				for(Player p : playtimePlayers){
					p.getPersistentDataContainer().set(new NamespacedKey(JackpotMCPlaytimeRewards.getInstance(), "playtime"), PersistentDataType.LONG,
							p.getPersistentDataContainer().getOrDefault(new NamespacedKey(JackpotMCPlaytimeRewards.getInstance(), "playtime"), PersistentDataType.LONG, 0L) + 1);
				}
			}
		}.runTaskTimer(JackpotMCPlaytimeRewards.getInstance(), 20L, 20L);
	}

	public void load(){
		final FileConfiguration config = JackpotMCPlaytimeRewards.getInstance().getLevelConfig().getConfig();
		for(String path : config.getConfigurationSection("levels.").getKeys(false)){

			ItemStack item = Utils.getSkull(config.getString("levels." + path + ".items.locked.url"), 1, false);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(Utils.c(config.getString("levels." + path + ".items.locked.name").replace("%level%", String.valueOf(path))));
			meta.setLore(Utils.cL(config.getStringList("levels." + path + ".items.locked.lore")));
			item.setItemMeta(meta);

			ItemStack claimed = Utils.getSkull(config.getString("levels." + path + ".items.claimed.url"), 1, false);
			ItemMeta cMeta = claimed.getItemMeta();
			cMeta.setDisplayName(Utils.c(config.getString("levels." + path + ".items.claimed.name").replace("%level%", String.valueOf(path))));
			cMeta.setLore(Utils.cL(config.getStringList("levels." + path + ".items.claimed.lore")));
			claimed.setItemMeta(cMeta);

			levels.add(new Level(
					Integer.parseInt(path),
					config.getLong("levels." + path + ".requirement"),
					config.getInt("levels." + path + ".menuslot"),
					config.getStringList("levels." + path + ".commands"),
					claimed,
					item
			));
		}
	}


	public void claimLevel(Level level, Player p){
		for(String s : level.getRewards()){
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("%player%", p.getName()));
		}
		p.sendMessage(Utils.c(JackpotMCPlaytimeRewards.getInstance().getConfig().getString("messages.claim-message")));
		p.getPersistentDataContainer().set(new NamespacedKey(JackpotMCPlaytimeRewards.getInstance(), "level"), PersistentDataType.INTEGER,
				p.getPersistentDataContainer().getOrDefault(new NamespacedKey(JackpotMCPlaytimeRewards.getInstance(), "level"), PersistentDataType.INTEGER, 0) + 1);
		p.playSound(p.getLocation(), Sound.valueOf(JackpotMCPlaytimeRewards.getInstance().getConfig().getString("sounds.claim-sound")), 1F, 1F);
	}
}
