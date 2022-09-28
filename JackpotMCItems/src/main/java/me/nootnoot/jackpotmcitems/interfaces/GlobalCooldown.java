package me.nootnoot.jackpotmcitems.interfaces;

import lombok.Getter;
import me.nootnoot.jackpotmcitems.JackpotMCItems;
import me.nootnoot.jackpotmcitems.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class GlobalCooldown {

	@Getter
	private static final HashMap<String, Long> globalCooldown = new HashMap<>();

	public static void runGlobalTask(Player p){
		new BukkitRunnable() {
			@Override
			public void run() {
				if (globalCooldown.containsKey(p.getName())) {
					globalCooldown.put(p.getName(), (globalCooldown.get(p.getName()) - 1));
					if (globalCooldown.get(p.getName()) == 0) {
						globalCooldown.remove(p.getName());
						p.sendMessage(Utils.c(JackpotMCItems.getInstance().getMessagesFile().getConfig().getString("COOLDOWN_OVER")));
					}
				} else {
					cancel();
				}
			}
		}.runTaskTimer(JackpotMCItems.getInstance(), 20L, 20L);
	}
}
