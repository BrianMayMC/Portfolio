package me.nootnoot.headhunting.tasks;

import me.nootnoot.headhunting.managers.BoosterManager;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BoosterTask extends BukkitRunnable {

	private final Player p;

	public BoosterTask(Player p){
		this.p = p;
	}
	@Override
	public void run() {
		BoosterManager.GetInstance().getActiveboosters().remove(p);
	}
}
