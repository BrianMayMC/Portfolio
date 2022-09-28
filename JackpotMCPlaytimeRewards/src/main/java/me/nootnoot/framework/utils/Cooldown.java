package me.nootnoot.framework.utils;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public abstract class Cooldown {

	protected HashMap<String, Long> playersOnCooldown = new HashMap<>();

	public void runTask(Player p, JavaPlugin instance, String messagePath){
		new BukkitRunnable() {
			@Override
			public void run() {
				if (playersOnCooldown.containsKey(p.getName())) {
					playersOnCooldown.put(p.getName(), (playersOnCooldown.get(p.getName()) - 1));
					if (playersOnCooldown.get(p.getName()) == 0) {
						playersOnCooldown.remove(p.getName());
						p.sendMessage(Utils.c(instance.getConfig().getString(instance.getConfig().getString(messagePath))));
					}
				} else {
					cancel();
				}
			}
		}.runTaskTimer(instance, 20L, 20L);
	}





}
