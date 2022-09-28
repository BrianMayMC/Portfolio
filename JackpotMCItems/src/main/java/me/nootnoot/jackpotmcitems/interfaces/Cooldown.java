package me.nootnoot.jackpotmcitems.interfaces;

import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public abstract class Cooldown {

	protected HashMap<String, Long> playersOnCooldown = new HashMap<>();



	public void runTask(Player p){
		new BukkitRunnable() {
			@Override
			public void run() {
				if (playersOnCooldown.containsKey(p.getName())) {
					playersOnCooldown.put(p.getName(), (playersOnCooldown.get(p.getName()) - 1));
					if (playersOnCooldown.get(p.getName()) == 0) {
						playersOnCooldown.remove(p.getName());
						p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("COOLDOWN_OVER")));
					}
				} else {
					cancel();
				}
			}
		}.runTaskTimer(JackpotMCItems.getInstance(), 20L, 20L);
	}





}
