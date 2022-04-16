package me.nootnoot.ventedmasks.listeners.tasks;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveHealthBoostTask extends BukkitRunnable {

	private final Player p;

	public RemoveHealthBoostTask(Player p){
		this.p = p;
	}


	@Override
	public void run() {
		if (p.hasPotionEffect(PotionEffectType.getByName("HEALTH_BOOST"))) {
			p.removePotionEffect(PotionEffectType.getByName("HEALTH_BOOST"));
		}
	}
}
