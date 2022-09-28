package me.nootnoot.luckyblockluckyblocks.tasks;

import me.nootnoot.luckyblockluckyblocks.entities.Luckyblock;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveTask extends BukkitRunnable {

	private final Luckyblock luckyblock;

	public RemoveTask(Luckyblock luckyblock) {
		this.luckyblock = luckyblock;
	}

	@Override
	public void run(){
		luckyblock.getHologram().remove();
	}

}
