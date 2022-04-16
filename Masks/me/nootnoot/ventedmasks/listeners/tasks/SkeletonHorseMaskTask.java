package me.nootnoot.ventedmasks.listeners.tasks;

import me.nootnoot.ventedmasks.listeners.masks.SkeletonHorseMaskListener;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SkeletonHorseMaskTask extends BukkitRunnable {

	private final Player p;

	public SkeletonHorseMaskTask(Player p){
		this.p = p;
	}

	@Override
	public void run() {
		SkeletonHorseMaskListener.playersOnCooldown.remove(p.getUniqueId());
	}
}
