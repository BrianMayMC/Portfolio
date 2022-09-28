package me.nootnoot.jackpotmcdropparty.tasks;

import me.nootnoot.jackpotmcdropparty.JackpotMCDropParty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveTask extends BukkitRunnable {

	private final Location location;
	public RemoveTask(Location location){
		this.location = location;
	}

	@Override
	public void run() {
		location.getBlock().setType(Material.AIR);
		JackpotMCDropParty.getInstance().getPartyItemManager().getPlacedDropParties().remove(location);
	}
}
