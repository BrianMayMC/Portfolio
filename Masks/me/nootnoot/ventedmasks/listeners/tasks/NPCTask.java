package me.nootnoot.ventedmasks.listeners.tasks;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class NPCTask extends BukkitRunnable {

	private final Player p;
	private final int slot;
	private final ItemStack item;
	public NPCTask(Player p, int slot, ItemStack item){
		this.p = p;
		this.slot = slot;
		this.item = item;
	}
	@Override
	public void run() {
		p.getInventory().setItem(slot, item);
	}
}
